package com.example.occfermetures.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.occfermetures.databinding.ActivityNewUtilisateurBinding;
import com.example.occfermetures.model.Utilisateur;
import com.example.occfermetures.utilitaire.WSUtils;
import com.example.occfermetures.view.UtilisateurAdapter;

import java.util.ArrayList;

public class GestionComptesUtilisateurActivity extends AppCompatActivity implements UtilisateurAdapter.OnUtilisateurAdapterListener {

    /*************************     ATTRIBUTS     ****************************/

    //composants graphiques
    private ActivityNewUtilisateurBinding binding;

    //données
    private final ArrayList<Utilisateur> data = new ArrayList<>();
    private int nomSize;
    private int mdpSize;
    private String nom;
    private String mdp;
    public String paramLoginUser;

    //outils
    private UtilisateurAdapter adapter = new UtilisateurAdapter(data);

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewUtilisateurBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l on recupere de OptionsActivity
        paramLoginUser = getIntent().getStringExtra("sendLoginUser");

        //on transmet l adapter au recycler view
        binding.rv.setAdapter(adapter);
        //on parametre l affichage de notre recycler view
        binding.rv.setLayoutManager(new GridLayoutManager(this, 1));

        adapter.setOnUtilisateurAdapterListener(this);

        new Thread() {
            @Override
            public void run() {
                try {
                    //on stocke dans une liste le contenu de la requete qui recupere tous les clients de la bdd
                    ArrayList<Utilisateur> list = WSUtils.getAllUsers();
                    //on vide la liste qui stocke a chaque requete les clients de la bdd
                    data.clear();
                    //on la remplit avec les nouvelles donnees de la bdd
                    data.addAll(list);
                    //on actualise le RecyclerView en informant notre adapter que les données ont changées pour ré-afficher la liste en fonction des nouvelles données
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
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
                            //creation du client en le stockant pour reutiliser ses donnees
                            WSUtils.createUser(nom, mdp);
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

    @Override
    public void onClick(Utilisateur utilisateur) {
        //on affiche une confirmation de vouloir supprimer le client
        onCreateDialogConfirmationDelete(utilisateur);
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