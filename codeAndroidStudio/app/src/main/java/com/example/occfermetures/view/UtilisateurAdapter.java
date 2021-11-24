package com.example.occfermetures.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.occfermetures.databinding.RowClientBinding;
import com.example.occfermetures.databinding.RowUtilisateurBinding;
import com.example.occfermetures.model.Client;
import com.example.occfermetures.model.Utilisateur;

import java.util.ArrayList;

public class UtilisateurAdapter extends RecyclerView.Adapter<UtilisateurAdapter.ViewHolder> {

    /*************************     3) ATTRIBUTS     ****************************/

    //creation d une liste pour retourner sa taille dans la methode getItemCount()
    private ArrayList<Utilisateur> data;
    //objet de type notre interface pour que notre adapter puisse transmettre notre clic
    private UtilisateurAdapter.OnUtilisateurAdapterListener onUtilisateurAdapterListener;

    /**************************     5) SETTER     ******************************/

    public void setOnUtilisateurAdapterListener(UtilisateurAdapter.OnUtilisateurAdapterListener onUtilisateurAdapterListener) {
        this.onUtilisateurAdapterListener = onUtilisateurAdapterListener;
    }

    /***********************     4) CONSTRUCTEUR     ***************************/

    //creation d un constructeur pour travailler et afficher la liste
    public UtilisateurAdapter(ArrayList<Utilisateur> data) {
        this.data = data;
    }

    /***********************     1) INNER CLASS     ***************************/

    protected class ViewHolder extends RecyclerView.ViewHolder {

        //declaration qui permettra de recuperer les composants graphiques dans le constructeur
        RowUtilisateurBinding binding;

        //obligation d appeler le constructeur de la classe mere ViewHolder
        //dans lequel on recupere nos composants graphiques
        public ViewHolder(RowUtilisateurBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /******************     2) METHODES IMPLEMENTEES     **********************/

    //cree une nouvelle ligne
    @NonNull
    @Override
    public UtilisateurAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //on crée une nouvelle instance de notre ligne
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //on crée un ViewHolder à partir de la ligne créée au dessus
        return new UtilisateurAdapter.ViewHolder(RowUtilisateurBinding.inflate(layoutInflater));
    }

    //affiche la nouvelle ligne
    @Override
    public void onBindViewHolder(@NonNull UtilisateurAdapter.ViewHolder holder, int position) {
        //on décide ici comment remplir notre ligne
        //on crée un pointeur vers un Client de ma liste à la position déclarée en param
        final Utilisateur utilisateur = data.get(position);
        //on déclare client en final, pour être sur que lors de l’exécution de la classe anonyme
        //on soit sur le même pointeur de client
        //on peut changer ses attributs mais surtout pas son pointeur

        //on appelle l’holder transmis en param pour chacun de nos composants graphiques
        //pour eviter un nullPointerException, on verifie que la liste des noms des clients ne soit pas vide
        if (utilisateur.getLogin() != null) {
            holder.binding.tvLogin.setText(utilisateur.getLogin());
            holder.binding.tvMdp.setText(utilisateur.getMdp());
        }

        //pour intercepter un clic sur notre RecyclerView et donc s y abonner
        //creation d une classe anonyme dont le seul interet intérêt sera d’implémenter onClickListenner() pour qu’elle s’exécute au moment du clic sur la ligne
        holder.binding.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onUtilisateurAdapterListener != null) {
                    onUtilisateurAdapterListener.onClick(utilisateur);
                }
            }
        });
    }

    //compte le nb d elements de notre liste a afficher
    @Override
    public int getItemCount() {
        return data.size();
    }

    //callback qui premettra de transmettre, à l activite ClientExistant, l’évènement clic sur un client
    public interface OnUtilisateurAdapterListener {
        void onClick(Utilisateur utilisateur);
    }
}
