package com.example.occfermetures.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.occfermetures.databinding.RowCategorieBinding;
import com.example.occfermetures.model.Categorie;

import java.util.ArrayList;

public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.ViewHolder> {

    /*************************     3) ATTRIBUTS     ****************************/

    //creation d une liste pour retourner sa taille dans la methode getItemCount()
    private ArrayList<Categorie> data;
    //objet de type notre interface pour que notre adapter puisse transmettre notre clic
    private OnCategorieAdapterListener onCategorieAdapterListener;

    /**************************     5) SETTER     ******************************/

    public void setOnCategorieAdapterListener(OnCategorieAdapterListener onCategorieAdapterListener) {
        this.onCategorieAdapterListener = onCategorieAdapterListener;
    }

    /***********************     4) CONSTRUCTEUR     ***************************/

    //creation d un constructeur pour travailler et afficher la liste
    public CategorieAdapter(ArrayList<Categorie> data) {
        this.data = data;
    }

    /***********************     1) INNER CLASS     ***************************/

    protected class ViewHolder extends RecyclerView.ViewHolder {

        //declaration qui permettra de recuperer les composants graphiques dans le constructeur
        RowCategorieBinding binding;

        //obligation d appeler le constructeur de la classe mere ViewHolder
        //dans lequel on recupere nos composants graphiques
        public ViewHolder(RowCategorieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /******************     2) METHODES IMPLEMENTEES     **********************/

    //cree une nouvelle ligne
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //on cr??e une nouvelle instance de notre ligne
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //on cr??e un ViewHolder ?? partir de la ligne cr????e au dessus
        return new ViewHolder(RowCategorieBinding.inflate(layoutInflater));
    }

    //affiche la nouvelle ligne
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //on d??cide ici comment remplir notre ligne
        //on cr??e un pointeur vers une Categorie de ma liste ?? la position d??clar??e en param
        final Categorie categorie = data.get(position);
        //on d??clare categorie en final, pour ??tre sur que lors de l???ex??cution de la classe anonyme
        //on soit sur le m??me pointeur de categorie
        //on peut changer ses attributs mais surtout pas son pointeur



        //on appelle l???holder transmis en param pour chacun de nos composants graphiques
        //pour eviter un nullPointerException, on verifie qu un attribut obligatoire ne soit pas vide : un sous projet ne peut pas etre sauvegarde si il n y a pas au moins une photo
        if (categorie.getNom() != null) {
            holder.binding.tvCat.setText(categorie.getNom());
        }

        //pour intercepter un clic sur notre RecyclerView et donc s y abonner
        //creation d une classe anonyme dont le seul interet int??r??t sera d???impl??menter onClickListenner() pour qu???elle s???ex??cute au moment du clic sur la ligne
        holder.binding.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCategorieAdapterListener != null) {
                    onCategorieAdapterListener.onClick(categorie);
                }
            }
        });
    }

    //compte le nb d elements de notre liste a afficher
    @Override
    public int getItemCount() {
        return data.size();
    }

    //callback qui premettra de transmettre, ?? l activite SousProjetActivity, l?????v??nement clic sur une categorie
    public interface OnCategorieAdapterListener {
        void onClick(Categorie categorie);
    }
}
