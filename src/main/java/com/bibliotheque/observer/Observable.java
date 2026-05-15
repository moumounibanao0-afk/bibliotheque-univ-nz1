package com.bibliotheque.observer;

// PATTERN OBSERVER — Interface Observable
public interface Observable {
    void ajouterObservateur(Observateur o);
    void supprimerObservateur(Observateur o);
    void notifierObservateurs(String message);
}
