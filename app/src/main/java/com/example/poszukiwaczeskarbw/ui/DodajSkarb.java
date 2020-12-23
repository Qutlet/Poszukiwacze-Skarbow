/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 23.12.20 14:33
 */

package com.example.poszukiwaczeskarbw.ui;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.poszukiwaczeskarbw.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class DodajSkarb extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_skarb);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        LatLng sydney = new LatLng(-34, 151);
        ArrayList<Marker> markerList = new ArrayList<>();

        Button start = findViewById(R.id.start);
        Button puknt = findViewById(R.id.punkt);
        Button koniec = findViewById(R.id.koniec);
        Button zapisz = findViewById(R.id.zapisz);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markerList.add(mMap.addMarker(new MarkerOptions().position(sydney).title("Start").draggable(true)));
                start.setVisibility(View.INVISIBLE);
                puknt.setVisibility(View.VISIBLE);
                koniec.setVisibility(View.VISIBLE);
                zapisz.setVisibility(View.VISIBLE); //usunac
            }
        });
        puknt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markerList.add(mMap.addMarker(new MarkerOptions().position(sydney).title("Punkt01").draggable(true))); //TODO zwiekszac id punktu
                //todo dodawanie zadan
            }
        });
        zapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(markerList.get(0).getPosition().toString());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(markerList.get(0).getPosition()));
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}