package com.bibliotheque.observer;

import java.util.ArrayList;
import java.util.List;

// PATTERN OBSERVER — Classe concrète
// Notifie les étudiants quand un ouvrage devient disponible
public class OuvrageDisponible implements Observable {

    private List<Observateur> observateurs = new ArrayList<>();
    private String titreOuvrage;

    public OuvrageDisponible(String titreOuvrage) {
        this.titreOuvrage = titreOuvrage;
    }

    @Override
    public void ajouterObservateur(Observateur o) {
        observateurs.add(o);
    }

    @Override
    public void supprimerObservateur(Observateur o) {
        observateurs.remove(o);
    }

    @Override
    public void notifierObservateurs(String message) {
        for (Observateur o : observateurs) {
            o.recevoirNotification(message);
        }
    }

    // Appelé quand l'ouvrage est retourné
    public void ouvrageRetourne() {
        notifierObservateurs("L'ouvrage '" + titreOuvrage + "' est maintenant disponible !");
    }
}
