package com.example.occ_fermetures.model.beans;

import javax.persistence.*;

@Entity
@Table(name = "app_sous_projet")
public class SousProjet {

    /*************************     ATTRIBUTS     ****************************/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSousProjet;
    private String lieu;
    private String photo1;
    private String photo2;
    private String photo3;
    private String photo4;
    private String longueur;
    private String largeur;
    private String detail;
    private Long idProjet;
    private Long idCat;
    //ManyToOne : plusieurs sous-projets peuvent avoir la meme categorie
    //fetch : chargement à la demande (LAZY) ou en même temps que l'objet (EAGER)
    //@JsonIgnore permet de resoudre le msg d erreur : cannot call senderror() after the response has been committed
    @ManyToOne(fetch = FetchType.LAZY)
    //@Transcient indique qu un attribut ne doit pas etre persistant:
    // cad que cet attribut ne sera donc jamais pris en compte lors de l exécution des requetes vers la base de donnees.
    @Transient
    //name est le nom de la cle etrangere dans la table sous projet et referencedColumnName est le nom de la cle primaire dans la table categorie
    @JoinColumn(name = "id_cat", referencedColumnName = "idCat")
    private Categorie categorie;
    @ManyToOne(fetch = FetchType.LAZY)
    @Transient
    @JoinColumn(name = "id_projet", referencedColumnName = "idProjet")
    private Projet projet;

    /***********************     CONSTRUCTEUR     ***************************/

    public SousProjet() {
    }

   /**********************     GETTERS/SETTERS     *************************/

    public Long getIdSousProjet() {
        return idSousProjet;
    }

    public void setIdSousProjet(Long idSousProjet) {
        this.idSousProjet = idSousProjet;
    }

    public String getLieu() { return lieu; }

    public void setLieu(String lieu) { this.lieu = lieu; }

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
