package com.example.occfermetures.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.occfermetures.databinding.RowSousProjetBinding;
import com.example.occfermetures.model.Categorie;
import com.example.occfermetures.model.Client;
import com.example.occfermetures.model.SousProjet;

import java.util.ArrayList;

public class SousProjetAdapter extends RecyclerView.Adapter<SousProjetAdapter.ViewHolder> {

    /*************************     3) ATTRIBUTS     ****************************/

    private ArrayList<SousProjet> dataSousProjet;
    private ArrayList<Categorie> dataCategorie;

    //objet de type notre interface pour que notre adapter puisse transmettre notre clic
    private OnSousProjetAdapterListener onSousProjetAdapterListener;

    /***********************     4) CONSTRUCTEUR     ***************************/

    //creation d un constructeur pour travailler et afficher la liste
    public SousProjetAdapter(ArrayList<SousProjet> dataSousProjet, ArrayList<Categorie> dataCategorie) {
        this.dataSousProjet = dataSousProjet;
        this.dataCategorie = dataCategorie;
    }

    /**************************     5) SETTER     ******************************/

    public void setOnSousProjetAdapterListener(OnSousProjetAdapterListener onSousProjetAdapterListener) {
        this.onSousProjetAdapterListener = onSousProjetAdapterListener;
    }

    /***********************     1) INNER CLASS     ***************************/

    protected class ViewHolder extends RecyclerView.ViewHolder {

        //declaration qui permettra de recuperer les composants graphiques dans le constructeur
        RowSousProjetBinding binding;

        //obligation d appeler le constructeur de la classe mere ViewHolder
        //dans lequel on recupere nos composants graphiques
        public ViewHolder(RowSousProjetBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
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
        return new ViewHolder(RowSousProjetBinding.inflate(layoutInflater));
    }

    //affiche la nouvelle ligne
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //on décide ici comment remplir notre ligne
        //on crée un pointeur vers un SousProjet de ma liste à la position déclarée en param
        final SousProjet sousProjet = dataSousProjet.get(position);
        //on déclare sousProjet en final, pour être sur que lors de l’exécution de la classe anonyme, on soit sur le même pointeur de sousProjet
        //on peut changer ses attributs mais surtout pas son pointeur

        Long idCat = sousProjet.getIdCat();

        //on appelle l’holder transmis en param pour chacun de nos composants graphiques
        //pour eviter un nullPointerException, on verifie qu un attribut obligatoire ne soit pas vide : un sous projet ne peut pas etre sauvegarde si il n y a pas au moins une photo
        if (sousProjet.getPhoto1() != null) {
            //on parcours la liste des categories
            for(int i = 0; i<dataCategorie.size(); i++){
                //on prend la categorie au rang i de la liste des categories
                final Categorie categorie = dataCategorie.get(i);
                //si l id de la categorie est la meme que l idCat associe au sousProjet, on remplit les champs du client a son projet
                if(categorie.getIdCat() == idCat) {
                    //on remplit le champ de notre ligne
                    holder.binding.tvSousProjet.setText(categorie.getNom() + " " + sousProjet.getLongueur() +"cm x " + sousProjet.getLargeur()+"cm");
                }
            }
        }

        //pour intercepter un clic sur notre RecyclerView et donc s y abonner
        //creation d une classe anonyme dont le seul interet intérêt sera d’implémenter onClickListenner() pour qu’elle s’exécute au moment du clic sur la ligne
        holder.binding.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSousProjetAdapterListener != null) {
                    onSousProjetAdapterListener.onClick(sousProjet);
                }
            }
        });
    }

    //compte le nb d elements de notre liste a afficher
    @Override
    public int getItemCount() {
        return dataSousProjet.size();
    }

    //callback qui premettra de transmettre, à l activite ClientExistant, l’évènement clic sur un sousProjet
    public interface OnSousProjetAdapterListener {
        void onClick(SousProjet sousProjet);
    }
}
