/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 31.12.20 02:07
 */

package com.example.poszukiwaczeskarbw.logika;

import java.util.ArrayList;

public class Mapa {
    private String imieAutora = "Poszukiwacz";
    private String nazwiskoAutora = "Skarbow";
    private String nazwa = "Mapa do skarbu Macieja";
    private ArrayList<PunktKontrolny> punktyKontrolne = new ArrayList<>();

    public Mapa(String imieAutora, String nazwiskoAutora, String nazwa) {
        this.imieAutora = imieAutora;
        this.nazwiskoAutora = nazwiskoAutora;
        this.nazwa = nazwa;
    }

    public Mapa() {
    }

    public String getImieAutora() {
        return imieAutora;
    }

    public String getNazwiskoAutora() {
        return nazwiskoAutora;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setImieAutora(String imieAutora) {
        this.imieAutora = imieAutora;
    }

    public void setNazwiskoAutora(String nazwiskoAutora) {
        this.nazwiskoAutora = nazwiskoAutora;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setPunktyKontrolne(ArrayList<PunktKontrolny> punktyKontrolne) {
        this.punktyKontrolne = punktyKontrolne;
    }

    public int iloscPunktowKontrolnych() {
        return punktyKontrolne.size();
    }

    public PunktKontrolny pobierzPunktKontrolny(int index) {
        return punktyKontrolne.get(index);
    }

    public boolean dodajPunktKontrolny(PunktKontrolny punktKontrolny) {
        return punktyKontrolne.add(punktKontrolny);
    }

    public String zapiszMapeJakoString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(imieAutora)
                .append(";")
                .append(nazwiskoAutora)
                .append(";")
                .append(nazwa)
                .append(";");
        for (PunktKontrolny p: punktyKontrolne) {
            stringBuilder.append(p.zapiszPunktKontrolnyJakoString());
        }
        stringBuilder.append("#");
        return stringBuilder.toString();
    }
}