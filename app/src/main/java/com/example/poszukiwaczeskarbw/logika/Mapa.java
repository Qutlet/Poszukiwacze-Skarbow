/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2021. All rights reserved
 * Last modified 03.01.21 11:59
 */

package com.example.poszukiwaczeskarbw.logika;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Mapa {
    private int _ID;
    private int _IDAutora;
    private String imieAutora = "Poszukiwacz";
    private String nazwiskoAutora = "Skarbow";
    private String nazwa = "Mapa do skarbu Macieja";
    private String opisSkarbu;
    private ArrayList<PunktKontrolny> punktyKontrolne = new ArrayList<>();

    public Mapa(String imieAutora, String nazwiskoAutora, String nazwa ,String opisSkarbu) {
        this.imieAutora = imieAutora;
        this.nazwiskoAutora = nazwiskoAutora;
        this.nazwa = nazwa;
        this.opisSkarbu = opisSkarbu;
    }

    public Mapa() {
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public int get_IDAutora() {
        return _IDAutora;
    }

    public void set_IDAutora(int _IDAutora) {
        this._IDAutora = _IDAutora;
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

    public String getOpisSkarbu() {
        return opisSkarbu;
    }

    public void setOpisSkarbu(String opisSkarbu) {
        this.opisSkarbu = opisSkarbu;
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

