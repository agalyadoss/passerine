package com.heaerie.pillarsync;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

import com.heaerie.common.MailReq;
import com.heaerie.common.PillarPropService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GpaHttpClient implements X509TrustManager, HostnameVerifier {

    private Set<X509Certificate> serverTrusted = new HashSet<>();
    private Map<X509Certificate, Long> clientTrusted;
    private  final String PROTOCOL;
    private  final String HOST;
    private   final String PORT;
    private  final String BASE ;
    final Logger logger = LogManager.getLogger();
    static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            //.excludeFieldsWithoutExposeAnnotation()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .setPrettyPrinting()
            .create();
    private String token ;
    private String key = PillarPropService.get("PILLAR_X_AUTH_JWT", "X-AUTH-JWT");

    private static final String USER_AGENT = "PillarSync/1.0";
    private int MaxRetryCount= PillarPropService.getNumber("MAX_RETRY_COUNT", 3);

    public GpaHttpClient(String protocol, String host, String port, String base)  {
        this.PROTOCOL = protocol;
        this.HOST = host;
        this.PORT = port;
        this.BASE = base;

        try {
            SSLContext sslContext= SSLContext.getInstance("TLSv1.2");
            KeyManager[] km = getKeyManager("/etc/heaerie/pillar/conf/pillar.jks", "1qaz2wsx");
            TrustManager[] tm = new TrustManager[] {this};
            sslContext.init(km, tm, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
            logger.error(e);
            throw  new RuntimeException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

    }

    public  static KeyManager[] getKeyManager(String jksPath, String pass) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, UnrecoverableKeyException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        FileInputStream fin = new FileInputStream(jksPath);
        KeyStore keyStore  = KeyStore.getInstance("JKS");
        keyStore.load(fin, pass.toCharArray());
        keyManagerFactory.init(keyStore, pass.toCharArray());
        return  keyManagerFactory.getKeyManagers();
    }


    public String getUrl() {
        if (BASE !=null && ! BASE.isEmpty()) {
            return  PROTOCOL + "://" + HOST + ":" + PORT + "/" + BASE ;
        } else {
            return  PROTOCOL + "://" + HOST + ":" + PORT ;
        }
    }

    public MailReq getMailDetails(String emailId) throws Exception {
       GpaHttpResponse response= callGET(getUrl() + "/mail/" + emailId + "/details", token);
       MailReq mailReq = gson.fromJson(response.getBody(), MailReq.class);
       return  mailReq;
    }


    GpaHttpResponse callGET(String url, String token) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        if (token != null) {
            con.setRequestProperty(key, token);
        }
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("URL = " + url + " GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return  new GpaHttpResponse(con.getHeaderFields(), responseCode, response.toString());
        } else {
            throw new Exception("Internal Server");
        }
    }



    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        logger.error("x509Certificates={}, s={}",x509Certificates, s);
    }

    public void checkServerTrusted(X509Certificate[] x509Certificates, String crypto) throws CertificateException {
        logger.error("x509Certificates={}, s={}",x509Certificates, crypto);
        if (x509Certificates != null) {
            for (X509Certificate item : x509Certificates) {
                logger.info("Trusting Cert={}, crypto={}", item.getSubjectDN(), crypto);
                serverTrusted.add(item);

            }
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    @Override
    public boolean verify(String s, SSLSession sslSession) {
        logger.info("s={}, sslSession={}", s, sslSession);
        if (s == null) {
            return  false;

        }
        return true;
    }

    public GpaHttpResponse callPut(String url, String toJson, String token) throws IOException {
        URL obj = new URL(url);
        logger.error("PUT url={},toJson= {}", url, toJson);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("User-Agent", USER_AGENT);

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(toJson.getBytes());
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        logger.info("PUT Response Code :: " + responseCode);


        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return new GpaHttpResponse(con.getHeaderFields(), responseCode, response.toString());
        } else {

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getErrorStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            logger.error("POST request not worked, responseCode={}, responseMessage={}", response.toString());
            return new GpaHttpResponse(null, 500, "POST request not worked");
        }
    }

    public GpaHttpResponse callPost(String url, String toJson, String token) throws IOException {
        URL obj = new URL(url);
        logger.error("POST url={}", url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(toJson.getBytes());
        os.flush();
        os.close();
        // For POST only - END
        Map<String, List<String>> headers = new HashMap<>();
        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        String body = null;
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            headers = con.getHeaderFields();
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            body= response.toString();
            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request not worked");
        }

        int code = responseCode;

        return new GpaHttpResponse(headers, code, body);
    }

    public void callCreateMail(MailReq mailReq) throws IOException {
        int retryCount=0;
        String url ="/mail/create";
        while (retryCount < MaxRetryCount) {
            GpaHttpResponse s = callPut(getUrl() + url, gson.toJson(mailReq), token);
            if (s.getCode() == HttpURLConnection.HTTP_OK || s.getCode() == HttpURLConnection.HTTP_CREATED) {
                return;
            }
            logger.error("retryCount={}, code={}, message={} ", retryCount, s.getCode(), s.getBody());
            retryCount++;
        }
    }
}
