/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2021. All rights reserved
 * Last modified 03.01.21 11:15
 */

package com.example.poszukiwaczeskarbw.logika;

public class Zadanie {
    private int numerZadania;
    private int rodzajZadania;
    private String trescZadania;
    private String wynikZaliczajacy;

    public Zadanie(int numerZadania, int rodzajZadania, String trescZadania, String wynikZaliczajacy) {
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

    public String getWynikZaliczajacy() {
        return wynikZaliczajacy;
    }

    public void setWynikZaliczajacy(String wynikZaliczajacy) {
        this.wynikZaliczajacy = wynikZaliczajacy;
    }

    public String zapiszZadanieJakoString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(numerZadania)
                .append(";")
                .append(rodzajZadania)
                .append(";")
                .append(trescZadania)
                .append(";")
                .append(wynikZaliczajacy)
                .append(";");
        return stringBuilder.toString();
    }
}
