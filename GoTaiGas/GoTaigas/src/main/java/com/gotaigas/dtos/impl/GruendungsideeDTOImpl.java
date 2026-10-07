package com.gotaigas.dtos.impl;

import com.gotaigas.dtos.GruendungsideeDTO;

public class GruendungsideeDTOImpl implements GruendungsideeDTO {
    private String titel;
    private String beschreibung;
    private String kategorie;
    private String phase;
    private String kapitalbedarf;


    @Override
    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    @Override
    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    @Override
    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    @Override
    public String getKapitalbedarf() {
        return kapitalbedarf;
    }

    public void setKapitalbedarf(String kapitalbedarf) {
        this.kapitalbedarf = kapitalbedarf;
    }


}
