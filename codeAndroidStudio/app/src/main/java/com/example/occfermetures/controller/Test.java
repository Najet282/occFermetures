package com.example.occfermetures.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.occfermetures.databinding.ActivityTestBinding;
import com.example.occfermetures.model.Categorie;
import com.example.occfermetures.model.Client;
import com.example.occfermetures.utilitaire.ConversionBitmapTexte;
import com.example.occfermetures.utilitaire.WSUtils;
import com.example.occfermetures.view.PhotoAdapter;

import java.util.ArrayList;
import java.util.List;

public class Test extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //composants graphiques
    private ActivityTestBinding binding;

    //Donnees
    private List<String> list = new ArrayList<>(); //pour stocker les noms des categories lors de la requete et les placer dans le spinner
    private List<String> listSpinner = new ArrayList<>(); //pour pouvoir etre videe et rempli a chaque nouvelle donnees de la list qui recupere les donnees de la bdd
    private String resultCat;
    private Long idCatRecup;
    private Client clientRecup;
    private String nomPrenomClient;

    //Outils
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toast.makeText(this, "coucou", Toast.LENGTH_SHORT).show();


        //mise en place du spinner (liste deroulante) des categories
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listSpinner);
        //spécifier la disposition que l'adaptateur doit utiliser pour afficher la liste des choix de spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //appliquer l'adaptateur à votre spinner
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.spinner.setAdapter(adapter);
                binding.spinner.setOnItemSelectedListener(Test.this);
            }
        });
        new Thread() {
            @Override
            public void run() {
                try {
                    //on stocke dans une liste le contenu de la requete qui recupere toutes les catégories de la bdd
                    list = WSUtils.getAllNomsCategories();
                    //on vide la liste qui stocke a chaque requete les categories de la bdd
                    listSpinner.clear();
                    //on la remplit avec les nouvelles donnees de la bdd
                    listSpinner.addAll(list);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //changement de la couleur de la categorie selectionnee
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.GRAY);

        //recuperation du nom de la categorie selectionne de la liste
        resultCat = adapterView.getItemAtPosition(i).toString();
        //Fonctionne aussi :
        //String resultCat = String.valueOf(binding.spinnerCat.getSelectedItem());
        //String resultCat = binding.spinnerCat.getSelectedItem().toString();
        if (resultCat == "porte") {
            Intent intent = new Intent(this, ClientsActivity.class);
            startActivity(intent);
        }
        if (resultCat == "fenetre") {
            Intent intent = new Intent(this, ProjetsActivity.class);
            startActivity(intent);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onBtAjouter(View view) {


        new Thread() {
            @Override
            public void run() {
                try {
                    idCatRecup = WSUtils.getIdCategorieDunSousProjet(resultCat);
                    clientRecup = WSUtils.getClientByIdProjet(58L);
                    nomPrenomClient = clientRecup.getNom() + "  " + clientRecup.getNom();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.textView.setText("idCat " + idCatRecup + " client " + nomPrenomClient);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}