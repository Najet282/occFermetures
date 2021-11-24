package com.example.occfermetures.model;

public class SousProjet {

    /*************************     ATTRIBUTS     ****************************/

    private Long idSousProjet;
    private String photo1;
    private String photo2;
    private String photo3;
    private String photo4;
    private String longueur;
    private String largeur;
    private String option;
    private String detail;
    private Long idProjet;
    private Long idCat;

    /***********************     CONSTRUCTEUR     ***************************/

    public SousProjet() {
    }

    public SousProjet(Long idProjet) {
        this.idProjet = idProjet;
    }

    public SousProjet(Long idSousProjet, Long idProjet) {
        this.idSousProjet = idSousProjet;
        this.idProjet = idProjet;
    }

    public SousProjet(Long idSousProjet, String photo1, String photo2, String photo3, String photo4, String longueur, String largeur, String detail, Long idProjet, Long idCat) {
        this.idSousProjet = idSousProjet;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
        this.photo4 = photo4;
        this.longueur = longueur;
        this.largeur = largeur;
        this.detail = detail;
        this.idProjet = idProjet;
        this.idCat = idCat;
    }

    public SousProjet(String photo1, String photo2, String photo3, String photo4, String longueur, String largeur, String detail, Long idProjet, Long idCat) {
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
        this.photo4 = photo4;
        this.longueur = longueur;
        this.largeur = largeur;
        this.detail = detail;
        this.idProjet = idProjet;
        this.idCat = idCat;
    }

    /**********************     GETTERS/SETTERS     *************************/

    public Long getIdSousProjet() {
        return idSousProjet;
    }

    public void setIdSousProjet(Long idSousProjet) {
        this.idSousProjet = idSousProjet;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    public String getPhoto4() {
        return photo4;
    }

    public void setPhoto4(String photo4) {
        this.photo4 = photo4;
    }

    public String getLongueur() {
        return longueur;
    }

    public void setLongueur(String longueur) {
        this.longueur = longueur;
    }

    public String getLargeur() {
        return largeur;
    }

    public void setLargeur(String largeur) {
        this.largeur = largeur;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(Long idProjet) {
        this.idProjet = idProjet;
    }

    public Long getIdCat() {
        return idCat;
    }

    public void setIdCat(Long idCat) {
        this.idCat = idCat;
    }
}
