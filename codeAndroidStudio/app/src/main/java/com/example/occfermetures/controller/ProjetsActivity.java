package com.example.occfermetures.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.occfermetures.databinding.ActivityProjetsBinding;
import com.example.occfermetures.model.Client;
import com.example.occfermetures.model.Projet;
import com.example.occfermetures.utilitaire.WSUtils;
import com.example.occfermetures.view.MultiAdapter;

import java.util.ArrayList;

public class ProjetsActivity extends AppCompatActivity implements MultiAdapter.OnMultiAdapterListener {

    /*************************     ATTRIBUTS     ****************************/

    //composants graphiques
    private ActivityProjetsBinding binding;

    //données
    private final ArrayList<Projet> dataProjet = new ArrayList<>();
    private final ArrayList<Client> dataClient = new ArrayList<>();
    private String filtre;
    public String paramLoginUser;

    //outils
    private MultiAdapter adapter = new MultiAdapter(dataProjet, dataClient);

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l on recupere de MenuActivity
        paramLoginUser = getIntent().getStringExtra("sendLoginUser");

        //on transmet l adapter au recycler view
        binding.rv.setAdapter(adapter);
        //on parametre l affichage de notre recycler view
        binding.rv.setLayoutManager(new GridLayoutManager(this, 1));

        adapter.setOnMultiAdapterListener(this);

        new Thread() {
            @Override
            public void run() {
                try {
                    //on stocke dans une liste le contenu de la requete qui recupere tous les projets de l entreprise puis tous les clients
                    ArrayList<Projet> listProjets = WSUtils.getAllProjets();
                    ArrayList<Client> listClients = WSUtils.getAllClients();
                    //si l entreprise a des projets on les affiche en rendant la visibilité au recyclerView
                    if (!listProjets.isEmpty()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.rv.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    //on vide la liste qui stocke a chaque requete les projets
                    dataProjet.clear();
                    dataClient.clear();
                    //on la remplit avec les nouvelles donnees de la bdd
                    dataProjet.addAll(listProjets);
                    dataClient.addAll(listClients);
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

        //permet d activer la recherche de nom avec filtre avec la touche entree du clavier
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.etName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                            filtre = binding.etName.getText().toString();
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        //on stocke dans une liste le contenu de la requete qui recupere les clients de la bdd contenant le filtre saisi
                                        ArrayList<Client> listClients = WSUtils.getClientsWithFilter(filtre);
                                        //on initialise la liste qui recupere les projets des clients filtres
                                        ArrayList<Projet> listProjets = new ArrayList<>();
                                        //on parcours la liste des clients filtres
                                        for (int i = 0; i < listClients.size(); i++) {
                                            //on recupere les projets des clients filtres
                                            ArrayList<Projet> listProjetsClient = WSUtils.getAllProjetsClient(listClients.get(i).getIdClient());
                                            //que l on ajoute a la liste utilisee lors de l affichage du recyclerview
                                            listProjets.addAll(listProjetsClient);
                                        }

                                        //on vide la liste qui stocke a chaque requete les clients de la bdd
                                        dataProjet.clear();
                                        dataClient.clear();
                                        //on la remplit avec les nouvelles donnees de la bdd
                                        dataProjet.addAll(listProjets);
                                        dataClient.addAll(listClients);
                                        //on actualise le RecyclerView en informant notre adapter que les données ont changées pour ré-afficher la liste en fonction des nouvelles données
                                        adapter.notifyDataSetChanged();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                        }
                        return false;
                    }
                });
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

    public void onClickBtSearch(View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                filtre = binding.etName.getText().toString();
            }
        });
        new Thread() {
            @Override
            public void run() {
                try {
                    //on stocke dans une liste le contenu de la requete qui recupere les clients de la bdd contenant le filtre saisi
                    ArrayList<Client> listClients = WSUtils.getClientsWithFilter(filtre);
                    //on initialise la liste qui recupere les projets des clients filtres
                    ArrayList<Projet> listProjets = new ArrayList<>();
                    //on parcours la liste des clients filtres
                    for (int i = 0; i < listClients.size(); i++) {
                        //on recupere les projets des clients filtres
                        ArrayList<Projet> listProjetsClient = WSUtils.getAllProjetsClient(listClients.get(i).getIdClient());
                        //que l on ajoute a la liste utilisee lors de l affichage du recyclerview
                        listProjets.addAll(listProjetsClient);
                    }

                    //on vide la liste qui stocke a chaque requete les clients de la bdd
                    dataProjet.clear();
                    dataClient.clear();
                    //on la remplit avec les nouvelles donnees de la bdd
                    dataProjet.addAll(listProjets);
                    dataClient.addAll(listClients);
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

    public void onClickBtAddNewProjet(View view) {
        Intent intent = new Intent(this, NewProjetByProjet.class);
        //passage de paramètre à envoyer dans NewProjetByProjet
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    @Override
    public void onClick(Projet projet) {
        Intent intent = new Intent(this, SousProjetActivity.class);
        //passage de paramètre à envoyer dans l activite SousProjetActivity
        intent.putExtra("sendIdProjet", projet.getIdProjet());
        intent.putExtra("sendNomProjet", projet.getNom());
        intent.putExtra("sendDateProjet", projet.getDate());
        intent.putExtra("sendIdClient", projet.getIdClient());
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }
}