package com.example.occfermetures.model;

public class Utilisateur {

    /*************************     ATTRIBUTS     ****************************/

    private Long idUser;
    private String login;
    private String mdp;
    private Boolean isAdmin;

    /***********************     CONSTRUCTEUR     ***************************/

    public Utilisateur() {
    }

    public Utilisateur(String login) {
        this.login = login;
    }

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

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
