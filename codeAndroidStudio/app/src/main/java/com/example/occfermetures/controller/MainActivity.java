package com.example.occfermetures.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.occfermetures.databinding.ActivityMainBinding;
import com.example.occfermetures.model.Client;
import com.example.occfermetures.model.Utilisateur;
import com.example.occfermetures.utilitaire.WSUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*************************     ATTRIBUTS     ****************************/

    //composants graphiques
    private ActivityMainBinding binding;

    //données
    private int loginSize;
    private int mdpSize;
    private String login;
    private String mdp;

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bloquer la vue en mode portrait uniquement a faire avant le setContentView
        //setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //permet d activer la recherche de nom avec filtre avec la touche entree du clavier
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.etMdp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                            seConnecter();
                        }
                        return false;
                    }
                });
            }
        });

    }

    /*****************     REDIRECTIONS CLIC SUR BOUTON    ******************/

    public void onClickBtSeConnecter(View view) throws Exception {
        seConnecter();
    }


    /**********************     METHODES DEPORTEES POUR ALLEGER LE CODE    **************************/

    //utilisee pour se connecter lors du clic sur le bouton ou de la touche entree
    public void seConnecter() {
        //on commence par cacher les eventuels precedents msg d erreur
        hideErrorMsg();

        //methode qui stocke la longueur des donnees saisies par l utilisateur et leur contenu
        recupContenuCompGraph();

        //si tous les champs sont remplis
        if (loginSize > 0 && mdpSize > 0) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        //on execute la requete qui controle les identifiants saisis
                        WSUtils.checkLogin(login, mdp);
                        Utilisateur utilisateur = WSUtils.getInfosUtilisateur(login);
                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        //passage de paramètre à envoyer dans MenuActivity et qui sera utilise tout le long de l utilisation de l appli pour autoriser ou non l acces a certaines fonctionnalites
                        intent.putExtra("sendLoginUser", utilisateur.getLogin());
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        showErrorMsg(e.getMessage());
                    }
                }
            }.start();
        } else {
            createDialog("Veuillez saisir votre login et votre mot de passe.");
        }

    }

    //création d'une popup affichant un message
    public void createDialog(String text) {
        AlertDialog ad = new AlertDialog.Builder(this)
                .setPositiveButton("Ok", null).setMessage(text).create();
        ad.show();
    }

    public void recupContenuCompGraph() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //calcul de la taille des champs qui doivent etre saisis avant de valider la connexion
                loginSize = binding.etLogin.getText().length();
                mdpSize = binding.etMdp.getText().length();
                login = binding.etLogin.getText().toString();
                mdp = binding.etMdp.getText().toString();
            }
        });
    }

    //cette methode utilise un runOnUiThread car fait appel a des composants graphiques
    public void showErrorMsg(String errorMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tvError.setVisibility(View.VISIBLE);
                binding.tvError.setText(errorMsg);
            }
        });
    }

    //rend le champ error invisible
    public void hideErrorMsg() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tvError.setVisibility(View.INVISIBLE);
            }
        });
    }

}