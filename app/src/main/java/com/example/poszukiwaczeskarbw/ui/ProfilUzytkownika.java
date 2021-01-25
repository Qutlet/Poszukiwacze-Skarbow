/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2021. All rights reserved
 * Last modified 01.01.21 17:38
 */

package com.example.poszukiwaczeskarbw.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.poszukiwaczeskarbw.PoszukiwaczeSkarbow;
import com.example.poszukiwaczeskarbw.R;
import com.example.poszukiwaczeskarbw.logika.Baza;
import com.example.poszukiwaczeskarbw.logika.Uzytkownik;

import java.text.ParseException;

public class ProfilUzytkownika extends AppCompatActivity {
    Uzytkownik uzytkownik = Uzytkownik.getUzytkowniczek();
    Baza baza = Baza.getBaza();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_uzytkownika);
        TextView imie = findViewById(R.id.imie_prof);
        imie.setText(uzytkownik.getImie());
        TextView nazwisko = findViewById(R.id.nazwisko_prof);
        nazwisko.setText(uzytkownik.getNazwisko());
        TextView data = findViewById(R.id.dataurodz_prof);
        data.setText(uzytkownik.getDataUrodzenia().toString());
        TextView email = findViewById(R.id.email_prof);
        email.setText(uzytkownik.getEmail());
        TextView przebyte = findViewById(R.id.przebytekm);
        przebyte.setText("Przebyte kilometry: " + uzytkownik.getPrzebyteKM());
        TextView odnalezione = findViewById(R.id.odnskarby);
        odnalezione.setText("Odnalezione skarby: " + uzytkownik.getOdnalezioneSkarby());
        TextView umieszczone = findViewById(R.id.umskarby);
        umieszczone.setText("Umieszczone skarby: " + uzytkownik.getUmieszczoneSkarby());
        Button usun = findViewById(R.id.usun_konto);
        usun.setOnClickListener(V->{
            baza.usunUzytkownika(uzytkownik.getEmail());
            Intent intent = new Intent(getApplicationContext(), PoszukiwaczeSkarbow.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        Button wyloguj = findViewById(R.id.wyloguj);
        wyloguj.setOnClickListener(V->{
            Intent intent = new Intent(getApplicationContext(), PoszukiwaczeSkarbow.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}