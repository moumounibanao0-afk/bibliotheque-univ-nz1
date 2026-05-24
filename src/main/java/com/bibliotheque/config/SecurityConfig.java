package com.bibliotheque.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // ✅ Routes publiques
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/utilisateurs/etudiant").permitAll()
                .requestMatchers("/*.html", "/css/**", "/js/**").permitAll()

                // ✅ Catalogue — lecture pour tous les connectés
                .requestMatchers(HttpMethod.GET, "/api/ouvrages/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/categories/**").authenticated()

                // ✅ Étudiant — ses emprunts et réservations
                .requestMatchers(HttpMethod.GET, "/api/emprunts/etudiant/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/reservations").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/reservations").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/reservations/*/annuler").authenticated()

                // ✅ BIBLIOTHÉCAIRE + ADMIN — gestion emprunts et catalogue
                .requestMatchers(HttpMethod.POST, "/api/ouvrages").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.PUT, "/api/ouvrages/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.DELETE, "/api/ouvrages/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.POST, "/api/emprunts").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.PUT, "/api/emprunts/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.GET, "/api/emprunts").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.POST, "/api/categories/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.PUT, "/api/reservations/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")

                // 🔒 ADMIN UNIQUEMENT — gestion utilisateurs et statistiques
                .requestMatchers("/api/utilisateurs/**").hasRole("ADMINISTRATEUR")
                .requestMatchers("/api/statistiques/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")

                // 🔒 Tout le reste — connecté
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}