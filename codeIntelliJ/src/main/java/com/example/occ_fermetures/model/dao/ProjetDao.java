package com.example.occ_fermetures.model.dao;
import com.example.occ_fermetures.model.beans.Projet;
import com.example.occ_fermetures.model.beans.SousProjet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface ProjetDao extends JpaRepository<Projet, Long> {

    //retourne un projet par son id pour utiliser ses infos
    Projet findByIdProjet(Long idProjet);

    //retourne la liste des projets d un client
    List<Projet> findByIdClient(Long idClient);

    //retourne la liste de tous les projets d un client par l idClient et du plus recent au dernier grace au numero de l idProjet
    List<Projet> findByIdClientOrderByIdProjetDesc(Long idClient);

    //retourne la liste de tous les projets du plus recent au dernier grace au numero de l idProjet
    List<Projet> findAllByOrderByIdProjetDesc();

    //retourne l id du client coorespondant au projet
    @Query("SELECT idClient FROM Projet WHERE idProjet=?1")
    Long getIdClient(Long idProjet);
}
