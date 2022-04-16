package com.heaerie.pillarsync;

import java.util.List;
import java.util.Map;

public class GpaHttpResponse {
    final Map<String, List<String>>  headers;
    final int code;
    final String body;

    public GpaHttpResponse(Map<String, List<String>> headers, int code, String body) {
        this.headers = headers;
        this.code = code;
        this.body = body;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public int getCode() {
        return code;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "GpaHttpResponse{" +
                "headers=" + headers +
                ", code=" + code +
                ", body='" + body + '\'' +
                '}';
    }
}
