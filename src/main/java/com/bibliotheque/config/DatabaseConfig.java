package com.bibliotheque.config;

// PATTERN SINGLETON
// Une seule instance de configuration de base de données
@Configuration
public class DatabaseConfig {

    // Instance unique
    private static DatabaseConfig instance;

    // Constructeur privé
    private DatabaseConfig() {}

    // Méthode pour obtenir l'instance unique
    public static DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }
}
