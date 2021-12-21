package com.example.occfermetures.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.occfermetures.databinding.ActivityMenuBinding;
import com.example.occfermetures.model.Client;
import com.example.occfermetures.utilitaire.WSUtils;

public class MenuActivity extends AppCompatActivity {

    /*************************     ATTRIBUTS     ****************************/

    //composants graphiques
    private ActivityMenuBinding binding;
    //données
    private boolean acces;
    public String paramLoginUser;

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l'on récupère de toutes les activites lorsqu on clique sur le bouton d accueil
        paramLoginUser = getIntent().getStringExtra("sendLoginUser");

        //si la personne qui se connecte est responsable elle aura la visibilite des menus options et comptes qui sont stockes dans linearLayoutResponsables que l on rend visible
        new Thread() {
            @Override
            public void run() {
                try {
                    acces = WSUtils.checkCodeAcces(paramLoginUser);
                    if(acces){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.linearLayoutResponsables.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /*****************     REDIRECTIONS CLIC SUR BOUTON    ******************/

    public void onClickBtClients(View view) {
        Intent intent = new Intent(this, ClientsActivity.class);
        //passage de paramètre à envoyer dans ClientsActivity
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    public void onClickBtProjets(View view) {
        Intent intent = new Intent(this, ProjetsActivity.class);
        //passage de paramètre à envoyer dans ProjetsActivity
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    public void onClickBtOptions(View view) {
        Intent intent = new Intent(this, OptionsActivity.class);
        //passage de paramètre à envoyer dans OptionsActivity
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    public void onClickBtComptes(View view) {
        Intent intent = new Intent(MenuActivity.this, GestionComptesUtilisateurActivity.class);
        //passage de paramètre à envoyer dans GestionComptesUtilisateurActivity
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

}