package com.heaerie.pillar.service;

public class PillarBadFormatException extends Exception {
    final int httpCode= 404;
    public PillarBadFormatException(String msg) {
        super(msg);
    }

    public int getHttpCode() {
        return httpCode;
    }
}
