package com.bibliotheque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void envoyerNotification(String destinataire, String sujet, String message) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(destinataire);
            email.setSubject(sujet);
            email.setText(message);
            email.setFrom("bibliotheque.unz@gmail.com");
            mailSender.send(email);
            System.out.println("Email envoyé à : " + destinataire);
        } catch (Exception e) {
            System.out.println("Erreur envoi email : " + e.getMessage());
        }
    }

    public void envoyerRappelRetour(String destinataire, String titreOuvrage, String dateRetour) {
        String sujet = "Rappel — Retour d'ouvrage";
        String message = "Bonjour,\n\n" +
            "Nous vous rappelons que l'ouvrage \"" + titreOuvrage + "\" " +
            "doit être retourné avant le " + dateRetour + ".\n\n" +
            "Merci.\n\nBibliothèque UNZ";
        envoyerNotification(destinataire, sujet, message);
    }

    public void envoyerDisponibilite(String destinataire, String titreOuvrage) {
        String sujet = "Ouvrage disponible — " + titreOuvrage;
        String message = "Bonjour,\n\n" +
            "L'ouvrage \"" + titreOuvrage + "\" que vous avez réservé " +
            "est maintenant disponible.\n\n" +
            "Venez le récupérer à la bibliothèque.\n\nBibliothèque UNZ";
        envoyerNotification(destinataire, sujet, message);
    }
}
