package com.example.occfermetures.controller;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.occfermetures.databinding.ActivityModifFicheSousProjetBinding;
import com.example.occfermetures.model.SousProjet;
import com.example.occfermetures.utilitaire.ConversionBitmapTexte;
import com.example.occfermetures.utilitaire.WSUtils;

public class ModifFicheSousProjetActivity extends AppCompatActivity {

    /*************************     ATTRIBUTS     ****************************/

    //composants graphiques
    private ActivityModifFicheSousProjetBinding binding;
    //donnees
    private int longueurSize;
    private int largeurSize;
    private int detailSize;
    public String longueur;
    public String largeur;
    public String detail;
    public Long paramIdProjet;
    public Long paramIdSousProjet;
    public String paramNomProjet;
    public String paramDateProjet;
    public Long paramIdCat;
    public String paramLongueur;
    public String paramLargeur;
    public String paramDetail;
    private SousProjet sousProjet;
    public String paramLoginUser;
    //donnees qui gerent la prise des photos
    private String contenuPhoto1;
    private String contenuPhoto2;
    private String contenuPhoto3;
    private String contenuPhoto4;
    public int countClickOnPhoto = 0; // permettra d enregistrer la photo au bon endroit selon le nb associe
    private static final int REQUEST_IMAGE_CAPTURE = 1; // permet de comprendre a quoi correspond le chiffre utilise dans le code, ici la demande de capture d img
    private Bitmap imageBitmap;
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private Bitmap bitmap4;

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModifFicheSousProjetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l'on récupère de FicheSousProjetActivity
        String paramNomCat = getIntent().getStringExtra("sendNomCat");
        paramLongueur = getIntent().getStringExtra("sendLongueur");
        paramLargeur = getIntent().getStringExtra("sendLargeur");
        paramDetail = getIntent().getStringExtra("sendDetail");
        paramNomProjet = getIntent().getStringExtra("sendNomProjet");
        paramDateProjet = getIntent().getStringExtra("sendDateProjet");
        paramIdProjet = getIntent().getExtras().getLong("sendIdProjet");
        paramIdSousProjet = getIntent().getExtras().getLong("sendIdSousProjet");
        paramIdCat = getIntent().getExtras().getLong("sendIdCat");
        paramLoginUser = getIntent().getStringExtra("sendLoginUser");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tvCat.setText(paramNomCat);
                binding.etLongueur.setText(paramLongueur);
                binding.etLargeur.setText(paramLargeur);
                binding.etDetail.setText(paramDetail);

            }
        });

        //on recupere les infos du sous projet pour proposer ou pas d ajouter des photos
        new Thread() {
            @Override
            public void run() {
                try {
                    sousProjet = WSUtils.getInfosSousProjet(paramIdSousProjet);
                    //si il n y  a aucune photo d enregistree, dans le champ il y aura au max "un chiffre + espace + null" ca laisse de la marge car les champs avec une photo enregistree ont des milliers de caracteres
                    if (sousProjet.getPhoto1().length() < 10) {
                        //on rend visible les images d appareils photos, qui, si on clique dessus, seront remplaces par la capture de l image
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.appareilPhoto1.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        //sinon on stocke le contenu representant la photo deja enregistree
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.etPhoto1.setText(sousProjet.getPhoto1());
                            }
                        });
                    }
                    if (sousProjet.getPhoto2().length() < 10) {
                        //on rend visible les images d appareils photos, qui, si on clique dessus, seront remplaces par la capture de l image
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.appareilPhoto2.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.etPhoto2.setText(sousProjet.getPhoto2());
                            }
                        });
                    }
                    if (sousProjet.getPhoto3().length() < 10) {
                        //on rend visible les images d appareils photos, qui, si on clique dessus, seront remplaces par la capture de l image
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.appareilPhoto3.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.etPhoto3.setText(sousProjet.getPhoto3());
                            }
                        });
                    }
                    if (sousProjet.getPhoto4().length() < 10) {
                        //on rend visible les images d appareils photos, qui, si on clique dessus, seront remplaces par la capture de l image
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.appareilPhoto4.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.etPhoto4.setText(sousProjet.getPhoto4());
                            }
                        });
                    }
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
        Intent intent = new Intent(this, FicheSousProjetActivity.class);
        //passage de paramètres à envoyer dans FicheSousProjetACtivity
        intent.putExtra("sendIdSousProjet", paramIdSousProjet);
        intent.putExtra("sendIdProjet", paramIdProjet);
        intent.putExtra("sendNomProjet", paramNomProjet);
        intent.putExtra("sendDateProjet", paramDateProjet);
        intent.putExtra("sendIdCat", paramIdCat);
        intent.putExtra("sendLongueur", paramLongueur);
        intent.putExtra("sendLargeur", paramLargeur);
        intent.putExtra("sendDetail", paramDetail);
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

    public void onClickBtSave(View view) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //calcul de la taille des champs qui doivent etre saisis avant de valider la modif du sousProjet
                longueurSize = binding.etLongueur.getText().length();
                largeurSize = binding.etLargeur.getText().length();
                detailSize = binding.etDetail.getText().length();
                //recuperation des contenus des champs
                longueur = binding.etLongueur.getText().toString();
                largeur = binding.etLargeur.getText().toString();
                detail = binding.etDetail.getText().toString();
                contenuPhoto1 = binding.etPhoto1.getText().toString();
                contenuPhoto2 = binding.etPhoto2.getText().toString();
                contenuPhoto3 = binding.etPhoto3.getText().toString();
                contenuPhoto4 = binding.etPhoto4.getText().toString();
            }
        });

        //si tous les champs sont remplis
        if (longueurSize > 0 && largeurSize > 0 && detailSize > 0) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        WSUtils.modifSousProjet(paramIdSousProjet, contenuPhoto1, contenuPhoto2, contenuPhoto3, contenuPhoto4, longueur, largeur, detail, paramIdProjet, paramIdCat);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            //redirection vers FicheSousProjet
            Intent intent = new Intent(ModifFicheSousProjetActivity.this, FicheSousProjetActivity.class);
            //passage de paramètres à envoyer dans FicheSousProjetACtivity
            intent.putExtra("sendIdSousProjet", paramIdSousProjet);
            intent.putExtra("sendIdProjet", paramIdProjet);
            intent.putExtra("sendNomProjet", paramNomProjet);
            intent.putExtra("sendDateProjet", paramDateProjet);
            intent.putExtra("sendIdCat", paramIdCat);
            intent.putExtra("sendLongueur", longueur);
            intent.putExtra("sendLargeur", largeur);
            intent.putExtra("sendDetail", detail);
            intent.putExtra("sendLoginUser", paramLoginUser);
            startActivity(intent);
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
            }
        } else {//sinon, on affiche la fenêtre de demande de permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //on declare l intention d ouvrir l appareil photo
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    //on lance l ouverture de l appareil photo qui attend un resultat que l on decrit dans la methode onActivityResult() ci-dessous
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}