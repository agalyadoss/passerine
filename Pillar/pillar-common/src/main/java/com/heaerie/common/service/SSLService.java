package com.heaerie.common.service;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class SSLService {

    public  static KeyManager[] getKeyManager(String jksPath, String pass) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, UnrecoverableKeyException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX505");
        FileInputStream fin = new FileInputStream(jksPath);
        KeyStore keyStore  = KeyStore.getInstance("JKS");
        keyStore.load(fin, pass.toCharArray());
        keyManagerFactory.init(keyStore, pass.toCharArray());
        return  keyManagerFactory.getKeyManagers();
    }
}
