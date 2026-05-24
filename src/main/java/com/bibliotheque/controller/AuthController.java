package com.bibliotheque.controller;

import com.bibliotheque.config.JwtUtil;
import com.bibliotheque.model.Utilisateur;
import com.bibliotheque.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String motDePasse = request.get("motDePasse");

        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElse(null);

        if (utilisateur == null) {
            return ResponseEntity.status(401).body("Email introuvable");
        }

        boolean valide = passwordEncoder.matches(motDePasse, utilisateur.getMotDePasse())
                || motDePasse.equals(utilisateur.getMotDePasse());

        if (!valide) {
            return ResponseEntity.status(401).body("Mot de passe incorrect");
        }

        String token = jwtUtil.genererToken(email, utilisateur.getRole().name());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", utilisateur.getRole().name());
        response.put("nom", utilisateur.getNom());
        response.put("prenom", utilisateur.getPrenom());
        response.put("id", utilisateur.getId());

        return ResponseEntity.ok(response);
    }
}
