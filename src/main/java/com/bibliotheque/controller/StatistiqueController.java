package com.bibliotheque.controller;

import com.bibliotheque.model.StatutEmprunt;
import com.bibliotheque.model.StatutReservation;
import com.bibliotheque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    // ✅ Statistiques générales — tableau de bord
    @GetMapping
    public ResponseEntity<Map<String, Object>> getStatistiques() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalOuvrages", ouvrageRepository.count());
        stats.put("totalEmprunts", empruntRepository.count());
        stats.put("totalUtilisateurs", utilisateurRepository.count());
        stats.put("totalReservations", reservationRepository.count());
        stats.put("empruntsEnCours", empruntRepository.findByStatut(StatutEmprunt.EN_COURS).size());
        stats.put("empruntsEnRetard", empruntRepository.findByStatut(StatutEmprunt.EN_RETARD).size());
        stats.put("reservationsEnAttente", reservationRepository.findByStatut(StatutReservation.EN_ATTENTE).size());
        stats.put("ouvragesDisponibles", ouvrageRepository.findByExemplairesDisponiblesGreaterThan(0).size());

        long totalEmprunts = empruntRepository.count();
        long enRetard = empruntRepository.findByStatut(StatutEmprunt.EN_RETARD).size();
        double tauxRetard = totalEmprunts > 0 ? (enRetard * 100.0 / totalEmprunts) : 0;
        stats.put("tauxRetard", Math.round(tauxRetard * 100.0) / 100.0);

        return ResponseEntity.ok(stats);
    }

    // ✅ AJOUTÉ — Ouvrages les plus empruntés
    @GetMapping("/ouvrages-populaires")
    public ResponseEntity<List<Map<String, Object>>> getOuvragesPopulaires() {
        List<Map<String, Object>> result = empruntRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        e -> e.getOuvrage().getId(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(10)
                .map(entry -> {
                    Map<String, Object> item = new HashMap<>();
                    empruntRepository.findAll().stream()
                            .filter(e -> e.getOuvrage().getId().equals(entry.getKey()))
                            .findFirst()
                            .ifPresent(e -> {
                                item.put("ouvrageId", entry.getKey());
                                item.put("titre", e.getOuvrage().getTitre());
                                item.put("auteur", e.getOuvrage().getAuteur());
                                item.put("nombreEmprunts", entry.getValue());
                            });
                    return item;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // ✅ AJOUTÉ — Emprunts par mois (12 derniers mois)
    @GetMapping("/emprunts-par-mois")
    public ResponseEntity<List<Map<String, Object>>> getEmpruntsParMois() {
        List<Map<String, Object>> result = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i <= 11; i++) {
            LocalDate mois = today.plusMonths(i);
            int annee = mois.getYear();
            int numMois = mois.getMonthValue();

            long count = empruntRepository.findAll().stream()
                    .filter(e -> e.getDateEmprunt() != null
                            && e.getDateEmprunt().getYear() == annee
                            && e.getDateEmprunt().getMonthValue() == numMois)
                    .count();

            Map<String, Object> item = new HashMap<>();
            item.put("mois", mois.getMonth().toString().substring(0, 3) + " " + annee);
            item.put("nombreEmprunts", count);
            result.add(item);
        }

        return ResponseEntity.ok(result);
    }

    // ✅ AJOUTÉ — Statistiques par catégorie
    @GetMapping("/par-categorie")
    public ResponseEntity<List<Map<String, Object>>> getStatistiquesParCategorie() {
        List<Map<String, Object>> result = empruntRepository.findAll()
                .stream()
                .filter(e -> e.getOuvrage().getCategorie() != null)
                .collect(Collectors.groupingBy(
                        e -> e.getOuvrage().getCategorie().getNom(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(entry -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("categorie", entry.getKey());
                    item.put("nombreEmprunts", entry.getValue());
                    return item;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // ✅ AJOUTÉ — Étudiants les plus actifs
    @GetMapping("/etudiants-actifs")
    public ResponseEntity<List<Map<String, Object>>> getEtudiantsActifs() {
        List<Map<String, Object>> result = empruntRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        e -> e.getEtudiant().getId(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(10)
                .map(entry -> {
                    Map<String, Object> item = new HashMap<>();
                    empruntRepository.findAll().stream()
                            .filter(e -> e.getEtudiant().getId().equals(entry.getKey()))
                            .findFirst()
                            .ifPresent(e -> {
                                item.put("etudiantId", entry.getKey());
                                item.put("nom", e.getEtudiant().getNom());
                                item.put("prenom", e.getEtudiant().getPrenom());
                                item.put("nombreEmprunts", entry.getValue());
                            });
                    return item;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // ✅ AJOUTÉ — Rapport retards détaillé
    @GetMapping("/retards")
    public ResponseEntity<List<Map<String, Object>>> getRapportRetards() {
        List<Map<String, Object>> result = empruntRepository
                .findByStatut(StatutEmprunt.EN_RETARD)
                .stream()
                .map(e -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("empruntId", e.getId());
                    item.put("etudiant", e.getEtudiant().getNom() + " " + e.getEtudiant().getPrenom());
                    item.put("email", e.getEtudiant().getEmail());
                    item.put("ouvrage", e.getOuvrage().getTitre());
                    item.put("dateRetourPrevue", e.getDateRetourPrevue());
                    long joursRetard = e.getDateRetourPrevue()
                            .until(LocalDate.now(), java.time.temporal.ChronoUnit.DAYS);
                    item.put("joursRetard", joursRetard);
                    item.put("penalite", joursRetard * 100); // 100 FCFA par jour
                    return item;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}