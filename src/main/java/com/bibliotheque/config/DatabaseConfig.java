package com.bibliotheque.config;

import org.springframework.context.annotation.Configuration;

// PATTERN SINGLETON
// Une seule instance de configuration de base de données
@Configuration
public class DatabaseConfig {

    private static DatabaseConfig instance;

    private DatabaseConfig() {}

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }
}
