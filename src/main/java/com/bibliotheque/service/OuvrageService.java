package com.bibliotheque.service;

import com.bibliotheque.model.Ouvrage;
import com.bibliotheque.repository.OuvrageRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Ouvrage ajouterOuvrage(Ouvrage ouvrage) {
        ouvrage.setExemplairesDisponibles(ouvrage.getNombreExemplaires());
        return ouvrageRepository.save(ouvrage);
    }

    public Ouvrage modifierOuvrage(Long id, Ouvrage ouvrage) {
        ouvrage.setId(id);
        return ouvrageRepository.save(ouvrage);
    }

    public void supprimerOuvrage(Long id) {
        ouvrageRepository.deleteById(id);
    }

    public List<Ouvrage> getOuvragesDisponibles() {
        return ouvrageRepository.findByExemplairesDisponiblesGreaterThan(0);
    }
}
