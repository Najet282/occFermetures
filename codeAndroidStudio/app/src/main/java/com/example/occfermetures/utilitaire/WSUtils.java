package com.example.occfermetures.utilitaire;

import com.example.occfermetures.model.Categorie;
import com.example.occfermetures.model.Client;
import com.example.occfermetures.model.Projet;
import com.example.occfermetures.model.SousProjet;
import com.example.occfermetures.model.Utilisateur;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class WSUtils {

    /*************************     ATTRIBUT     ****************************/

    private final static Gson myGson = new Gson();

    private final static String URL_WS_OCC_FERMETURES_SAVE_USER = "http://10.0.2.2:8080/saveUtilisateur";
    private final static String URL_WS_OCC_FERMETURES_DELETE_USER = "http://10.0.2.2:8080/deleteUtilisateur";
    private final static String URL_WS_OCC_FERMETURES_GET_INFOS_UTILISATEUR = "http://10.0.2.2:8080/getInfosUtilisateur";
    private final static String URL_WS_OCC_FERMETURES_CHECK_ACCES_COMPTES = "http://10.0.2.2:8080/checkAccesComptes";
    private static final String URL_WS_OCC_FERMETURES_GET_ALL_USERS = "http://10.0.2.2:8080/getAllUsers";
    private final static String URL_WS_OCC_FERMETURES_CHECK_LOGIN = "http://10.0.2.2:8080/checkLogin";
    private final static String URL_WS_OCC_FERMETURES_CREATE_CLIENT = "http://10.0.2.2:8080/createClient";
    private final static String URL_WS_OCC_FERMETURES_CHECK_EMAIL = "http://10.0.2.2:8080/checkEmail";
    private final static String URL_WS_OCC_FERMETURES_GET_ALL_CLIENTS = "http://10.0.2.2:8080/getAllClients";
    private final static String URL_WS_OCC_FERMETURES_GET_ALL_CLIENTS_WITH_FILTER = "http://10.0.2.2:8080/getClientsWithFilter";
    private final static String URL_WS_OCC_FERMETURES_GET_INFOS_CLIENT = "http://10.0.2.2:8080/getInfosClient";
    private final static String URL_WS_OCC_FERMETURES_GET_CLIENT_BY_ID_PROJET = "http://10.0.2.2:8080/getClientByIdProjet";
    private final static String URL_WS_OCC_FERMETURES_MODIF_CLIENT = "http://10.0.2.2:8080/modifClient";
    private final static String URL_WS_OCC_FERMETURES_DELETE_CLIENT = "http://10.0.2.2:8080/deleteClient";
    private final static String URL_WS_OCC_FERMETURES_CREATE_PROJET_CLIENT = "http://10.0.2.2:8080/createProjetClient";
    private final static String URL_WS_OCC_FERMETURES_DELETE_PROJET = "http://10.0.2.2:8080/deleteProjet";
    private final static String URL_WS_OCC_FERMETURES_GET_ALL_PROJETS = "http://10.0.2.2:8080/getAllProjets";
    private final static String URL_WS_OCC_FERMETURES_GET_ALL_PROJETS_CLIENT = "http://10.0.2.2:8080/getAllProjetsClient";
    private final static String URL_WS_OCC_FERMETURES_GET_INFOS_PROJET = "http://10.0.2.2:8080/getInfosProjet";
    private final static String URL_WS_OCC_FERMETURES_CREATE_SOUS_PROJET = "http://10.0.2.2:8080/createSousProjet";
    private final static String URL_WS_OCC_FERMETURES_MODIF_SOUS_PROJET = "http://10.0.2.2:8080/modifSousProjet";
    private final static String URL_WS_OCC_FERMETURES_DELETE_SOUS_PROJET = "http://10.0.2.2:8080/deleteSousProjet";
    private final static String URL_WS_OCC_FERMETURES_GET_ALL_SOUS_PROJETS = "http://10.0.2.2:8080/getAllSousProjets";
    private final static String URL_WS_OCC_FERMETURES_GET_ALL_SOUS_PROJETS_D_UN_PROJET = "http://10.0.2.2:8080/getAllSousProjetsDunProjet";
    private final static String URL_WS_OCC_FERMETURES_GET_INFOS_SOUS_PROJET = "http://10.0.2.2:8080/getInfosSousProjet";
    private final static String URL_WS_OCC_FERMETURES_GET_ALL_PHOTOS = "http://10.0.2.2:8080/getAllPhotos";
    private final static String URL_WS_OCC_FERMETURES_CREATE_CATEGORIE = "http://10.0.2.2:8080/createCategorie";
    private final static String URL_WS_OCC_FERMETURES_GET_ALL_CATEGORIES = "http://10.0.2.2:8080/getAllCategories";
    private final static String URL_WS_OCC_FERMETURES_GET_ALL_NOMS_CATEGORIES = "http://10.0.2.2:8080/getAllNomsCategories";
    private final static String URL_WS_OCC_FERMETURES_GET_NOM_CATEGORIE_D_UN_SOUS_PROJET = "http://10.0.2.2:8080/getNomCategorieDunSousProjet";
    private final static String URL_WS_OCC_FERMETURES_GET_ID_CATEGORIE_D_UN_SOUS_PROJET = "http://10.0.2.2:8080/getIdCategorieDunSousProjet";


    /*************************     METHODES     ****************************/

    public static void createUser(String login, String mdp) throws Exception {
        //on cree un new user avec les parametres qui seront saisis lors de l appel de la methode
        Utilisateur utilisateur = new Utilisateur(login, mdp);
        //on transforme notre user en json
        String json = myGson.toJson(utilisateur);
        //on effecue la requete de parsing
        OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_SAVE_USER, json);
    }

    public static void deleteUser(String login) throws Exception {
        //on cree un new user avec le parametre qui sera saisi lors de l appel de la methode
        Utilisateur utilisateur = new Utilisateur(login);
        //on transforme notre user en json
        String json = myGson.toJson(utilisateur);
        //on effecue la requete de parsing
        OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_DELETE_USER, json);
    }

    public static Utilisateur getInfosUtilisateur(String login) throws Exception {
        //on cree un new client avec le parametre qui sera saisi lors de l appel de la methode
        Utilisateur utilisateur = new Utilisateur(login);
        //on transforme notre client en json
        String json = myGson.toJson(utilisateur);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_GET_INFOS_UTILISATEUR, json);
        //on recupere un json de type Utilisateur
        Utilisateur user = myGson.fromJson(myJson, Utilisateur.class);
        //on retourne ce json
        return user;
    }

    public static boolean checkCodeAcces(String login) throws Exception {
        //on cree un new user avec le parametre qui sera saisi lors de l appel de la methode
        Utilisateur user = new Utilisateur(login);
        //on transforme notre user en json
        String json = myGson.toJson(user);
        //on effectue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_CHECK_ACCES_COMPTES, json);
        //on recupere un json de type boolean
        boolean bool = myGson.fromJson(myJson, boolean.class);
        //si l acces est autorise retournera true sinon false
        return bool;
    }

    public static ArrayList<Utilisateur> getAllUsers() throws Exception {
        //on effectue la requete de parsing
        String myJson = OkHttpUtils.sendCetOkHttpRequest(URL_WS_OCC_FERMETURES_GET_ALL_USERS);
        //on recupere un json de type ArrayList<Client>
        ArrayList<Utilisateur> usersList = myGson.fromJson(myJson, new TypeToken<ArrayList<Utilisateur>>() {
        }.getType());
        //controle avant de retourner la liste de clients
        if (usersList.isEmpty()) {
            throw new Exception("Aucun utilisateur enregistré");
        }
        return usersList;
    }

    public static void checkLogin(String login, String mdp) throws Exception {
        //on cree un new user avec les parametres qui seront saisis lors de l appel de la methode
        Utilisateur utilisateur = new Utilisateur(login, mdp);
        //on transforme notre user en json
        String json = myGson.toJson(utilisateur);
        //on effectue la requete de parsing
        OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_CHECK_LOGIN, json);
    }

    public static Client createClient(String nom, String prenom, String tel, String email, String adresse, String cp, String ville) throws Exception {
        //on cree un new client avec les parametres qui seront saisis lors de l appel de la methode
        Client client = new Client(nom, prenom, tel, email, adresse, cp, ville);
        //on transforme notre client en json
        String json = myGson.toJson(client);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_CREATE_CLIENT, json);
        //on recupere un json de type Client
        Client customer = myGson.fromJson(myJson, Client.class);
        //on retourne ce json
        return customer;
    }

    public static String checkEmail(String email) throws Exception {
        //on cree un new client avec le parametre qui sera saisi lors de l appel de la methode
        Client client = new Client(email);
        //on transforme notre user en json
        String json = myGson.toJson(client);
        //on effectue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_CHECK_EMAIL, json);
        //si le json retourne du texte c'est que l email saisi n existe pas encore et sera accepte pour enregistrer un nouveau client
        //si il ne retourne pas du texte c est que la requete a leve une exception car l email saisi existe deja et donc que le client est deja enregistre
        return myJson;
    }

    public static ArrayList<Client> getAllClients() throws Exception {
        //on effectue la requete de parsing
        String myJson = OkHttpUtils.sendCetOkHttpRequest(URL_WS_OCC_FERMETURES_GET_ALL_CLIENTS);
        //on recupere un json de type ArrayList<Client>
        ArrayList<Client> clientsList = myGson.fromJson(myJson, new TypeToken<ArrayList<Client>>() {
        }.getType());
        //controle avant de retourner la liste de clients
        if (clientsList.isEmpty()) {
            throw new Exception("Aucun client enregistré");
        }
        return clientsList;
    }

    public static ArrayList<Client> getClientsWithFilter(String nom) throws Exception {
        //on cree un new client avec le parametre qui sera saisi lors de l appel de la methode
        Client client = new Client(nom, null);
        //on transforme notre projet en json
        String json = myGson.toJson(client);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_GET_ALL_CLIENTS_WITH_FILTER, json);
        //on recupere un json de type ArrayList<Client>
        ArrayList<Client> clientsList = myGson.fromJson(myJson, new TypeToken<ArrayList<Client>>() {
        }.getType());
        //on retourne ce json
        return clientsList;
    }

    public static Client getInfosClient(Long idClient) throws Exception {
        //on cree un new client avec le parametre qui sera saisi lors de l appel de la methode
        Client client = new Client(idClient);
        //on transforme notre client en json
        String json = myGson.toJson(client);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_GET_INFOS_CLIENT, json);
        //on recupere un json de type Client
        Client customer = myGson.fromJson(myJson, Client.class);
        //on retourne ce json
        return customer;
    }

    public static Client getClientByIdProjet(Long idProjet) throws Exception {
        //on cree un new client avec le parametre qui sera saisi lors de l appel de la methode
        Projet projet = new Projet(idProjet, null);
        //on transforme notre projet en json
        String json = myGson.toJson(projet);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_GET_CLIENT_BY_ID_PROJET, json);
        //on recupere un json de type Client
        Client customer = myGson.fromJson(myJson, Client.class);
        //on retourne ce json
        return customer;
    }

    public static String modifClient(Long idClient, String nom, String prenom, String tel, String email, String adresse, String cp, String ville) throws Exception {
        //on cree un new client avec les parametres qui seront saisis lors de l appel de la methode
        Client client = new Client(idClient, nom, prenom, tel, email, adresse, cp, ville);
        //on transforme notre client en json
        String json = myGson.toJson(client);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_MODIF_CLIENT, json);
        //on recupere un json de type Client
        Client customer = myGson.fromJson(myJson, Client.class);
        //on retourne le nom et prenom de ce json
        return customer.getNom() + " " + customer.getPrenom();
    }

    public static void deleteClient(String email) throws Exception {
        //on cree un new client avec le parametre qui sera saisi lors de l appel de la methode
        Client client = new Client(email);
        //on transforme notre user en json
        String json = myGson.toJson(client);
        //on effecue la requete de parsing
        OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_DELETE_CLIENT, json);
    }

    public static Projet createProjetClient(String nom, String date, Long idClient) throws Exception {
        //on cree un new projet avec les parametres qui seront saisis lors de l appel de la methode
        Projet projet = new Projet(nom, date, idClient);
        //on transforme notre projet en json
        String json = myGson.toJson(projet);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_CREATE_PROJET_CLIENT, json);
        //on recupere un json de type Projet
        Projet project = myGson.fromJson(myJson, Projet.class);
        //on retourne ce json
        return project;
    }

    public static void deleteProjet(Long idProjet) throws Exception {
        //on cree un new projet avec le parametre qui sera saisi lors de l appel de la methode
        Projet projet = new Projet(idProjet, null);
        //on transforme notre projet en json
        String json = myGson.toJson(projet);
        //on effecue la requete de parsing
        OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_DELETE_PROJET, json);
    }

    public static ArrayList<Projet> getAllProjets() throws Exception {
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendCetOkHttpRequest(URL_WS_OCC_FERMETURES_GET_ALL_PROJETS);
        //on recupere un json de type ArrayList<Projet>
        ArrayList<Projet> projetsList = myGson.fromJson(myJson, new TypeToken<ArrayList<Projet>>() {
        }.getType());
        //controle avant de retourner la liste de clients
        if (projetsList.isEmpty()) {
            throw new Exception("Aucun projet enregistré");
        }
        //on retourne ce json
        return projetsList;
    }

    public static ArrayList<Projet> getAllProjetsClient(Long idClient) throws Exception {
        //on cree un new projet avec le parametre qui sera saisi lors de l appel de la methode
        Projet projet = new Projet(idClient);
        //on transforme notre projet en json
        String json = myGson.toJson(projet);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_GET_ALL_PROJETS_CLIENT, json);
        //on recupere un json de type ArrayList<Projet>
        ArrayList<Projet> projetsList = myGson.fromJson(myJson, new TypeToken<ArrayList<Projet>>() {
        }.getType());
        //on retourne ce json
        return projetsList;
    }

    public static Projet getInfosProjet(Long idProjet) throws Exception {
        //on cree un new projet avec le parametre qui sera saisi lors de l appel de la methode
        Projet projet = new Projet(idProjet, null);
        //on transforme notre projet en json
        String json = myGson.toJson(projet);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_GET_INFOS_PROJET, json);
        //on recupere un json de type Long
        Projet project = myGson.fromJson(myJson, Projet.class);
        //on retourne le json de projet
        return project;
    }

    public static SousProjet createSousProjet(String lieu, String photo1, String photo2, String photo3, String photo4, String longueur, String largeur, String detail, Long idProjet, Long categorie) throws Exception {
        //on cree un new sous-projet avec les parametres qui seront saisis lors de l appel de la methode
        SousProjet sousProjet = new SousProjet(lieu, photo1, photo2, photo3, photo4, longueur, largeur, detail, idProjet, categorie);
        //on transforme notre projet en json
        String json = myGson.toJson(sousProjet);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_CREATE_SOUS_PROJET, json);
        //on recupere un json de type Projet
        SousProjet project = myGson.fromJson(myJson, SousProjet.class);
        //on retourne ce json
        return project;
    }

    public static void modifSousProjet(Long idSousProjet, String lieu, String photo1, String photo2, String photo3, String photo4, String longueur, String largeur, String detail, Long idProjet, Long idCat) throws Exception {
        //on cree un new sousProjet avec les parametres qui seront saisis lors de l appel de la methode
        SousProjet sousProjet = new SousProjet(idSousProjet, lieu, photo1, photo2, photo3, photo4, longueur, largeur, detail, idProjet, idCat);
        //on transforme notre sousProjet en json
        String json = myGson.toJson(sousProjet);
        //on effecue la requete de parsing
        OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_MODIF_SOUS_PROJET, json);
    }

    public static void deleteSousProjet(Long idSousProjet) throws Exception {
        //on cree un new sousProjet avec le parametre qui sera saisi lors de l appel de la methode
        SousProjet sousProjet = new SousProjet(idSousProjet, null);
        //on transforme notre projet en json
        String json = myGson.toJson(sousProjet);
        //on effecue la requete de parsing
        OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_DELETE_SOUS_PROJET, json);
    }

    public static ArrayList<SousProjet> getAllSousProjets() throws Exception {
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendCetOkHttpRequest(URL_WS_OCC_FERMETURES_GET_ALL_SOUS_PROJETS);
        //on recupere un json de type ArrayList<Projet>
        ArrayList<SousProjet> sousProjetsList = myGson.fromJson(myJson, new TypeToken<ArrayList<SousProjet>>() {
        }.getType());
        //controle avant de retourner la liste de clients
        if (sousProjetsList.isEmpty()) {
            throw new Exception("Aucun sous-projet enregistré");
        }
        //on retourne ce json
        return sousProjetsList;
    }

    public static ArrayList<SousProjet> getAllSousProjetsDunProjet(Long idProjet) throws Exception {
        //on cree un new sous-projet avec le parametre qui sera saisi lors de l appel de la methode
        SousProjet sousProjet = new SousProjet(idProjet);
        //on transforme notre sous-projet en json
        String json = myGson.toJson(sousProjet);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_GET_ALL_SOUS_PROJETS_D_UN_PROJET, json);
        //on recupere un json de type ArrayList<SousProjet>
        ArrayList<SousProjet> sousProjetsList = myGson.fromJson(myJson, new TypeToken<ArrayList<SousProjet>>() {
        }.getType());
        //on retourne ce json
        return sousProjetsList;
    }

    public static SousProjet getInfosSousProjet(Long idSousProjet) throws Exception {
        //on cree un new sous projet avec le parametre qui sera saisi lors de l appel de la methode
        SousProjet sousProjet = new SousProjet(idSousProjet, null);
        //on transforme notre projet en json
        String json = myGson.toJson(sousProjet);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_GET_INFOS_SOUS_PROJET, json);
        //on recupere un json de type Long
        SousProjet ssProject = myGson.fromJson(myJson, SousProjet.class);
        //on retourne le json de sous projet
        return ssProject;
    }

    public static ArrayList<String> getAllPhotos(Long idSousProjet) throws Exception {
        //on cree un new sous-projet avec le parametre qui sera saisi lors de l appel de la methode
        SousProjet sousProjet = new SousProjet(idSousProjet, null);
        //on transforme notre projet en json
        String json = myGson.toJson(sousProjet);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_GET_ALL_PHOTOS, json);
        //on recupere un json de type ArrayList<String>
        ArrayList<String> photosList = myGson.fromJson(myJson, new TypeToken<ArrayList<String>>() {
        }.getType());
        //on retourne ce json
        return photosList;
    }

    public static Categorie createCategorie(String nom, String detail) throws Exception {
        //on cree un new projet avec les parametres qui seront saisis lors de l appel de la methode
        Categorie categorie = new Categorie(nom, detail);
        //on transforme notre projet en json
        String json = myGson.toJson(categorie);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_CREATE_CATEGORIE, json);
        //on recupere un json de type Projet
        Categorie cat = myGson.fromJson(myJson, Categorie.class);
        //on retourne ce json
        return cat;
    }


    public static ArrayList<Categorie> getAllCategories() throws Exception {

        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendCetOkHttpRequest(URL_WS_OCC_FERMETURES_GET_ALL_CATEGORIES);
        //on recupere un json de type ArrayList<String>
        ArrayList<Categorie> categoriesList = myGson.fromJson(myJson, new TypeToken<ArrayList<Categorie>>() {
        }.getType());
        //controle avant de retourner la liste de clients
        if (categoriesList.isEmpty()) {
            throw new Exception("Aucune catégorie enregistrée");
        }
        //on retourne ce json
        return categoriesList;
    }

    public static ArrayList<String> getAllNomsCategories() throws Exception {
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendCetOkHttpRequest(URL_WS_OCC_FERMETURES_GET_ALL_NOMS_CATEGORIES);
        //on recupere un json de type ArrayList<String>
        ArrayList<String> categoriesList = myGson.fromJson(myJson, new TypeToken<ArrayList<String>>() {
        }.getType());
        //controle avant de retourner la liste de clients
        if (categoriesList.isEmpty()) {
            throw new Exception("Aucune catégorie enregistrée");
        }
        //on retourne ce json
        return categoriesList;
    }

    public static String getNomCategorieDunSousProjet(Long idCat) throws Exception {
        //on cree une new categorie avec le parametre qui sera saisi lors de l appel de la methode
        Categorie categorie = new Categorie(idCat);
        //on transforme notre categorie en json
        String json = myGson.toJson(categorie);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_GET_NOM_CATEGORIE_D_UN_SOUS_PROJET, json);
        //on retourne le nom retourne par la requete
        return myJson;
    }

    public static Long getIdCategorieDunSousProjet(String nomCat) throws Exception {
        //on cree une new categorie avec le parametre qui sera saisi lors de l appel de la methode
        Categorie categorie = new Categorie(nomCat);
        //on transforme notre categorie en json
        String json = myGson.toJson(categorie);
        //on effecue la requete de parsing
        String myJson = OkHttpUtils.sendPostOkHttpRequest(URL_WS_OCC_FERMETURES_GET_ID_CATEGORIE_D_UN_SOUS_PROJET, json);
        //on recupere un json de type Long
        Categorie cat = myGson.fromJson(myJson, Categorie.class);
        //on retourne ce json
        return cat.getIdCat();
    }


}
