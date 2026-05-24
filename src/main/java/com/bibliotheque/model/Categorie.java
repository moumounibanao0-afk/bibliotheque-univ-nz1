package com.bibliotheque.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    private String description;

    // Rayon de la bibliothèque
    private String rayon;

    @OneToMany(mappedBy = "categorie")
    private List<Ouvrage> ouvrages;
}