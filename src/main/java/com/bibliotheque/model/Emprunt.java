package com.bibliotheque.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "emprunts")
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "ouvrage_id", nullable = false)
    private Ouvrage ouvrage;

    private LocalDate dateEmprunt = LocalDate.now();
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourReelle;

    @Enumerated(EnumType.STRING)
    private StatutEmprunt statut = StatutEmprunt.EN_COURS;
}
