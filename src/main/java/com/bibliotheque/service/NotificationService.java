package com.bibliotheque.service;

import com.bibliotheque.model.Emprunt;
import com.bibliotheque.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    private static final String FROM_EMAIL = "moumounibanao0@gmail.com";
    private static final String NOM_APP = "Bibliothèque UNZ";

    private void envoyerEmail(String destinataire, String sujet, String message) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom(FROM_EMAIL);
            email.setTo(destinataire);
            email.setSubject(sujet);
            email.setText(message);
            mailSender.send(email);
            System.out.println("Email envoyé à " + destinataire);
        } catch (Exception e) {
            System.err.println("Erreur envoi email à " + destinataire + " : " + e.getMessage());
        }
    }

    public void envoyerNotification(String destinataire, String sujet, String message) {
        envoyerEmail(destinataire, sujet, message);
    }

    public void envoyerConfirmationEmprunt(Emprunt emprunt) {
        String prenom = emprunt.getEtudiant().getPrenom();
        String titre = emprunt.getOuvrage().getTitre();
        String dateRetour = emprunt.getDateRetourPrevue().toString();
        envoyerEmail(emprunt.getEtudiant().getEmail(),
            "[" + NOM_APP + "] Confirmation de votre emprunt",
            "Bonjour " + prenom + ",\n\nVotre emprunt a bien été enregistré.\n\n📖 Ouvrage : " + titre + "\n📅 Date de retour prévue : " + dateRetour + "\n\nCordialement,\n" + NOM_APP);
    }

    public void envoyerRappelRetour(Emprunt emprunt) {
        String prenom = emprunt.getEtudiant().getPrenom();
        String titre = emprunt.getOuvrage().getTitre();
        String dateRetour = emprunt.getDateRetourPrevue().toString();
        envoyerEmail(emprunt.getEtudiant().getEmail(),
            "[" + NOM_APP + "] Rappel : retour dans 2 jours",
            "Bonjour " + prenom + ",\n\nRappel : retournez l'ouvrage dans 2 jours.\n\n📖 " + titre + "\n📅 " + dateRetour + "\n\nCordialement,\n" + NOM_APP);
    }

    public void envoyerNotificationRetard(Emprunt emprunt, double penalite) {
        String prenom = emprunt.getEtudiant().getPrenom();
        String titre = emprunt.getOuvrage().getTitre();
        envoyerEmail(emprunt.getEtudiant().getEmail(),
            "[" + NOM_APP + "] Retard détecté",
            "Bonjour " + prenom + ",\n\nVotre emprunt est en retard.\n\n📖 " + titre + "\n💰 Pénalité : " + penalite + " FCFA\n\nCordialement,\n" + NOM_APP);
    }

    public void envoyerConfirmationReservation(Reservation reservation) {
        String prenom = reservation.getEtudiant().getPrenom();
        String titre = reservation.getOuvrage().getTitre();
        envoyerEmail(reservation.getEtudiant().getEmail(),
            "[" + NOM_APP + "] Confirmation de votre réservation",
            "Bonjour " + prenom + ",\n\nVotre réservation a bien été enregistrée.\n\n📖 " + titre + "\n\nCordialement,\n" + NOM_APP);
    }

    public void envoyerNotificationDisponibilite(Reservation reservation) {
        String prenom = reservation.getEtudiant().getPrenom();
        String titre = reservation.getOuvrage().getTitre();
        envoyerEmail(reservation.getEtudiant().getEmail(),
            "[" + NOM_APP + "] Votre ouvrage est disponible !",
            "Bonjour " + prenom + ",\n\nL'ouvrage que vous avez réservé est disponible.\n\n📖 " + titre + "\n\nCordialement,\n" + NOM_APP);
    }

    public void envoyerAnnulationReservation(Reservation reservation) {
        String prenom = reservation.getEtudiant().getPrenom();
        String titre = reservation.getOuvrage().getTitre();
        envoyerEmail(reservation.getEtudiant().getEmail(),
            "[" + NOM_APP + "] Réservation annulée",
            "Bonjour " + prenom + ",\n\nVotre réservation a été annulée.\n\n📖 " + titre + "\n\nCordialement,\n" + NOM_APP);
    }

    public void envoyerBienvenueEtudiant(String email, String prenom) {
        envoyerEmail(email,
            "[" + NOM_APP + "] Bienvenue à la bibliothèque !",
            "Bonjour " + prenom + ",\n\nVotre compte a été créé avec succès.\n\nCordialement,\n" + NOM_APP);
    }
}
