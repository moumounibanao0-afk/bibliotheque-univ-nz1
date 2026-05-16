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

    public Reservation creerReservation(Etudiant etudiant, Ouvrage ouvrage) {
        if (reservationRepository.existsByEtudiantIdAndOuvrageIdAndStatut(
                etudiant.getId(), ouvrage.getId(), StatutReservation.EN_ATTENTE)) {
            throw new RuntimeException("Vous avez déjà réservé cet ouvrage");
        }

        Reservation reservation = new Reservation();
        reservation.setEtudiant(etudiant);
        reservation.setOuvrage(ouvrage);
        reservation.setStatut(StatutReservation.EN_ATTENTE);
        return reservationRepository.save(reservation);
    }

    public Reservation annulerReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
        reservation.setStatut(StatutReservation.ANNULEE);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsEtudiant(Long etudiantId) {
        return reservationRepository.findByEtudiantId(etudiantId);
    }

    public List<Reservation> getToutesReservations() {
        return reservationRepository.findAll();
    }
}
