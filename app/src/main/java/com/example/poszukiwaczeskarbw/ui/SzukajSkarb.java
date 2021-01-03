/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 31.12.20 02:37
 */

package com.example.poszukiwaczeskarbw.ui;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import com.example.poszukiwaczeskarbw.R;
import com.example.poszukiwaczeskarbw.logika.Mapa;
import com.example.poszukiwaczeskarbw.logika.PunktKontrolny;
import com.example.poszukiwaczeskarbw.logika.Zadanie;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SzukajSkarb extends FragmentActivity implements OnMapReadyCallback, LocationListener, SensorEventListener {

    private GoogleMap mMap;
    private Mapa mapa;
    Location mLastLocation;
    Marker wskaznikNastepnegoPukntyKontrolnego;
    private LocationManager locationManager;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometr;
    private Sensor mojaBliskosc;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    AlertDialog ff15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mapa = new Mapa("Plan","jaki","test");
        mapa.dodajPunktKontrolny(new PunktKontrolny(new LatLng(51.8554,19.3930),"Start",null));
        mapa.dodajPunktKontrolny(new PunktKontrolny(new LatLng(51.8555,19.3906),"P1",new Zadanie(1,2,"sprytny","0")));
        mapa.dodajPunktKontrolny(new PunktKontrolny(new LatLng(51.8563,19.3905),"P2",new Zadanie(2,2,"sprytny","0")));
        mapa.dodajPunktKontrolny(new PunktKontrolny(new LatLng(51.8563,19.3915),"Koniec",new Zadanie(3,2,"sprytny","0")));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szukaj_skarb);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mSensorManager =
                (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer =
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometr = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mojaBliskosc = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, this); //co 2 sekundy czulosc 1 metr
        test.start();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(mapa.pobierzPunktKontrolny(0).getWspolrzedneGeograficznePunktuKontrolnego());
            markerOptions.title("Start");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            wskaznikNastepnegoPukntyKontrolnego = mMap.addMarker(markerOptions);
        }
    }
    private boolean dotarlemDoPunktuKontrolego = false;
    private boolean z1 = false;
    private boolean z2 = false;
    private boolean z3 = false;
    private int rodzajZadania = 0;
    final int[] i = {0};
    @Override
    public void onLocationChanged(@NonNull Location location) {
        mLastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
        mMap.animateCamera(cameraUpdate);

        if (czyZnajdujeSieBliskoPunktu(latLng,mapa.pobierzPunktKontrolny(i[0]).getWspolrzedneGeograficznePunktuKontrolnego())) {
            if (i[0] == 0) {
                ff15 = new AlertDialog.Builder(this).create();
                ff15.setTitle("Start");
                ff15.setMessage("Gratulacje poszukiwaczu dotarłeś na start. Teraz musisz przejść do punktu kontrolnego który pokazał ci się na mapie. Wykonuj zadania i zdobądż skarb");
                ff15.setButton(AlertDialog.BUTTON_NEUTRAL, "Do dzieła", (dialog, which) -> ff15.dismiss());
                ff15.show();
                i[0]++;
                markerOptions.position(mapa.pobierzPunktKontrolny(i[0]).getWspolrzedneGeograficznePunktuKontrolnego());
                markerOptions.title(mapa.pobierzPunktKontrolny(i[0]).getNazwa());
                wskaznikNastepnegoPukntyKontrolnego.remove();
                wskaznikNastepnegoPukntyKontrolnego = mMap.addMarker(markerOptions);
            } else {
                dotarlemDoPunktuKontrolego = true;
                z1 = false;
                rodzajZadania = mapa.pobierzPunktKontrolny(i[0]).getZadanie().getRodzajZadania();
                ff15 = new AlertDialog.Builder(this).create();
                final boolean[] nieRobie = {false};
                ff15.setTitle("Kolejny punkt kontrolny");
                ff15.setMessage("Gratulacje poszukiwaczu dotarłeś do kolejnego punktu kontrolnego, aby kontynuować swoją przygode musisz wykonac następujace zadanie: " +
                        mapa.pobierzPunktKontrolny(i[0]).getZadanie().getTrescZadania());
                final EditText input = new EditText(this);
                if (mapa.pobierzPunktKontrolny(i[0]).getZadanie().getRodzajZadania() == 3) {
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    ff15.setView(input);
                    ff15.setButton(AlertDialog.BUTTON_POSITIVE,"Sprawdź", ((dialog, which) -> {
                        if (input.getText().toString().equals(mapa.pobierzPunktKontrolny(i[0]).getZadanie().getWynikZaliczajacy())){
                            Toast.makeText(getApplicationContext(),"Brawo!!! to prawidłowa odpowiedź, kontynułuj swoją przygodę i odnajdz wspaniały skarb",Toast.LENGTH_LONG).show();
                            ff15.dismiss();
                        }
                    }));
                }
                ff15.setButton(AlertDialog.BUTTON_NEGATIVE, "Sprobuję nastepnym razem", (dialog, which) -> {
                    nieRobie[0] = true;
                    Toast.makeText(getApplicationContext(), "Anulowałeś zadanie, możesz ponowić próbe klikajac na znaczek zadania, bądz wrocic tutaj nastepnym razem", Toast.LENGTH_LONG).show();
                    ff15.dismiss();
                });
                ff15.setOnDismissListener(dialog -> {
                    if (!nieRobie[0]) {
                        if (i[0] == mapa.iloscPunktowKontrolnych()-1) {
                            AlertDialog koniec = new AlertDialog.Builder(this).create();
                            koniec.setTitle("KONIEC");
                            koniec.setMessage("Gratulacje poszukiwaczu dotarleś do końca. Twój skarb powinieneś znaleźć");  //todo + instrukcje czym jest i jak odnalezc skarb
                                                                                                                            //todo dodac taka aktywnosci do klasy dodaj skarb na koniec kiedy nazywa sie mape
                                                                                                                            //todo dodac pole do klasy mapa i zmodyfikosc metdode odczytujace zapis z bazy
                            //todo textView'y przestawiajace statystyki szukania skarbu itp
                            //todo ewentualna mozliwosc oceny mapy, zgloszenia autora mapy o oszustwie
                            koniec.setButton(AlertDialog.BUTTON_NEUTRAL,"HURA!!",((dialog1, which) -> {
                                koniec.dismiss();
                                //todo metoda przestawiajaca mape do archiwum
                                finish();
                            }));

                        } else {
                            i[0]++;
                            markerOptions.position(mapa.pobierzPunktKontrolny(i[0]).getWspolrzedneGeograficznePunktuKontrolnego());
                            markerOptions.title(mapa.pobierzPunktKontrolny(i[0]).getNazwa());
                            wskaznikNastepnegoPukntyKontrolnego.remove();
                            wskaznikNastepnegoPukntyKontrolnego = mMap.addMarker(markerOptions);
                        }
                    }
                });
                ff15.show();
            }
        }
    }

    //todo przerwanie poszukiwan, info na ten temat, mozliwosc zgloszenie tworcy mapy badz samej mapy

    private boolean czyZnajdujeSieBliskoPunktu(LatLng P1,LatLng P2)  {
        double R = 6378137.0;
        double odlegloscE = Math.toRadians(P2.latitude-P1.latitude);
        double odlegloscN = Math.toRadians(P2.longitude-P1.longitude);
        double a = Math.sin(odlegloscE / 2) * Math.sin(odlegloscE / 2) +
            Math.cos(Math.toRadians(P1.latitude)) * Math.cos(Math.toRadians(P2.latitude)) *
            Math.sin(odlegloscN / 2) * Math.sin(odlegloscN / 2);
        double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        double d = R *c;
        return d <= 5;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            float acceleration = (float) Math.sqrt(x*x + y*y + z*z) -
                    SensorManager.GRAVITY_EARTH;
            if (acceleration > 3) {
                z1 = true;
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            if (x > 100 || y > 100 || z > 100) {
                z2 = true;
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            float x = event.values[0];
            if (x <= 2) {
                z3 = true;
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,mAccelerometer,SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this,mMagnetometr,SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this,mojaBliskosc,SensorManager.SENSOR_DELAY_UI);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Thread test = new Thread(() -> {
        while (true) {
            synchronized (this) {
                while (dotarlemDoPunktuKontrolego) {
                    switch (rodzajZadania) {
                        case 0:
                            try {
                                if (z1) {
                                    dotarlemDoPunktuKontrolego = false;
                                    z1 = false;
                                    ff15.dismiss();
                                    Thread.sleep(1000);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 1:
                            try {
                                if (z2) {
                                    dotarlemDoPunktuKontrolego = false;
                                    z2 = false;
                                    ff15.dismiss();
                                    Thread.sleep(1000);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                if (z3) {
                                    dotarlemDoPunktuKontrolego = false;
                                    z3 = false;
                                    ff15.dismiss();
                                    Thread.sleep(1000);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            }
        }
    });

}