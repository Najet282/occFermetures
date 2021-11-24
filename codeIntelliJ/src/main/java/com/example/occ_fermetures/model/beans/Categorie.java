package com.example.occ_fermetures.model.beans;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_categorie")
public class Categorie {

    /*************************     ATTRIBUTS     ****************************/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCat;
    private String nom;
    private String detail;
    //OneToMany : une categorie peut avoir plusieurs sous projets
    //mappedBy : nom de l attribut referencant cette classe dans la classe SousProjet
    //fetch : chargement à la demande (LAZY) ou en même temps que l'objet (EAGER)
    @OneToMany(mappedBy = "idCat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SousProjet> sousProjets;

    /***********************     CONSTRUCTEUR     ***************************/

    public Categorie() {
    }

    /**********************     GETTERS/SETTERS     *************************/

    public Long getIdCat() {
        return idCat;
    }

    public void setIdCat(Long idCat) {
        this.idCat = idCat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
