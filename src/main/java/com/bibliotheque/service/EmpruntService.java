package com.bibliotheque.service;

import com.bibliotheque.model.*;
import com.bibliotheque.repository.*;
import com.bibliotheque.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmpruntService {

    @Autowired
    private EmpruntRepository empruntRepository;

    @Autowired
    private OuvrageRepository ouvrageRepository;

    @Autowired
    private NotificationService notificationService;

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

        Emprunt saved = empruntRepository.save(emprunt);

        // ✅ Notification confirmation emprunt
        notificationService.envoyerConfirmationEmprunt(saved);

        return saved;
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

    public List<Emprunt> getTousLesEmprunts() {
        return empruntRepository.findAll();
    }

    // ✅ AJOUTÉ — Tâche automatique : rappel J-2 avant retour (tous les jours à 8h)
    @Scheduled(cron = "0 0 8 * * *")
    public void envoyerRappelsRetour() {
        LocalDate dansDeuxJours = LocalDate.now().plusDays(2);
        List<Emprunt> emprunts = empruntRepository.findAll();
        for (Emprunt emprunt : emprunts) {
            if (emprunt.getStatut() == StatutEmprunt.EN_COURS &&
                emprunt.getDateRetourPrevue().equals(dansDeuxJours)) {
                notificationService.envoyerRappelRetour(emprunt);
            }
        }
    }

    // ✅ AJOUTÉ — Tâche automatique : détection retards (tous les jours à 9h)
    @Scheduled(cron = "0 0 9 * * *")
    public void detecterRetards() {
        LocalDate aujourd_hui = LocalDate.now();
        List<Emprunt> emprunts = empruntRepository.findAll();
        for (Emprunt emprunt : emprunts) {
            if (emprunt.getStatut() == StatutEmprunt.EN_COURS &&
                emprunt.getDateRetourPrevue().isBefore(aujourd_hui)) {
                emprunt.setStatut(StatutEmprunt.EN_RETARD);
                empruntRepository.save(emprunt);
                double penalite = calculerPenalite(emprunt.getId());
                notificationService.envoyerNotificationRetard(emprunt, penalite);
            }
        }
    }
}