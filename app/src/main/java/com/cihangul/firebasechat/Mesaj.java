package com.cihangul.firebasechat;

/**
 * Created by Cihan on 16.11.2016.
 */

public class Mesaj {
    private String mesajMetni;
    private String mesajSahibi;
    private String mesajSaati;
    private String mesajSahibiResmi;
    private String mesajSahibiId;

    public Mesaj(String mesajMetni, String mesajSahibi, String mesajSaati, String mesajSahibiResmi) {
        this.mesajMetni = mesajMetni;
        this.mesajSahibi = mesajSahibi;
        this.mesajSaati = mesajSaati;
        this.mesajSahibiResmi = mesajSahibiResmi;
    }

    public Mesaj() {
    }

    public String getMesajMetni() {
        return mesajMetni;
    }

    public void setMesajMetni(String mesajMetni) {
        this.mesajMetni = mesajMetni;
    }

    public String getMesajSahibi() {
        return mesajSahibi;
    }

    public void setMesajSahibi(String mesajSahibi) {
        this.mesajSahibi = mesajSahibi;
    }

    public String getMesajSaati() {
        return mesajSaati;
    }

    public void setMesajSaati(String mesajSaati) {
        this.mesajSaati = mesajSaati;
    }

    public String getMesajSahibiResmi() {
        return mesajSahibiResmi;
    }

    public void setMesajSahibiResmi(String mesajSahibiResmi) {
        this.mesajSahibiResmi = mesajSahibiResmi;
    }

    public String getMesajSahibiId() {
        return mesajSahibiId;
    }

    public void setMesajSahibiId(String mesajSahibiId) {
        this.mesajSahibiId = mesajSahibiId;
    }
}
