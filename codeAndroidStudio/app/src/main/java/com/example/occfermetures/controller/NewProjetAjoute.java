package com.example.occfermetures.controller;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.occfermetures.databinding.ActivityNewProjetAjouteBinding;
import com.example.occfermetures.model.SousProjet;
import com.example.occfermetures.utilitaire.ConversionBitmapTexte;
import com.example.occfermetures.utilitaire.WSUtils;

import java.util.ArrayList;
import java.util.List;

public class NewProjetAjoute extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /*************************     ATTRIBUTS     ****************************/

    //composants graphiques
    private ActivityNewProjetAjouteBinding binding;

    //données
    private List<String> list = new ArrayList<>(); //pour stocker les noms des categories lors de la requete et les placer dans le spinner
    private List<String> listSpinner = new ArrayList<>(); //pour pouvoir etre videe et rempli a chaque nouvelle donnees de la list qui recupere les donnees de la bdd
    public Long paramIdClient;
    public Long paramIdProjet;
    public String paramNomProjet;
    public String paramDateProjet;
    private Long idCatRecup;
    private String resultCat;
    private int sizeNomProjet;
    private int sizePhoto1;
    private int sizePhoto2;
    private int sizePhoto3;
    private int sizePhoto4;
    private int sizeLongueur;
    private int sizeLargeur;
    private int sizeDetail;
    private String longueur;
    private String largeur;
    private String detail;
    public String paramLoginUser;
    //donnees qui gerent la prise des photos
    private String contenuPhoto1;
    private String contenuPhoto2;
    private String contenuPhoto3;
    private String contenuPhoto4;
    private int countClickOnPhoto = 0; // permettra d enregistrer la photo au bon endroit selon le nb associe
    private static final int REQUEST_IMAGE_CAPTURE = 1; // permet de comprendre a quoi correspond le chiffre utilise dans le code, ici la demande de capture d img
    private Bitmap imageBitmap;
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private Bitmap bitmap4;

    //outils
    private ArrayAdapter<String> adapter;

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewProjetAjouteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l'on récupère de NewProjetByClient ou NewProjetByProjet
        paramIdClient = getIntent().getExtras().getLong("sendIdClient");
        paramLoginUser = getIntent().getStringExtra("sendLoginUser");
        //passage de param que l'on récupère de cette meme activity si l on clique sur AJOUTER UN SOUS PROJET
        paramIdProjet = getIntent().getExtras().getLong("sendIdProjet");
        paramNomProjet = getIntent().getStringExtra("sendNomProjet");
        paramDateProjet = getIntent().getStringExtra("sendDateProjet");

        //on rend visible les images d appareils photos, qui, si on clique dessus, seront remplaces par la capture de l image
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.appareilPhoto1.setVisibility(View.VISIBLE);
                binding.appareilPhoto2.setVisibility(View.VISIBLE);
                binding.appareilPhoto3.setVisibility(View.VISIBLE);
                binding.appareilPhoto4.setVisibility(View.VISIBLE);
            }
        });

        //mise en place du spinner (liste deroulante) des categories
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listSpinner);
        //spécifier la disposition que l'adaptateur doit utiliser pour afficher la liste des choix de spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //appliquer l'adaptateur à votre spinner
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.spinnerCat.setAdapter(adapter);
                binding.spinnerCat.setOnItemSelectedListener(NewProjetAjoute.this);
            }
        });

        //on s occupe de recuperer la liste d element a ajouter dans le spinner via la requete
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

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tvNomProjet.setText(paramNomProjet);
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

    public void onClickBtRetour(View view) {
        Intent intent = new Intent(this, SousProjetActivity.class);
        //passage de paramètre à envoyer dans FicheClientActivity
        intent.putExtra("sendIdProjet", paramIdProjet);
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    public void onClickBtPhoto1(View view) {
        //on rend invisible l image d appareil photos pour etre remplacee par la capture de l image
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.appareilPhoto1.setVisibility(View.INVISIBLE);
            }
        });
        countClickOnPhoto = 1;
        onBtPrendrePhoto(view);
    }

    public void onClickBtPhoto2(View view) {
        //on rend invisible l image d appareil photos pour etre remplacee par la capture de l image
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.appareilPhoto2.setVisibility(View.INVISIBLE);
            }
        });
        countClickOnPhoto = 2;
        onBtPrendrePhoto(view);
    }

    public void onClickBtPhoto3(View view) {
        //on rend invisible l image d appareil photos pour etre remplacee par la capture de l image
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.appareilPhoto3.setVisibility(View.INVISIBLE);
            }
        });
        countClickOnPhoto = 3;
        onBtPrendrePhoto(view);
    }

    public void onClickBtPhoto4(View view) {
        //on rend invisible l image d appareil photos pour etre remplacee par la capture de l image
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.appareilPhoto4.setVisibility(View.INVISIBLE);
            }
        });
        countClickOnPhoto = 4;
        onBtPrendrePhoto(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            //recuperation de la photo prise
            imageBitmap = (Bitmap) extras.get("data");
            //on transmet la capture de l img dans notre composant graphique imageView
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (countClickOnPhoto == 1) {
                        binding.img1.setVisibility(View.VISIBLE);
                        binding.img1.setImageBitmap(imageBitmap);
                        //recupere la version bitmap de la photo prise
                        bitmap1 = ((BitmapDrawable) binding.img1.getDrawable()).getBitmap();
                        //transformation des bitmap en texte dans les etPhoto pour le stocker dans la bdd
                        binding.etPhoto1.setText(ConversionBitmapTexte.bitmapToString(bitmap1));
                    }
                    if (countClickOnPhoto == 2) {
                        binding.img2.setVisibility(View.VISIBLE);
                        binding.img2.setImageBitmap(imageBitmap);
                        bitmap2 = ((BitmapDrawable) binding.img2.getDrawable()).getBitmap();
                        binding.etPhoto2.setText(ConversionBitmapTexte.bitmapToString(bitmap2));
                    }
                    if (countClickOnPhoto == 3) {
                        binding.img3.setVisibility(View.VISIBLE);
                        binding.img3.setImageBitmap(imageBitmap);
                        bitmap3 = ((BitmapDrawable) binding.img3.getDrawable()).getBitmap();
                        binding.etPhoto3.setText(ConversionBitmapTexte.bitmapToString(bitmap3));
                    }
                    if (countClickOnPhoto == 4) {
                        binding.img4.setVisibility(View.VISIBLE);
                        binding.img4.setImageBitmap(imageBitmap);
                        bitmap4 = ((BitmapDrawable) binding.img4.getDrawable()).getBitmap();
                        binding.etPhoto4.setText(ConversionBitmapTexte.bitmapToString(bitmap4));
                    }
                }
            });
        }
    }

    public void onClickBtAddNewSousProjet(View view) {
        //on commence par cacher les eventuels precedents msg d erreur
        hideErrorMsg();

        recupContenuComposantsGraphiques();

        //si tous les champs sont remplis
        if (sizeNomProjet > 0 && sizeLongueur > 0 && sizeLargeur > 0 && sizeDetail > 0) {
            //au moins une photo doit etre prise
            if (sizePhoto1 > 0 || sizePhoto2 > 0 || sizePhoto3 > 0 || sizePhoto4 > 0) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            //je recupere l id de la categorie a l aide du nom selectionne sur le spinner
                            idCatRecup = WSUtils.getIdCategorieDunSousProjet(resultCat);

                            //on enregistre le sous projet
                            SousProjet ssProj = WSUtils.createSousProjet(contenuPhoto1, contenuPhoto2, contenuPhoto3, contenuPhoto4, longueur, largeur, detail, paramIdProjet, idCatRecup);

                            //enregistrement de la photo dans la galerie
                            //MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "nomImg", "descriptionImg");

                            //une fois le projet cree, ouverture de l activite ProjetsActivity
                            Intent intent = new Intent(NewProjetAjoute.this, NewProjetAjoute.class);
                            //passage de paramètre à envoyer dans l editText de NewProjetAjoute
                            intent.putExtra("sendIdProjet", ssProj.getIdProjet());
                            intent.putExtra("sendNomProjet", paramNomProjet);
                            intent.putExtra("sendDateProjet", paramDateProjet);
                            //passage de paramètre à renvoyer dans cette meme activite
                            intent.putExtra("sendLoginUser", paramLoginUser);

                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            showErrorMsg(e.getMessage());
                        }
                    }
                }.start();
            } else {
                createDialog("Veuillez prendre au moins une photo.");
            }

        } else {
            createDialog("Veuillez remplir tous les champs");
        }
    }

    public void onClickBtSave(View view) {
        ////on commence par cacher les eventuels precedents msg d erreur
        hideErrorMsg();

        recupContenuComposantsGraphiques();

        //si tous les champs sont remplis
        if (sizeNomProjet > 0 && sizeLongueur > 0 && sizeLargeur > 0 && sizeDetail > 0) {
            //au moins une photo doit etre prise
            if (sizePhoto1 > 0 || sizePhoto2 > 0 || sizePhoto3 > 0 || sizePhoto4 > 0) {

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            //je recupere l id de la categorie a l aide du nom selectionne sur le spinner
                            idCatRecup = WSUtils.getIdCategorieDunSousProjet(resultCat);
                            //on enregistre le sous projet
                            WSUtils.createSousProjet(contenuPhoto1, contenuPhoto2, contenuPhoto3, contenuPhoto4, longueur, largeur, detail, paramIdProjet, idCatRecup);

                            //enregistrement de la photo dans la galerie
                            //MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "nomImg", "descriptionImg");

                            //une fois le projet cree, ouverture de l activite SousProjetActivity
                            Intent intent = new Intent(NewProjetAjoute.this, SousProjetActivity.class);
                            //passage de paramètres à envoyer dans SousProjetActivity
                            intent.putExtra("sendIdProjet", paramIdProjet);
                            intent.putExtra("sendNomProjet", paramNomProjet);
                            intent.putExtra("sendDateProjet", paramDateProjet);
                            intent.putExtra("sendIdClient", paramIdClient);
                            intent.putExtra("sendLoginUser", paramLoginUser);
                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                            showErrorMsg(e.getMessage());
                        }
                    }
                }.start();
            } else {
                createDialog("Veuillez prendre au moins une photo.");
            }
        } else {
            createDialog("Veuillez remplir tous les champs.");
        }

    }

    /**********************     METHODES IMPLEMENTEES    **************************/

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //changement de la couleur de la categorie selectionnee
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);

        //recuperation du nom de la categorie selectionne de la liste
        resultCat = adapterView.getItemAtPosition(i).toString();
        //Fonctionne aussi :
        //String resultCat = String.valueOf(binding.spinnerCat.getSelectedItem());
        //String resultCat = binding.spinnerCat.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**********************     METHODES DEPORTEES POUR ALLEGER LE CODE    **************************/

    //création d'une popup affichant un message
    private void createDialog(String text) {
        AlertDialog ad = new AlertDialog.Builder(this)
                .setPositiveButton("Ok", null).setMessage(text).create();
        ad.show();
    }

    private void recupContenuComposantsGraphiques() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //calcul de la taille des champs qui doivent etre saisis avant de valider la creation du nouveau projet ou d ajouter un sous projet a ce projet
                sizeNomProjet = binding.tvNomProjet.getText().length();
                sizePhoto1 = binding.etPhoto1.getText().length();
                sizePhoto2 = binding.etPhoto2.getText().length();
                sizePhoto3 = binding.etPhoto3.getText().length();
                sizePhoto4 = binding.etPhoto4.getText().length();
                sizeLongueur = binding.etLongueur.getText().length();
                sizeLargeur = binding.etLargeur.getText().length();
                sizeDetail = binding.etDetail.getText().length();
                //on recupere le nom du projet pour l envoyer dans l editText de NewProjet lorsque qu on veut enregistrer plusieurs sous projets pour un meme projet
                //nomProjetSaisi = binding.tvNomProjet.getText().toString();
                //on recupere le contenu des champs qui stockent la conversion bitmapToString
                contenuPhoto1 = binding.etPhoto1.getText().toString();
                contenuPhoto2 = binding.etPhoto2.getText().toString();
                contenuPhoto3 = binding.etPhoto3.getText().toString();
                contenuPhoto4 = binding.etPhoto4.getText().toString();
                //on recupere le contenu des autres champ pour la creation d un new projet
                longueur = binding.etLongueur.getText().toString();
                largeur = binding.etLargeur.getText().toString();
                detail = binding.etDetail.getText().toString();
            }
        });
    }

    public void onBtPrendrePhoto(View view) {
        //si on a la permission d utiliser l appareil photo
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            //on incremente le compteur pour comptabiliser le nb de clic sur le bouton PrendrePhoto
            //on declare l intention d ouvrir l appareil photo
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                //on lance l ouverture de l appareil photo qui attend un resultat que l on decrit dans la methode onActivityResult() ci-dessous
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                showErrorMsg(e.getMessage());
            }
        } else {//sinon, on affiche la fenêtre de demande de permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //on incremente le compteur pour comptabiliser le nb de clic sur le bouton PrendrePhoto
                //on declare l intention d ouvrir l appareil photo
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    //on lance l ouverture de l appareil photo qui attend un resultat que l on decrit dans la methode onActivityResult() ci-dessous
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    showErrorMsg(e.getMessage());
                }
            }
        }
    }


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

    //rend le champ error invisible
    private void hideErrorMsg() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tvError.setVisibility(View.INVISIBLE);
            }
        });
    }

}