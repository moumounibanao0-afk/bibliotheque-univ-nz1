package com.bibliotheque.strategy;

// PATTERN STRATEGY — Calcul boursier (50 FCFA par jour)
public class PenaliteBoursier implements CalculPenalite {

    @Override
    public double calculer(int joursRetard) {
        return joursRetard * 50.0;
    }
}
