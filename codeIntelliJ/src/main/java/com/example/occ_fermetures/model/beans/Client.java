package com.example.occ_fermetures.model.beans;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_client")
public class Client {

    /*************************     ATTRIBUTS     ****************************/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;
    private String nom;
    private String prenom;
    private String tel;
    private String email;
    private String adresse;
    private String cp;
    private String ville;
    //OneToMany : un client peut avoir plusieurs projets
    //mappedBy : nom de l attribut referencant cette classe dans la classe Projet
    //fetch : chargement à la demande (LAZY) ou en même temps que l'objet (EAGER)
    @OneToMany(mappedBy = "idClient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Projet> Projets;

    /***********************     CONSTRUCTEUR     ***************************/

    public Client() {
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
