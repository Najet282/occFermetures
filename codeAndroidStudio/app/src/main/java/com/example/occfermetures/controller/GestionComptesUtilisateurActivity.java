package com.example.occfermetures.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.occfermetures.databinding.ActivityNewUtilisateurBinding;
import com.example.occfermetures.model.Utilisateur;
import com.example.occfermetures.utilitaire.WSUtils;

public class GestionComptesUtilisateurActivity extends AppCompatActivity{

    /*************************     ATTRIBUTS     ****************************/

    //composants graphiques
    private ActivityNewUtilisateurBinding binding;

    //données
    private int nomSize;
    private int mdpSize;
    private String nom;
    private String mdp;
    public String paramLoginUser;

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewUtilisateurBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l on recupere de OptionsActivity
        paramLoginUser = getIntent().getStringExtra("sendLoginUser");
    }

    /*****************     REDIRECTIONS CLIC SUR BOUTON    ******************/

    public void onClickBtLogoAccueil(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        //passage de paramètre à envoyer dans MenuActivity
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    public void onClickBtSave(View view) {//on commence par cacher les eventuels precedents msg d erreur
        hideErrorMsg();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //calcul de la taille des champs qui doivent etre saisis avant de valider la creation du nouveau user
                nomSize = binding.etNom.getText().length();
                mdpSize = binding.etMdp.getText().length();
                //recuperation des contenus des champs de la creation du nouveau user
                nom = binding.etNom.getText().toString();
                mdp = binding.etMdp.getText().toString();
            }
        });

        //si tous les champs sont remplis
        if (nomSize > 0 &&  mdpSize> 0) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            //String hashed = BCrypt.hashpw(mdp, BCrypt.gensalt());
                            //Utilisateur utilisateur = new Utilisateur(nom, hashed);
                            //creation du client en le stockant pour reutiliser ses donnees
                            WSUtils.createUser(nom, mdp); //nom, mdp utilisateur.getLogin(), utilisateur.getMdp()
                            //redirection vers NewUtilisateurActivity apres enregistrement
                            Intent intent = new Intent(GestionComptesUtilisateurActivity.this, GestionComptesUtilisateurActivity.class);
                            //passage de paramètre à renvoyer dans cette meme activity
                            intent.putExtra("sendLoginUser", paramLoginUser);
                                startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            showErrorMsg(e.getMessage());
                        }
                    }
                }.start();

        } else {
            createDialog("Veuillez remplir tous les champs.");
        }
    }

    /**********************     METHODES DEPORTEES POUR ALLEGER LE CODE    **************************/

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

    public void onCreateDialogConfirmationDelete(Utilisateur utilisateur) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Demande de confirmation");
        builder.setMessage("Voulez-vous supprimer cet utilisateur ?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //effacement du client;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            WSUtils.deleteUser(utilisateur.getLogin());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                Toast.makeText(getApplicationContext(), utilisateur.getLogin() + " supprimé", Toast.LENGTH_SHORT).show();
                //on retourne sur GestionComptesUtilisateurActivity
                Intent intent = new Intent(GestionComptesUtilisateurActivity.this, GestionComptesUtilisateurActivity.class);
                //passage de paramètre à renvoyer dans cette meme activity
                intent.putExtra("sendLoginUser", paramLoginUser);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //on retourne sur ClientsActivity
                Intent intent = new Intent(GestionComptesUtilisateurActivity.this, GestionComptesUtilisateurActivity.class);
                //passage de paramètre à renvoyer dans cette meme activity
                intent.putExtra("sendLoginUser", paramLoginUser);
                startActivity(intent);
            }
        });
        builder.show();
    }
}