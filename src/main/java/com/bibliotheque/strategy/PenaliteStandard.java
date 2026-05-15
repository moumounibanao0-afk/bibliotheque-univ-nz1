package com.bibliotheque.strategy;

// PATTERN STRATEGY — Calcul standard (100 FCFA par jour)
public class PenaliteStandard implements CalculPenalite {

    @Override
    public double calculer(int joursRetard) {
        return joursRetard * 100.0;
    }
}
