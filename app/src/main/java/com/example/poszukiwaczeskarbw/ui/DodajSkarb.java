/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 31.12.20 02:07
 */

package com.example.poszukiwaczeskarbw.ui;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.poszukiwaczeskarbw.R;
import com.example.poszukiwaczeskarbw.logika.PunktKontrolny;
import com.example.poszukiwaczeskarbw.logika.Zadanie;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.LocationListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DodajSkarb extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public boolean flagaStart = true;
    public boolean flagaPunkt = false;
    public boolean flagaKoniec = false;
    ArrayList<Marker> markerList = new ArrayList<>();
    ArrayList<PunktKontrolny> punktKontrolne = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_skarb);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button start = findViewById(R.id.start);
        Button punkt = findViewById(R.id.punkt);
        Button koniec = findViewById(R.id.koniec);
        Button zapisz = findViewById(R.id.zapisz);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMapReady(mMap);
                start.setVisibility(View.INVISIBLE);
                punkt.setVisibility(View.VISIBLE);
                koniec.setVisibility(View.VISIBLE);
                zapisz.setVisibility(View.VISIBLE);
                flagaStart=false;
                flagaPunkt=true;
            }
        });
        zapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMapReady(mMap);
                flagaPunkt=false;
                flagaKoniec=true;
            }
        });
        koniec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMapReady(mMap);
                flagaKoniec=false;
            }
        });
        punkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(markerList.toString());;
            }
        });
    }

    //TODO: Dodawanie zadan do punktów
    //TODO: Customowe ikonki z numerami
    //TODO: Ograniczenia odległosc

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (flagaStart) {
                    markerOptions.position(latLng);
                    markerOptions.title("Start");
                    mMap.clear();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    markerList.add(mMap.addMarker((markerOptions)));
                    punktKontrolne.add(new PunktKontrolny(latLng,"Start",null));
                }

                if (flagaPunkt) {
                    markerOptions.position(latLng);
                    markerOptions.title("Punkt " + markerList.size());
                    //mMap.clear();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    markerList.add(mMap.addMarker((markerOptions)));
                }
                if (flagaKoniec) {
                    markerOptions.position(latLng);
                    markerOptions.title("Koniec");
                    //mMap.clear();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    markerList.add(mMap.addMarker((markerOptions)));

                    punktKontrolne.add(new PunktKontrolny(latLng,"Koniec",new Zadanie(punktKontrolne.size()-1,0,"test",0)));
                }
            }


            // Add a marker in Sydney and move the camera
            //LatLng sydney = new LatLng(-34, 151);
            //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").draggable(true));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        });

    }

//    private ArrayList<PunktKontrolny> zapiszPunktyZMapyJakoPunktyKontrolne(){
//        ArrayList<PunktKontrolny> punktKontrolne = new ArrayList<>();
//        for(Marker marek : markerList){
//            punktKontrolne.add(new PunktKontrolny(marek.getPosition().latitude,marek.getPosition().longitude,marek.getTitle(),))
//        }
//        return punktKontrolne;
//    }

}