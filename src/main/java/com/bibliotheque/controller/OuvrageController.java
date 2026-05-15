package com.bibliotheque.controller;

import com.bibliotheque.model.Ouvrage;
import com.bibliotheque.service.OuvrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ouvrages")
@CrossOrigin(origins = "*")
public class OuvrageController {

    @Autowired
    private OuvrageService ouvrageService;

    // GET tous les ouvrages
    @GetMapping
    public List<Ouvrage> getTousLesOuvrages() {
        return ouvrageService.getTousLesOuvrages();
    }

    // GET ouvrage par ID
    @GetMapping("/{id}")
    public ResponseEntity<Ouvrage> getOuvrageParId(@PathVariable Long id) {
        return ouvrageService.getOuvrageParId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // GET recherche par titre
    @GetMapping("/recherche/titre")
    public List<Ouvrage> rechercherParTitre(@RequestParam String titre) {
        return ouvrageService.rechercherParTitre(titre);
    }

    // GET recherche par auteur
    @GetMapping("/recherche/auteur")
    public List<Ouvrage> rechercherParAuteur(@RequestParam String auteur) {
        return ouvrageService.rechercherParAuteur(auteur);
    }

    // GET ouvrages disponibles
    @GetMapping("/disponibles")
    public List<Ouvrage> getOuvragesDisponibles() {
        return ouvrageService.getOuvragesDisponibles();
    }

    // POST ajouter ouvrage
    @PostMapping
    public Ouvrage ajouterOuvrage(@RequestBody Ouvrage ouvrage) {
        return ouvrageService.ajouterOuvrage(ouvrage);
    }

    // PUT modifier ouvrage
    @PutMapping("/{id}")
    public Ouvrage modifierOuvrage(@PathVariable Long id, @RequestBody Ouvrage ouvrage) {
        return ouvrageService.modifierOuvrage(id, ouvrage);
    }

    // DELETE supprimer ouvrage
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerOuvrage(@PathVariable Long id) {
        ouvrageService.supprimerOuvrage(id);
        return ResponseEntity.ok().build();
    }
}
