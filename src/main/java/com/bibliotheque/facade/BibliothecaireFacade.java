package com.bibliotheque.facade;

// PATTERN FACADE
// Simplifie les operations complexes du bibliothecaire
// en regroupant EmpruntService, ReservationService et NotificationService
// derriere une interface unique.

import com.bibliotheque.model.*;
import com.bibliotheque.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BibliothecaireFacade {

    @Autowired
    private EmpruntService empruntService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private NotificationService notificationService;

    // Enregistrer un emprunt et notifier l etudiant
    public Emprunt enregistrerEmprunt(Etudiant etudiant, Ouvrage ouvrage) {
        Emprunt emprunt = empruntService.creerEmprunt(etudiant, ouvrage);
        return emprunt;
    }

    // Enregistrer un retour
    public Emprunt enregistrerRetour(Long empruntId) {
        return empruntService.retournerEmprunt(empruntId);
    }

    // Annuler une reservation
    public Reservation annulerReservation(Long reservationId) {
        return reservationService.annulerReservation(reservationId);
    }

    // Calculer la penalite d un emprunt
    public double obtenirPenalite(Long empruntId) {
        return empruntService.calculerPenalite(empruntId);
    }
}
