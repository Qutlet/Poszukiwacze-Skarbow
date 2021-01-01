/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 31.12.20 02:07
 */

package com.example.poszukiwaczeskarbw.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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
            Intent szukajSkarbu = new Intent(getApplicationContext(), SzukajSkarb.class);
            startActivity(szukajSkarbu);
        });
    }
}