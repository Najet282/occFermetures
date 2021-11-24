package com.example.occfermetures.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.occfermetures.databinding.ActivityNewClientBinding;
import com.example.occfermetures.model.Client;
import com.example.occfermetures.utilitaire.WSUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewClientActivity extends AppCompatActivity {

    /*************************     ATTRIBUTS     ****************************/

    //composants graphiques
    private ActivityNewClientBinding binding;
    //données
    private int nomSize;
    private int prenomSize;
    private int telSize;
    private int emailSize;
    private int adresseSize;
    private int cpSize;
    private int villeSize;
    private String nom;
    private String prenom;
    private String tel;
    private String email;
    private String adresse;
    private String cp;
    private String ville;
    public String paramLoginUser;

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l'on récupère de ClientsActivity
        paramLoginUser = getIntent().getStringExtra("sendLoginUser");
    }

    /*****************     REDIRECTIONS CLIC SUR BOUTON    ******************/

    public void onClickBtLogoAccueil(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        //passage de paramètre à envoyer dans MenuActivity
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    public void onClickBtSave(View view) {

        //on commence par cacher les eventuels precedents msg d erreur
        hideErrorMsg();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //calcul de la taille des champs qui doivent etre saisis avant de valider la creation du nouveau client
                nomSize = binding.etNom.getText().length();
                prenomSize = binding.etPrenom.getText().length();
                telSize = binding.etTel.getText().length();
                emailSize = binding.etEmail.getText().length();
                adresseSize = binding.etAdresse.getText().length();
                cpSize = binding.etCp.getText().length();
                villeSize = binding.etVille.getText().length();
                //recuperation des contenus des champs de la creation du nouveau client
                nom = binding.etNom.getText().toString();
                prenom = binding.etPrenom.getText().toString();
                tel = binding.etTel.getText().toString();
                email = binding.etEmail.getText().toString();
                adresse = binding.etAdresse.getText().toString();
                cp = binding.etCp.getText().toString();
                ville = binding.etVille.getText().toString();
            }
        });

        //si tous les champs sont remplis
        if (nomSize > 0 && prenomSize > 0 && telSize > 0 && emailSize > 0 && adresseSize > 0 && cpSize > 0 && villeSize > 0) {
            //si le format de l email est correct
            if (isEmailAdress(email)) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            //requete qui verifie que le client n est pas deja cree en controlant l existence ou non de l email saisi
                            //cette requete retournera du texte ("Email inexistant") si l email est inexsistant et levera une exception si l email existe deja
                            String emailRecup = WSUtils.checkEmail(email);
                            //si la requete a retourné du texte
                            if (emailRecup != null) {
                                //creation du client en le stockant pour reutiliser ses donnees
                                Client createdClient = WSUtils.createClient(nom, prenom, tel, email, adresse, cp, ville);
                                //redirection vers ProjetClient en utilisant les donnees du client
                                Intent intent = new Intent(NewClientActivity.this, FicheClientActivity.class);
                                //passage de paramètre à envoyer dans FicheClientActivity
                                intent.putExtra("sendIdClient", createdClient.getIdClient());
                                intent.putExtra("sendLoginUser", paramLoginUser);
                                startActivity(intent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showErrorMsg(e.getMessage());
                        }
                    }
                }.start();


            } else {
                createDialog("Format de l'email incorrect.");
            }

        } else {
            createDialog("Veuillez remplir tous les champs.");
        }
    }

    public void onClickBtRetour(View view) {
        Intent intent = new Intent(this, ClientsActivity.class);
        //passage de paramètre à envoyer dans ClientsActivity
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }


    /**********************     METHODES DEPORTEES POUR ALLEGER LE CODE    **************************/

    public boolean isEmailAdress(String email) {
        Pattern p = Pattern
                .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = p.matcher(email.toUpperCase());
        return m.matches();
    }

    //création d'une popup affichant un message
    private void createDialog(String text) {
        AlertDialog ad = new AlertDialog.Builder(this)
                .setPositiveButton("Ok", null).setMessage(text).create();
        ad.show();
    }

    //cette methode utilise un runOnUiThread car fait appel a des composants graphiques
    //rend le champ error visible avec le texte saisi en parametre
    private void showErrorMsg(String errorMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tvError.setVisibility(View.VISIBLE);
                binding.tvError.setText(errorMsg);
            }
        });
    }

    //rend le champ error invisible
    private void hideErrorMsg() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tvError.setVisibility(View.INVISIBLE);
            }
        });
    }

}