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
import android.widget.ImageButton;

import com.example.poszukiwaczeskarbw.R;

public class Wybor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybor);
        Button dodaj = findViewById(R.id.button1);
        dodaj.setOnClickListener(V->{
            Intent dodajSkarb = new Intent(getApplicationContext(), DodajSkarb.class);
            startActivity(dodajSkarb);
        });
        Button szukaj = findViewById(R.id.button2);
        szukaj.setOnClickListener(V->{
            Intent lista = new Intent(getApplicationContext(), ListaSkarbow.class);
            startActivity(lista);
        });
        ImageButton profil = findViewById(R.id.profil);
        profil.setOnClickListener(V->{
            Intent pokazProfil = new Intent(getApplicationContext(), ProfilUzytkownika.class);
            startActivity(pokazProfil);
        });
        Button info = findViewById(R.id.info);
        info.setOnClickListener(V->{
            Intent pokazInfo = new Intent(getApplicationContext(), Info.class);
            startActivity(pokazInfo);
        });
    }
}