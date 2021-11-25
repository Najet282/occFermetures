package com.example.occfermetures.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.occfermetures.databinding.ActivitySousProjetBinding;
import com.example.occfermetures.model.Categorie;
import com.example.occfermetures.model.SousProjet;
import com.example.occfermetures.utilitaire.WSUtils;
import com.example.occfermetures.view.SousProjetAdapter;

import java.util.ArrayList;

public class SousProjetActivity extends AppCompatActivity implements SousProjetAdapter.OnSousProjetAdapterListener {

    /*************************     ATTRIBUTS     ****************************/

    //composants graphiques
    private ActivitySousProjetBinding binding;

    //données
    private final ArrayList<SousProjet> dataSousProjet = new ArrayList<>();
    private final ArrayList<Categorie> dataCategorie = new ArrayList<>();
    public String paramNomProjet;
    public String paramDateProjet;
    public Long paramIdProjet;
    public Long paramIdClient;
    public String paramLoginUser;

    //outils
    private SousProjetAdapter adapter = new SousProjetAdapter(dataSousProjet, dataCategorie);

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySousProjetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l'on récupère de ProjetsActivity ou tous les NewProjet
        paramIdProjet = getIntent().getExtras().getLong("sendIdProjet");
        paramDateProjet = getIntent().getStringExtra("sendDateProjet");
        paramNomProjet = getIntent().getStringExtra("sendNomProjet");
        paramIdClient = getIntent().getExtras().getLong("sendIdClient");
        paramLoginUser = getIntent().getStringExtra("sendLoginUser");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tvNomProjet.setText(paramNomProjet);
                binding.tvDate.setText(paramDateProjet);
            }
        });

        //on transmet l adapter au recycler view
        binding.rv.setAdapter(adapter);
        //on parametre l affichage de notre recycler view
        binding.rv.setLayoutManager(new GridLayoutManager(this, 1));

        adapter.setOnSousProjetAdapterListener(this);


        new Thread() {
            @Override
            public void run() {
                try {
                    //on stocke dans une liste le contenu de la requete qui recupere tous les sous projets du client puis toutes les categories existantes
                    ArrayList<SousProjet> listSousProjets = WSUtils.getAllSousProjetsDunProjet(paramIdProjet);
                    ArrayList<Categorie> listCategories = WSUtils.getAllCategories();
                    //si le client a des projets existants on les affiche en rendant la visibilité au recyclerView
                    if (!listSousProjets.isEmpty()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.rv.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    //on vide la liste qui stocke a chaque requete les sousProjets
                    dataSousProjet.clear();
                    dataCategorie.clear();
                    //on la remplit avec les nouvelles donnees de la bdd
                    dataSousProjet.addAll(listSousProjets);
                    dataCategorie.addAll(listCategories);
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
        Intent intent = new Intent(this, ProjetsActivity.class);
        //passage de paramètre à envoyer dans ProjetsActivity
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    public void onClickBtDelete(View view) {
        onCreateDialogConfirmationDelete(view);
    }

    @Override
    public void onClick(SousProjet sousProjet) {
        Intent intent = new Intent(this, FicheSousProjetActivity.class);
        //passage de paramètres à envoyer dans l activite FicheSousProjetActivity
        intent.putExtra("sendIdSousProjet", sousProjet.getIdSousProjet());
        intent.putExtra("sendIdProjet", sousProjet.getIdProjet());
        intent.putExtra("sendNomProjet", paramNomProjet);
        intent.putExtra("sendDateProjet", paramDateProjet);
        intent.putExtra("sendIdCat", sousProjet.getIdCat());
        intent.putExtra("sendLieuCible", sousProjet.getLieu());
        intent.putExtra("sendLongueur", sousProjet.getLongueur());
        intent.putExtra("sendLargeur", sousProjet.getLargeur());
        intent.putExtra("sendDetail", sousProjet.getDetail());
        intent.putExtra("sendPhoto1", sousProjet.getPhoto1());
        intent.putExtra("sendPhoto2", sousProjet.getPhoto2());
        intent.putExtra("sendPhoto3", sousProjet.getPhoto3());
        intent.putExtra("sendPhoto4", sousProjet.getPhoto4());
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    /**********************     METHODES DEPORTEES POUR ALLEGER LE CODE    **************************/

    public void onCreateDialogConfirmationDelete(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Demande de confirmation");
        builder.setMessage("Voulez-vous supprimer le projet dans son intégralité ?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //effacement du client;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            //on recupere les infos du clients grace a l id recu de ClientsActivity ou de NewClientActivity
                            WSUtils.deleteProjet(paramIdProjet);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                //on retourne sur ProjetsActivity
                Intent intent = new Intent(SousProjetActivity.this, ProjetsActivity.class);
                //passage de paramètre à envoyer dans ProjetsActivity
                intent.putExtra("sendLoginUser", paramLoginUser);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //on retourne sur ProjetsActivity
                Intent intent = new Intent(SousProjetActivity.this, SousProjetActivity.class);
                //passage de paramètre à envoyer dans l activite SousProjetActivity
                intent.putExtra("sendIdProjet", paramIdProjet);
                intent.putExtra("sendNomProjet", paramNomProjet);
                intent.putExtra("sendDateProjet", paramDateProjet);
                intent.putExtra("sendLoginUser", paramLoginUser);
                startActivity(intent);
            }
        });
        builder.show();
    }
}
