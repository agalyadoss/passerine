package com.heaerie.passerine.pojo;

import java.util.List;

public class ThiruKural {
    String id;
    List<Iyal> iyal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Iyal> getIyal() {
        return iyal;
    }

    public void setIyal(List<Iyal> iyal) {
        this.iyal = iyal;
    }

    @Override
    public String toString() {
        return "ThiruKural{" +
                "id='" + id + '\'' +
                ", iyal=" + iyal +
                '}';
    }
}
