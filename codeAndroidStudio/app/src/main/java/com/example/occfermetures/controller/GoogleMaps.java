package com.example.occfermetures.controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.occfermetures.databinding.ActivityGoogleMapsBinding;

public class GoogleMaps extends AppCompatActivity {

    private ActivityGoogleMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGoogleMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Thread() {
            @Override
            public void run() {
                try {
                    //controle de la permission: si on l'a on appelle showWeather
                    if (ContextCompat.checkSelfPermission(GoogleMaps.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        //on recupere les coordonnées géographiques de notre position
                        //Location myLocalisation = LocationUtils.getLastKnownLocation(GoogleMaps.this);
                        //Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr="+myLocalisation.getLatitude()+","+myLocalisation.getLongitude()+"&daddr=43.8656,1.505");
                        //Uri gmmIntentUri = Uri.parse("geo: 43.7150237, 1.4222381");

                        //Uri gmmIntentUri = Uri.parse("google.navigation:q=Super U + Bruguieres + France");
                        //Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        //mapIntent.setPackage("com.google.android.apps.maps");
                        //startActivity(mapIntent);
                        //sinon on la demande
                    } else {
                        ActivityCompat.requestPermissions(GoogleMaps.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}