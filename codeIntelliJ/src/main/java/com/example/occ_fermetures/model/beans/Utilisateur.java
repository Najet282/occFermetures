package com.example.occ_fermetures.model.beans;

import javax.persistence.*;

@Entity
@Table(name = "app_utilisateur")
public class Utilisateur {

    /*************************     ATTRIBUTS     ****************************/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private String login;
    private String mdp;
    private Boolean isAdmin=false;

    /***********************     CONSTRUCTEUR     ***************************/

    public Utilisateur() { }


    public Utilisateur(String login, String mdp) {
        this.login = login;
        this.mdp = mdp;
    }

    /**********************     GETTERS/SETTERS     *************************/

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Boolean getIsAdmin() { return isAdmin; }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
