package com.heaerie.common;

import javax.persistence.Id;
import java.util.List;

public class LIST001TT {
    @Id
    private String id;
    List<String> list;
    List<MAIL001TT> mailList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<MAIL001TT> getMailList() {
        return mailList;
    }

    public void setMailList(List<MAIL001TT> mailList) {
        this.mailList = mailList;
    }

    @Override
    public String toString() {
        return "LIST001TT{" +
                "id='" + id + '\'' +
                ", list=" + list +
                ", mailList=" + mailList +
                '}';
    }
}
