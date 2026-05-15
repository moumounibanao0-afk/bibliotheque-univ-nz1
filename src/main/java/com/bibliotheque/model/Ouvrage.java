package com.bibliotheque.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ouvrages")
public class Ouvrage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String auteur;

    @Column(unique = true)
    private String isbn;

    private String description;
    private int anneePublication;
    private int nombreExemplaires;
    private int exemplairesDisponibles;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
}
