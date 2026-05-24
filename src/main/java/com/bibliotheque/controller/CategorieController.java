package com.bibliotheque.controller;

import com.bibliotheque.model.Categorie;
import com.bibliotheque.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    // GET toutes les catégories
    @GetMapping
    public List<Categorie> getToutesLesCategories() {
        return categorieRepository.findAll();
    }

    // GET catégorie par ID
    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategorieParId(@PathVariable Long id) {
        return categorieRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET catégories par rayon
    @GetMapping("/rayon/{rayon}")
    public List<Categorie> getCategoriesParRayon(@PathVariable String rayon) {
        return categorieRepository.findByRayon(rayon);
    }

    // POST ajouter catégorie (admin uniquement)
    @PostMapping
    public ResponseEntity<?> ajouterCategorie(@RequestBody Categorie categorie) {
        try {
            return ResponseEntity.ok(categorieRepository.save(categorie));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT modifier catégorie
    @PutMapping("/{id}")
    public ResponseEntity<?> modifierCategorie(@PathVariable Long id,
                                                @RequestBody Categorie categorie) {
        return categorieRepository.findById(id).map(c -> {
            c.setNom(categorie.getNom());
            c.setDescription(categorie.getDescription());
            c.setRayon(categorie.getRayon());
            return ResponseEntity.ok(categorieRepository.save(c));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE supprimer catégorie
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCategorie(@PathVariable Long id) {
        categorieRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}