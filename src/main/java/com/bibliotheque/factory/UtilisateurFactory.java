package com.bibliotheque.factory;

// PATTERN FACTORY
// Crée le bon type d'utilisateur selon le rôle
public class UtilisateurFactory {

    public static String creerUtilisateur(String role, String nom, String email) {
        switch (role.toUpperCase()) {
            case "ETUDIANT":
                return "Etudiant créé : " + nom;
            case "BIBLIOTHECAIRE":
                return "Bibliothécaire créé : " + nom;
            case "ADMINISTRATEUR":
                return "Administrateur créé : " + nom;
            default:
                throw new IllegalArgumentException("Rôle inconnu : " + role);
        }
    }
}
