package com.bibliotheque.service;

import com.bibliotheque.model.Ouvrage;
import com.bibliotheque.repository.OuvrageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OuvrageService {

    @Autowired
    private OuvrageRepository ouvrageRepository;

    public List<Ouvrage> getTousLesOuvrages() {
        return ouvrageRepository.findAll();
    }

    public Optional<Ouvrage> getOuvrageParId(Long id) {
        return ouvrageRepository.findById(id);
    }

    public List<Ouvrage> rechercherParTitre(String titre) {
        return ouvrageRepository.findByTitreContainingIgnoreCase(titre);
    }

    public List<Ouvrage> rechercherParAuteur(String auteur) {
        return ouvrageRepository.findByAuteurContainingIgnoreCase(auteur);
    }

    // ✅ AJOUTÉ — recherche par ISBN
    public Optional<Ouvrage> rechercherParIsbn(String isbn) {
        return ouvrageRepository.findByIsbn(isbn);
    }

    // ✅ AJOUTÉ — recherche avancée multi-critères
    public List<Ouvrage> rechercheAvancee(String motCle) {
        return ouvrageRepository.rechercheAvancee(motCle);
    }

    // ✅ AJOUTÉ — recherche par nom de catégorie
    public List<Ouvrage> rechercherParCategorie(String nom) {
        return ouvrageRepository.findByCategorieNomContaining(nom);
    }

    // ✅ AJOUTÉ — recherche par rayon
    public List<Ouvrage> rechercherParRayon(String rayon) {
        return ouvrageRepository.findByCategorieRayonIgnoreCase(rayon);
    }

    public Ouvrage ajouterOuvrage(Ouvrage ouvrage) {
        ouvrage.setExemplairesDisponibles(ouvrage.getNombreExemplaires());
        return ouvrageRepository.save(ouvrage);
    }

    public Ouvrage modifierOuvrage(Long id, Ouvrage ouvrage) {
        ouvrage.setId(id);
        return ouvrageRepository.save(ouvrage);
    }

    public void supprimerOuvrage(@NonNull Long id) {
        ouvrageRepository.deleteById(id);
    }

    public List<Ouvrage> getOuvragesDisponibles() {
        return ouvrageRepository.findByExemplairesDisponiblesGreaterThan(0);
    }
}