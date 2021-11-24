package com.example.occfermetures.model;

public class Client {

    /*************************     ATTRIBUTS     ****************************/

    private Long idClient;
    private String nom;
    private String prenom;
    private String tel;
    private String email;
    private String adresse;
    private String cp;
    private String ville;

    /***********************     CONSTRUCTEUR     ***************************/

    public Client() {
    }

    public Client(Long idClient) {
        this.idClient = idClient;
    }

    public Client(String email) {
        this.email = email;
    }

    //creation de ce constructeur car besoin de 2 constructeurs avec un seul parametre de type String (une fois avec l email, celui du dessus, une fois avec le nom)
    //quand on utilisera le constructeur avec 2 parametres, on indiquera null pour le 2eme param pour se retrouver avec un constructeur avec un param de type String pour le nom cette fois-ci
    public Client(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }


    public Client(String nom, String prenom, String tel, String email, String adresse, String cp, String ville) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.adresse = adresse;
        this.cp = cp;
        this.ville = ville;
    }

    public Client(Long idClient, String nom, String prenom, String tel, String email, String adresse, String cp, String ville) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.adresse = adresse;
        this.cp = cp;
        this.ville = ville;
    }

    /**********************     GETTERS/SETTERS     *************************/

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
