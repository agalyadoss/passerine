package com.heaerie.pillar.storage.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heaerie.common.MAIL001TT;
import com.heaerie.common.MailReq;
import com.heaerie.common.PillarPropService;
import com.heaerie.common.USER002TT;
import com.heaerie.pillarsync.GpaHttpClient;
import com.heaerie.pillarsync.GpaHttpResponse;
import com.heaerie.pillarsync.HostConfigException;
import com.heaerie.pillarsync.PillarSyn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestPost {
    static final Logger logger = LogManager.getLogger();
    static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            //.excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();
@Before
public void  init() throws IOException {
    PillarPropService.init();
    }
    //@Test
    public void testLogin() {
        GpaHttpClient cl = new GpaHttpClient("http", "192.168.1.59", "8080", "pillar/api");
        USER002TT user = gson.fromJson("{\n" +
                "  \"usr_id\": \"durai\",\n" +
                "  \"version\": \"001\",\n" +
                "  \"portal\": \"Pillar\",\n" +
                "  \"domain\": \"heaerieglobalsolutions.com\",\n" +
                "  \"password\": \"1qaz2wsx\",\n" +
                "  \"first_name\": \"durai\",\n" +
                "  \"last_name\": \"murugan\",\n" +
                "  \"role\": \"admin\"\n" +
                "}", USER002TT.class);
        try {
            System.out.println("user=" + gson.toJson(user));
            GpaHttpResponse response = cl.callPost(cl.getUrl() + "/user/v2/login", gson.toJson(user), null);
            System.out.println(response);
            if (response.getHeaders().get("X-AUTH-JWT") != null) {
                System.out.println("token=" + response.getHeaders().get("X-AUTH-JWT").get(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // @Test
    public void test1() {
        List<String> hosts = new ArrayList<>();
        hosts.add("heaerieglobalsolutions.com");
        PillarSyn.PillarHttpClient hcPillar = null;
        try {
            hcPillar = new PillarSyn.PillarHttpClient("http", hosts, "8080", "pillar");
        } catch (HostConfigException e) {
            e.printStackTrace();
        }
        GpaHttpClient hcWebSer = new GpaHttpClient("http", "192.168.1.59", "8081", "");
        try {
            List<MAIL001TT> mails = hcPillar.getPillarMails("PENDING");
            System.out.println(mails);
            for (MAIL001TT mail : mails) {
                MailReq mailReq = mail.getRequest();
                System.out.println(mailReq);
                hcWebSer.callCreateMail(mailReq);
                mail.setStatus("PROCESSED");
                hcPillar.updateStatus(mail);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
