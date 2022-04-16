package com.heaerie.passerine.pojo;

import java.util.List;

public class Athigaram {
    List<Kural> kurals;

    public List<Kural> getKurals() {
        return kurals;
    }

    public void setKurals(List<Kural> kurals) {
        this.kurals = kurals;
    }

    @Override
    public String toString() {
        return "Athigaram {" +
                "kurals=" + kurals +
                '}';
    }
}
