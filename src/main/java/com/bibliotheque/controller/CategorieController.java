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

    @GetMapping
    public List<Categorie> getToutesCategories() {
        return categorieRepository.findAll();
    }

    @PostMapping
    public Categorie ajouterCategorie(@RequestBody Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorie> modifierCategorie(
            @PathVariable Long id, @RequestBody Categorie categorie) {
        categorie.setId(id);
        return ResponseEntity.ok(categorieRepository.save(categorie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCategorie(@PathVariable Long id) {
        categorieRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
