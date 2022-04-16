package com.heaerie.common;

import javax.persistence.Id;

public class SUBO003TT {
    @Id
    String id;
    MAIL001TT request;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MAIL001TT getRequest() {
        return request;
    }

    public void setRequest(MAIL001TT request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "SUBO003TT{" +
                "id='" + id + '\'' +
                ", request=" + request +
                '}';
    }
}
