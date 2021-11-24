package com.example.occfermetures.model;

import java.util.Date;

public class Projet {

    /*************************     ATTRIBUTS     ****************************/

    private Long idProjet;
    private String nom;
    private String date;
    private Long idClient;

    /***********************     CONSTRUCTEUR     ***************************/

    public Projet() {
    }

    public Projet(Long idClient) {
        this.idClient = idClient;
    }

    public Projet(Long idProjet, Long idClient) {
        this.idProjet = idProjet;
        this.idClient = idClient;
    }

    public Projet(String nom, String date, Long idClient) {
        this.nom = nom;
        this.date = date;
        this.idClient = idClient;
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
