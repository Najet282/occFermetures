package com.example.occfermetures.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.occfermetures.databinding.ActivityNewCategorieBinding;
import com.example.occfermetures.utilitaire.WSUtils;

public class NewCategorieActivity extends AppCompatActivity {

    /*************************     ATTRIBUTS     ****************************/

    //composants graphiques
    private ActivityNewCategorieBinding binding;
    //données
    private String nomCategorie;
    private String detailCat;
    public String paramLoginUser;

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewCategorieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l'on récupère de OptionsActivity
        paramLoginUser = getIntent().getStringExtra("sendLoginUser");
    }

    /*****************     REDIRECTIONS CLIC SUR BOUTON    ******************/

    public void onClickBtLogoAccueil(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        //passage de paramètre à envoyer dans MenuActivity
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    public void onClickBtRetour(View view) {
        Intent intent = new Intent(this, OptionsActivity.class);
        //passage de paramètre à envoyer dans OptionsActivity
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    public void onClickBtSave(View view) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //recuperation des contenus des champs de la categorie a creer
                nomCategorie = binding.etNomCat.getText().toString();
                detailCat = binding.etDetail.getText().toString();
            }
        });
        new Thread() {
            @Override
            public void run() {
                try {
                    WSUtils.createCategorie(nomCategorie, detailCat);
                    Intent intent = new Intent(NewCategorieActivity.this, OptionsActivity.class);
                    //passage de paramètre à envoyer dans OptionsActivity
                    intent.putExtra("sendLoginUser", paramLoginUser);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}