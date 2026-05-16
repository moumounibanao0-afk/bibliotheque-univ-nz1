package com.bibliotheque.repository;

import com.bibliotheque.model.Reservation;
import com.bibliotheque.model.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByEtudiantId(Long etudiantId);
    List<Reservation> findByOuvrageId(Long ouvrageId);
    List<Reservation> findByStatut(StatutReservation statut);
    boolean existsByEtudiantIdAndOuvrageIdAndStatut(Long etudiantId, Long ouvrageId, StatutReservation statut);
}
