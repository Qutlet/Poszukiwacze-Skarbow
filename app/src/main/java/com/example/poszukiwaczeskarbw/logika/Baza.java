/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 23.12.20 20:28
 */

package com.example.poszukiwaczeskarbw.logika;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Baza {
    private static Baza bazunia = new Baza();
    public static Baza getBaza() {
        return bazunia;
    }
    Connection polaczenie = null;

    /**
     * otwarcie polaczenia z baza danych
     */
    private void polacz() {
        StrictMode.ThreadPolicy politka = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(politka);
        try {
            final String parametryPoloczenia = "jdbc:sqlserver://poszukiwaczeskarbow.database.windows.net:1433;database=PoszukiwaczeSkarbow;user=qutelt@poszukiwaczeskarbow;password=YmsupDsk7pmTCU9;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
            final String sterownikSQL = "net.sourceforge.jtds.jdbc.Driver";
            Class.forName(sterownikSQL);
            polaczenie = DriverManager.getConnection(parametryPoloczenia);

        } catch (SQLException | ClassNotFoundException e) {
            //TODO: dodac ewentualna obsluge wyjatkow, informacje dla uzytkownika o braku  mozliwosci polaczenie sie z serwerem
            e.printStackTrace();
        }
    }

    /**
     * zamykanie polaczenia z baza danych
     */
    private void rozlacz() {
        if (polaczenie != null) {
            try {
                polaczenie.close();
            } catch (SQLException e) {
                //TODO: dodac ewentualna obsluge wyjatku
                e.printStackTrace();
            }
        }
    }

    /**
     * dodaje nowego uzytkownika do systemu
     * @param imie - imie
     * @param nazwisko - nazwisko
     * @param nazwaUzytkownika -nazwa uzytkownika todo usunac w razie potrzeby
     * @param dataUrodzenia - data urodzenia
     * @param email - email
     * @return 0 - operacja zakonczona powodzenie / 1 - nazwa uzytkownika zajeta / 100 - wystapil blad podczas operacji
     */
    public int dodajNowegoUzytkownika(String imie, String nazwisko, String nazwaUzytkownika,String haslo ,String dataUrodzenia, String email){//TODO: czy jakąś nazwe użytkownika tu dajemy?
        if (sprawdzDostepnoscNazwyUzytkownika(nazwaUzytkownika) == 0){ //todo jezeli nie dodajemy nazwy uztkownika usunac tego if'a oraz metode sprawdzDostepnoscNazwyUzytkownika(String nazwaUzytkownika), nastepnie przerobic tą metode aby zwracala boolean'a (true - ok/false - not ok)
            polacz();
            String zapytanie = "insert into Uzytkownicy (imie,nazwisko,nazwaUzytkownika,haslo,dataUrodzenia,email) values (?,?,?,?,?,?);"; //todo z tego zapytania i ponizszego komunikatu rowniez w ewentualnosci usunac nazweUzytkownika
            try (PreparedStatement komunikat = polaczenie.prepareStatement(zapytanie)){
                komunikat.setString(1,imie);
                komunikat.setString(2,nazwisko);
                komunikat.setString(3,nazwaUzytkownika);
                komunikat.setString(4,haslo);
                komunikat.setDate(5,Date.valueOf(dataUrodzenia));//todo 1) emm upewnic sie czy to dziala 2) foramt daty podanej przy rejestracji to DD-MM-RRRR
                komunikat.setString(6,email);
                komunikat.execute();
                rozlacz();
                return 0;
            } catch (SQLException e) {
                //TODO: dodac ewentualna obsluge wyjatku
                e.printStackTrace();
                return 100;
            }
        }
        return 1;
    }

    /**
     * sprawdza czy uzytkownik o podanej nazwie uzytkownika znajduję się już w systemie
     * @param nazwaUzytkownika - podana nazwa uzytkownika
     * @return 1 - jezeli nazwa wystepuje juz w sytemie / 0 - jezeli nazwa jest dostepna / 100 - blad operacji
     */
    private int sprawdzDostepnoscNazwyUzytkownika(String nazwaUzytkownika){
        polacz();
        String zapytanie = "select nazwaUzytkownika from Uzytkownicy";
        try (PreparedStatement komunikat = polaczenie.prepareStatement(zapytanie)){
            ResultSet tablicaWynikow = komunikat.executeQuery();
            while (tablicaWynikow.next()) {
                if (tablicaWynikow.getString(1).equals(nazwaUzytkownika)){
                    rozlacz();
                    return 1;
                }
            }
        } catch (SQLException e){
            //TODO: dodac ewentualna obsluge wyjatku
            e.printStackTrace();
            return 100;
        }
        rozlacz();
        return 0;
    }

    /**
     * sprawdza dane logowania uzytkownika
     * @param nazwaUzytkownika - podana nazwa uzytkownika
     * @param haslo - haslo
     * @return 0 - dane poprawne / 1 - dane niepoprawne / 100 - blad operacji
     */
    public int sprawdzDaneLogowaniaUzytkownika(String nazwaUzytkownika /*String email */, String haslo){
        //todo w zaleznosci od podjetych wyzej decyzji (logowac sie za pomoca nazwyUzytkownika badz adresuu emial) zmienic odpowiednie wartosci w tej metodzie, oraz dokumentacje powyzej
        polacz();
        String zapytanie = "select nazwaUzytkownika,haslo from Uzytkownicy"; //String zapytanie = "select email,haslo from Uzytkownicy";
        try (PreparedStatement komunikat = polaczenie.prepareStatement(zapytanie)){
            ResultSet tablicaWynikow = komunikat.executeQuery();
            while (tablicaWynikow.next()) {
                if (tablicaWynikow.getString(1).equals(nazwaUzytkownika)){  //if (tablicaWynikow.getString(1).equals(email)){
                    if (tablicaWynikow.getString(2).equals(haslo)){
                        rozlacz();
                        return 0;
                    }
                }
            }
        }catch (SQLException e){
            //TODO: dodac ewentualna obsluge wyjatku
            e.printStackTrace();
            return 100;
        }
        rozlacz();
        return 1;
    }
}
