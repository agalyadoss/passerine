package com.heaerie.common;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PillarPropService {

    private static PillarPropService singleton;
    private static final String FILE_PATH = "/etc/heaerie/pillar/conf/pillar.conf";

    static Properties prop;

    Map<String, String> map = new HashMap<>();

    private PillarPropService() throws IOException {
        prop = readPropertiesFile(FILE_PATH);
    }

    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch (IOException fnfe) {
            fnfe.printStackTrace();
        } finally {
            fis.close();
        }
        return prop;
    }

    public static PillarPropService init() throws IOException {

        singleton = new PillarPropService();
        return singleton;

    }

    public static String get(String key, String notFound) {
        return prop.getProperty(key, notFound);
    }

    public static Integer getNumber(String key, Integer notFound) {
        String value =prop.getProperty(key, notFound.toString());
        return Integer.parseInt(value);
    }
}
