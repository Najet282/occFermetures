package com.example.occ_fermetures.model.dao;
import com.example.occ_fermetures.model.beans.Client;
import com.example.occ_fermetures.model.beans.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface ClientDao extends JpaRepository<Client, Long> {

    //retourne un Client par son email
    Client findByEmail(String email);
    //retourne un Client par son id
    Client findByIdClient(Long id);

    //retourne la liste de Client avec le filtre passe en param
    List<Client> findByNomContaining(String nom);

    //retourne la liste de tous les clients du plus recent au dernier grace au numero de l idClient
    List<Client> findAllByOrderByIdClientDesc();

    //sauvegarde les donnees modifiees d un client en fonction de son id
    @Modifying
    @Query("UPDATE Client c SET c.nom=?1 WHERE c.idClient=?2")
    void updateNom(String nom, Long idClient);

    @Modifying
    @Query("UPDATE Client c SET c.prenom=?1 WHERE c.idClient=?2")
    void updatePrenom(String prenom, Long idClient);

    @Modifying
    @Query("UPDATE Client c SET c.tel=?1 WHERE c.idClient=?2")
    void updateTel(String tel, Long idClient);

    @Modifying
    @Query("UPDATE Client c SET c.email=?1 WHERE c.idClient=?2")
    void updateEmail(String email, Long idClient);

    @Modifying
    @Query("UPDATE Client c SET c.adresse=?1 WHERE c.idClient=?2")
    void updateAdresse(String adresse, Long idClient);

    @Modifying
    @Query("UPDATE Client c SET c.cp=?1 WHERE c.idClient=?2")
    void updateCp(String cp, Long idClient);

    @Modifying
    @Query("UPDATE Client c SET c.ville=?1 WHERE c.idClient=?2")
    void updateVille(String ville, Long idClient);

}
