package com.bibliotheque.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "bibliothecaires")
public class Bibliothecaire extends Utilisateur {

    private String matricule;
    private String service;
}