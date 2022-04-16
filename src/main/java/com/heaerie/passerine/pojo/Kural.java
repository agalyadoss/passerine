package com.heaerie.passerine.pojo;

import java.util.List;

public class Kural {
    int en;
    List<String> varigal;

    public int getEn() {
        return en;
    }

    public void setEn(int en) {
        this.en = en;
    }

    public List<String> getVarigal() {
        return varigal;
    }

    public void setVarigal(List<String> varigal) {
        this.varigal = varigal;
    }

    @Override
    public String toString() {
        return "Kural{" +
                "en=" + en +
                ", varigal=" + varigal +
                '}';
    }
}
