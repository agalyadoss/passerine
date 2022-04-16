package com.heaerie.passerine.service;

import com.heaerie.passerine.cube.PAS001MTMapper;
import com.heaerie.passerine.pojo.PAS001MT;
import com.heaerie.sqlite.PrimaryKeyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;


public class SearchTamil {
    static final Logger logger = LogManager.getLogger();
    private static final String USER_AGENT = "passerinebot/1.0";
    private static final int PASSERINE_FORK_JOIN_POOL = 16;
    static volatile private int i=0;
    static volatile private int j=0;
    static volatile private int k=0;
    static volatile private int l=0;
    static Base64.Encoder encoder = Base64.getUrlEncoder();


    static  {
        System.setProperty("BasePath",getBasePath() );

        System.setProperty("log4j.configurationFile", getLogPath());
    }

    private static String getBasePath() {
        if (System.getProperty("os.name").startsWith("Windows")) {

            return  "C:\\heaerie\\passerine\\conf\\";
        } else {
            return  "/etc/passerine/conf/";
        }
    }

    private static String getLogPath() {
        System.out.println("Os.Name=" + System.getProperty("os.name"));
        if (System.getProperty("os.name").startsWith("Windows")) {

           return  "C:\\heaerie\\passerine\\conf\\passerine-log4j.xml";
        } else {
           return  "/etc/passerine/conf/passerine-log4j.xml";
        }
    }

    private static synchronized String getNextIp() throws ExceedIPRange {
        if (l==255) {
            l=0;
            k++;
        }
        if (k == 255) {
            k=0;
            j++;
        }
        if (j == 255) {
            j=0;
            j++;
        }
        if (i == 255) {
            throw new ExceedIPRange(i +"." + j +"." + k + "." + l);
        }
        l++;
         return  i + "." + j + "." + k + "." + l;

    }

    private static synchronized String getNextIp(int a, int b, int c, int d) throws ExceedIPRange {

        return  a + "." + b + "." + c + "." + d;

    }

    static String printStringArr(String arvg[]) {

        int i=0;
        StringBuffer sb = new StringBuffer( "length=" + arvg.length + "[");
        for (String s : arvg) {
            sb.append(i);
            sb.append("=");
            sb.append(s);
            if (i < arvg.length) {
                sb.append(",");
            }
            i++;
        }
        sb.append("]");
        return  sb.toString();
    }

    public static void main(String argv []) throws SQLException {
        System.out.println("Log File" + getLogPath());
        System.out.println("System Log4j=" +  System.getProperty("log4j.configurationFile"));
        logger.error("info {}", "info");
        CubeFactory cbeFactory=CubeFactory.getInstance();
        //processByRange("1.186.0.0", "1.186.255.255");
        System.out.println("passerine argv=" + printStringArr(argv));
            if (argv.length != 2) {
                System.out.println("passerine execute with start and end ips");
                System.exit(1);
            }
            processByRange(argv[0], argv[1]);
    }

           private static void processByRange(String stratIp, String endIp) {
               System.out.println("Started..stratIp=" +stratIp  + ",endIp=" + endIp);
               long total=0;
               long totalDuration=0;
               LocalDate startTime = LocalDate.now();

              String [] startList= stratIp.split("\\.");
              String [] endList = endIp.split("\\.");

              int startA = Integer.parseInt(startList[0]);
              int startB = Integer.parseInt(startList[1]);
              int startC = Integer.parseInt(startList[2]);
              int startD = Integer.parseInt(startList[3]);


               int endA = Integer.parseInt(endList[0]);
               int endB = Integer.parseInt(endList[1]);
               int endC = Integer.parseInt(endList[2]);
               int endD = Integer.parseInt(endList[3]);
               for (int a =startA; a <= endA;a++) {
                   for (int b = startB; b <= endB; b++) {
                       List<String> ips = new ArrayList<>();
                       for (int c = startC; c <= endC; c++) {
                           for (int d = startD; d <= endD; d++) {

                               try {
                                   ips.add(getNextIp(a, b, c, d));
                                   //ips.add(getNextIp());
                               } catch (ExceedIPRange e) {
                                   LocalDate endTime = LocalDate.now();
                                   System.out.println("start=" + startTime + ", endTime=" + endTime);
                                   e.printStackTrace();
                                   System.exit(1);
                               }

                           }
                       }
                       LocalDateTime batchStartTime = LocalDateTime.now();
                       ForkJoinPool pool = new ForkJoinPool(PASSERINE_FORK_JOIN_POOL);

                       try {
                           pool.submit(()->{
                               ips.parallelStream().forEach(ip4s->{
                                   // System.out.println("ip4s=" + ip4s);

                                   try {
                                       processIp(ip4s);
                                   } catch (PrimaryKeyException e) {
                                       e.printStackTrace();
                                   } catch (InvocationTargetException e) {
                                       e.printStackTrace();
                                   } catch (NoSuchMethodException e) {
                                       e.printStackTrace();
                                   } catch (IllegalAccessException e) {
                                       e.printStackTrace();
                                   }


                               });
                           }).get();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       } catch (ExecutionException e) {
                           e.printStackTrace();
                       }
                       total = total + ips.size() ;
                       LocalDateTime batchEndTime = LocalDateTime.now();
                       totalDuration = Duration.between(startTime, batchEndTime).getNano();
                       //System.out.println("batch start=" + batchStartTime + ", batchEndTime=" + batchEndTime +", Count=" + ips.size()  + ", Duration=" + Duration.between(batchStartTime, batchEndTime) + ", TPS=" + (Duration.between(batchStartTime,batchEndTime).getSeconds()/ ips.size()) );
                       if (ips.size() > 0) {
                           System.out.println("batch total=" + total + ", totalDuration=" + totalDuration + ", start=" + batchStartTime + ", batchEndTime=" + batchEndTime + ", Count=" + ips.size() + ", Duration=" + Duration.between(batchStartTime, batchEndTime).getNano() + ", TPS=" + (Duration.between(batchStartTime, batchEndTime).getNano() / ips.size()));
                       }


                   }
               }



           }



    private static void processIp(String ip4s) throws  PrimaryKeyException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String timeout="";
        LocalDateTime startDateTime = LocalDateTime.now();

        String rdns="";
         try {
            URL url = new URL("https://" + ip4s + ":443/");
            InetAddress ia = InetAddress.getByName(ip4s);
            rdns =ia.getCanonicalHostName();
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

             HostnameVerifier hostNameVerifier=new HostnameVerifier() {
                 public boolean verify( final String arg0,    final SSLSession arg1) {
                     logger.error("arg0={}, arg1={}", arg0, arg1);
                     if (arg1 != null) {
                         logger.error("getCipherSuite={}", arg1.getCipherSuite());
                         try {
                             logger.error("ia={},getPeerPrincipal.getName={}, subjectAltName={}", ia, arg1.getPeerPrincipal().getName(), arg1.getPeerPrincipal(), arg1.getValue("subjectAltName"));
                         } catch (SSLPeerUnverifiedException e) {
                             e.printStackTrace();
                         }
                     }

                     return true;
                 }
             };
            con.setHostnameVerifier(hostNameVerifier);
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            Map<String, String> parameters = new HashMap<>();
            parameters.put("param1", "val");
            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            out.flush();
            out.close();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setInstanceFollowRedirects(false);
            int status = con.getResponseCode();
            Reader streamReader = null;

            if (status != 200) {
                if (con.getErrorStream() != null) {
                    streamReader = new InputStreamReader(con.getErrorStream());
                } if (con.getInputStream() != null) {
                    streamReader = new InputStreamReader(con.getInputStream());
                }
            } else {
                streamReader = new InputStreamReader(con.getInputStream());
            }

            BufferedReader br = new BufferedReader(streamReader);
            StringBuffer content = new StringBuffer();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                content.append(inputLine);
            }
            br.close();
             LocalDateTime endDateTime = LocalDateTime.now();
             PAS001MTMapper pas001MTMapper= CubeFactory.getPAS001MTMapper();
             PAS001MT pas001mt = new PAS001MT();
             pas001mt.setIp(ip4s);
             pas001mt.setContent(new String(encoder.encode(content.toString().getBytes(StandardCharsets.UTF_8))));
             pas001mt.setRespCode(status);
             pas001mt.setScanStartTime(startDateTime.toString());
             pas001mt.setScanEndTime(endDateTime.toString());
             pas001mt.setRdns(rdns);

             pas001MTMapper.save(pas001mt);
        } catch (IOException e) {
             timeout = e.getMessage();
             LocalDateTime endDateTime = LocalDateTime.now();
             PAS001MTMapper pas001MTMapper= CubeFactory.getPAS001MTMapper();
             PAS001MT pas001mt = new PAS001MT();
             pas001mt.setIp(ip4s);
             pas001mt.setRespCode(0);
             pas001mt.setTimeout(timeout);
             pas001mt.setScanStartTime(startDateTime.toString());
             pas001mt.setScanEndTime(endDateTime.toString());
             pas001mt.setRdns(rdns);
             pas001MTMapper.save(pas001mt);
        }

    }

    public static class ParameterStringBuilder {
        public static String getParamsString(Map<String, String> params)
                throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;
        }
    }

}
