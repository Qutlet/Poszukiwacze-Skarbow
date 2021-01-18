/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2021. All rights reserved
 * Last modified 03.01.21 11:16
 */

package com.example.poszukiwaczeskarbw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.poszukiwaczeskarbw.ui.ListaSkarbow;
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