package com.bibliotheque.service;

import com.bibliotheque.model.*;
import com.bibliotheque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private OuvrageRepository ouvrageRepository;

    @Autowired
    private NotificationService notificationService;

    public Reservation creerReservation(Etudiant etudiant, Ouvrage ouvrage) {
        if (reservationRepository.existsByEtudiantIdAndOuvrageIdAndStatut(
                etudiant.getId(), ouvrage.getId(), StatutReservation.EN_ATTENTE)) {
            throw new RuntimeException("Vous avez déjà réservé cet ouvrage");
        }

        Reservation reservation = new Reservation();
        reservation.setEtudiant(etudiant);
        reservation.setOuvrage(ouvrage);
        reservation.setStatut(StatutReservation.EN_ATTENTE);

        Reservation saved = reservationRepository.save(reservation);

        // ✅ Notification confirmation réservation
        notificationService.envoyerConfirmationReservation(saved);

        return saved;
    }

    public Reservation annulerReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        reservation.setStatut(StatutReservation.ANNULEE);
        Reservation saved = reservationRepository.save(reservation);

        // ✅ Notification annulation
        notificationService.envoyerAnnulationReservation(saved);

        return saved;
    }

    // ✅ AJOUTÉ — notifier le premier étudiant en attente quand un ouvrage est retourné
    public void notifierDisponibilite(Ouvrage ouvrage) {
        List<Reservation> reservations = reservationRepository
                .findByOuvrageIdAndStatut(ouvrage.getId(), StatutReservation.EN_ATTENTE);
        if (!reservations.isEmpty()) {
            Reservation premiere = reservations.get(0);
            notificationService.envoyerNotificationDisponibilite(premiere);
        }
    }

    public List<Reservation> getReservationsEtudiant(Long etudiantId) {
        return reservationRepository.findByEtudiantId(etudiantId);
    }

    public List<Reservation> getToutesReservations() {
        return reservationRepository.findAll();
    }
}