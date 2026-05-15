package com.bibliotheque.config;

import org.springframework.context.annotation.Configuration;

// PATTERN SINGLETON adapté pour Spring Boot
@Configuration
public class DatabaseConfig {

    // Spring Boot gère lui-même le Singleton
    // via le conteneur IoC — c'est le pattern Singleton de Spring
    public DatabaseConfig() {}

}
