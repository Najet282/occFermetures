package com.example.occ_fermetures.controller;

import com.example.occ_fermetures.model.beans.*;
import com.example.occ_fermetures.model.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class WebServices {

    //avec @Autowired : injection de dépendance, Spring va gérer la connexion avec la bdd
    @Autowired
    private UtilisateurDao utilisateurDao;
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private ProjetDao projetDao;
    @Autowired
    private CategorieDao categorieDao;
    @Autowired
    private SousProjetDao sousProjetDao;

    //http://localhost:8080/test
    @GetMapping("/test")
    public String test(){
        System.out.println("/test");
        return "Connection ok";
    }

    /*************************     RESPONSABLES     ****************************/

    /** ENREGISTRER UN UTILISATEUR MENUISIER **/
    //http://localhost:8080/saveUtilisateur
    //json attendu :
    //{"login":"nom", "mdp":"mdp", "isAdmin":false}
    @PostMapping("/saveUtilisateur")
    public void saveUtilisateur(@RequestBody Utilisateur utilisateur, HttpServletResponse response) throws Exception {
        System.out.println("/saveUtilisateur");
        //contrôles des donnees saisies avant de les enregistrer
        if(utilisateur.getLogin()==null || utilisateur.getLogin().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucun login saisi");
        }
        if(utilisateur.getMdp()==null || utilisateur.getMdp().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucun mot de passe saisi");
        }
        utilisateurDao.save(utilisateur);
    }

    /** SUPPRIMER UN UTILISATEUR MENUISIER **/
    //http://localhost:8080/deleteUtilisateur
    //json attendu :
    //{"login":"nom"}
    @PostMapping("/deleteUtilisateur")
    public void deleteUtilisateur(@RequestBody Utilisateur utilisateur, HttpServletResponse response) throws Exception {
        System.out.println("/deleteUtilisateur");
        //contrôle du login saisi avant de supprimer l'utilisateur
        if(utilisateur.getLogin()==null || utilisateur.getLogin().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucun login saisi");
        }
        //recupere un utilisateur par le login saisi en param lors de l appel de la requete
        Utilisateur userBdd = utilisateurDao.findByLogin(utilisateur.getLogin());
        //si je ne trouve pas de login -> utilisateur n existe pas
        if(userBdd==null) {
            throw new Exception("Login incorrect");
        }else {//si le login existe et qu il correspond a celui retourne dans le json
            utilisateurDao.delete(userBdd);
        }
    }


    /** RECUPERER LES INFOS D UN UTILISATEUR **/
    //http://localhost:8080/getInfosUtilisateur
    //json attendu :
    //{"login":"gilles"}
    @PostMapping("/getInfosUtilisateur")
    public Utilisateur getInfosUtilisateur(@RequestBody Utilisateur utilisateur)  throws Exception {
        System.out.println("/getInfosClient");
        //recupere un utilisateur par le login saisi en param lors de l appel de la requete
        Utilisateur userBdd = utilisateurDao.findByLogin(utilisateur.getLogin());
        if(userBdd==null){
            throw new Exception("Login incorrect");
        }
        return userBdd;
    }

    /** CHECK CODE D'ACCES POUR GERER COMPTES UTILISATEURS **/
    //http://localhost:8080/checkAccesComptes
    //json attendu :
    //{"login":"test1"}
    @PostMapping("/checkAccesComptes")
    public void checkAccesComptes(@RequestBody Utilisateur utilisateur) throws Exception {
        System.out.println("/checkAccesComptes");
        //recupere un utilisateur par le login saisi en param lors de l appel de la requete
        Utilisateur userBdd = utilisateurDao.findByLogin(utilisateur.getLogin());
        //si le login de userBdd==null -> user n existe pas
        if(userBdd==null){
            throw new Exception("Login inconnu");
        }
        if(userBdd.getIsAdmin()==false){
            throw new Exception("Accès non autorisé");
        }
    }

    /** RECUPERER LA LISTE DE TOUS LES UTILISATEURS **/
    //http://localhost:8080/getAllUsers
    @GetMapping("/getAllUsers")
    public List<Utilisateur> getAllUsers() throws Exception{
        System.out.println("/getAllUsers");
        if(utilisateurDao.findAll()==null){
            throw new Exception("Aucun utilisateur enregistré");
        }
        return utilisateurDao.findAll();
    }


    /*************************     UTILISATEURS     ****************************/

    /** CHECK LES LOGIN ET MDP POUR SE CONNECTER **/
    //http://localhost:8080/checkLogin
    //json attendu :
    //{"login":"nom", "mdp":"mdp"}
    @PostMapping("/checkLogin")
    public void checkLogin(@RequestBody Utilisateur utilisateur) throws Exception {
        System.out.println("/checkLogin");
        //recupere un utilisateur par le login saisi en param lors de l appel de la requete
        Utilisateur userBdd = utilisateurDao.findByLogin(utilisateur.getLogin());
        //si le login de userBdd==null -> user n existe pas
        if(userBdd==null){
            throw new Exception("Login incorrect");
        }
        //si le login de userBdd!=null on compare le mdp
        String mdpBdd = userBdd.getMdp();
        String mdpUser = utilisateur.getMdp();
        //si mdp different de celui en bdd
        if(!mdpBdd.equals(mdpUser)){
            throw new Exception("Mot de passe incorrect");
        }
    }

    /** CREER UN COMPTE CLIENT **/
    //http://localhost:8080/createClient
    //json attendu :
    //{"nom":"Toto", "prenom":"Toto", "tel":"0123456789", "email":"email@hotmail.fr", "adresse":"adresse1"}
    @PostMapping("/createClient")
    public Client createClient(@RequestBody Client client, HttpServletResponse response) throws Exception {
        System.out.println("/createClient");
        //contrôle des donnees saisies avant de les enregistrer
        if(client.getNom()==null || client.getNom().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucun nom saisi");
        }
        if(client.getPrenom()==null || client.getPrenom().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucun prénom saisi");
        }
        if(client.getTel()==null || client.getTel().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucun numéro de téléphone saisi");
        }
        if(client.getEmail()==null || client.getEmail().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucun email saisi");
        }
        if(client.getAdresse()==null || client.getAdresse().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucune adresse saisie");
        }
        if(client.getCp()==null || client.getCp().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucun code postal saisi");
        }
        if(client.getVille()==null || client.getVille().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucune ville saisie");
        }
        //retourne boolean; si retourne false format email incorrect
        if(!isEmailAdress(client.getEmail())){
            throw new Exception(response.getStatus()+"\nFormat de l'email incorrect");
        }
        //si tout est ok on sauvegarde les donnees saisies
        clientDao.save(client);
        return client;
    }

    /** CHECK SI FORMAT EMAIL CLIENT CORRECT **/
    //methode utilisee pour le controle de l email avant la sauvegarde d un client (ci dessus) et la modif d un client (plus bas)
    public boolean isEmailAdress(String email) {
        Pattern p = Pattern
                .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = p.matcher(email.toUpperCase());
        return m.matches();
    }

    /** CHECK SI EMAIL CLIENT DEJA EXISTANT **/
    //http://localhost:8080/checkEmail
    //json attendu :
    //{"email":"email@blablabla"}
    @PostMapping("/checkEmail")
    public String checkEmail(@RequestBody Client client) throws Exception {
        System.out.println("/checkEmail");
        //recupere un client par l email saisi en param lors de l appel de la requete
        Client clientBdd = clientDao.findByEmail(client.getEmail());
        //si clientBdd!=null -> client existe deja
        if(clientBdd!=null){
            throw new Exception("L'adresse " + clientBdd.getEmail() + " existe déjà");
        }else {
            return "Email inexistant";
        }
    }

    /** RECUPERER LA LISTE DE TOUS LES CLIENTS **/
    //http://localhost:8080/getAllClients
    @GetMapping("/getAllClients")
    public List<Client> getAllClients() throws Exception{
        System.out.println("/getAllClients");
        if(clientDao.findAll()==null){
            throw new Exception("Aucun client enregistré");
        }
        return clientDao.findAllByOrderByIdClientDesc();
    }

    /** RECUPERER LA LISTE DES CLIENTS AVEC UN FILTRE**/
    //http://localhost:8080/getClientsWithFilter
    //json attendu :
    //{"nom":"tes"}
    @PostMapping("/getClientsWithFilter")
    public List<Client> getClientsWithFilter(@RequestBody Client client)  throws Exception{
        System.out.println("/getClientsWithFilter");
        if(clientDao.findByNomContaining(client.getNom())==null){
            throw new Exception("Aucun client enregistré");
        }
        return clientDao.findByNomContaining(client.getNom());
    }

    /** RECUPERER LES INFOS D UN CLIENT **/
    //http://localhost:8080/getInfosClient
    //json attendu :
    //{"idClient":"4"}
    @PostMapping("/getInfosClient")
    public Client getInfosClient(@RequestBody Client client)  throws Exception {
        System.out.println("/getInfosClient");
        if(clientDao.findByIdClient(client.getIdClient())==null){
            throw new Exception("Aucune info pour ce client : improbable! Je dirai donc aucun client correspondant.");
        }
        return clientDao.findByIdClient(client.getIdClient());
    }

    /** MODIFIER UN COMPTE CLIENT **/
    //http://localhost:8080/modifClient
    //json attendu :
    //{"idClient":"4", "nom":"Toto", "prenom":"tata", "tel":"0123456789", "email":"emailToto@hotmail.fr", "adresse":"chezToto"}
    @PostMapping("/modifClient")
    public void modifClient(@RequestBody Client client, HttpServletResponse response) throws Exception {
        System.out.println("/modifClient");
        //recupere un client par son id envoye en param lors de l appel de la requete
        Client clientBdd = clientDao.findByIdClient(client.getIdClient());
        //si je ne trouve pas d'id -> client n existe pas
        if (clientBdd == null) {
            throw new Exception("Ce client n'est pas enregistré");
        } else {//si l'id' existe et qu il correspond a celui retourne dans le json
            //verification de modification entre les donnees enregistrees dans la bdd et les champs recus, pour n'enregistrer que les modifications
            if(!clientBdd.getNom().equals(client.getNom())){
                //controle a nouveau que le champ de soit pas vide
                if(client.getNom()==null || client.getNom().trim().length()==0){
                    throw new Exception(response.getStatus()+"\nAucun nom saisi");
                }else{
                    clientDao.updateNom(client.getNom(), clientBdd.getIdClient());
                }
            }
            if(!clientBdd.getPrenom().equals(client.getPrenom())){
                if(client.getPrenom()==null || client.getPrenom().trim().length()==0){
                    throw new Exception(response.getStatus()+"\nAucun prénom saisi");
                }else{
                    clientDao.updatePrenom(client.getPrenom(), clientBdd.getIdClient());
                }
            }
            if(!clientBdd.getTel().equals(client.getTel())){
                if(client.getTel()==null || client.getTel().trim().length()==0){
                    throw new Exception(response.getStatus()+"\nAucun numéro de téléphone saisi");
                }else{
                    clientDao.updateTel(client.getTel(), clientBdd.getIdClient());
                }
            }
            if(!clientBdd.getEmail().equals(client.getEmail())){
                if(client.getEmail()==null || client.getEmail().trim().length()==0){
                    throw new Exception(response.getStatus()+"\nAucun email saisi");
                }//si il le champ email n est pas vide, on verifie son format
                else if(!isEmailAdress(client.getEmail())){
                    throw new Exception(response.getStatus()+"\nFormat de l'email incorrect");
                }
                else{
                    clientDao.updateEmail(client.getEmail(), clientBdd.getIdClient());
                }
            }
            if(!clientBdd.getAdresse().equals(client.getAdresse())){
                if(client.getAdresse()==null || client.getAdresse().trim().length()==0){
                    throw new Exception(response.getStatus()+"\nAucune adresse saisie");
                }else{
                    clientDao.updateAdresse(client.getAdresse(), clientBdd.getIdClient());
                }
            }
            if(!clientBdd.getCp().equals(client.getCp())){
                if(client.getCp()==null || client.getCp().trim().length()==0){
                    throw new Exception(response.getStatus()+"\nAucun code postal saisie");
                }else{
                    clientDao.updateCp(client.getCp(), clientBdd.getIdClient());
                }
            }
            if(!clientBdd.getVille().equals(client.getVille())){
                if(client.getVille()==null || client.getVille().trim().length()==0){
                    throw new Exception(response.getStatus()+"\nAucune ville saisie");
                }else{
                    clientDao.updateVille(client.getVille(), clientBdd.getIdClient());
                }
            }
        }
    }

    /** SUPPRIMER UN COMPTE CLIENT **/
    //http://localhost:8080/deleteClient
    //json attendu :
    //{"email":"email@hotmail.fr"}
    @PostMapping("/deleteClient")
    public void deleteClient(@RequestBody Client client, HttpServletResponse response) throws Exception {
        System.out.println("/deleteClient");
        //contrôle de l'email envoye avant de supprimer le client
        if(client.getEmail()==null || client.getEmail().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucun email saisi");
        }
        //recupere un client par son email envoye en param lors de l appel de la requete
        Client clientBdd = clientDao.findByEmail(client.getEmail());
        //si je ne trouve pas d email -> client n existe pas
        if(clientBdd==null) {
            throw new Exception("Email incorrect");
        }else {//si le login existe et qu il correspond a celui retourne dans le json
            //on cree une liste contenant tous les projets du clients a supprimer
            List<Projet> listProjets = projetDao.findByIdClient(client.getIdClient());
            if(listProjets!=null){
                //on parcourt la liste des projets
                for(int i = 0; i<listProjets.size(); i++) {
                    //on cree une liste contenant tous les sous projets du projet a supprimer
                    List<SousProjet> listSousProjets = sousProjetDao.findByIdProjet(listProjets.get(i).getIdProjet());
                    for(int j = 0; j<listSousProjets.size(); j++) {
                        //on supprime les sous projets un a un
                        sousProjetDao.delete(listSousProjets.get(j));
                    }
                    //on supprime les projets un a un
                    projetDao.delete(listProjets.get(i));
                }
            }
            clientDao.delete(clientBdd);
        }
    }

    /** CREER UN PROJET CLIENT **/
    //http://localhost:8080/createProjetClient
    //json attendu :
    //{"nom":"renovation", "date":"04-11-2021", "idClient":"4"}
    @PostMapping("/createProjetClient")
    public Projet createProjetClient(@RequestBody Projet projet, HttpServletResponse response) throws Exception {
        System.out.println("/createProjetClient");
        //contrôles des donnees saisies avant de les enregistrer
        if(projet.getNom()==null || projet.getNom().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucun nom saisi");
        }
        if(projet.getIdClient()==null){
            throw new Exception(response.getStatus()+"\nAucun id client reconnu");
        }
        if(projet.getDate()==null){
            throw new Exception(response.getStatus()+"\nAucune date générée");
        }
        //si tout est ok on sauvegarde les donnees saisies
        projetDao.save(projet);
        return projet;
    }

    /** SUPPRIMER UN PROJET CLIENT **/
    //http://localhost:8080/deleteProjet
    //json attendu :
    //{"idProjet":4}
    @PostMapping("/deleteProjet")
    public void deleteProjet(@RequestBody Projet projet, HttpServletResponse response) throws Exception {
        System.out.println("/deleteClient");
        //contrôle de l id envoye avant de supprimer le projet
        if(projet.getIdProjet()==null){
            throw new Exception(response.getStatus()+"\nAucun id projet saisi");
        }
        //recupere un client par son email envoye en param lors de l appel de la requete
        Projet projetBdd = projetDao.findByIdProjet(projet.getIdProjet());
        //si je ne trouve pas d email -> client n existe pas
        if(projetBdd==null) {
            throw new Exception("Id Projet incorrect");
        }else {//si le login existe et qu il correspond a celui retourne dans le json
            //on cree une liste contenant tous les sous projets du projet a supprimer
            List<SousProjet> listSousProjets = sousProjetDao.findByIdProjet(projet.getIdProjet());
            if(listSousProjets!=null){
                //on parcourt la liste des sous projets et on les supprime un a un
                for(int i = 0; i<listSousProjets.size(); i++) {
                    sousProjetDao.delete(listSousProjets.get(i));
                }
            }
            projetDao.delete(projetBdd);
        }
    }

    /** RECUPERER LA LISTE DE TOUS LES PROJETS **/
    //http://localhost:8080/getAllProjets
    @GetMapping("/getAllProjets")
    public List<Projet> getAllProjets() throws Exception{
        System.out.println("/getAllProjets");
        if(projetDao.findAll().isEmpty()){
            throw new Exception("Aucun projet enregistré");
        }
        return projetDao.findAllByOrderByIdProjetDesc();
    }

    /** RECUPERER LA LISTE DE TOUS LES PROJETS D'UN CLIENT**/
    //http://localhost:8080/getAllProjetsClient
    //json attendu :
    //{"idClient":4}
    @PostMapping("/getAllProjetsClient")
    public List<Projet> getAllProjetsClient(@RequestBody Projet projet) throws Exception {
        System.out.println("/getAllProjetsClient : ");
        if(projetDao.findByIdClient(projet.getIdClient())==null){
            throw new Exception("Aucun projet pour ce client");
        }
        return projetDao.findByIdClientOrderByIdProjetDesc(projet.getIdClient());
    }

    /** RECUPERER LES INFOS D'UN PROJET **/
    //http://localhost:8080/getInfosProjet
    //json attendu :
    //{"idProjet":4}
    @PostMapping("/getInfosProjet")
    public Projet getInfosProjet(@RequestBody Projet projet) throws Exception {
        System.out.println("/getInfosProjet : ");
        if(projetDao.findByIdProjet(projet.getIdProjet())==null){
            throw new Exception("Aucune info pour ce projet : improbable! Je dirai donc aucun projet correspondant.");
        }
        return projetDao.findByIdProjet(projet.getIdProjet());
    }

    /** CREER UN SOUS-PROJET **/
    //http://localhost:8080/createSousProjet
    //json attendu :
    //{"photo1":"photos.jpg", "longueur":"220cm", "largeur":"120cm", "option":"", "detail":"blablabla", "idCat":"1", "idProjet":"4"}
    @PostMapping("/createSousProjet")
    public SousProjet createSousProjet(@RequestBody SousProjet sousProjet, HttpServletResponse response) throws Exception {
        System.out.println("/createSousProjet");
        //contrôles des donnees saisies avant de les enregistrer
        if((sousProjet.getPhoto1()==null || sousProjet.getPhoto1().trim().length()==0) && (sousProjet.getPhoto2()==null || sousProjet.getPhoto2().trim().length()==0) && (sousProjet.getPhoto3()==null || sousProjet.getPhoto3().trim().length()==0) && (sousProjet.getPhoto4()==null || sousProjet.getPhoto4().trim().length()==0)){
            throw new Exception(response.getStatus()+"\nAucune photo prise");
        }
        if(sousProjet.getLongueur()==null || sousProjet.getLongueur().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucune longueur saisie");
        }
        if(sousProjet.getLargeur()==null || sousProjet.getLargeur().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucune largeur saisie");
        }
        if(sousProjet.getDetail()==null || sousProjet.getDetail().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucun détail saisi");
        }
        //si tout est ok on sauvegarde les donnees saisies
        sousProjetDao.save(sousProjet);
        return sousProjet;
    }

    /** MODIFIER UN SOUS PROJET **/
    //http://localhost:8080/modifSousProjet
    //json attendu :
    //{"idSousProjet":"4", "idCat":"fenetre", "idProjet":87, "longueur":"120", "largeur":"220", "detail":"aucun"}
    @PostMapping("/modifSousProjet")
    public void modifSousProjet(@RequestBody SousProjet sousProjet, HttpServletResponse response) throws Exception {
        System.out.println("/modifSousProjet");
        //recupere un sousProjet par son id envoye en param lors de l appel de la requete
        SousProjet sousProjetBdd = sousProjetDao.findByIdSousProjet(sousProjet.getIdSousProjet());
        //si je ne trouve pas d'id -> sousProejt n existe pas
        if (sousProjetBdd == null) {
            throw new Exception("Ce sous-projet n'est pas enregistré");
        } else {//si l'id' existe et qu il correspond a celui retourne dans le json
            //verification de modification entre les donnees enregistrees dans la bdd et les champs recus, pour n'enregistrer que les modifications
            if(!sousProjetBdd.getLongueur().equals(sousProjet.getLongueur())){
                //controle a nouveau que le champ de soit pas vide
                if(sousProjetBdd.getLongueur()==null || sousProjet.getLongueur().trim().length()==0){
                    throw new Exception(response.getStatus()+"\nAucune longueur saisie");
                }else{
                    sousProjetDao.updateLongueur(sousProjet.getLongueur(), sousProjet.getIdSousProjet());
                }
            }
            if(!sousProjetBdd.getLargeur().equals(sousProjet.getLargeur())){
                //controle a nouveau que le champ de soit pas vide
                if(sousProjetBdd.getLargeur()==null || sousProjet.getLargeur().trim().length()==0){
                    throw new Exception(response.getStatus()+"\nAucune largeur saisie");
                }else{
                    sousProjetDao.updateLargeur(sousProjet.getLargeur(), sousProjet.getIdSousProjet());
                }
            }
            if(!sousProjetBdd.getDetail().equals(sousProjet.getDetail())){
                //controle a nouveau que le champ de soit pas vide
                if(sousProjetBdd.getDetail()==null || sousProjet.getDetail().trim().length()==0){
                    throw new Exception(response.getStatus()+"\nAucun détail saisi");
                }else{
                    sousProjetDao.updateDetail(sousProjet.getDetail(), sousProjet.getIdSousProjet());
                }
            }
            sousProjetDao.save(sousProjet);

        }
    }

    /** SUPPRIMER UN SOUS PROJET **/
    //http://localhost:8080/deleteSousProjet
    //json attendu :
    //{"idSousProjet":24}
    @PostMapping("/deleteSousProjet")
    public void deleteSousProjet(@RequestBody SousProjet sousProjet, HttpServletResponse response) throws Exception {
        System.out.println("/deleteClient");
        //contrôle de l idSousProjet envoye avant de supprimer le sousProjet
        if(sousProjet.getIdSousProjet()==null){
            throw new Exception(response.getStatus()+"\nAucun id sous-projet saisi");
        }
        //recupere un sousProjet par son id envoye en param lors de l appel de la requete
        SousProjet sousProjetBdd = sousProjetDao.findByIdSousProjet(sousProjet.getIdSousProjet());
        //si je ne trouve pas d id -> sousPorjet n existe pas
        if(sousProjetBdd==null) {
            throw new Exception("Id sous-projet incorrect");
        }else {//si le login existe et qu il correspond a celui retourne dans le json
            sousProjetDao.delete(sousProjetBdd);
        }
    }

    /** RECUPERER LA LISTE DE TOUS LES SOUS PROJETS **/
    //http://localhost:8080/getAllSousProjets
    @GetMapping("/getAllSousProjets")
    public List<SousProjet> getAllSousProjets() throws Exception{
        System.out.println("/getAllSousProjets");
        if(sousProjetDao.findAll().isEmpty()){
            throw new Exception("Aucun sous-projet enregistré");
        }
        return sousProjetDao.findAllByOrderByIdSousProjetDesc();
    }

    /** RECUPERER LA LISTE DE TOUS LES SOUS PROJETS D'UN CLIENT **/
    //http://localhost:8080/getAllSousProjetsDunProjet
    //json attendu :
    //{"idProjet":4}
    @PostMapping("/getAllSousProjetsDunProjet")
    public List<SousProjet> getAllSousProjetsDunProjet(@RequestBody SousProjet sousProjet) throws Exception {
        System.out.println("/getAllSousProjetsDunProjet : ");
        if(sousProjetDao.findByIdProjet(sousProjet.getIdProjet())==null){
            throw new Exception("Aucun sous-projet dans ce projet");
        }
        return sousProjetDao.findByIdProjetOrderByIdProjetDesc(sousProjet.getIdProjet());
    }

    /** RECUPERER LES INFOS D'UN SOUS PROJET **/
    //http://localhost:8080/getInfosSousProjet
    //json attendu :
    //{"idSousProjet":4}
    @PostMapping("/getInfosSousProjet")
    public SousProjet getInfosSousProjet(@RequestBody SousProjet sousProjet) throws Exception {
        System.out.println("/getInfosProjet : ");
        if(sousProjetDao.findByIdSousProjet(sousProjet.getIdSousProjet())==null){
            throw new Exception("Aucune info pour ce projet : improbable! Je dirai donc aucun projet correspondant.");
        }
        return sousProjetDao.findByIdSousProjet(sousProjet.getIdSousProjet());
    }

    /** RECUPERER LA LISTE DE TOUTES LES PHOTOS D'UN SOUS-PROJET**/
    //http://localhost:8080/getAllPhotos
    //json attendu :
    //{"idSousProjet":11}
    @PostMapping("/getAllPhotos")
    public List<String> getAllPhotos(@RequestBody SousProjet sousProjet) throws Exception {
        System.out.println("/getAllPhotos : ");
        ArrayList<String> listPhotos = new ArrayList<>();
        if(sousProjetDao.findPhoto1(sousProjet.getIdSousProjet())!=null && sousProjetDao.findPhoto1(sousProjet.getIdSousProjet()).trim().length()>10) {
            listPhotos.add(sousProjetDao.findPhoto1(sousProjet.getIdSousProjet()));
        }
        //si il n y a aucun contenu; la requete retourne "un chiffre + espace + null" lorsque le champ de la photo est vide, alors pour ne pas ajouter a la liste un champ vide je n ajoute que ceux qui sont superieur a 10, ca laisse de la marge et les champs non vide ont des milliers de caracteres, donc pas de risque d erreur
        if(sousProjetDao.findPhoto2(sousProjet.getIdSousProjet())!=null && sousProjetDao.findPhoto2(sousProjet.getIdSousProjet()).trim().length()>10) {
            listPhotos.add(sousProjetDao.findPhoto2(sousProjet.getIdSousProjet()));
        }
        if(sousProjetDao.findPhoto3(sousProjet.getIdSousProjet())!=null && sousProjetDao.findPhoto3(sousProjet.getIdSousProjet()).trim().length()>10) {
            listPhotos.add(sousProjetDao.findPhoto3(sousProjet.getIdSousProjet()));
        }
        if(sousProjetDao.findPhoto4(sousProjet.getIdSousProjet())!=null && sousProjetDao.findPhoto4(sousProjet.getIdSousProjet()).trim().length()>10) {
            listPhotos.add(sousProjetDao.findPhoto4(sousProjet.getIdSousProjet()));
        }
        if(listPhotos==null){
            throw new Exception("Aucune photo dans ce sous-projet");
        }
        return listPhotos;
    }

    /** CREER UNE CATEGORIE DE PROJET **/
    //http://localhost:8080/createCategorie
    //json attendu :
    //{"nom":"fenetre", "detail":""}
    @PostMapping("/createCategorie")
    public Categorie createCategorie(@RequestBody Categorie categorie, HttpServletResponse response) throws Exception {
        System.out.println("/createCategorie");
        //contrôles des donnees saisies avant de les enregistrer
        if(categorie.getNom()==null || categorie.getNom().trim().length()==0){
            throw new Exception(response.getStatus()+"\nAucun nom de catégorie saisi");
        }
        //si tout est ok on sauvegarde les donnees saisies
        categorieDao.save(categorie);
        return categorie;
    }

    /** RECUPERER LA LISTE DE TOUTES LES CATEGORIES POUR LE RECYCLERVIEW DE CATEGORIES **/
    //http://localhost:8080/getAllCategories
    @GetMapping("/getAllCategories")
    public List<Categorie> getAllCategories() throws Exception{
        System.out.println("/getAllCategories");
        if(categorieDao.findAll()==null){
            throw new Exception("Aucune catégorie enregistrée");
        }
        return categorieDao.findAll();
    }

    /** RECUPERER LA LISTE DE TOUS LES NOMS DE CATEGORIE POUR SPINNER (MENU DEROULANT) **/
    //http://localhost:8080/getAllNomsCategories
    @GetMapping("/getAllNomsCategories")
    public List<String> getAllNomsCategories() throws Exception{
        System.out.println("/getAllNomsCategories");
        if(categorieDao.findNomCat()==null){
            throw new Exception("Aucune catégorie enregistrée");
        }
        return categorieDao.findNomCat();
    }

    /** RECUPERER LE NOM DE LA CATEGORIE D'UN SOUS-PROJET**/
    //http://localhost:8080/getNomCategorieDunSousProjet
    //json attendu :
    //{"idCat":1}
    @PostMapping("/getNomCategorieDunSousProjet")
    public String getNomCategorieDunSousProjet(@RequestBody Categorie categorie) throws Exception {
        System.out.println("/getNomCategorieDunSousProjet : ");
        if(categorieDao.findNomByIdCat(categorie.getIdCat())==null){
            throw new Exception("Aucun nom de catégorie pour cet identidiant.");
        }
        return categorieDao.findNomByIdCat(categorie.getIdCat());
    }

    /** RECUPERER L ID DE LA CATEGORIE D'UN SOUS-PROJET**/
    //http://localhost:8080/getIdCategorieDunSousProjet
    //json attendu :
    //{"nom":"fenetre"}
    @PostMapping("/getIdCategorieDunSousProjet")
    public Categorie getIdCategorieDunSousProjet(@RequestBody Categorie categorie) throws Exception {
        System.out.println("/getNomCategorieDunSousProjet : ");
        if(categorieDao.findAllByNom(categorie.getNom())==null){
            throw new Exception("Aucun identifiant de catégorie pour ce nom.");
        }
        return categorieDao.findAllByNom(categorie.getNom());
    }

    /** RECUPERER LES INFOS D'UN CLIENT PAR UN ID PPROJET**/
    //http://localhost:8080/getClientByIdProjet
    //json attendu :
    //{"idProjet":4}
    @PostMapping("/getClientByIdProjet")
    public Client getClientByIdProjet(@RequestBody Projet projet) throws Exception {
        System.out.println("/getClientByIdProjet : ");
        //on recup l id d un client par un id projet
        Long idClient = projetDao.getIdClient(projet.getIdProjet());
        if(idClient==null){
            throw new Exception("Aucun id client pour cet id projet.");
        }
        Client client = clientDao.findByIdClient(idClient);
        if(client==null){
            throw new Exception("Aucun nom de client pour cet id client.");
        }
        return client;
    }
}
