/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 28.12.20 12:25
 */

package com.example.poszukiwaczeskarbw.logika;

public class PunktKontrolny {
    private double wspolrzednaSzerokosciGeograficznejPunktuKontrolnego;
    private double wspolrzednaWysokosciGeograficznejPunktuKontrolnego;
    private String zadanie;

    public PunktKontrolny(double wspolrzednaSzerokosciGeograficznejPunktuKontrolnego, double wspolrzednaWysokosciGeograficznejPunktuKontrolnego, String zadanie) {
        this.wspolrzednaSzerokosciGeograficznejPunktuKontrolnego = wspolrzednaSzerokosciGeograficznejPunktuKontrolnego;
        this.wspolrzednaWysokosciGeograficznejPunktuKontrolnego = wspolrzednaWysokosciGeograficznejPunktuKontrolnego;
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

    public String getZadanie() {
        return zadanie;
    }

    public void setZadanie(String zadanie) {
        this.zadanie = zadanie;
    }
}
