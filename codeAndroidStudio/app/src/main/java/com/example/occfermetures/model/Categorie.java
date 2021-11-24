package com.example.occfermetures.model;

public class Categorie {

    /*************************     ATTRIBUTS     ****************************/

    private Long idCat;
    private String nom;
    private String detail;

    /***********************     CONSTRUCTEUR     ***************************/

    public Categorie() {
    }

    public Categorie(Long idCat) {
        this.idCat = idCat;
    }

    public Categorie(String nom) {
        this.nom = nom;
    }

    public Categorie(Long idCat, String nom) {
        this.idCat = idCat;
        this.nom = nom;
    }

    public Categorie(String nom, String detail) {
        this.nom = nom;
        this.detail = detail;
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
