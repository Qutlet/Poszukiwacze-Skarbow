/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2021. All rights reserved
 * Last modified 01.01.21 17:38
 */

package com.example.poszukiwaczeskarbw.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.net.ParseException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.poszukiwaczeskarbw.R;
import com.example.poszukiwaczeskarbw.logika.Baza;
import com.example.poszukiwaczeskarbw.logika.Uzytkownik;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Rejestracja extends AppCompatActivity {
    Baza baza = Baza.getBaza();
    Uzytkownik uzytkownik = Uzytkownik.getUzytkowniczek();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);
        final EditText imie = findViewById(R.id.imie);
        final EditText nazwisko = findViewById(R.id.nazwisko);
        final EditText haslo = findViewById(R.id.haslo);
        final EditText data = findViewById(R.id.data);
        final EditText email = findViewById(R.id.Email);
        final TextView error = findViewById(R.id.errorData);
        Button sign = findViewById(R.id.zarejestruj);
        error.setVisibility(View.INVISIBLE);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean powodzenia = checkDate("yyyy-MM-dd",data.getText().toString());
                if (powodzenia){
                    int powodzenie =  baza.dodajNowegoUzytkownika(imie.getText().toString(),nazwisko.getText().toString(),haslo.getText().toString(),data.getText().toString(),email.getText().toString());
                    if (powodzenie == 0){
                        Intent zarejestruj = new Intent(getApplicationContext(), Wybor.class);
                        startActivity(zarejestruj);
                    }
                } else {
                    error.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean checkDate(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException | java.text.ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }
}