package com.example.occfermetures.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.occfermetures.databinding.ActivityModifFicheClientBinding;
import com.example.occfermetures.utilitaire.WSUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifFicheClientActivity extends AppCompatActivity {

    /*************************     ATTRIBUTS     ****************************/

    //composants graphiques
    private ActivityModifFicheClientBinding binding;
    //donnees
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
    public Long paramIdClient;
    public String paramLoginUser;

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModifFicheClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l'on récupère de FicheClientActivity
        String paramNom = getIntent().getStringExtra("sendNom");
        String paramPrenom = getIntent().getStringExtra("sendPrenom");
        String paramAdresse = getIntent().getStringExtra("sendAdresse");
        String paramCp = getIntent().getStringExtra("sendCp");
        String paramVille = getIntent().getStringExtra("sendVille");
        String paramEmail = getIntent().getStringExtra("sendEmail");
        String paramTel = getIntent().getStringExtra("sendTel");
        paramIdClient = getIntent().getExtras().getLong("sendIdClient");
        paramLoginUser = getIntent().getStringExtra("sendLoginUser");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.etNom.setText(paramNom);
                binding.etPrenom.setText(paramPrenom);
                binding.etTel.setText(paramTel);
                binding.etEmail.setText(paramEmail);
                binding.etAdresse.setText(paramAdresse);
                binding.etCp.setText(paramCp);
                binding.etVille.setText(paramVille);
            }
        });
    }

    /*****************     REDIRECTIONS CLIC SUR BOUTON    ******************/

    public void onClickBtLogoAccueil(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        //passage de paramètre à envoyer dans MenuActivity
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    public void onClickBtRetour(View view) {
        Intent intent = new Intent(this, FicheClientActivity.class);
        //passage de paramètre à envoyer dans FicheClientActivity
        intent.putExtra("sendIdClient", paramIdClient);
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    public void onClickBtSave(View view) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //calcul de la taille des champs qui doivent etre saisis avant de valider la modif du client
                nomSize = binding.etNom.getText().length();
                prenomSize = binding.etPrenom.getText().length();
                telSize = binding.etTel.getText().length();
                emailSize = binding.etEmail.getText().length();
                adresseSize = binding.etAdresse.getText().length();
                cpSize = binding.etCp.getText().length();
                villeSize = binding.etVille.getText().length();
                //recuperation des contenus des champs
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
        if (nomSize > 0 && prenomSize > 0 && telSize > 0 && emailSize > 0 && adresseSize > 0) {
            if (isEmailAdress(email)) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            WSUtils.modifClient(paramIdClient, nom, prenom, tel, email, adresse, cp, ville);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                //redirection vers FicheClient
                Intent intent = new Intent(ModifFicheClientActivity.this, FicheClientActivity.class);
                //passage de paramètres à envoyer dans FicheClientACtivity
                intent.putExtra("sendIdClient", paramIdClient);
                intent.putExtra("sendLoginUser", paramLoginUser);
                startActivity(intent);
            } else {
                createDialog("Format de l'email incorrect.");
            }
        } else {
            createDialog("Veuillez remplir tous les champs.");
        }
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

}