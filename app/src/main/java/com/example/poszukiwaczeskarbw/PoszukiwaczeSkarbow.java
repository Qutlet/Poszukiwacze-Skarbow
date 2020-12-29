/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 24.12.20 02:06
 */

package com.example.poszukiwaczeskarbw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.poszukiwaczeskarbw.ui.DodajSkarb;
import com.example.poszukiwaczeskarbw.ui.Logowanie;
import com.example.poszukiwaczeskarbw.ui.Rejestracja;
import com.example.poszukiwaczeskarbw.ui.SzukajSkarb;

public class PoszukiwaczeSkarbow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signIn = findViewById(R.id.Zaloguj);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIn = new Intent(getApplicationContext(), Logowanie.class);
                startActivity(logIn);
            }
        });

        Button signUp = findViewById(R.id.Zarejestruj);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(getApplicationContext(), Rejestracja.class);
                startActivity(signUp);
            }
        });
    }
}