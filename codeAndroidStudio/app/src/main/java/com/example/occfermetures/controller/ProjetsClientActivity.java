package com.example.occfermetures.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.occfermetures.databinding.ActivityProjetsClientBinding;
import com.example.occfermetures.model.Client;
import com.example.occfermetures.model.Projet;
import com.example.occfermetures.utilitaire.WSUtils;
import com.example.occfermetures.view.ProjetAdapter;

import java.util.ArrayList;

public class ProjetsClientActivity extends AppCompatActivity implements ProjetAdapter.OnProjetAdapterListener {

    //composants graphiques
    private ActivityProjetsClientBinding binding;

    //données
    private final ArrayList<Projet> data = new ArrayList<>();
    private Client client;
    public Long paramIdClient;
    public String paramLoginUser;

    //outils
    private ProjetAdapter adapter = new ProjetAdapter(data);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjetsClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l on recupere de FicheClientActivity
        paramIdClient = getIntent().getExtras().getLong("sendIdClient");
        paramLoginUser = getIntent().getStringExtra("sendLoginUser");

        //on recupere les infos du client
        new Thread() {
            @Override
            public void run() {
                try {
                    client = WSUtils.getInfosClient(paramIdClient);
                    //on affiche le nom du client
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.tvNomClient.setText(client.getNom() + " " + client.getPrenom());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //on transmet l adapter au recycler view
        binding.rv.setAdapter(adapter);
        //on parametre l affichage de notre recycler view
        binding.rv.setLayoutManager(new GridLayoutManager(this, 1));

        adapter.setOnProjetAdapterListener(this);

        new Thread() {
            @Override
            public void run() {
                try {
                    //on stocke dans une liste le contenu de la requete qui recupere tous les projets du client
                    ArrayList<Projet> listProjets = WSUtils.getAllProjetsClient(paramIdClient);
                    //si le client a des projets on les affiche en rendant la visibilité au recyclerView
                    if (!listProjets.isEmpty()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.rv.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    //on vide la liste qui stocke a chaque requete les projets
                    data.clear();
                    //on la remplit avec les nouvelles donnees de la bdd
                    data.addAll(listProjets);
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

    public void onClickBtRetour(View view) {
        Intent intent = new Intent(this, FicheClientActivity.class);
        //passage de paramètres à envoyer dans FicheClientActivity
        intent.putExtra("sendIdClient", paramIdClient);
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }


    public void onClickBtAddNewProjet(View view) {
        Intent intent = new Intent(this, NewProjetByClient.class);
        //passage de paramètres à envoyer dans NewProjetByClient
        intent.putExtra("sendIdClient", paramIdClient);
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    @Override
    public void onClick(Projet projet) {
        Intent intent = new Intent(this, SousProjetActivity.class);
        //passage de paramètres à envoyer dans SousProjetActivity
        intent.putExtra("sendIdProjet", projet.getIdProjet());
        intent.putExtra("sendNomProjet", projet.getNom());
        intent.putExtra("sendDateProjet", projet.getDate());
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }
}