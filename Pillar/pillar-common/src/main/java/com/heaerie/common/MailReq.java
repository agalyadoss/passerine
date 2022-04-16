package com.heaerie.common;

import java.util.Date;
import java.util.List;

public class MailReq {
    Number  mkrId  ;
    Date dtCreated  ;
    Number  athId  ;
    Date  dtModified  ;
    String  uuid  ; //uuid.timebased
    String  msgId  ;
    String  replyId  ;
    String  linkId  ; // thread id
    String  subject  ;
    String  shortBody  ;  // text "is_htmal": false,  "bodytext": "REQ001\n\n",
    String  body  ;  //"is_htmal": true, "bodytext": "<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<style type=\"text/css\" style=\"display:none;\"> P {margin-top:0;margin-bottom:0;} </style>\n</head>\n<body dir=\"ltr\">\n<div style=\"font-family: Calibri, Helvetica, sans-serif; font-size: 12pt; color: rgb(0, 0, 0); background-color: rgb(255, 255, 255);\">\nREQ001</div>\n</body>\n</html>\n\n",
    Date  receivedDate  ; // new date
    Date  sentDate  ;   //date
    List<String> fromList  ;
    List<String>  toList  ;
    String  isNewMail  ;
    String  isFlag  ;
    Number  grpId  ;
    String  hasAttachment  ;
    String userId ;
    boolean inboundOrOutbound;

    public Number getMkrId() {
        return mkrId;
    }

    public void setMkrId(Number mkrId) {
        this.mkrId = mkrId;
    }

    public Date getDtCreated() {
        return dtCreated;
    }

    public void setDtCreated(Date dtCreated) {
        this.dtCreated = dtCreated;
    }

    public Number getAthId() {
        return athId;
    }

    public void setAthId(Number athId) {
        this.athId = athId;
    }

    public Date getDtModified() {
        return dtModified;
    }

    public void setDtModified(Date dtModified) {
        this.dtModified = dtModified;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getShortBody() {
        return shortBody;
    }

    public void setShortBody(String shortBody) {
        this.shortBody = shortBody;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public List<String> getFromList() {
        return fromList;
    }

    public void setFromList(List<String> fromList) {
        this.fromList = fromList;
    }

    public List<String> getToList() {
        return toList;
    }

    public void setToList(List<String> toList) {
        this.toList = toList;
    }

    public String getIsNewMail() {
        return isNewMail;
    }

    public void setIsNewMail(String isNewMail) {
        this.isNewMail = isNewMail;
    }

    public String getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag;
    }

    public Number getGrpId() {
        return grpId;
    }

    public void setGrpId(Number grpId) {
        this.grpId = grpId;
    }

    public String getHasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachment(String hasAttachment) {
        this.hasAttachment = hasAttachment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isInboundOrOutbound() {
        return inboundOrOutbound;
    }

    public void setInboundOrOutbound(boolean inboundOrOutbound) {
        this.inboundOrOutbound = inboundOrOutbound;
    }

    @Override
    public String toString() {
        return "MailReq{" +
                "mkrId=" + mkrId +
                ", dtCreated=" + dtCreated +
                ", athId=" + athId +
                ", dtModified=" + dtModified +
                ", uuid='" + uuid + '\'' +
                ", msgId='" + msgId + '\'' +
                ", replyId='" + replyId + '\'' +
                ", linkId='" + linkId + '\'' +
                ", subject='" + subject + '\'' +
                ", shortBody='" + shortBody + '\'' +
                ", body='" + body + '\'' +
                ", receivedDate=" + receivedDate +
                ", sentDate=" + sentDate +
                ", fromList=" + fromList +
                ", toList=" + toList +
                ", isNewMail='" + isNewMail + '\'' +
                ", isFlag='" + isFlag + '\'' +
                ", grpId=" + grpId +
                ", hasAttachment='" + hasAttachment + '\'' +
                ", userId='" + userId + '\'' +
                ", inboundOrOutbound=" + inboundOrOutbound +
                '}';
    }
}
