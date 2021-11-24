package com.example.occfermetures.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.occfermetures.databinding.ActivityFicheClientBinding;
import com.example.occfermetures.model.Client;
import com.example.occfermetures.model.Projet;
import com.example.occfermetures.utilitaire.WSUtils;

import java.util.ArrayList;

public class FicheClientActivity extends AppCompatActivity {

    /*************************     ATTRIBUTS     ****************************/

    //composants graphiques
    private ActivityFicheClientBinding binding;

    //données
    public Long paramIdClient;
    public String paramNom;
    public String paramPrenom;
    public String paramAdresse;
    public String paramCp;
    public String paramVille;
    public String paramTel;
    public String paramEMail;
    public String paramLoginUser;

    /*****************     PAGE D ACCUEIL DE L ACTIVITE     *****************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFicheClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //passage de param que l on recupere de ClientsActivity ou de NewClientActivity ou de ModifFicheClientActivity
        paramIdClient = getIntent().getExtras().getLong("sendIdClient");
        paramLoginUser = getIntent().getStringExtra("sendLoginUser");

        new Thread() {
            @Override
            public void run() {
                try {
                    //on recupere les infos du clients grace a l id recu
                    Client clientRecup = WSUtils.getInfosClient(paramIdClient);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //on remplit tous les champs avec les infos recuperees
                            binding.tvNom.setText(clientRecup.getNom());
                            binding.tvPrenom.setText(clientRecup.getPrenom());
                            binding.tvTel.setText(clientRecup.getTel());
                            binding.tvEmail.setText(clientRecup.getEmail());
                            binding.tvAdresse.setText(clientRecup.getAdresse());
                            binding.tvCp.setText(clientRecup.getCp());
                            binding.tvVille.setText(clientRecup.getVille());
                            //on stocke les infos recuperees et retranscrites sur la page pour les comparer si l on souhaite les modifier
                            paramNom = clientRecup.getNom();
                            paramPrenom = clientRecup.getPrenom();
                            paramEMail = clientRecup.getEmail();
                            paramTel = clientRecup.getTel();
                            paramAdresse = clientRecup.getAdresse();
                            paramCp = clientRecup.getCp();
                            paramVille = clientRecup.getVille();
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
        Intent intent = new Intent(this, ClientsActivity.class);
        //passage de paramètre à envoyer dans ClientsActivity
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    public void onClickBtProjets(View view) {
        Intent intent = new Intent(this, ProjetsClientActivity.class);
        //passage de paramètres à envoyer dans ProjetsClientActivity
        intent.putExtra("sendIdClient", paramIdClient);
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    public void onClickBtModify(View view) {
        Intent intent = new Intent(this, ModifFicheClientActivity.class);
        //passage de paramètres à envoyer dans ModifFicheClientActivity
        intent.putExtra("sendIdClient", paramIdClient);
        intent.putExtra("sendNom", paramNom);
        intent.putExtra("sendPrenom", paramPrenom);
        intent.putExtra("sendTel", paramTel);
        intent.putExtra("sendEmail", paramEMail);
        intent.putExtra("sendAdresse", paramAdresse);
        intent.putExtra("sendCp", paramCp);
        intent.putExtra("sendVille", paramVille);
        intent.putExtra("sendLoginUser", paramLoginUser);
        startActivity(intent);
    }

    public void onClickBtDelete(View view) {
        //on affiche une confirmation de vouloir supprimer le client
        onCreateDialogConfirmationDelete(view);
    }

    /**********************     METHODES DEPORTEES POUR ALLEGER LE CODE    **************************/

    public void onCreateDialogConfirmationDelete(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Demande de confirmation");
        builder.setMessage("Voulez-vous supprimer le client ?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //effacement du client;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            WSUtils.deleteClient(paramEMail);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                Toast.makeText(getApplicationContext(), paramNom + "  " + paramPrenom + " supprimé", Toast.LENGTH_SHORT).show();
                //on retourne sur ClientsActivity
                Intent intent = new Intent(FicheClientActivity.this, ClientsActivity.class);
                intent.putExtra("sendLoginUser", paramLoginUser);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //on retourne sur ClientsActivity
                Intent intent = new Intent(FicheClientActivity.this, FicheClientActivity.class);
                //passage de paramètres à renvoyer dans cette meme activite
                intent.putExtra("sendIdClient", paramIdClient);
                intent.putExtra("sendLoginUser", paramLoginUser);
                startActivity(intent);
            }
        });
        builder.show();
    }

}