package com.bibliotheque.controller;
import com.bibliotheque.model.*;
import com.bibliotheque.repository.UtilisateurRepository;
import com.bibliotheque.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = "*")
public class UtilisateurController {
    @Autowired private UtilisateurRepository utilisateurRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private NotificationService notificationService;

    @GetMapping
    public List<Utilisateur> getTousLesUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurParId(@PathVariable Long id) {
        return utilisateurRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifierProfil(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            Utilisateur u = utilisateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Non trouvé"));
            if (body.containsKey("nom"))    u.setNom(body.get("nom"));
            if (body.containsKey("prenom")) u.setPrenom(body.get("prenom"));
            if (body.containsKey("email"))  u.setEmail(body.get("email"));
            return ResponseEntity.ok(utilisateurRepository.save(u));
        } catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> changerMotDePasse(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            Utilisateur u = utilisateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Non trouvé"));
            String actuel = body.get("motDePasseActuel");
            String nouveau = body.get("nouveauMotDePasse");
            if (!passwordEncoder.matches(actuel, u.getMotDePasse())) {
                return ResponseEntity.badRequest().body("Mot de passe actuel incorrect");
            }
            u.setMotDePasse(passwordEncoder.encode(nouveau));
            utilisateurRepository.save(u);
            return ResponseEntity.ok("Mot de passe modifie");
        } catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PostMapping("/etudiant")
    public ResponseEntity<?> inscrireEtudiant(@RequestBody Etudiant etudiant) {
        try {
            if (utilisateurRepository.existsByEmail(etudiant.getEmail()))
                return ResponseEntity.badRequest().body("Email deja utilise");
            etudiant.setRole(Role.ETUDIANT);
            etudiant.setMotDePasse(passwordEncoder.encode(etudiant.getMotDePasse()));
            Etudiant saved = (Etudiant) utilisateurRepository.save(etudiant);
            notificationService.envoyerBienvenueEtudiant(saved.getEmail(), saved.getPrenom());
            return ResponseEntity.ok(saved);
        } catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PostMapping("/bibliothecaire")
    public ResponseEntity<?> creerBibliothecaire(@RequestBody Bibliothecaire b) {
        try {
            if (utilisateurRepository.existsByEmail(b.getEmail()))
                return ResponseEntity.badRequest().body("Email deja utilise");
            b.setRole(Role.BIBLIOTHECAIRE);
            b.setMotDePasse(passwordEncoder.encode(b.getMotDePasse()));
            return ResponseEntity.ok(utilisateurRepository.save(b));
        } catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PostMapping("/admin")
    public ResponseEntity<?> creerAdmin(@RequestBody Utilisateur admin) {
        try {
            if (utilisateurRepository.existsByEmail(admin.getEmail()))
                return ResponseEntity.badRequest().body("Email deja utilise");
            admin.setRole(Role.ADMINISTRATEUR);
            admin.setMotDePasse(passwordEncoder.encode(admin.getMotDePasse()));
            return ResponseEntity.ok(utilisateurRepository.save(admin));
        } catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable Long id) {
        try { utilisateurRepository.deleteById(id); return ResponseEntity.ok().build(); }
        catch (Exception e) { return ResponseEntity.badRequest().build(); }
    }
}
