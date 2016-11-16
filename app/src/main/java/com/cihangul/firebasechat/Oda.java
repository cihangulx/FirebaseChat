package com.cihangul.firebasechat;

/**
 * Created by Cihan on 16.11.2016.
 */

public class Oda {
    private String odaId;
    private String odaIsmi;

    public Oda() {
    }

    public Oda(String odaId, String odaIsmi) {
        this.odaId = odaId;
        this.odaIsmi = odaIsmi;

    }

    public String getOdaId() {
        return odaId;
    }

    public void setOdaId(String odaId) {
        this.odaId = odaId;
    }

    public String getOdaIsmi() {
        return odaIsmi;
    }

    public void setOdaIsmi(String odaIsmi) {
        this.odaIsmi = odaIsmi;
    }
}
