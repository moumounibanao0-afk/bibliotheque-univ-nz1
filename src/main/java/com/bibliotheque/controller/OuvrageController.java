package com.bibliotheque.controller;

import com.bibliotheque.model.Ouvrage;
import com.bibliotheque.model.Categorie;
import com.bibliotheque.repository.CategorieRepository;
import com.bibliotheque.service.OuvrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ouvrages")
@CrossOrigin(origins = "*")
public class OuvrageController {

    @Autowired
    private OuvrageService ouvrageService;
    @Autowired
    private CategorieRepository categorieRepository;

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

    // GET recherche par ISBN ✅ AJOUTÉ
    @GetMapping("/recherche/isbn")
    public ResponseEntity<Ouvrage> rechercherParIsbn(@RequestParam String isbn) {
        return ouvrageService.rechercherParIsbn(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET recherche avancée multi-critères ✅ AJOUTÉ
    @GetMapping("/recherche")
    public List<Ouvrage> rechercheAvancee(@RequestParam String motCle) {
        return ouvrageService.rechercheAvancee(motCle);
    }

    // GET recherche par catégorie ✅ AJOUTÉ
    @GetMapping("/recherche/categorie")
    public List<Ouvrage> rechercherParCategorie(@RequestParam String nom) {
        return ouvrageService.rechercherParCategorie(nom);
    }

    // GET recherche par rayon ✅ AJOUTÉ
    @GetMapping("/recherche/rayon")
    public List<Ouvrage> rechercherParRayon(@RequestParam String rayon) {
        return ouvrageService.rechercherParRayon(rayon);
    }

    // GET ouvrages disponibles
    @GetMapping("/disponibles")
    public List<Ouvrage> getOuvragesDisponibles() {
        return ouvrageService.getOuvragesDisponibles();
    }

    // POST ajouter ouvrage
    @PostMapping
    public Ouvrage ajouterOuvrage(@RequestBody Map<String, Object> body) {
        Ouvrage ouvrage = new Ouvrage();
        ouvrage.setTitre((String) body.get("titre"));
        ouvrage.setAuteur((String) body.get("auteur"));
        ouvrage.setIsbn((String) body.get("isbn"));
        ouvrage.setDescription((String) body.get("description"));
        if (body.get("anneePublication") != null)
            ouvrage.setAnneePublication((Integer) body.get("anneePublication"));
        if (body.get("nombreExemplaires") != null)
            ouvrage.setNombreExemplaires((Integer) body.get("nombreExemplaires"));
        if (body.get("categorieId") != null) {
            Long catId = Long.valueOf(body.get("categorieId").toString());
            categorieRepository.findById(catId).ifPresent(ouvrage::setCategorie);
        }
        return ouvrageService.ajouterOuvrage(ouvrage);
    }

    // PUT modifier ouvrage
    @PutMapping("/{id}")
    public Ouvrage modifierOuvrage(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Ouvrage ouvrage = ouvrageService.getOuvrageParId(id).orElseThrow();
        if (body.get("titre") != null) ouvrage.setTitre((String) body.get("titre"));
        if (body.get("auteur") != null) ouvrage.setAuteur((String) body.get("auteur"));
        if (body.get("isbn") != null) ouvrage.setIsbn((String) body.get("isbn"));
        if (body.get("anneePublication") != null) ouvrage.setAnneePublication((Integer) body.get("anneePublication"));
        if (body.get("nombreExemplaires") != null) ouvrage.setNombreExemplaires((Integer) body.get("nombreExemplaires"));
        if (body.get("categorieId") != null) {
            Long catId = Long.valueOf(body.get("categorieId").toString());
            categorieRepository.findById(catId).ifPresent(ouvrage::setCategorie);
        }
        return ouvrageService.modifierOuvrage(id, ouvrage);
    }

    // DELETE supprimer ouvrage
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerOuvrage(@PathVariable Long id) {
        ouvrageService.supprimerOuvrage(id);
        return ResponseEntity.ok().build();
    }
}