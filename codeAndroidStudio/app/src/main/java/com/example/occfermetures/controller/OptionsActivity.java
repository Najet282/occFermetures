package com.example.occfermetures.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.occfermetures.databinding.ActivityOptionsBinding;
import com.example.occfermetures.model.Categorie;
import com.example.occfermetures.utilitaire.WSUtils;
import com.example.occfermetures.view.CategorieAdapter;

import java.util.ArrayList;

public class OptionsActivity extends AppCompatActivity implements CategorieAdapter.OnCategorieAdapterListener {

    /*************************     ATTRIBUTS     ****************************/

    //composants graphiques
    private ActivityOptionsBinding binding;

    //donnees
    private final ArrayList<Categorie> data = new ArrayList<>(); //pour stocker les noms des categories lors de la requete et les placer dans le spinner
    public String paramLoginUser;

    //outils
    private CategorieAdapter adapter = new CategorieAdapter(data);

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l on recupere de MenuActivity
        paramLoginUser = getIntent().getStringExtra("sendLoginUser");

        //on transmet l adapter au recycler view
        binding.rv.setAdapter(adapter);
        //on parametre l affichage de notre recycler view
        binding.rv.setLayoutManager(new GridLayoutManager(this, 1));

        adapter.setOnCategorieAdapterListener(this);

        new Thread() {
            @Override
            public void run() {
                try {
                    //on stocke dans une liste le contenu de la requete qui recupere toutes les catégories de la bdd
                    ArrayList<Categorie> list = WSUtils.getAllCategories();
                    //si l entreprise a mis en place des categories on les affiche en rendant la visibilité au recyclerView
                    if (!list.isEmpty()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.rv.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    //on vide la liste qui stocke a chaque requete les categories de la bdd
                    data.clear();
                    //on la remplit avec les nouvelles donnees de la bdd
                    data.addAll(list);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //on actualise le spinner en informant notre adapter que les données ont changées pour ré-afficher la liste en fonction des nouvelles données
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

    public void onBtClickAddNewCategorie(View view) {
        new Thread() {
            @Override
            public void run() {
                try {
                    WSUtils.checkCodeAcces(paramLoginUser);
                    Intent intent = new Intent(OptionsActivity.this, NewCategorieActivity.class);
                    //passage de paramètre à envoyer dans GestionComptesUtilisateurActivity
                    intent.putExtra("sendLoginUser", paramLoginUser);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    showErrorMsg(e.getMessage());
                }
            }
        }.start();
    }

    @Override
    public void onClick(Categorie categorie) {
    }

    /**********************     METHODES DEPORTEES POUR ALLEGER LE CODE    **************************/

    //cette methode utilise un runOnUiThread car fait appel a des composants graphiques
    private void showErrorMsg(String errorMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tvError.setVisibility(View.VISIBLE);
                binding.tvError.setText(errorMsg);
            }
        });
    }
}