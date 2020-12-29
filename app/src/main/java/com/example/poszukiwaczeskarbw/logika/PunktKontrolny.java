/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 28.12.20 12:25
 */

package com.example.poszukiwaczeskarbw.logika;

public class PunktKontrolny {
    private double wspolrzednaSzerokosciGeograficznejPunktuKontrolnego;
    private double wspolrzednaWysokosciGeograficznejPunktuKontrolnego;
    private String nazwa;
    private Zadanie zadanie;

    public PunktKontrolny(double wspolrzednaSzerokosciGeograficznejPunktuKontrolnego, double wspolrzednaWysokosciGeograficznejPunktuKontrolnego,String nazwa ,Zadanie zadanie) {
        this.wspolrzednaSzerokosciGeograficznejPunktuKontrolnego = wspolrzednaSzerokosciGeograficznejPunktuKontrolnego;
        this.wspolrzednaWysokosciGeograficznejPunktuKontrolnego = wspolrzednaWysokosciGeograficznejPunktuKontrolnego;
        this.nazwa = nazwa;
        this.zadanie = zadanie;
    }

    public double getWspolrzednaSzerokosciGeograficznejPunktuKontrolnego() {
        return wspolrzednaSzerokosciGeograficznejPunktuKontrolnego;
    }

    public void setWspolrzednaSzerokosciGeograficznejPunktuKontrolnego(double wspolrzednaSzerokosciGeograficznejPunktuKontrolnego) {
        this.wspolrzednaSzerokosciGeograficznejPunktuKontrolnego = wspolrzednaSzerokosciGeograficznejPunktuKontrolnego;
    }

    public double getWspolrzednaWysokosciGeograficznejPunktuKontrolnego() {
        return wspolrzednaWysokosciGeograficznejPunktuKontrolnego;
    }

    public void setWspolrzednaWysokosciGeograficznejPunktuKontrolnego(double wspolrzednaWysokosciGeograficznejPunktuKontrolnego) {
        this.wspolrzednaWysokosciGeograficznejPunktuKontrolnego = wspolrzednaWysokosciGeograficznejPunktuKontrolnego;
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
}
