/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2021. All rights reserved
 * Last modified 03.01.21 15:06
 */

package com.example.poszukiwaczeskarbw.logika;

import android.annotation.SuppressLint;
import android.os.StrictMode;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public void polacz() {
        StrictMode.ThreadPolicy politka = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(politka);
        try {
            @SuppressLint("AuthLeak") final String parametryPoloczenia = "jdbc:jtds:sqlserver://poszukiwaczeskarbow.database.windows.net:1433;databaseName=PoszukiwaczeSkarbow;user=qutelt@poszukiwaczeskarbow;password=YmsupDsk7pmTCU9;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
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
        zapytanie = "select count(*) from Mapy where idAutora = " + uzytkowniczek.getIdUzytkownika();
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

    /**
     * funcja zapisujaca utworzana mape w systemie
     * @param nowaMapaDoZapisu - podana mapa
     * @return true - operacja zakonczona powodzeniem, false - operacja zakonczona niepowodzeniem
     */
    public boolean dodajNowaMape(Mapa nowaMapaDoZapisu){
        polacz();
        String zapytanie = "insert into Mapy (idAutora,zapis,iloscPunktow,opisSkarbu) values (?,?,?,?)";
        try (PreparedStatement komunikat = polaczenie.prepareStatement(zapytanie)){
            komunikat.setInt(1,uzytkowniczek.getIdUzytkownika());
            komunikat.setString(2,nowaMapaDoZapisu.zapiszMapeJakoString());
            komunikat.setInt(3,nowaMapaDoZapisu.iloscPunktowKontrolnych());
            komunikat.setString(4,nowaMapaDoZapisu.getOpisSkarbu());
            komunikat.execute();
            rozlacz();
            return true;
        } catch (SQLException e){
            //TODO: dodac ewentualna obsluge wyjatku
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Mapa> pobierzMapeZBazyDanych(){
        ArrayList<Mapa> mapy = new ArrayList<>();
        polacz();
        int mapa = 0;
        int rozmiar =0;
        String zapytanie = "select idMapy,idAutora,zapis,iloscPunktow,opisSkarbu from Mapy";
        try (PreparedStatement komunikat = polaczenie.prepareStatement(zapytanie)) {
            ResultSet tablicaWynikow = komunikat.executeQuery();
            while (tablicaWynikow.next()) {
                String[] stringi = new String[45];
                double[] latLangi = new double[14];
                int _ID = tablicaWynikow.getInt(1);
                int _IDA = tablicaWynikow.getInt(2);
                String zapis =tablicaWynikow.getString(3);
                int iloscPunktow = tablicaWynikow.getInt(4);
                String opisSkarbu = tablicaWynikow.getString(5);
                //imieAutora;nazwisko;nazwa;wspp1;nazwap1;nrZad,rodzZad,tresczad,wynikZad ....
                //
                int i =0;
                int c=0;
                StringBuilder budowniczy = new StringBuilder();
                while (zapis.charAt(i) != '#'){ //dopuki nie # czyli znak konza zapisu
                    //dopuki nie ; tworz wartosc
                    while (zapis.charAt(i) != ';'){ //dochodi do ; i dalej petla nie idzie
                        budowniczy.append(zapis.charAt(i));
                    }
                    if (zapis.charAt(i) == ';'){
                        //jak jest ; tworzy obiekt i zeruje tworzenie
                        stringi[rozmiar] = budowniczy.toString();
                        budowniczy = new StringBuilder();
                    }
                    rozmiar++;
                }
                for (int j = 3; j <= 41; j=j+6) {
                    stringi[j] = stringi[j].substring(10);
                    stringi[j] = stringi[j].substring(0,stringi[j].length()-1);
                    latLangi[c] = Double.parseDouble(stringi[j].split(",")[0]);
                    c++;
                    latLangi[c] = Double.parseDouble(stringi[j].split(",")[1]);
                    c++;
                }
                mapy.add(new Mapa(stringi[0],stringi[1],stringi[2],opisSkarbu));
                mapy.get(mapa).set_ID(_ID);
                mapy.get(mapa).set_IDAutora(_IDA);
                mapy.get(mapa).dodajPunktKontrolny(new PunktKontrolny(new LatLng(latLangi[0],latLangi[1]),stringi[4],new Zadanie(Integer.parseInt(stringi[5]),Integer.parseInt(stringi[6]),stringi[7],stringi[8]))); //start
                mapy.get(mapa).dodajPunktKontrolny(new PunktKontrolny(new LatLng(latLangi[2],latLangi[3]),stringi[10],new Zadanie(Integer.parseInt(stringi[11]),Integer.parseInt(stringi[12]),stringi[13],stringi[14])));
                if (iloscPunktow > 2)
                    mapy.get(mapa).dodajPunktKontrolny(new PunktKontrolny(new LatLng(latLangi[4],latLangi[5]),stringi[16],new Zadanie(Integer.parseInt(stringi[17]),Integer.parseInt(stringi[18]),stringi[19],stringi[20])));
                if (iloscPunktow > 3)
                    mapy.get(mapa).dodajPunktKontrolny(new PunktKontrolny(new LatLng(latLangi[6],latLangi[7]),stringi[22],new Zadanie(Integer.parseInt(stringi[23]),Integer.parseInt(stringi[24]),stringi[25],stringi[26])));
                if (iloscPunktow > 4)
                    mapy.get(mapa).dodajPunktKontrolny(new PunktKontrolny(new LatLng(latLangi[8],latLangi[9]),stringi[28],new Zadanie(Integer.parseInt(stringi[29]),Integer.parseInt(stringi[30]),stringi[31],stringi[32])));
                if (iloscPunktow > 5)
                    mapy.get(mapa).dodajPunktKontrolny(new PunktKontrolny(new LatLng(latLangi[10],latLangi[11]),stringi[34],new Zadanie(Integer.parseInt(stringi[35]),Integer.parseInt(stringi[36]),stringi[37],stringi[38])));
                if (iloscPunktow > 6)
                    mapy.get(mapa).dodajPunktKontrolny(new PunktKontrolny(new LatLng(latLangi[12],latLangi[13]),stringi[40],new Zadanie(Integer.parseInt(stringi[41]),Integer.parseInt(stringi[42]),stringi[43],stringi[44])));
                mapa++;
            }
        } catch (SQLException e){
            //TODO: dodac ewentualna obsluge wyjatku
            e.printStackTrace();
        }
        return mapy;
    }

    public void przeniesMapeDoArchiwum(Mapa ukonczonaMapa){
        polacz();
        String zapytanie = "insert into ArchiwumMap (idMapy,idAutora,zapis,iloscPunktow,opisSkarbu,idGracza) values (?,?,?,?,?,?)";
        try (PreparedStatement komunikat = polaczenie.prepareStatement(zapytanie)){
            komunikat.setInt(1,ukonczonaMapa.get_ID());
            komunikat.setInt(2,ukonczonaMapa.get_IDAutora());
            komunikat.setString(3,ukonczonaMapa.zapiszMapeJakoString());
            komunikat.setInt(4,ukonczonaMapa.iloscPunktowKontrolnych());
            komunikat.setString(5,ukonczonaMapa.getOpisSkarbu());
            komunikat.setInt(6,uzytkowniczek.getIdUzytkownika());
            komunikat.execute();
        } catch (SQLException e){
            //TODO: dodac ewentualna obsluge wyjatku
            e.printStackTrace();
        }
        zapytanie = "delete from Mapy where idMapy =" + ukonczonaMapa.get_ID();
        try (PreparedStatement komunikat = polaczenie.prepareStatement(zapytanie)){
            komunikat.execute();
        } catch (SQLException e){
            //TODO: dodac ewentualna obsluge wyjatku
            e.printStackTrace();
        }
        rozlacz();
    }

    public void zglosUzytkownika(int _IDAutora, String opisZgloszenia, int _IDMapy){
        polacz();
        String zapytanie = "insert into Zgloszenia (idAutoraMapy,idMapy,idZglaszajacego,opisZgloszenia) values (?,?,?,?)";
        try (PreparedStatement komunikat = polaczenie.prepareStatement(zapytanie)){
            komunikat.setInt(1,_IDAutora);
            komunikat.setInt(2,_IDMapy);
            komunikat.setInt(3,uzytkowniczek.getIdUzytkownika());
            komunikat.setString(4,opisZgloszenia);
            komunikat.execute();
        } catch (SQLException e){
            //TODO: dodac ewentualna obsluge wyjatku
            e.printStackTrace();
        }
        rozlacz();
    }

}
