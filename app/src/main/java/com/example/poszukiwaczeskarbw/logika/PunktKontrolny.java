/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2021. All rights reserved
 * Last modified 03.01.21 12:00
 */

package com.example.poszukiwaczeskarbw.logika;

import com.google.android.gms.maps.model.LatLng;

public class PunktKontrolny {
    private LatLng wspolrzedneGeograficznePunktuKontrolnego;
    private String nazwa;
    private Zadanie zadanie;

    public PunktKontrolny(LatLng wspolrzedneGeograficznePunktuKontrolnego, String nazwa, Zadanie zadanie) {
        this.wspolrzedneGeograficznePunktuKontrolnego = wspolrzedneGeograficznePunktuKontrolnego;
        this.nazwa = nazwa;
        this.zadanie = zadanie;
    }

    public LatLng getWspolrzedneGeograficznePunktuKontrolnego() {
        return wspolrzedneGeograficznePunktuKontrolnego;
    }

    public void setWspolrzedneGeograficznePunktuKontrolnego(LatLng wspolrzedneGeograficznePunktuKontrolnego) {
        this.wspolrzedneGeograficznePunktuKontrolnego = wspolrzedneGeograficznePunktuKontrolnego;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Zadanie getZadanie() {
        return zadanie;
    }

    public void setZadanie(Zadanie zadanie) {
        this.zadanie = zadanie;
    }

    public String zapiszPunktKontrolnyJakoString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(wspolrzedneGeograficznePunktuKontrolnego)
                .append(";")
                .append(nazwa)
                .append(";");
        if (zadanie == null){
            stringBuilder.append(";;;;");
        } else {
            stringBuilder.append(zadanie.zapiszZadanieJakoString());
        }
        return stringBuilder.toString();
    }
}
