package com.bibliotheque.service;

import com.bibliotheque.model.*;
import com.bibliotheque.repository.*;
import com.bibliotheque.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmpruntService {

    @Autowired
    private EmpruntRepository empruntRepository;

    @Autowired
    private OuvrageRepository ouvrageRepository;

    // PATTERN STRATEGY — calcul de pénalité
    private CalculPenalite calculPenalite = new PenaliteStandard();

    public Emprunt creerEmprunt(Etudiant etudiant, Ouvrage ouvrage) {
        if (ouvrage.getExemplairesDisponibles() <= 0) {
            throw new RuntimeException("Aucun exemplaire disponible pour cet ouvrage");
        }

        Emprunt emprunt = new Emprunt();
        emprunt.setEtudiant(etudiant);
        emprunt.setOuvrage(ouvrage);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(14));
        emprunt.setStatut(StatutEmprunt.EN_COURS);

        ouvrage.setExemplairesDisponibles(ouvrage.getExemplairesDisponibles() - 1);
        ouvrageRepository.save(ouvrage);

        return empruntRepository.save(emprunt);
    }

    public Emprunt retournerEmprunt(Long empruntId) {
        Emprunt emprunt = empruntRepository.findById(empruntId)
            .orElseThrow(() -> new RuntimeException("Emprunt non trouvé"));

        emprunt.setDateRetourReelle(LocalDate.now());
        emprunt.setStatut(StatutEmprunt.RETOURNE);

        Ouvrage ouvrage = emprunt.getOuvrage();
        ouvrage.setExemplairesDisponibles(ouvrage.getExemplairesDisponibles() + 1);
        ouvrageRepository.save(ouvrage);

        return empruntRepository.save(emprunt);
    }

    public double calculerPenalite(Long empruntId) {
        Emprunt emprunt = empruntRepository.findById(empruntId)
            .orElseThrow(() -> new RuntimeException("Emprunt non trouvé"));

        if (emprunt.getDateRetourReelle() != null &&
            emprunt.getDateRetourReelle().isAfter(emprunt.getDateRetourPrevue())) {
            int joursRetard = (int) emprunt.getDateRetourPrevue()
                .until(emprunt.getDateRetourReelle(), java.time.temporal.ChronoUnit.DAYS);
            return calculPenalite.calculer(joursRetard);
        }
        return 0.0;
    }

    public List<Emprunt> getEmpruntsParEtudiant(Long etudiantId) {
        return empruntRepository.findByEtudiantId(etudiantId);
    }
}
