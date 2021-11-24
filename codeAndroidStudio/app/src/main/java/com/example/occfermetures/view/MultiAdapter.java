package com.example.occfermetures.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.occfermetures.databinding.RowClientProjetBinding;
import com.example.occfermetures.databinding.RowProjetBinding;
import com.example.occfermetures.model.Client;
import com.example.occfermetures.model.Projet;
import com.example.occfermetures.utilitaire.WSUtils;

import java.util.ArrayList;

public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.ViewHolder> {

    /*************************     3) ATTRIBUTS     ****************************/

    private ArrayList<Projet> dataProjet;
    private ArrayList<Client> dataClient;

    //objet de type notre interface pour que notre adapter puisse transmettre notre clic
    private OnMultiAdapterListener onMultiAdapterListener;

    /***********************     4) CONSTRUCTEUR     ***************************/

    //creation d un constructeur pour travailler et afficher la liste
    public MultiAdapter(ArrayList<Projet> dataProjet, ArrayList<Client> dataClient) {
        this.dataProjet = dataProjet;
        this.dataClient = dataClient;
    }

    /**************************     5) SETTER     ******************************/

    public void setOnMultiAdapterListener(MultiAdapter.OnMultiAdapterListener onMultiAdapterListener) {
        this.onMultiAdapterListener = onMultiAdapterListener;
    }

    /***********************     1) INNER CLASS     ***************************/

    protected class ViewHolder extends RecyclerView.ViewHolder {

        //declaration qui permettra de recuperer les composants graphiques dans le constructeur
        RowClientProjetBinding binding;

        //obligation d appeler le constructeur de la classe mere ViewHolder
        //dans lequel on recupere nos composants graphiques
        public ViewHolder(RowClientProjetBinding bindingClientProjet) {
            super(bindingClientProjet.getRoot());
            this.binding = bindingClientProjet;
        }
    }

    /******************     2) METHODES IMPLEMENTEES     **********************/

    //cree une nouvelle ligne
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //on crée une nouvelle instance de notre ligne
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        //on crée un ViewHolder à partir de la ligne créée au dessus
        return new ViewHolder(RowClientProjetBinding.inflate(layoutInflater));
    }

    //affiche la nouvelle ligne
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //on décide ici comment remplir notre ligne
        //on crée un pointeur vers un Projet de ma liste à la position déclarée en param
        final Projet projet = dataProjet.get(position);
        //on déclare projet en final, pour être sur que lors de l’exécution de la classe anonyme, on soit sur le même pointeur de projet
        //on peut changer ses attributs mais surtout pas son pointeur

        Long idClient = projet.getIdClient();

        //on appelle l’holder transmis en param pour chacun de nos composants graphiques
        //pour eviter un nullPointerException, on verifie qu un attribut obligatoire ne soit pas vide : un projet ne peut pas etre sauvegarde si il n y a pas au moins un nom
        if(projet.getNom()!=null) {
            //on remplit un des champs de notre ligne
            holder.binding.tvProjet.setText(projet.getNom());

            //on parcours la liste des clients
            for(int i = 0; i<dataClient.size(); i++){
                //on prend le client au rang i de la liste des clients
                final Client client = dataClient.get(i);
                //si l id du client est le meme que l idClient associe au projet, on remplit les champs du client a son projet
                if(client.getIdClient() == idClient) {
                    //on remplit les autrrs champs de notre ligne
                    holder.binding.tvNomClient.setText(client.getNom() + " " + client.getPrenom());
                }
            }
        }

        //pour intercepter un clic sur notre RecyclerView et donc s y abonner
        //creation d une classe anonyme dont le seul interet intérêt sera d’implémenter onClickListenner() pour qu’elle s’exécute au moment du clic sur la ligne
        holder.binding.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMultiAdapterListener != null) {
                    onMultiAdapterListener.onClick(projet);
                }
            }
        });
    }

    //compte le nb d elements de notre liste a afficher
    @Override
    public int getItemCount() {
        return dataProjet.size();
    }

    //callback qui premettra de transmettre, à l activite ProjetsActivity, l’évènement clic sur projets
    public interface OnMultiAdapterListener {
        void onClick(Projet projet);
    }

}