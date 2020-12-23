/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 23.12.20 14:35
 */

package com.example.poszukiwaczeskarbw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.poszukiwaczeskarbw.ui.DodajSkarb;
import com.example.poszukiwaczeskarbw.ui.SzukajSkarb;

public class PoszukiwaczeSkarbow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intet = new Intent(getApplicationContext(), DodajSkarb.class);
                Intent intet = new Intent(getApplicationContext(), SzukajSkarb.class);
                startActivity(intet);
            }
        });
    }
}