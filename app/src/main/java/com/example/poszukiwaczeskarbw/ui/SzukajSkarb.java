/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2020. All rights reserved
 * Last modified 23.12.20 20:29
 */

package com.example.poszukiwaczeskarbw.ui;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.poszukiwaczeskarbw.R;
import com.example.poszukiwaczeskarbw.logika.Mapa;
import com.example.poszukiwaczeskarbw.logika.Zadanie;
import com.google.android.gms.common.api.GoogleApiClient;
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

public class SzukajSkarb extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private Mapa mapa;
    Location mLastLocation;
    Marker wskaznikNastepnegoPukntyKontrolnego;
    private LocationManager locationManager;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szukaj_skarb);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        mLastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        wskaznikNastepnegoPukntyKontrolnego = mMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
        mMap.animateCamera(cameraUpdate);

        int i =0;
        if (location.getLatitude() == mapa.pobierzPunktKontrolny(i).getWspolrzednaSzerokosciGeograficznejPunktuKontrolnego() && location.getLongitude() == mapa.pobierzPunktKontrolny(i).getWspolrzednaWysokosciGeograficznejPunktuKontrolnego()){
            if (dojscieDoKolejnegoPunktuKontrolnego(mapa.pobierzPunktKontrolny(i).getZadanie())){
                i++;
                markerOptions.position(new LatLng(mapa.pobierzPunktKontrolny(i).getWspolrzednaSzerokosciGeograficznejPunktuKontrolnego(),mapa.pobierzPunktKontrolny(i).getWspolrzednaWysokosciGeograficznejPunktuKontrolnego()));
                markerOptions.title(mapa.pobierzPunktKontrolny(i).getNazwa());
                wskaznikNastepnegoPukntyKontrolnego.remove();
                wskaznikNastepnegoPukntyKontrolnego = mMap.addMarker(markerOptions);
            }
        }
    }

    private boolean dojscieDoKolejnegoPunktuKontrolnego(Zadanie zadanieKontrolne){

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

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

}