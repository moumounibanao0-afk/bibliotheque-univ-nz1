package com.bibliotheque.controller;

import com.bibliotheque.model.*;
import com.bibliotheque.service.ReservationService;
import com.bibliotheque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private OuvrageRepository ouvrageRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping
    public ResponseEntity<?> creerReservation(@RequestBody Map<String, Long> request) {
        try {
            Etudiant etudiant = (Etudiant) utilisateurRepository
                .findById(request.get("etudiantId"))
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));
            Ouvrage ouvrage = ouvrageRepository
                .findById(request.get("ouvrageId"))
                .orElseThrow(() -> new RuntimeException("Ouvrage non trouvé"));
            return ResponseEntity.ok(reservationService.creerReservation(etudiant, ouvrage));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/annuler")
    public ResponseEntity<?> annulerReservation(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reservationService.annulerReservation(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/etudiant/{etudiantId}")
    public List<Reservation> getReservationsEtudiant(@PathVariable Long etudiantId) {
        return reservationService.getReservationsEtudiant(etudiantId);
    }

    @GetMapping
    public List<Reservation> getToutesReservations() {
        return reservationService.getToutesReservations();
    }
}
