package com.bibliotheque.controller;

import com.bibliotheque.model.*;
import com.bibliotheque.repository.UtilisateurRepository;
import com.bibliotheque.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = "*")
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationService notificationService;

    // GET tous les utilisateurs
    @GetMapping
    public List<Utilisateur> getTousLesUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    // GET utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurParId(@PathVariable Long id) {
        return utilisateurRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST inscrire étudiant — public
    @PostMapping("/etudiant")
    public ResponseEntity<?> inscrireEtudiant(@RequestBody Etudiant etudiant) {
        try {
            if (utilisateurRepository.existsByEmail(etudiant.getEmail())) {
                return ResponseEntity.badRequest().body("Email déjà utilisé");
            }
            etudiant.setRole(Role.ETUDIANT);
            etudiant.setMotDePasse(passwordEncoder.encode(etudiant.getMotDePasse()));
            Etudiant saved = (Etudiant) utilisateurRepository.save(etudiant);

            // ✅ Email de bienvenue
            notificationService.envoyerBienvenueEtudiant(saved.getEmail(), saved.getPrenom());

            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // POST créer bibliothécaire — admin uniquement
    @PostMapping("/bibliothecaire")
    public ResponseEntity<?> creerBibliothecaire(@RequestBody Bibliothecaire bibliothecaire) {
        try {
            if (utilisateurRepository.existsByEmail(bibliothecaire.getEmail())) {
                return ResponseEntity.badRequest().body("Email déjà utilisé");
            }
            bibliothecaire.setRole(Role.BIBLIOTHECAIRE);
            bibliothecaire.setMotDePasse(passwordEncoder.encode(bibliothecaire.getMotDePasse()));
            return ResponseEntity.ok(utilisateurRepository.save(bibliothecaire));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // POST créer administrateur — admin uniquement
    @PostMapping("/admin")
    public ResponseEntity<?> creerAdmin(@RequestBody Utilisateur admin) {
        try {
            if (utilisateurRepository.existsByEmail(admin.getEmail())) {
                return ResponseEntity.badRequest().body("Email déjà utilisé");
            }
            admin.setRole(Role.ADMINISTRATEUR);
            admin.setMotDePasse(passwordEncoder.encode(admin.getMotDePasse()));
            return ResponseEntity.ok(utilisateurRepository.save(admin));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE supprimer utilisateur — admin uniquement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable Long id) {
        try {
            utilisateurRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}