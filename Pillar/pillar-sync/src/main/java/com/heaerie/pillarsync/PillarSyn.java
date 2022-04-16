package com.heaerie.pillarsync;

import com.google.common.reflect.TypeToken;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heaerie.common.MAIL001TT;
import com.heaerie.common.MailReq;
import com.heaerie.common.PillarPropService;
import com.heaerie.common.USER002TT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
// com.heaerie.pillarsync.PillarSyn
public class PillarSyn {
    static final Logger logger = LogManager.getLogger();
    static  Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            //.excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    public static void main(String ... arg) {
        try {
            PillarPropService.init();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        PillarSynTask pillarSynTask = new PillarSynTask();
        Thread t = new Thread(pillarSynTask);
        t.start();
        System.out.println("Thread is started");
        logger.info("Thread is started");
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("ShutdownHook is called");
                logger.info("ShutdownHook is called");
                pillarSynTask.setRunStatus(false);
                Thread.interrupted();
            }
        }));
    }



    public static class PillarHttpClient  {
        private final String X_AUTH_JWT  ;
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                //.excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .setPrettyPrinting()
                .create();


        Map<Integer, HostTrack> clientMap = new ConcurrentHashMap<>();
        static int hostCount;
        static int seq=0;
        private String token;

        public PillarHttpClient(String protocal, List<String> hosts, String port, String base) throws HostConfigException {
            X_AUTH_JWT = PillarPropService.get("PILLAR_X_AUTH_JWT", "X-AUTH-JWT");
            if (hosts == null || hosts.isEmpty()) {
                throw new HostConfigException("Host list is mandatory");
            }

            hosts.forEach(host -> {
                clientMap.put(hostCount++, new HostTrack(new GpaHttpClient(protocal, host, port, base)));
            });

        }

        public void updateStatus(MAIL001TT mail) throws IOException {
            HostTrack track=clientMap.get(Math.abs(seq++) % hostCount);
            String url  = track.client.getUrl() + "/mail/save";
            track.callCount ++;
            track.urlCount.put(url, track.callCount);
            GpaHttpResponse response = track.getClient().callPut(url, gson.toJson(mail), token);
            token=getToken(response);

        }

        public void userLogin(USER002TT user) throws IOException {
            HostTrack track=clientMap.get(Math.abs(seq++) % hostCount);
            String url  = track.client.getUrl() + "/user/v2/login";
            track.callCount ++;
            track.urlCount.put(url, track.callCount);
            GpaHttpResponse response=track.getClient().callPost(url, gson.toJson(user), token);
            token = getToken(response);
            if (token == null) {
                throw new RuntimeException("login failed " + response.getBody());
            }



        }

        private String getToken(GpaHttpResponse response) {
            String token=this.token;
            if (response.getCode() == 200) {
                if (response.getHeaders() != null ) {
                    if (response.getHeaders().get(X_AUTH_JWT) != null) {
                        token= response.getHeaders().get(X_AUTH_JWT).get(0);
                    }
                }
            }
            return  token;
        }


        public List<MAIL001TT> getPillarMails(String status) throws Exception {
            HostTrack track=clientMap.get(Math.abs(seq++) % hostCount);
            String url  = track.client.getUrl() + "/v2/mail/";
            GpaHttpResponse response= track.getClient().callGET(url + "?status=" + status, token);
            System.out.println(response);
            Type listOfMyClassObject = new TypeToken<ArrayList<MAIL001TT>>() {}.getType();
            List<MAIL001TT> mails = gson.fromJson(response.getBody(), listOfMyClassObject);
            token=getToken(response);
            return  mails;
        }
    }
}
