package com.heaerie.common;

import java.util.List;
import java.util.Map;

public class HARAKAMAIL {
    HarakaHeader header;
    List<String> header_lines;
    boolean isHtml;
    Map<String,Object> options;
    List<String> filters;
    String bodytext;
    String bodyEncoding;
    String boundary;
    String ct;
    String decodeFunction;
    List<HARAKAMAIL> children;
    String state;

    public HarakaHeader getHeader() {
        return header;
    }

    public void setHeader(HarakaHeader header) {
        this.header = header;
    }

    public List<String> getHeader_lines() {
        return header_lines;
    }

    public void setHeader_lines(List<String> header_lines) {
        this.header_lines = header_lines;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean html) {
        isHtml = html;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public String getBodytext() {
        return bodytext;
    }

    public void setBodytext(String bodytext) {
        this.bodytext = bodytext;
    }

    public String getBodyEncoding() {
        return bodyEncoding;
    }

    public void setBodyEncoding(String bodyEncoding) {
        this.bodyEncoding = bodyEncoding;
    }

    public String getBoundary() {
        return boundary;
    }

    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getDecodeFunction() {
        return decodeFunction;
    }

    public void setDecodeFunction(String decodeFunction) {
        this.decodeFunction = decodeFunction;
    }

    public List<HARAKAMAIL> getChildren() {
        return children;
    }

    public void setChildren(List<HARAKAMAIL> children) {
        this.children = children;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "HARAKAMAIL{" +
                "header=" + header +
                ", header_lines=" + header_lines +
                ", isHtmal=" + isHtml +
                ", options=" + options +
                ", filters=" + filters +
                ", bodytext='" + bodytext + '\'' +
                ", bodyEncoding='" + bodyEncoding + '\'' +
                ", boundary='" + boundary + '\'' +
                ", ct='" + ct + '\'' +
                ", decodeFunction='" + decodeFunction + '\'' +
                ", children=" + children +
                ", state='" + state + '\'' +
                '}';
    }
}
