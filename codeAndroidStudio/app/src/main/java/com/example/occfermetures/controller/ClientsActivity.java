package com.example.occfermetures.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.occfermetures.databinding.ActivityClientsBinding;
import com.example.occfermetures.model.Client;
import com.example.occfermetures.utilitaire.WSUtils;
import com.example.occfermetures.view.ClientAdapter;

import java.util.ArrayList;

public class ClientsActivity extends AppCompatActivity implements ClientAdapter.OnClientAdapterListener {

    /*************************     ATTRIBUTS     ****************************/

    //composants graphiques
    private ActivityClientsBinding binding;

    //données
    private final ArrayList<Client> data = new ArrayList<>();
    private String filtre;
    public String paramLoginUser;

    //outils
    private ClientAdapter adapter = new ClientAdapter(data);

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l'on récupère de MainActivity
        paramLoginUser = getIntent().getStringExtra("sendLoginUser");

        //on transmet l adapter au recycler view
        binding.rv.setAdapter(adapter);
        //on parametre l affichage de notre recycler view
        binding.rv.setLayoutManager(new GridLayoutManager(this, 1));

        adapter.setOnClientAdapterListener(this);

        new Thread() {
            @Override
            public void run() {
                try {
                    //on stocke dans une liste le contenu de la requete qui recupere tous les clients de la bdd
                    ArrayList<Client> list = WSUtils.getAllClients();
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
                                        ArrayList<Client> list = WSUtils.getClientsWithFilter(filtre);
                                        //on vide la liste qui stocke a chaque requete les clients de la bdd
                                        data.clear();
                                        //on la remplit avec les nouvelles donnees de la bdd
                                        data.addAll(list);
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
                    ArrayList<Client> list = WSUtils.getClientsWithFilter(filtre);
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

    public void onClickBtAddNewClient(View view) {
        Intent intent = new Intent(this, NewClientActivity.class);
        //passage de paramètre à envoyer dans NewClientActivity
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    @Override
    public void onClick(Client client) {
        Intent intent = new Intent(this, FicheClientActivity.class);
        //passage de paramètres à envoyer dans FicheClientActivity
        intent.putExtra("sendIdClient", client.getIdClient());
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    //TODO gerer le recyclerview avec plusieurs elements cliquables
}