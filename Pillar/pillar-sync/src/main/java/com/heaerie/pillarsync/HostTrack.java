package com.heaerie.pillarsync;

import java.util.HashMap;
import java.util.Map;

public class HostTrack {
    GpaHttpClient client;
    int callCount;
    Map<Integer, Integer> statusCount;
    Map<String, Integer > urlCount;
    Map<String, Integer > urlResponse;
    int prority;
    HostTrack(GpaHttpClient gpaHttpClient) {
        client = gpaHttpClient;
        callCount = 0;
        statusCount = new HashMap<>();
        prority =0;
        urlCount = new HashMap<>();
        urlResponse = new HashMap<>();
    }

    public GpaHttpClient getClient() {
        return client;
    }

    public void setClient(GpaHttpClient client) {
        this.client = client;
    }

    public int getCallCount() {
        return callCount;
    }

    public void setCallCount(int callCount) {
        this.callCount = callCount;
    }

    public Map<Integer, Integer> getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(Map<Integer, Integer> statusCount) {
        this.statusCount = statusCount;
    }

    public Map<String, Integer> getUrlCount() {
        return urlCount;
    }

    public void setUrlCount(Map<String, Integer> urlCount) {
        this.urlCount = urlCount;
    }

    public Map<String, Integer> getUrlResponse() {
        return urlResponse;
    }

    public void setUrlResponse(Map<String, Integer> urlResponse) {
        this.urlResponse = urlResponse;
    }

    public int getPrority() {
        return prority;
    }

    public void setPrority(int prority) {
        this.prority = prority;
    }

    @Override
    public String toString() {
        return "HostTrack{" +
                "client=" + client +
                ", callCount=" + callCount +
                ", statusCount=" + statusCount +
                ", urlCount=" + urlCount +
                ", urlResponse=" + urlResponse +
                ", prority=" + prority +
                '}';
    }
}
