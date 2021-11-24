package com.example.occ_fermetures.model.dao;

import com.example.occ_fermetures.model.beans.Projet;
import com.example.occ_fermetures.model.beans.SousProjet;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
@Repository
public interface SousProjetDao extends JpaRepository<SousProjet, Long> {

    //retourne la liste de tous les sous projets d un projet
    List<SousProjet> findByIdProjet(Long idProjet);

    //retourne la liste de tous les sous projets d un projet par l idProjet et du plus recent au dernier grace au numero de l idSousProjet
    List<SousProjet> findByIdProjetOrderByIdProjetDesc(Long idProjet);

    //retourne la liste de tous les sous projets du plus recent au dernier grace au numero de l idSousProjet
    List<SousProjet> findAllByOrderByIdSousProjetDesc();

    //retourne un sous projet par son id pour utiliser ses infos
    SousProjet findByIdSousProjet(Long idSousProjet);

    //sauvegarde un sous projet
    //@SQLInsert(sql = "INSERT INTO SousProjet VALUES(photo1=?1, phot2=?2, photo3=?3, photo4=?4, largeur=?5, longueur=?6, option=?7, detail=?8, idCat=?9, idProjet=?10)")
    //SousProjet saveSousProjet(String photo1, String photo2, String photo3, String photo4, String largeur, String longueur, String option, String detail, Long idCat, Long idProjet);

    //retourne les photos d un projet (enregistrees en String grace a leur conversion Bitmap/String)
    @Query("SELECT photo1 FROM SousProjet WHERE idSousProjet=?1")
    String findPhoto1(Long idProjet);
    @Query("SELECT photo2 FROM SousProjet WHERE idSousProjet=?1")
    String findPhoto2(Long idProjet);
    @Query("SELECT photo3 FROM SousProjet WHERE idSousProjet=?1")
    String findPhoto3(Long idProjet);
    @Query("SELECT photo4 FROM SousProjet WHERE idSousProjet=?1")
    String findPhoto4(Long idProjet);

    //sauvegarde les donnees modifiees d un sousProjet en fonction de son id
    @Modifying
    @Query("UPDATE SousProjet SET longueur=?1 WHERE idSousProjet=?2")
    void updateLongueur(String longueur, Long idSousProjet);

    @Modifying
    @Query("UPDATE SousProjet SET largeur=?1 WHERE idSousProjet=?2")
    void updateLargeur(String largeur, Long idSousProjet);

    @Modifying
    @Query("UPDATE SousProjet SET detail=?1 WHERE idSousProjet=?2")
    void updateDetail(String detail, Long idSousProjet);

    @Modifying
    @Query("UPDATE SousProjet SET photo1=?1 WHERE idSousProjet=?2")
    void updatePhoto1(String photo1, Long idSousProjet);

    @Modifying
    @Query("UPDATE SousProjet SET photo2=?1 WHERE idSousProjet=?2")
    void updatePhoto2(String photo2, Long idSousProjet);

    @Modifying
    @Query("UPDATE SousProjet SET photo3=?1 WHERE idSousProjet=?2")
    void updatePhoto3(String photo3, Long idSousProjet);

    @Modifying
    @Query("UPDATE SousProjet SET photo4=?1 WHERE idSousProjet=?2")
    void updatePhoto4(String photo4, Long idSousProjet);

}
