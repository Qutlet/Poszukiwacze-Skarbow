/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 28.12.20 15:29
 */

package com.example.poszukiwaczeskarbw.logika;

public class Zadanie {
    private int numerZadania = 1;
    private int rodzajZadania = 0;
    private String trescZadania = "Obróc telefon 5 razy";
    private int wynikZaliczajacy =5;

    public Zadanie(int numerZadania, int rodzajZadania, String trescZadania, int wynikZaliczajacy) {
        this.numerZadania = numerZadania;
        this.rodzajZadania = rodzajZadania;
        this.trescZadania = trescZadania;
        this.wynikZaliczajacy = wynikZaliczajacy;
    }

    public int getNumerZadania() {
        return numerZadania;
    }

    public void setNumerZadania(int numerZadania) {
        this.numerZadania = numerZadania;
    }

    public int getRodzajZadania() {
        return rodzajZadania;
    }

    public void setRodzajZadania(int rodzajZadania) {
        this.rodzajZadania = rodzajZadania;
    }

    public String getTrescZadania() {
        return trescZadania;
    }

    public void setTrescZadania(String trescZadania) {
        this.trescZadania = trescZadania;
    }

    public int getWynikZaliczajacy() {
        return wynikZaliczajacy;
    }

    public void setWynikZaliczajacy(int wynikZaliczajacy) {
        this.wynikZaliczajacy = wynikZaliczajacy;
    }
}
