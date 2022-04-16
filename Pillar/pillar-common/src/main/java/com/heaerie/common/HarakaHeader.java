package com.heaerie.common;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HarakaHeader {

    Header headers;
    Header headers_decoded;

    public Header getHeaders() {
        return headers;
    }

    public void setHeaders(Header headers) {
        this.headers = headers;
    }

    public Header getHeaders_decoded() {
        return headers_decoded;
    }

    public void setHeaders_decoded(Header headers_decoded) {
        this.headers_decoded = headers_decoded;
    }

    @Override
    public String toString() {
        return "HarakaHeader{" +
                "headers=" + headers +
                ", headers_decoded=" + headers_decoded +
                '}';
    }

    public static class Header {


        List<String> received;
        // google
        @SerializedName("dkim-signature")
        List<String> dkimSignature;
        // google
        @SerializedName("x-google-dkim-signature")
        List<String> xGoogleDkimSignature;
        @SerializedName("x-gm-message-state")
        List<String> xGmMessageState;

        @SerializedName("x-received")
        List<String> xReceived;

        @SerializedName("importance")
        List<String> importance;

        @SerializedName("savedfromemail")
        List<String> savedfromemail;
        // google
        @SerializedName("mime-version")
        List<String> mimeVersion;
        // ymail
        @SerializedName("x-ymail-osg")
        List<String> xYmailOsg ;
        @SerializedName("x-mailer")
        List<String> xMailer;
        @SerializedName("content-length")
        List<String> contentLength;
        @SerializedName("arc-seal")
        List<String> arcSeal;
        @SerializedName("arc-message-signature")
        List<String> arcMessageSignature;
        @SerializedName("arc-authentication-results")
        List<String> arcAuthenticationResults;
        List<String> from;
        List<String> to;
        List<String> subject;
        @SerializedName("thread-topic")
        List<String> threadTopic;
        @SerializedName("thread-index")
        List<String> threadIndex;
        List<String> date;
        @SerializedName("message-id")
        List<String> messageId;
        @SerializedName("accept-language")
        List<String> acceptLanguage;
        @SerializedName("content-language")
        List<String> contentLanguage;
        @SerializedName("x-ms-has-attach")
        List<String> xMsHasAttach;
        @SerializedName("x-ms-tnef-correlator")
        List<String> xMsTnefCorrelator;
        @SerializedName("x-incomingtopheadermarker")
        List<String> xIncomingtopheadermarker;
        @SerializedName("x-ms-exchange-messagesentrepresentingtype")
        List<String> xMsExchangeMessagesentrepresentingtype;
        @SerializedName("x-tmn")
        List<String> xTmn;
        @SerializedName("x-ms-publictraffictype")
        List<String> xMsPublictraffictype;
        @SerializedName("x-incomingheadercount")
        List<String> xIncomingheadercount;
        @SerializedName("x-eopattributedmessage")
        List<String> xEopattributedmessage;
        @SerializedName("x-ms-office365-filtering-correlation-id")
        List<String> xMsOffice365FilteringCorrelationId;
        @SerializedName("x-ms-traffictypediagnostic")
        List<String> xMsTraffictypediagnostic;
        @SerializedName("x-microsoft-antispam")
        List<String> xMicrosoftAntispam;
        @SerializedName("x-microsoft-antispam-message-info")
        List<String> xMicrosoftAntispamMessageInfo;
        @SerializedName("x-forefront-antispam-report")
        List<String> xForefrontAntispamReport;
        @SerializedName("x-ms-exchange-antispam-messagedata")
        List<String> xMsExchangeAntispamMessagedata;
        @SerializedName("x-ms-exchange-transport-forked")
        List<String> xMsExchangeTransportForked;
        @SerializedName("content-type")
        List<String> contentType;
        @SerializedName("x-originatororg")
        List<String> xOriginatororg;
        @SerializedName("x-ms-exchange-crosstenant-rms-persistedconsumerorg")
        List<String> xMsExchangeCrosstenantRmsPersistedconsumerorg;
        @SerializedName("x-ms-exchange-crosstenant-network-message-id")
        List<String> xMsExchangeCrosstenantNetworkMessageId;
        @SerializedName("x-ms-exchange-crosstenant-originalarrivaltime")
        List<String> xMsExchangeCrosstenantOriginalarrivaltime;
        @SerializedName("x-ms-exchange-crosstenant-fromentityheader")
        List<String> xMsExchangeCrosstenantFromentityheader;
        @SerializedName("x-ms-exchange-crosstenant-id")
        List<String> xMsExchangeCrosstenantId;
        @SerializedName("x-ms-exchange-transport-crosstenantheadersstamped")
        List<String> xMsExchangeTransportCrosstenantheadersstamped;


        public List<String> getReceived() {
            return received;
        }

        public void setReceived(List<String> received) {
            this.received = received;
        }

        public List<String> getDkimSignature() {
            return dkimSignature;
        }

        public void setDkimSignature(List<String> dkimSignature) {
            this.dkimSignature = dkimSignature;
        }

        public List<String> getxGoogleDkimSignature() {
            return xGoogleDkimSignature;
        }

        public void setxGoogleDkimSignature(List<String> xGoogleDkimSignature) {
            this.xGoogleDkimSignature = xGoogleDkimSignature;
        }

        public List<String> getxGmMessageState() {
            return xGmMessageState;
        }

        public void setxGmMessageState(List<String> xGmMessageState) {
            this.xGmMessageState = xGmMessageState;
        }

        public List<String> getxReceived() {
            return xReceived;
        }

        public void setxReceived(List<String> xReceived) {
            this.xReceived = xReceived;
        }

        public List<String> getImportance() {
            return importance;
        }

        public void setImportance(List<String> importance) {
            this.importance = importance;
        }

        public List<String> getSavedfromemail() {
            return savedfromemail;
        }

        public void setSavedfromemail(List<String> savedfromemail) {
            this.savedfromemail = savedfromemail;
        }

        public List<String> getMimeVersion() {
            return mimeVersion;
        }

        public void setMimeVersion(List<String> mimeVersion) {
            this.mimeVersion = mimeVersion;
        }

        public List<String> getxYmailOsg() {
            return xYmailOsg;
        }

        public void setxYmailOsg(List<String> xYmailOsg) {
            this.xYmailOsg = xYmailOsg;
        }

        public List<String> getxMailer() {
            return xMailer;
        }

        public void setxMailer(List<String> xMailer) {
            this.xMailer = xMailer;
        }

        public List<String> getContentLength() {
            return contentLength;
        }

        public void setContentLength(List<String> contentLength) {
            this.contentLength = contentLength;
        }

        public List<String> getArcSeal() {
            return arcSeal;
        }

        public void setArcSeal(List<String> arcSeal) {
            this.arcSeal = arcSeal;
        }

        public List<String> getArcMessageSignature() {
            return arcMessageSignature;
        }

        public void setArcMessageSignature(List<String> arcMessageSignature) {
            this.arcMessageSignature = arcMessageSignature;
        }

        public List<String> getArcAuthenticationResults() {
            return arcAuthenticationResults;
        }

        public void setArcAuthenticationResults(List<String> arcAuthenticationResults) {
            this.arcAuthenticationResults = arcAuthenticationResults;
        }

        public List<String> getFrom() {
            return from;
        }

        public void setFrom(List<String> from) {
            this.from = from;
        }

        public List<String> getTo() {
            return to;
        }

        public void setTo(List<String> to) {
            this.to = to;
        }

        public List<String> getSubject() {
            return subject;
        }

        public void setSubject(List<String> subject) {
            this.subject = subject;
        }

        public List<String> getThreadTopic() {
            return threadTopic;
        }

        public void setThreadTopic(List<String> threadTopic) {
            this.threadTopic = threadTopic;
        }

        public List<String> getThreadIndex() {
            return threadIndex;
        }

        public void setThreadIndex(List<String> threadIndex) {
            this.threadIndex = threadIndex;
        }

        public List<String> getDate() {
            return date;
        }

        public void setDate(List<String> date) {
            this.date = date;
        }

        public List<String> getMessageId() {
            return messageId;
        }

        public void setMessageId(List<String> messageId) {
            this.messageId = messageId;
        }

        public List<String> getAcceptLanguage() {
            return acceptLanguage;
        }

        public void setAcceptLanguage(List<String> acceptLanguage) {
            this.acceptLanguage = acceptLanguage;
        }

        public List<String> getContentLanguage() {
            return contentLanguage;
        }

        public void setContentLanguage(List<String> contentLanguage) {
            this.contentLanguage = contentLanguage;
        }

        public List<String> getxMsHasAttach() {
            return xMsHasAttach;
        }

        public void setxMsHasAttach(List<String> xMsHasAttach) {
            this.xMsHasAttach = xMsHasAttach;
        }

        public List<String> getxMsTnefCorrelator() {
            return xMsTnefCorrelator;
        }

        public void setxMsTnefCorrelator(List<String> xMsTnefCorrelator) {
            this.xMsTnefCorrelator = xMsTnefCorrelator;
        }

        public List<String> getxIncomingtopheadermarker() {
            return xIncomingtopheadermarker;
        }

        public void setxIncomingtopheadermarker(List<String> xIncomingtopheadermarker) {
            this.xIncomingtopheadermarker = xIncomingtopheadermarker;
        }

        public List<String> getxMsExchangeMessagesentrepresentingtype() {
            return xMsExchangeMessagesentrepresentingtype;
        }

        public void setxMsExchangeMessagesentrepresentingtype(List<String> xMsExchangeMessagesentrepresentingtype) {
            this.xMsExchangeMessagesentrepresentingtype = xMsExchangeMessagesentrepresentingtype;
        }

        public List<String> getxTmn() {
            return xTmn;
        }

        public void setxTmn(List<String> xTmn) {
            this.xTmn = xTmn;
        }

        public List<String> getxMsPublictraffictype() {
            return xMsPublictraffictype;
        }

        public void setxMsPublictraffictype(List<String> xMsPublictraffictype) {
            this.xMsPublictraffictype = xMsPublictraffictype;
        }

        public List<String> getxIncomingheadercount() {
            return xIncomingheadercount;
        }

        public void setxIncomingheadercount(List<String> xIncomingheadercount) {
            this.xIncomingheadercount = xIncomingheadercount;
        }

        public List<String> getxEopattributedmessage() {
            return xEopattributedmessage;
        }

        public void setxEopattributedmessage(List<String> xEopattributedmessage) {
            this.xEopattributedmessage = xEopattributedmessage;
        }

        public List<String> getxMsOffice365FilteringCorrelationId() {
            return xMsOffice365FilteringCorrelationId;
        }

        public void setxMsOffice365FilteringCorrelationId(List<String> xMsOffice365FilteringCorrelationId) {
            this.xMsOffice365FilteringCorrelationId = xMsOffice365FilteringCorrelationId;
        }

        public List<String> getxMsTraffictypediagnostic() {
            return xMsTraffictypediagnostic;
        }

        public void setxMsTraffictypediagnostic(List<String> xMsTraffictypediagnostic) {
            this.xMsTraffictypediagnostic = xMsTraffictypediagnostic;
        }

        public List<String> getxMicrosoftAntispam() {
            return xMicrosoftAntispam;
        }

        public void setxMicrosoftAntispam(List<String> xMicrosoftAntispam) {
            this.xMicrosoftAntispam = xMicrosoftAntispam;
        }

        public List<String> getxMicrosoftAntispamMessageInfo() {
            return xMicrosoftAntispamMessageInfo;
        }

        public void setxMicrosoftAntispamMessageInfo(List<String> xMicrosoftAntispamMessageInfo) {
            this.xMicrosoftAntispamMessageInfo = xMicrosoftAntispamMessageInfo;
        }

        public List<String> getxForefrontAntispamReport() {
            return xForefrontAntispamReport;
        }

        public void setxForefrontAntispamReport(List<String> xForefrontAntispamReport) {
            this.xForefrontAntispamReport = xForefrontAntispamReport;
        }

        public List<String> getxMsExchangeAntispamMessagedata() {
            return xMsExchangeAntispamMessagedata;
        }

        public void setxMsExchangeAntispamMessagedata(List<String> xMsExchangeAntispamMessagedata) {
            this.xMsExchangeAntispamMessagedata = xMsExchangeAntispamMessagedata;
        }

        public List<String> getxMsExchangeTransportForked() {
            return xMsExchangeTransportForked;
        }

        public void setxMsExchangeTransportForked(List<String> xMsExchangeTransportForked) {
            this.xMsExchangeTransportForked = xMsExchangeTransportForked;
        }

        public List<String> getContentType() {
            return contentType;
        }

        public void setContentType(List<String> contentType) {
            this.contentType = contentType;
        }

        public List<String> getxOriginatororg() {
            return xOriginatororg;
        }

        public void setxOriginatororg(List<String> xOriginatororg) {
            this.xOriginatororg = xOriginatororg;
        }

        public List<String> getxMsExchangeCrosstenantRmsPersistedconsumerorg() {
            return xMsExchangeCrosstenantRmsPersistedconsumerorg;
        }

        public void setxMsExchangeCrosstenantRmsPersistedconsumerorg(List<String> xMsExchangeCrosstenantRmsPersistedconsumerorg) {
            this.xMsExchangeCrosstenantRmsPersistedconsumerorg = xMsExchangeCrosstenantRmsPersistedconsumerorg;
        }

        public List<String> getxMsExchangeCrosstenantNetworkMessageId() {
            return xMsExchangeCrosstenantNetworkMessageId;
        }

        public void setxMsExchangeCrosstenantNetworkMessageId(List<String> xMsExchangeCrosstenantNetworkMessageId) {
            this.xMsExchangeCrosstenantNetworkMessageId = xMsExchangeCrosstenantNetworkMessageId;
        }

        public List<String> getxMsExchangeCrosstenantOriginalarrivaltime() {
            return xMsExchangeCrosstenantOriginalarrivaltime;
        }

        public void setxMsExchangeCrosstenantOriginalarrivaltime(List<String> xMsExchangeCrosstenantOriginalarrivaltime) {
            this.xMsExchangeCrosstenantOriginalarrivaltime = xMsExchangeCrosstenantOriginalarrivaltime;
        }

        public List<String> getxMsExchangeCrosstenantFromentityheader() {
            return xMsExchangeCrosstenantFromentityheader;
        }

        public void setxMsExchangeCrosstenantFromentityheader(List<String> xMsExchangeCrosstenantFromentityheader) {
            this.xMsExchangeCrosstenantFromentityheader = xMsExchangeCrosstenantFromentityheader;
        }

        public List<String> getxMsExchangeCrosstenantId() {
            return xMsExchangeCrosstenantId;
        }

        public void setxMsExchangeCrosstenantId(List<String> xMsExchangeCrosstenantId) {
            this.xMsExchangeCrosstenantId = xMsExchangeCrosstenantId;
        }

        public List<String> getxMsExchangeTransportCrosstenantheadersstamped() {
            return xMsExchangeTransportCrosstenantheadersstamped;
        }

        public void setxMsExchangeTransportCrosstenantheadersstamped(List<String> xMsExchangeTransportCrosstenantheadersstamped) {
            this.xMsExchangeTransportCrosstenantheadersstamped = xMsExchangeTransportCrosstenantheadersstamped;
        }

        @Override
        public String toString() {
            return "Header{" +
                    "received=" + received +
                    ", dkimSignature=" + dkimSignature +
                    ", xGoogleDkimSignature=" + xGoogleDkimSignature +
                    ", xGmMessageState=" + xGmMessageState +
                    ", xReceived=" + xReceived +
                    ", importance=" + importance +
                    ", savedfromemail=" + savedfromemail +
                    ", mimeVersion=" + mimeVersion +
                    ", xYmailOsg=" + xYmailOsg +
                    ", xMailer=" + xMailer +
                    ", contentLength=" + contentLength +
                    ", arcSeal=" + arcSeal +
                    ", arcMessageSignature=" + arcMessageSignature +
                    ", arcAuthenticationResults=" + arcAuthenticationResults +
                    ", from=" + from +
                    ", to=" + to +
                    ", subject=" + subject +
                    ", threadTopic=" + threadTopic +
                    ", threadIndex=" + threadIndex +
                    ", date=" + date +
                    ", messageId=" + messageId +
                    ", acceptLanguage=" + acceptLanguage +
                    ", contentLanguage=" + contentLanguage +
                    ", xMsHasAttach=" + xMsHasAttach +
                    ", xMsTnefCorrelator=" + xMsTnefCorrelator +
                    ", xIncomingtopheadermarker=" + xIncomingtopheadermarker +
                    ", xMsExchangeMessagesentrepresentingtype=" + xMsExchangeMessagesentrepresentingtype +
                    ", xTmn=" + xTmn +
                    ", xMsPublictraffictype=" + xMsPublictraffictype +
                    ", xIncomingheadercount=" + xIncomingheadercount +
                    ", xEopattributedmessage=" + xEopattributedmessage +
                    ", xMsOffice365FilteringCorrelationId=" + xMsOffice365FilteringCorrelationId +
                    ", xMsTraffictypediagnostic=" + xMsTraffictypediagnostic +
                    ", xMicrosoftAntispam=" + xMicrosoftAntispam +
                    ", xMicrosoftAntispamMessageInfo=" + xMicrosoftAntispamMessageInfo +
                    ", xForefrontAntispamReport=" + xForefrontAntispamReport +
                    ", xMsExchangeAntispamMessagedata=" + xMsExchangeAntispamMessagedata +
                    ", xMsExchangeTransportForked=" + xMsExchangeTransportForked +
                    ", contentType=" + contentType +
                    ", xOriginatororg=" + xOriginatororg +
                    ", xMsExchangeCrosstenantRmsPersistedconsumerorg=" + xMsExchangeCrosstenantRmsPersistedconsumerorg +
                    ", xMsExchangeCrosstenantNetworkMessageId=" + xMsExchangeCrosstenantNetworkMessageId +
                    ", xMsExchangeCrosstenantOriginalarrivaltime=" + xMsExchangeCrosstenantOriginalarrivaltime +
                    ", xMsExchangeCrosstenantFromentityheader=" + xMsExchangeCrosstenantFromentityheader +
                    ", xMsExchangeCrosstenantId=" + xMsExchangeCrosstenantId +
                    ", xMsExchangeTransportCrosstenantheadersstamped=" + xMsExchangeTransportCrosstenantheadersstamped +
                    '}';
        }
    }

}

