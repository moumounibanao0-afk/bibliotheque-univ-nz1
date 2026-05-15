package com.bibliotheque.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "etudiants")
public class Etudiant extends Utilisateur {

    private String numeroEtudiant;
    private String filiere;
}
