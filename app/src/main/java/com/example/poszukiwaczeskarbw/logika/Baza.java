/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 24.12.20 02:08
 */

package com.example.poszukiwaczeskarbw.logika;

import android.annotation.SuppressLint;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Baza {
    private Uzytkownik uzytkowniczek = Uzytkownik.getUzytkowniczek();
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
            @SuppressLint("AuthLeak") final String parametryPoloczenia = "jdbc:sqlserver://poszukiwaczeskarbow.database.windows.net:1433;database=PoszukiwaczeSkarbow;user=qutelt@poszukiwaczeskarbow;password=YmsupDsk7pmTCU9;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
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
     * @param dataUrodzenia - data urodzenia
     * @param email - email
     * @return 0 - operacja zakonczona powodzenie / 1 - nazwa uzytkownika zajeta / 100 - wystapil blad podczas operacji
     */
    public int dodajNowegoUzytkownika(String imie, String nazwisko,String haslo ,String dataUrodzenia, String email){
        if (sprawdzCzyPodanyAdresEmailJestJuzWUzyciu(email) == 0){
            polacz();
            String zapytanie = "insert into Uzytkownicy (imie,nazwisko,haslo,dataUrodzenia,email) values (?,?,?,?,?);";
            try (PreparedStatement komunikat = polaczenie.prepareStatement(zapytanie)){
                komunikat.setString(1,imie);
                komunikat.setString(2,nazwisko);
                komunikat.setString(3,haslo);
                komunikat.setDate(4,Date.valueOf(dataUrodzenia));//todo 1) emm upewnic sie czy to dziala 2) foramt daty podanej przy rejestracji to RRRR-MM-DD
                komunikat.setString(5,email);
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
     * sprawdza czy uzytkownik z podanym adresem email znajduję się już w systemie
     * @param email - podany adres email
     * @return 1 - jezeli nazwa wystepuje juz w sytemie / 0 - jezeli nazwa jest dostepna / 100 - blad operacji
     */
    private int sprawdzCzyPodanyAdresEmailJestJuzWUzyciu(String email){
        polacz();
        String zapytanie = "select email from Uzytkownicy";
        try (PreparedStatement komunikat = polaczenie.prepareStatement(zapytanie)){
            ResultSet tablicaWynikow = komunikat.executeQuery();
            while (tablicaWynikow.next()) {
                if (tablicaWynikow.getString(1).equals(email)){
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
     * @param email - podany adres email
     * @param haslo - haslo
     * @return 0 - dane poprawne / 1 - dane niepoprawne / 100 - blad operacji
     */
    public int weryfikujDaneLogowaniaUzytkownika(String email , String haslo){
        polacz();
        String zapytanie = "select email,haslo from Uzytkownicy";
        try (PreparedStatement komunikat = polaczenie.prepareStatement(zapytanie)){
            ResultSet tablicaWynikow = komunikat.executeQuery();
            while (tablicaWynikow.next()) {
                if (tablicaWynikow.getString(1).equals(email)){
                    if (tablicaWynikow.getString(2).equals(haslo)){
                        rozlacz();
                        pobierzWszystkieDaneUzytkownikaZaWyjatkiemHaslaOrazWstawJeDoKlasyUzytkownik(email);
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

    /**
     * wykonuje sie automatycznie, podczas weryfikacji danych logowania w metodzie weryfikujDaneLogowaniaUzytkownika(String email , String haslo)
     * pobiera dane uzytkownika z systemu takie jak:
     * id, imie, nazwisko, data urodzenie, email
     * oraz jego statystyki:
     * odnalezione i umieszczone skarby oraz przebyte km
     * nastepnie zapisuje te dane w klasie Uytkownik w celu ulatwienia dalszego dostepu do nich
     * jezeli podczas operacji wystopi blad do danych uzytkownika zostana przypisane przykladowe dane
     * @param email - parametr logowania to na jego podstawie szukany jest uzytkownik w systemie
     */
    private void pobierzWszystkieDaneUzytkownikaZaWyjatkiemHaslaOrazWstawJeDoKlasyUzytkownik(String email){
        polacz();
        String zapytanie = "select idUzytkownika, imie, nazwisko, dataUrodzenia, email, odnalezioneSkarby, przebyteKM from Uzytkownicy where email = '" + email + "'";
        try (PreparedStatement komunikat = polaczenie.prepareStatement(zapytanie)){
            ResultSet tablicaWynikow = komunikat.executeQuery();
            if (tablicaWynikow.next()) {
              uzytkowniczek.setIdUzytkownika(tablicaWynikow.getInt(1));
              uzytkowniczek.setImie(tablicaWynikow.getString(2));
              uzytkowniczek.setNazwisko(tablicaWynikow.getString(3));
              uzytkowniczek.setDataUrodzenia(tablicaWynikow.getDate(4));
              uzytkowniczek.setEmail(email);
              uzytkowniczek.setOdnalezioneSkarby(6);
              uzytkowniczek.setPrzebyteKM(7);
            }
        }catch (SQLException e){
            //TODO: dodac ewentualna obsluge wyjatku
            e.printStackTrace();
        }
        zapytanie = "slect count(*) from Mapy where idAutora = " + uzytkowniczek.getIdUzytkownika();
        try (PreparedStatement komunikat = polaczenie.prepareStatement(zapytanie)){
            ResultSet tablicaWynikow = komunikat.executeQuery();
            if (tablicaWynikow.next()) {
                uzytkowniczek.setUmieszczoneSkarby(tablicaWynikow.getInt(1));
            }
        }catch (SQLException e){
            //TODO: dodac ewentualna obsluge wyjatku
            e.printStackTrace();
        }
    }
}
