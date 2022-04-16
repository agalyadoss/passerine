package com.heaerie.common;




import javax.persistence.Id;
import java.util.Date;

public class MAIL001TT {
    @Id
    String id;
    Date sendTime;
    MailReq request;
    Boolean inboundOrOutbound;
    String userId;
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

    public MailReq getRequest() {
        return request;
    }

    public void setRequest(MailReq request) {
        this.request = request;
    }

    public Boolean getInboundOrOutbound() {
        return inboundOrOutbound;
    }

    public void setInboundOrOutbound(Boolean inboundOrOutbound) {
        this.inboundOrOutbound = inboundOrOutbound;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MAIL001TT{" +
                "id='" + id + '\'' +
                ", sendTime=" + sendTime +
                ", request=" + request +
                ", inboundOrOutbound=" + inboundOrOutbound +
                ", userId='" + userId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
