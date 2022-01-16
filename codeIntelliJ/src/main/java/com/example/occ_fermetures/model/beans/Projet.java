package com.example.occ_fermetures.model.beans;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "app_projet")
public class Projet {

    /*************************     ATTRIBUTS     ****************************/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProjet;
    private String nom;
    private String date;
    private Long idClient;
    //OneToMany : un projet peut avoir plusieurs sous projets
    //mappedBy : nom de l attribut referencant cette classe dans la classe SousProjet
    //fetch : chargement à la demande (LAZY) ou en même temps que l'objet (EAGER)
    @OneToMany(mappedBy = "idProjet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SousProjet> sousProjets;
    @ManyToOne(fetch = FetchType.LAZY)
    //@Transcient indique qu un attribut ne doit pas etre persistant:
    // cad que cet attribut ne sera donc jamais pris en compte lors de l exécution des requetes vers la base de donnees.
    @Transient
    //name est le nom de la cle etrangere dans la table projet et referencedColumnName est le nom de la cle primaire dans la table client
    @JoinColumn(name = "id_client", referencedColumnName = "idClient")
    private Client client;

    /***********************     CONSTRUCTEUR     ***************************/

    public Projet() {
    }

    /**********************     GETTERS/SETTERS     *************************/

    public Long getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(Long idProjet) {
        this.idProjet = idProjet;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }
}
