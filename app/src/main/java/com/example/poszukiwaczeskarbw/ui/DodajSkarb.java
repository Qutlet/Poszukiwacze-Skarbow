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
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.FragmentActivity;

import com.example.poszukiwaczeskarbw.R;
import com.example.poszukiwaczeskarbw.logika.Baza;
import com.example.poszukiwaczeskarbw.logika.Mapa;
import com.example.poszukiwaczeskarbw.logika.PunktKontrolny;
import com.example.poszukiwaczeskarbw.logika.Uzytkownik;
import com.example.poszukiwaczeskarbw.logika.Zadanie;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.InvocationHandler;
import java.util.ArrayList;

public class DodajSkarb extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final Baza bazunia = new Baza();
    public boolean flagaStart = true;
    public boolean flagaPunkt = false;
    public boolean flagaKoniec = false;
    public boolean flagaDodajTaska = false;
    public boolean flagatekst = false;
    public boolean flagatekst1 = false;
    String z1 = "Potrząśnij telefonem";
    String z2 = "Zbliz reke do ekranu na 2cm";
    String z3 = "Zbliz telefon do metalu";
    LatLng[] latLangi = new LatLng[6];
    int[] rodzajeZadan = new int[6];
    ArrayList<Marker> markerList = new ArrayList<>();
    Uzytkownik uzytkownik = Uzytkownik.getUzytkowniczek();
    ArrayList<PunktKontrolny> punktKontrolne = new ArrayList<>();
    Spinner dropdown;
    EditText pytanie;
    EditText odpowiedz;
    EditText nazwaMapy;
    EditText gratulacje;
    Button jp100;
    Button jp101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_skarb);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button start = findViewById(R.id.start);
        //Button punkt = findViewById(R.id.punkt);
        Button koniec = findViewById(R.id.koniec);
        Button zapisz = findViewById(R.id.zapisz);
        jp101 = findViewById(R.id.button4);
        jp101.setVisibility(View.INVISIBLE);
        jp100 = findViewById(R.id.button3);
        jp100.setVisibility(View.INVISIBLE);

        pytanie = (EditText) findViewById(R.id.trescPytaniaW);
        String pytaniee = pytanie.getText().toString();
        odpowiedz = (EditText) findViewById(R.id.odpowiedzW);
        String odpowiedzz = odpowiedz.getText().toString();
        nazwaMapy = (EditText) findViewById(R.id.nazwaMapy);
        gratulacje =(EditText) findViewById(R.id.nazwaMapy3);
        gratulacje.setVisibility(View.INVISIBLE);
        pytanie.setVisibility(View.INVISIBLE);
        odpowiedz.setVisibility(View.INVISIBLE);
        nazwaMapy.setVisibility(View.INVISIBLE);
        //get the spinner from the xml.
        dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"0", "1", "2", "3"};
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
                //punkt.setVisibility(View.VISIBLE);
                koniec.setVisibility(View.VISIBLE);
                zapisz.setVisibility(View.INVISIBLE);
                flagaStart = false;
                flagaPunkt = true;
                flagaDodajTaska = true;
                dropdown.setVisibility(View.VISIBLE);
            }
        });
        zapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMapReady(mMap);
                flagaKoniec = false;
                String strinzek = nazwaMapy.getText().toString();
                String strinzek1 = gratulacje.getText().toString();
                Mapa mapa = new Mapa(uzytkownik.getImie(), uzytkownik.getNazwisko(), strinzek, strinzek1);
                mapa.setPunktyKontrolne(punktKontrolne);
                bazunia.dodajNowaMape(mapa);
                finish();
            }
        });
        koniec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMapReady(mMap);
                flagaPunkt = false;
                flagaKoniec = true;
                pytanie.setVisibility(View.INVISIBLE);
                odpowiedz.setVisibility(View.INVISIBLE);
                jp100.setVisibility(View.INVISIBLE);
                jp101.setVisibility(View.INVISIBLE);
                dropdown.setVisibility(View.INVISIBLE);
                zapisz.setVisibility(View.VISIBLE);
                //punkt.setVisibility(View.INVISIBLE);
                koniec.setVisibility(View.INVISIBLE);
                nazwaMapy.setVisibility(View.VISIBLE);
                gratulacje.setVisibility(View.VISIBLE);
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
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                    markerList.add(mMap.addMarker((markerOptions)));
                    punktKontrolne.add(new PunktKontrolny(latLng, "Start", new Zadanie(99, 0, "start", "start")));
                }

                if (flagaPunkt) {
                    if (markerList.size() < 6) {
                        pytanie.setVisibility(View.INVISIBLE);
                        odpowiedz.setVisibility(View.INVISIBLE);
                        markerOptions.position(latLng);
                        markerOptions.title("Punkt " + markerList.size());
                        //mMap.clear();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                        markerList.add(mMap.addMarker((markerOptions)));
                        int numerek = markerList.size();
                        if (flagaDodajTaska) {
                            jp100.setVisibility(View.INVISIBLE);
                            dropdown.setVisibility(View.VISIBLE);
                            String r_Zadania = dropdown.getSelectedItem().toString();
                            int rodzaj_Zadania = Integer.parseInt(r_Zadania);
                            if (rodzaj_Zadania != 3) {
                                System.out.println(rodzaj_Zadania);
                                if(rodzaj_Zadania == 0)
                                    punktKontrolne.add(new PunktKontrolny(latLng, "Punkt" + numerek, new Zadanie(numerek, rodzaj_Zadania, z1, "test" + numerek)));
                                if(rodzaj_Zadania == 1)
                                    punktKontrolne.add(new PunktKontrolny(latLng, "Punkt" + numerek, new Zadanie(numerek, rodzaj_Zadania, z2, "test" + numerek)));
                                if(rodzaj_Zadania == 2)
                                    punktKontrolne.add(new PunktKontrolny(latLng, "Punkt" + numerek, new Zadanie(numerek, rodzaj_Zadania, z3, "test" + numerek)));
                            } else {
                                jp101.setVisibility(View.VISIBLE);
                                jp101.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        jp100.setVisibility(View.VISIBLE);
                                        pytanie.setVisibility(View.VISIBLE);
                                        odpowiedz.setVisibility(View.VISIBLE);
                                        jp101.setVisibility(View.INVISIBLE);
                                    }
                                });
                                jp100.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String pytaniee = pytanie.getText().toString();
                                        String odpowiedzz = odpowiedz.getText().toString();
                                        punktKontrolne.add(new PunktKontrolny(latLng, "Punkt" + numerek, new Zadanie(numerek, rodzaj_Zadania, pytaniee, odpowiedzz)));
                                        pytanie.setVisibility(View.INVISIBLE);
                                        odpowiedz.setVisibility(View.INVISIBLE);
                                        pytanie.setText("Pytanie");
                                        odpowiedz.setText("Odpowiedź");
                                        jp100.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }
                        }
                    }
                }
                if (flagaKoniec) {
                    if (markerList.size() < 7) {
                        markerOptions.position(latLng);
                        markerOptions.title("Koniec");
                        //dropdown.setVisibility(View.INVISIBLE);
                        //mMap.clear();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                        markerList.add(mMap.addMarker((markerOptions)));

                        if (flagaDodajTaska) {
                            jp100.setVisibility(View.INVISIBLE);
                            dropdown.setVisibility(View.INVISIBLE);
                            String r_Zadania = dropdown.getSelectedItem().toString();
                            int rodzaj_Zadania = Integer.parseInt(r_Zadania);
                            if (rodzaj_Zadania != 3) {
                                System.out.println(rodzaj_Zadania);
                                if(rodzaj_Zadania == 0)
                                    punktKontrolne.add(new PunktKontrolny(latLng, "Koniec", new Zadanie(6, rodzaj_Zadania, z1, "testKoniec")));
                                if(rodzaj_Zadania == 1)
                                    punktKontrolne.add(new PunktKontrolny(latLng, "Koniec", new Zadanie(6, rodzaj_Zadania, z2, "testKoniec")));
                                if(rodzaj_Zadania == 2)
                                    punktKontrolne.add(new PunktKontrolny(latLng, "Koniec", new Zadanie(6, rodzaj_Zadania, z3, "testKoniec")));
                            } else {
                                jp101.setVisibility(View.VISIBLE);
                                jp101.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        jp100.setVisibility(View.VISIBLE);
                                        pytanie.setVisibility(View.VISIBLE);
                                        odpowiedz.setVisibility(View.VISIBLE);
                                        jp101.setVisibility(View.INVISIBLE);
                                    }
                                });
                                jp100.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String pytaniee = pytanie.getText().toString();
                                        String odpowiedzz = odpowiedz.getText().toString();
                                        punktKontrolne.add(new PunktKontrolny(latLng, "Koniec", new Zadanie(6, rodzaj_Zadania, pytaniee, odpowiedzz)));
                                        pytanie.setVisibility(View.INVISIBLE);
                                        odpowiedz.setVisibility(View.INVISIBLE);
                                        pytanie.setText("Pytanie");
                                        odpowiedz.setText("Odpowiedź");
                                        jp100.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }
                        }
                        //String r_Zadania = dropdown.getSelectedItem().toString();
                        //int rodzaj_Zadania = Integer.parseInt(r_Zadania);
                        //rodzajeZadan[5] = rodzaj_Zadania;
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
                        //punktKontrolne.add(new PunktKontrolny(latLng, "Koniec", new Zadanie(89, 0, "koniec", "koniec")));
                        //Tu sie dzieje dodawanie punktow
//                        for (int i = 0; i < markerList.size() - 2; i++) {
//                            punktKontrolne.add(new PunktKontrolny(latLangi[i], "Punkt" + i + 1, new Zadanie(i + 1, rodzajeZadan[i + 1], "test" + i + 1, "tak")));
//                        }
                    }
                }
            }
        });

    }
}