package com.example.occfermetures.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.occfermetures.databinding.ActivityImgPleinEcranBinding;
import com.example.occfermetures.utilitaire.ConversionBitmapTexte;

public class ImgPleinEcran extends AppCompatActivity {

    /*************************     ATTRIBUTS     ****************************/

    private ActivityImgPleinEcranBinding binding;

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImgPleinEcranBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l'on récupère de FicheSousProjetActivity
        String paramImg = getIntent().getStringExtra("sendImg");

        //affichage de l image que l on recupere en String et que l on convertit en Bitmap
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.imgView.setImageBitmap(ConversionBitmapTexte.stringToBitmap(paramImg));
            }
        });

    }


}