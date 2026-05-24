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

    private static final String FROM = "moumounibanao0@gmail.com";
    private static final String NOM_APP = "Bibliothèque UNZ";

    // ✅ Notification confirmation d'emprunt
    public void envoyerConfirmationEmprunt(Emprunt emprunt) {
        String email = emprunt.getEtudiant().getEmail();
        String prenom = emprunt.getEtudiant().getPrenom();
        String titre = emprunt.getOuvrage().getTitre();
        String dateRetour = emprunt.getDateRetourPrevue().toString();

        String sujet = "[" + NOM_APP + "] Confirmation de votre emprunt";
        String message = "Bonjour " + prenom + ",\n\n"
                + "Votre emprunt a bien été enregistré.\n\n"
                + "📖 Ouvrage : " + titre + "\n"
                + "📅 Date de retour prévue : " + dateRetour + "\n\n"
                + "Merci de respecter la date de retour pour éviter des pénalités.\n\n"
                + "Cordialement,\n" + NOM_APP;

        envoyerEmail(email, sujet, message);
    }

    // ✅ Notification rappel de retour (J-2)
    public void envoyerRappelRetour(Emprunt emprunt) {
        String email = emprunt.getEtudiant().getEmail();
        String prenom = emprunt.getEtudiant().getPrenom();
        String titre = emprunt.getOuvrage().getTitre();
        String dateRetour = emprunt.getDateRetourPrevue().toString();

        String sujet = "[" + NOM_APP + "] ⚠️ Rappel : retour dans 2 jours";
        String message = "Bonjour " + prenom + ",\n\n"
                + "Rappel : vous devez retourner l'ouvrage suivant dans 2 jours.\n\n"
                + "📖 Ouvrage : " + titre + "\n"
                + "📅 Date limite de retour : " + dateRetour + "\n\n"
                + "Passé ce délai, des pénalités seront appliquées.\n\n"
                + "Cordialement,\n" + NOM_APP;

        envoyerEmail(email, sujet, message);
    }

    // ✅ Notification retard
    public void envoyerNotificationRetard(Emprunt emprunt, double penalite) {
        String email = emprunt.getEtudiant().getEmail();
        String prenom = emprunt.getEtudiant().getPrenom();
        String titre = emprunt.getOuvrage().getTitre();

        String sujet = "[" + NOM_APP + "] 🔴 Retard détecté sur votre emprunt";
        String message = "Bonjour " + prenom + ",\n\n"
                + "Votre emprunt est en retard.\n\n"
                + "📖 Ouvrage : " + titre + "\n"
                + "💰 Pénalité actuelle : " + penalite + " FCFA\n\n"
                + "Veuillez retourner l'ouvrage dès que possible.\n\n"
                + "Cordialement,\n" + NOM_APP;

        envoyerEmail(email, sujet, message);
    }

    // ✅ Notification confirmation de réservation
    public void envoyerConfirmationReservation(Reservation reservation) {
        String email = reservation.getEtudiant().getEmail();
        String prenom = reservation.getEtudiant().getPrenom();
        String titre = reservation.getOuvrage().getTitre();

        String sujet = "[" + NOM_APP + "] Confirmation de votre réservation";
        String message = "Bonjour " + prenom + ",\n\n"
                + "Votre réservation a bien été enregistrée.\n\n"
                + "📖 Ouvrage : " + titre + "\n"
                + "📋 Statut : EN ATTENTE\n\n"
                + "Vous serez notifié dès que l'ouvrage sera disponible.\n\n"
                + "Cordialement,\n" + NOM_APP;

        envoyerEmail(email, sujet, message);
    }

    // ✅ Notification disponibilité ouvrage
    public void envoyerNotificationDisponibilite(Reservation reservation) {
        String email = reservation.getEtudiant().getEmail();
        String prenom = reservation.getEtudiant().getPrenom();
        String titre = reservation.getOuvrage().getTitre();

        String sujet = "[" + NOM_APP + "] ✅ Votre ouvrage est disponible !";
        String message = "Bonjour " + prenom + ",\n\n"
                + "Bonne nouvelle ! L'ouvrage que vous avez réservé est maintenant disponible.\n\n"
                + "📖 Ouvrage : " + titre + "\n\n"
                + "Présentez-vous à la bibliothèque pour le récupérer.\n\n"
                + "Cordialement,\n" + NOM_APP;

        envoyerEmail(email, sujet, message);
    }

    // ✅ Notification annulation réservation
    public void envoyerAnnulationReservation(Reservation reservation) {
        String email = reservation.getEtudiant().getEmail();
        String prenom = reservation.getEtudiant().getPrenom();
        String titre = reservation.getOuvrage().getTitre();

        String sujet = "[" + NOM_APP + "] Réservation annulée";
        String message = "Bonjour " + prenom + ",\n\n"
                + "Votre réservation a été annulée.\n\n"
                + "📖 Ouvrage : " + titre + "\n\n"
                + "Cordialement,\n" + NOM_APP;

        envoyerEmail(email, sujet, message);
    }

    // ✅ Notification inscription
    public void envoyerBienvenueEtudiant(String email, String prenom) {
        String sujet = "[" + NOM_APP + "] Bienvenue à la bibliothèque !";
        String message = "Bonjour " + prenom + ",\n\n"
                + "Votre compte a été créé avec succès sur la plateforme de la bibliothèque.\n\n"
                + "Vous pouvez dès maintenant :\n"
                + "  - Consulter le catalogue\n"
                + "  - Emprunter des ouvrages\n"
                + "  - Faire des réservations\n\n"
                + "Cordialement,\n" + NOM_APP;

        envoyerEmail(email, sujet, message);
    }

    // Méthode générique d'envoi
    private void envoyerEmail(String destinataire, String sujet, String message) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(FROM);
            mail.setTo(destinataire);
            mail.setSubject(sujet);
            mail.setText(message);
            mailSender.send(mail);
        } catch (Exception e) {
            System.err.println("Erreur envoi email à " + destinataire + " : " + e.getMessage());
        }
    }

    public void envoyerNotification(String string, String string2, String string3) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'envoyerNotification'");
    }
}