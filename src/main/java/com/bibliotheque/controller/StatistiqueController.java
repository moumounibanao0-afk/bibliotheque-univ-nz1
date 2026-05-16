package com.bibliotheque.controller;

import com.bibliotheque.repository.*;
import com.bibliotheque.model.StatutEmprunt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/statistiques")
@CrossOrigin(origins = "*")
public class StatistiqueController {

    @Autowired
    private OuvrageRepository ouvrageRepository;

    @Autowired
    private EmpruntRepository empruntRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getStatistiques() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalOuvrages", ouvrageRepository.count());
        stats.put("totalEmprunts", empruntRepository.count());
        stats.put("totalUtilisateurs", utilisateurRepository.count());
        stats.put("totalReservations", reservationRepository.count());
        stats.put("empruntsEnCours", empruntRepository
            .findByStatut(StatutEmprunt.EN_COURS).size());
        stats.put("empruntsEnRetard", empruntRepository
            .findByStatut(StatutEmprunt.EN_RETARD).size());

        return ResponseEntity.ok(stats);
    }
}
