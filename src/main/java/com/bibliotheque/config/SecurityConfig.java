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
                .requestMatchers("/", "/index.html", "/*.html", "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/utilisateurs/etudiant").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/ouvrages/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/emprunts/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/reservations/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/statistiques/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/utilisateurs/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/reservations").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/reservations/*/annuler").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/emprunts").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/ouvrages").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.PUT, "/api/ouvrages/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.DELETE, "/api/ouvrages/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.PUT, "/api/emprunts/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.POST, "/api/emprunts/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.POST, "/api/categories/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.PUT, "/api/reservations/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers("/api/notifications/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
                .requestMatchers(HttpMethod.PUT, "/api/utilisateurs/**").authenticated()
                .requestMatchers("/api/utilisateurs/**").hasRole("ADMINISTRATEUR")
                .requestMatchers("/api/statistiques/**").hasAnyRole("BIBLIOTHECAIRE", "ADMINISTRATEUR")
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
