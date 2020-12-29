/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 24.12.20 02:06
 */

package com.example.poszukiwaczeskarbw.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.poszukiwaczeskarbw.PoszukiwaczeSkarbow;
import com.example.poszukiwaczeskarbw.R;
import com.example.poszukiwaczeskarbw.logika.Baza;
import com.example.poszukiwaczeskarbw.logika.Uzytkownik;

import java.sql.Date;

public class Logowanie extends AppCompatActivity {
    Baza baza = Baza.getBaza();
    Uzytkownik uzytkownik = Uzytkownik.getUzytkowniczek();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logowanie);
        final EditText email = findViewById(R.id.email);
        final EditText pass = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginBtn);
        final TextView error = findViewById(R.id.errorLogin);
        error.setVisibility(View.INVISIBLE);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int powodzenie = baza.weryfikujDaneLogowaniaUzytkownika(email.getText().toString(),pass.getText().toString());
                if (powodzenie == 0){
                    Intent wybor = new Intent(getApplicationContext(), Wybor.class);
                    startActivity(wybor);
                }
                else {
                    error.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}