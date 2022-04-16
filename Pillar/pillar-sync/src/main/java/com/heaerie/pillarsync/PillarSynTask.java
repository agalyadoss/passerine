package com.heaerie.pillarsync;

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
import java.util.ArrayList;
import java.util.List;

public class PillarSynTask implements Runnable {
    static final Logger logger = LogManager.getLogger();
    private  final Integer PILLAR_SYNC_FREQ_MS  ;
    private  final String PILLAR_SYNC_PASSWORD ;
    private  final String PILLAR_SYNC_USER ;
    private  final String PILLAR_GPA_WEB_PORT  ;
    private  final String PILLAR_GPA_WEB_BASE  ;
    private  final String PILLAR_GPA_WEB_HOST  ;
    private  final String PILLAR_GPA_PROTOCOL  ;
    private  final String PILLAR_SYNC_WEB_PORT ;
    private  final String PILLAR_SYNC_WEB_BASE ;
    private  final String PILLAR_SYNC_PROTOCOL ;
    private  final String PILLAR_SYNC_WEB_HOST ;

    PillarSyn.PillarHttpClient hcPillar;
    GpaHttpClient hcWebSer;
    Boolean runStatus =true;
    static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            //.excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    String token;
    public PillarSynTask() {

        PILLAR_SYNC_PROTOCOL= PillarPropService.get("PILLAR_SYNC_PROTOCOL", "https");
        PILLAR_SYNC_WEB_HOST= PillarPropService.get("PILLAR_SYNC_WEB_HOST", "heaerieglobalsolutions.com");
        PILLAR_SYNC_WEB_BASE= PillarPropService.get("PILLAR_SYNC_WEB_BASE", "pillar/api/");
        PILLAR_SYNC_WEB_PORT= PillarPropService.get("PILLAR_SYNC_WEB_PORT", "8080");

        PILLAR_SYNC_USER= PillarPropService.get("PILLAR_SYNC_USER", "durai");
        PILLAR_SYNC_PASSWORD= PillarPropService.get("PILLAR_SYNC_USER", "1qaz2wsx");

        PILLAR_GPA_PROTOCOL= PillarPropService.get("PILLAR_GPA_PROTOCOL", "http");
        PILLAR_GPA_WEB_HOST= PillarPropService.get("PILLAR_GPA_WEB_HOST", "192.168.1.59");
        PILLAR_GPA_WEB_BASE= PillarPropService.get("PILLAR_GPA_WEB_BASE", "");
        PILLAR_GPA_WEB_PORT= PillarPropService.get("PILLAR_GPA_WEB_PORT", "8081");
        PILLAR_SYNC_FREQ_MS= PillarPropService.getNumber("PILLAR_SYNC_FREQ_MS", 500);


        List<String> hosts = new ArrayList<>();
        hosts.add(PILLAR_SYNC_WEB_HOST);

        try {
            hcPillar = new PillarSyn.PillarHttpClient(PILLAR_SYNC_PROTOCOL, hosts, PILLAR_SYNC_WEB_PORT, PILLAR_SYNC_WEB_BASE);
        } catch (HostConfigException e) {
            e.printStackTrace();
        }
        this.hcWebSer = new GpaHttpClient(PILLAR_GPA_PROTOCOL, PILLAR_GPA_WEB_HOST, PILLAR_GPA_WEB_PORT, PILLAR_GPA_WEB_BASE);
        logger.info("PillarSynTask is initialized");



        USER002TT user =  new USER002TT();
        user.setPassword(PILLAR_SYNC_PASSWORD);
        user.setUsrId(PILLAR_SYNC_USER);

        try {
            hcPillar.userLogin(user);
        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (runStatus) {
            logger.info("runStatus ={}", runStatus);
            try {
                List<MAIL001TT> mails = hcPillar.getPillarMails("PENDING");
                if (mails.size() == 0) {
                    logger.info("mails no pending={}", mails.size());
                    System.out.println("mails no pending=" + mails.size() + " PILLAR_SYNC_FREQ_MS=" + PILLAR_SYNC_FREQ_MS );

                    Thread.sleep(PILLAR_SYNC_FREQ_MS);
                    System.out.println("wake up");
                    continue;
                }
                for (MAIL001TT mail : mails) {
                    MailReq mailReq = mail.getRequest();
                    logger.info("mail id={}", mail.getId());

                    if ( null != mailReq ) {
                        hcWebSer.callCreateMail(mailReq);
                        logger.info("callCreateMail ", mail.getId());
                    }
                    mail.setStatus("PROCESSED");
                    hcPillar.updateStatus(mail);
                    logger.info("update status to processed ", mail.getId());

                }

            } catch (Exception e) {
                e.printStackTrace();
                logger.info("exception ",e);
            }
        }
    }

    public void setRunStatus(Boolean runStatus) {
        logger.info("runStatus={} ",runStatus);
        this.runStatus = runStatus;
    }
}
