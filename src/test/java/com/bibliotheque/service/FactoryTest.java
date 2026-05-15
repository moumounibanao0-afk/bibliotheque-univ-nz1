package com.bibliotheque.service;

import com.bibliotheque.factory.UtilisateurFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FactoryTest {

    @Test
    void testCreerEtudiant() {
        String result = UtilisateurFactory.creerUtilisateur("ETUDIANT", "Moumouni", "m@test.com");
        assertTrue(result.contains("Moumouni"));
    }

    @Test
    void testCreerBibliothecaire() {
        String result = UtilisateurFactory.creerUtilisateur("BIBLIOTHECAIRE", "Ali", "a@test.com");
        assertTrue(result.contains("Ali"));
    }

    @Test
    void testCreerAdministrateur() {
        String result = UtilisateurFactory.creerUtilisateur("ADMINISTRATEUR", "Admin", "admin@test.com");
        assertTrue(result.contains("Admin"));
    }

    @Test
    void testRoleInconnu() {
        assertThrows(IllegalArgumentException.class, () -> {
            UtilisateurFactory.creerUtilisateur("INCONNU", "Test", "t@test.com");
        });
    }
}
