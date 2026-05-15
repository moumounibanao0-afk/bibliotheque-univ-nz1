package com.bibliotheque.service;

import com.bibliotheque.model.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    @Test
    void testOuvrage() {
        Ouvrage o = new Ouvrage();
        o.setTitre("Test");
        o.setAuteur("Auteur");
        o.setIsbn("111");
        o.setNombreExemplaires(3);
        o.setExemplairesDisponibles(3);
        assertEquals("Test", o.getTitre());
        assertEquals("Auteur", o.getAuteur());
        assertEquals(3, o.getNombreExemplaires());
        assertEquals(3, o.getExemplairesDisponibles());
    }

    @Test
    void testEtudiant() {
        Etudiant e = new Etudiant();
        e.setNom("Moumouni");
        e.setPrenom("Test");
        e.setEmail("test@univ.bf");
        e.setRole(Role.ETUDIANT);
        e.setNumeroEtudiant("ETU001");
        e.setFiliere("Informatique");
        assertEquals("Moumouni", e.getNom());
        assertEquals("ETU001", e.getNumeroEtudiant());
        assertEquals(Role.ETUDIANT, e.getRole());
    }

    @Test
    void testCategorie() {
        Categorie c = new Categorie();
        c.setNom("Informatique");
        c.setRayon("A1");
        assertEquals("Informatique", c.getNom());
        assertEquals("A1", c.getRayon());
    }

    @Test
    void testEmprunt() {
        Emprunt emprunt = new Emprunt();
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(14));
        emprunt.setStatut(StatutEmprunt.EN_COURS);
        assertEquals(StatutEmprunt.EN_COURS, emprunt.getStatut());
        assertNotNull(emprunt.getDateEmprunt());
        assertNotNull(emprunt.getDateRetourPrevue());
    }

    @Test
    void testRole() {
        assertEquals("ETUDIANT", Role.ETUDIANT.name());
        assertEquals("BIBLIOTHECAIRE", Role.BIBLIOTHECAIRE.name());
        assertEquals("ADMINISTRATEUR", Role.ADMINISTRATEUR.name());
    }

    @Test
    void testStatutEmprunt() {
        assertEquals("EN_COURS", StatutEmprunt.EN_COURS.name());
        assertEquals("RETOURNE", StatutEmprunt.RETOURNE.name());
        assertEquals("EN_RETARD", StatutEmprunt.EN_RETARD.name());
    }

    @Test
    void testUtilisateur() {
        Utilisateur u = new Utilisateur();
        u.setNom("Test");
        u.setPrenom("User");
        u.setEmail("user@test.com");
        u.setMotDePasse("password");
        u.setRole(Role.ADMINISTRATEUR);
        assertEquals("Test", u.getNom());
        assertEquals("user@test.com", u.getEmail());
        assertEquals(Role.ADMINISTRATEUR, u.getRole());
    }
}
