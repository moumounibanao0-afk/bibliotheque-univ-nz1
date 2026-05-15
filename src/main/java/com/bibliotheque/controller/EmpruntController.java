package com.bibliotheque.controller;

import com.bibliotheque.model.*;
import com.bibliotheque.service.EmpruntService;
import com.bibliotheque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emprunts")
@CrossOrigin(origins = "*")
public class EmpruntController {

    @Autowired
    private EmpruntService empruntService;

    @Autowired
    private OuvrageRepository ouvrageRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // POST créer un emprunt
    @PostMapping
    public ResponseEntity<?> creerEmprunt(@RequestBody Map<String, Long> request) {
        try {
            Etudiant etudiant = (Etudiant) utilisateurRepository
                .findById(request.get("etudiantId"))
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));

            Ouvrage ouvrage = ouvrageRepository
                .findById(request.get("ouvrageId"))
                .orElseThrow(() -> new RuntimeException("Ouvrage non trouvé"));

            Emprunt emprunt = empruntService.creerEmprunt(etudiant, ouvrage);
            return ResponseEntity.ok(emprunt);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT retourner un emprunt
    @PutMapping("/{id}/retour")
    public ResponseEntity<?> retournerEmprunt(@PathVariable Long id) {
        try {
            Emprunt emprunt = empruntService.retournerEmprunt(id);
            return ResponseEntity.ok(emprunt);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET historique d'un étudiant
    @GetMapping("/etudiant/{etudiantId}")
    public List<Emprunt> getEmpruntsEtudiant(@PathVariable Long etudiantId) {
        return empruntService.getEmpruntsParEtudiant(etudiantId);
    }

    // GET pénalité d'un emprunt
    @GetMapping("/{id}/penalite")
    public ResponseEntity<Double> getPenalite(@PathVariable Long id) {
        double penalite = empruntService.calculerPenalite(id);
        return ResponseEntity.ok(penalite);
    }
}
