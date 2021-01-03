/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 31.12.20 02:07
 */

package com.example.poszukiwaczeskarbw.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.FragmentActivity;

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

import java.util.ArrayList;

public class DodajSkarb extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public boolean flagaStart = true;
    public boolean flagaPunkt = false;
    public boolean flagaKoniec = false;
    public boolean flagaDodajTaska = false;
    LatLng[] latLangi = new LatLng[6];
    int[] rodzajeZadan = new int[6];
    ArrayList<Marker> markerList = new ArrayList<>();
    ArrayList<PunktKontrolny> punktKontrolne = new ArrayList<>();
    Spinner dropdown;

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


        //get the spinner from the xml.
        dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"0", "1", "2"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown.setVisibility(View.INVISIBLE);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMapReady(mMap);
                start.setVisibility(View.INVISIBLE);
                punkt.setVisibility(View.VISIBLE);
                koniec.setVisibility(View.VISIBLE);
                zapisz.setVisibility(View.INVISIBLE);
                flagaStart = false;
                flagaPunkt = true;
                flagaDodajTaska = true;
                //dropdown.setVisibility(View.VISIBLE);
            }
        });
        zapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMapReady(mMap);
                flagaKoniec = false;
            }
        });
        koniec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMapReady(mMap);
                flagaPunkt = false;
                flagaKoniec = true;
                zapisz.setVisibility(View.VISIBLE);
                punkt.setVisibility(View.INVISIBLE);
                koniec.setVisibility(View.INVISIBLE);
            }
        });
        punkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagaDodajTaska = true;
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
                    punktKontrolne.add(new PunktKontrolny(latLng, "Start", null));
                }

                if (flagaPunkt) {
                    if (markerList.size() < 6) {
                        markerOptions.position(latLng);
                        markerOptions.title("Punkt " + markerList.size());
                        //mMap.clear();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                        markerList.add(mMap.addMarker((markerOptions)));
                        if (flagaDodajTaska) {
                            dropdown.setVisibility(View.VISIBLE);
                            //int numerek = markerList.size() - 1;
                            String r_Zadania = dropdown.getSelectedItem().toString();
                            int rodzaj_Zadania = Integer.parseInt(r_Zadania);
                            rodzajeZadan[markerList.size()-2] = rodzaj_Zadania;
                            latLangi[markerList.size()-2] = latLng;
                            System.out.println(rodzaj_Zadania);
                            //punktKontrolne.add(new PunktKontrolny(latLng, "Punkt" + numerek, new Zadanie(punktKontrolne.size() - 1,rodzaj_Zadania , "test" + numerek, "tak")));
                        }
                    }
                }
                if (flagaKoniec) {
                    if (markerList.size() < 7) {
                        markerOptions.position(latLng);
                        markerOptions.title("Koniec");
                        dropdown.setVisibility(View.INVISIBLE);
                        //mMap.clear();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                        markerList.add(mMap.addMarker((markerOptions)));
                        String r_Zadania = dropdown.getSelectedItem().toString();
                        int rodzaj_Zadania = Integer.parseInt(r_Zadania);
                        rodzajeZadan[5] = rodzaj_Zadania;
//                        System.out.println("chuj");
//                        System.out.println(rodzajeZadan[0]);
//                        System.out.println(rodzajeZadan[1]);
//                        System.out.println(rodzajeZadan[2]);
//                        System.out.println(rodzajeZadan[3]);
//                        System.out.println(rodzajeZadan[4]);
//                        System.out.println(rodzajeZadan[5]);
//                        System.out.println("lat chuj leng chuj");
//                        System.out.println(latLangi[0]);
//                        System.out.println(latLangi[1]);
//                        System.out.println(latLangi[2]);
//                        System.out.println(latLangi[3]);
//                        System.out.println(latLangi[4]);
                        //punktKontrolne.add(new PunktKontrolny(latLng, "Koniec", new Zadanie(punktKontrolne.size() - 1, 0, "test", "tak")));
                        //Tu sie dzieje dodawanie punktow
                        for(int i=0; i<markerList.size()-2;i++){
                            punktKontrolne.add(new PunktKontrolny(latLangi[i], "Punkt" + i+1, new Zadanie( i+1,rodzajeZadan[i+1], "test" + i+1, "tak")));
                        }
                    }
                }
            }
            // Add a marker in Sydney and move the camera
            //LatLng sydney = new LatLng(-34, 151);
            //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").draggable(true));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        });

    }
}