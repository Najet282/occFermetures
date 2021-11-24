package com.example.occfermetures.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.occfermetures.databinding.RowImgListViewBinding;
import com.example.occfermetures.utilitaire.ConversionBitmapTexte;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    /*************************     3) ATTRIBUTS     ****************************/

    //creation d une liste dont on retorunera sa taille dans la methode getItemCount()
    private ArrayList<String> data;
    //objet de type notre interface pour que notre adapter puisse transmettre notre clic
    private OnPhotoAdapterListener onPhotoAdapterListener;


    /**************************     5) SETTER     ******************************/

    public void setOnPhotoAdapterListener(OnPhotoAdapterListener onPhotoAdapterListener) {
        this.onPhotoAdapterListener = onPhotoAdapterListener;
    }

    /***********************     4) CONSTRUCTEUR     ***************************/

    //creation d un constructeur pour travailler et afficher la liste
    public PhotoAdapter(ArrayList<String> data) {
        this.data = data;
    }

    /***********************     1) INNER CLASS     ***************************/

    protected class ViewHolder extends RecyclerView.ViewHolder {

        //declaration qui permettra de recuperer les composants graphiques dans le constructeur
        RowImgListViewBinding binding;

        //obligation d appeler le constructeur de la classe mere ViewHolder
        //dans lequel on recupere nos composants graphiques
        public ViewHolder(RowImgListViewBinding binding) {
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
        return new ViewHolder(RowImgListViewBinding.inflate(layoutInflater));
    }


    //affiche la nouvelle ligne
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //on décide ici comment remplir notre ligne
        //on crée un pointeur vers un String de ma liste à la position déclarée en param
        final String str = data.get(position);
        //on déclare client en final, pour être sur que lors de l’exécution de la classe anonyme
        //on soit sur le même pointeur de String
        //on peut changer ses attributs mais surtout pas son pointeur

        //on appelle l’holder transmis en param pour chacun de nos composants graphiques
        //pour eviter un nullPointerException, on verifie que la liste des String ne soit pas vide
        if(str!=null) {
            holder.binding.imgView.setVisibility(View.VISIBLE);
            holder.binding.imgZoom.setVisibility(View.VISIBLE);
            holder.binding.imgView.setImageBitmap(ConversionBitmapTexte.stringToBitmap(str));
        }

        //pour intercepter un clic sur notre RecyclerView et donc s y abonner
        //creation d une classe anonyme dont le seul interet intérêt sera d’implémenter onClickListenner() pour qu’elle s’exécute au moment du clic sur la ligne
        holder.binding.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPhotoAdapterListener != null) {
                    onPhotoAdapterListener.onClick(str);
                }
            }
        });
    }

    //compte le nb d elements de notre liste a afficher
    @Override
    public int getItemCount() {
        return data.size();
    }

    //callback qui premettra de transmettre, à l activite DetailProjet, l’évènement clic sur une photo
    public interface OnPhotoAdapterListener {
        void onClick(String str);
    }
}