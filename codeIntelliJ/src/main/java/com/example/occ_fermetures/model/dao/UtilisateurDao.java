package com.example.occ_fermetures.model.dao;
import com.example.occ_fermetures.model.beans.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {

    //retourne un utilisateur par son login
    Utilisateur findByLogin(String login);

}