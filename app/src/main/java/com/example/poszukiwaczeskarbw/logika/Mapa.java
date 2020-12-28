/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 24.12.20 02:06
 */

package com.example.poszukiwaczeskarbw.logika;

import java.util.ArrayList;

public class Mapa {
    private String imieAutora = "Poszukiwacz";
    private String nazwiskoAutora = "Skarbow";
    private ArrayList<PunktKontrolny> punktyKontrolne = new ArrayList<>();

    public Mapa(String imieAutora, String nazwiskoAutora) {
        this.imieAutora = imieAutora;
        this.nazwiskoAutora = nazwiskoAutora;
    }

    public String getImieAutora() {
        return imieAutora;
    }

    public String getNazwiskoAutora() {
        return nazwiskoAutora;
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
}