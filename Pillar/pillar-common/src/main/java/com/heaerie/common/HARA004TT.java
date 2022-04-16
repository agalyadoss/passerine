package com.heaerie.common;

import javax.persistence.Id;
import java.util.Date;

public class HARA004TT {

    @Id
    String id;
    Date sendTime;
    HARAKAMAIL request;
    String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public HARAKAMAIL getRequest() {
        return request;
    }

    public void setRequest(HARAKAMAIL request) {
        this.request = request;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HARA004TT{" +
                "id='" + id + '\'' +
                ", sendTime=" + sendTime +
                ", request=" + request +
                ", status='" + status + '\'' +
                '}';
    }
}
