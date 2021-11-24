package com.example.occ_fermetures.model.dao;

import com.example.occ_fermetures.model.beans.Categorie;
import com.example.occ_fermetures.model.beans.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Repository
public interface CategorieDao extends JpaRepository<Categorie, Long> {

    //retourne la liste des noms des categories
    @Query("SELECT nom FROM Categorie")
    ArrayList<String> findNomCat();

    //retourne le nom de la categorie
    @Query("SELECT nom FROM Categorie WHERE idCat=?1")
    String findNomByIdCat(Long idCat);

    //retourne l id de la categorie
    Categorie findAllByNom(String nom);
}
