package com.example.occfermetures.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.occfermetures.databinding.ActivityFicheSousProjetBinding;
import com.example.occfermetures.model.Client;
import com.example.occfermetures.utilitaire.WSUtils;
import com.example.occfermetures.view.PhotoAdapter;

import java.util.ArrayList;

public class FicheSousProjetActivity extends AppCompatActivity implements PhotoAdapter.OnPhotoAdapterListener {

    /*************************     ATTRIBUTS     ****************************/

    //composants graphiques
    private ActivityFicheSousProjetBinding binding;

    //données
    private final ArrayList<String> data = new ArrayList<>();
    public Long paramIdSousProjet;
    public Long paramIdProjet;
    public String paramNomProjet;
    public String paramDateProjet;
    public Client clientRecup;
    public String nomPrenomClient;
    public Long paramIdCat;
    public String nomCatRecup;
    public String paramLieuCible;
    public String paramLongueur;
    public String paramLargeur;
    public String paramDetail;
    public String paramLoginUser;

    //outils
    private PhotoAdapter adapter = new PhotoAdapter(data);

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFicheSousProjetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l'on récupère de SousProjetActivity ou ModifFicheSousProjetActivity
        paramIdSousProjet = getIntent().getExtras().getLong("sendIdSousProjet");
        paramIdProjet = getIntent().getExtras().getLong("sendIdProjet");
        paramNomProjet = getIntent().getStringExtra("sendNomProjet");
        paramDateProjet = getIntent().getStringExtra("sendDateProjet");
        paramIdCat = getIntent().getExtras().getLong("sendIdCat");
        paramLieuCible = getIntent().getStringExtra("sendLieuCible");
        paramLongueur = getIntent().getStringExtra("sendLongueur");
        paramLargeur = getIntent().getStringExtra("sendLargeur");
        paramDetail = getIntent().getStringExtra("sendDetail");
        paramLoginUser = getIntent().getStringExtra("sendLoginUser");

        new Thread() {
            @Override
            public void run() {

                try {
                    clientRecup = WSUtils.getClientByIdProjet(paramIdProjet);
                    nomPrenomClient = clientRecup.getNom() + "  " + clientRecup.getNom();
                    nomCatRecup = WSUtils.getNomCategorieDunSousProjet(paramIdCat);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.tvCat.setText(nomCatRecup);
                            binding.tvLieu.setText(paramLieuCible);
                            binding.tvLongueur.setText(paramLongueur);
                            binding.tvLargeur.setText(paramLargeur);
                            binding.tvDetail.setText(paramDetail);
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
        binding.rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        //binding.rv.setLayoutManager(new GridLayoutManager(this, 1));

        adapter.setOnPhotoAdapterListener(this);

        new Thread() {
            @Override
            public void run() {

                try {
                    //on stocke dans une liste le contenu de la requete qui recupere toutes les photos d un projet client de la bdd
                    ArrayList<String> list = WSUtils.getAllPhotos(paramIdSousProjet);
                    if (list != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.rv.setVisibility(View.VISIBLE);
                            }
                        });
                    }
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

    public void onClickBtRetour(View view) {
        Intent intent = new Intent(this, SousProjetActivity.class);
        //passage de paramètres à envoyer dans SousProjetActivity
        intent.putExtra("sendIdProjet", paramIdProjet);
        intent.putExtra("sendNomProjet", paramNomProjet);
        intent.putExtra("sendDateProjet", paramDateProjet);
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    @Override
    public void onClick(String str) {
        //a chaque clic sur une photo du recycler view
        //ouverture de l activite ImgPleinEcran
        Intent intent = new Intent(this, ImgPleinEcran.class);
        //passage de paramètres à envoyer dans l imageview de ImgPleinEcran
        intent.putExtra("sendImg", str);
        startActivity(intent);
    }

    public void onClickBtModify(View view) {
        Intent intent = new Intent(this, ModifFicheSousProjetActivity.class);
        //passage de paramètres à envoyer dans ModifFicheSousProjetActivity
        intent.putExtra("sendIdProjet", paramIdProjet);
        intent.putExtra("sendNomProjet", paramNomProjet);
        intent.putExtra("sendDateProjet", paramDateProjet);
        intent.putExtra("sendIdSousProjet", paramIdSousProjet);
        intent.putExtra("sendLieuCible", paramLieuCible);
        intent.putExtra("sendLongueur", paramLongueur);
        intent.putExtra("sendLargeur", paramLargeur);
        intent.putExtra("sendDetail", paramDetail);
        intent.putExtra("sendIdCat", paramIdCat);
        intent.putExtra("sendNomCat", nomCatRecup);
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    public void onClickBtDelete(View view) {
        onCreateDialogConfirmationDelete(view);
    }

    /**********************     METHODES DEPORTEES POUR ALLEGER LE CODE    **************************/

    public void onCreateDialogConfirmationDelete(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Demande de confirmation");
        builder.setMessage("Voulez-vous supprimer cet élément du projet ?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //effacement du client;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            WSUtils.deleteSousProjet(paramIdSousProjet);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                //on retourne sur SousProjetActivity
                Intent intent = new Intent(FicheSousProjetActivity.this, SousProjetActivity.class);
                //passage de paramètres à envoyer dans SousProjetActivity
                intent.putExtra("sendIdProjet", paramIdProjet);
                intent.putExtra("sendNomProjet", paramNomProjet);
                intent.putExtra("sendDateProjet", paramDateProjet);
                intent.putExtra("sendLoginUser", paramLoginUser);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //on retourne sur ProjetsActivity
                Intent intent = new Intent(FicheSousProjetActivity.this, SousProjetActivity.class);
                //passage de paramètres à envoyer dans SousProjetActivity
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