/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2021. All rights reserved
 * Last modified 01.01.21 17:38
 */

package com.example.poszukiwaczeskarbw.logika;

import java.sql.Date;

public class Uzytkownik {
    private int idUzytkownika = 1;
    private String imie = "Poszukiwacz";
    private String nazwisko = "Skarbow";
    private Date dataUrodzenia = Date.valueOf("1999-01-01");
    private String email = "poszukiwaczSkarbow@example.com";
    private int przebyteKM = 0;
    private int odnalezioneSkarby = 0;
    private int umieszczoneSkarby = 0;
    private static Uzytkownik uzytkowniczek = new Uzytkownik();

    public static Uzytkownik getUzytkowniczek() {
        return uzytkowniczek;
    }

    public int getIdUzytkownika() {
        return idUzytkownika;
    }

    public void setIdUzytkownika(int idUzytkownika) {
        this.idUzytkownika = idUzytkownika;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Date getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(Date dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPrzebyteKM() {
        return przebyteKM;
    }

    public void setPrzebyteKM(int przebyteKM) {
        this.przebyteKM = przebyteKM;
    }

    public int getOdnalezioneSkarby() {
        return odnalezioneSkarby;
    }

    public void setOdnalezioneSkarby(int odnalezioneSkarby) {
        this.odnalezioneSkarby = odnalezioneSkarby;
    }

    public int getUmieszczoneSkarby() {
        return umieszczoneSkarby;
    }

    public void setUmieszczoneSkarby(int umieszczoneSkarby) {
        this.umieszczoneSkarby = umieszczoneSkarby;
    }

}
