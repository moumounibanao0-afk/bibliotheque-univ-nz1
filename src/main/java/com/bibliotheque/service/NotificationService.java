package com.bibliotheque.service;

import com.bibliotheque.model.Emprunt;
import com.bibliotheque.model.Reservation;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class NotificationService {

    @Value("${SENDGRID_API_KEY:}")
    private String sendGridApiKey;

    private static final String FROM_EMAIL = "moumounibanao0@gmail.com";
    private static final String NOM_APP = "Bibliothèque UNZ";

    private void envoyerEmail(String destinataire, String sujet, String message) {
        try {
            Email from = new Email(FROM_EMAIL, NOM_APP);
            Email to = new Email(destinataire);
            Content content = new Content("text/plain", message);
            Mail mail = new Mail(from, sujet, to, content);
            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("Email envoyé à " + destinataire + " - Status: " + response.getStatusCode());
        } catch (IOException e) {
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
            "[" + NOM_APP + "] ⚠️ Rappel : retour dans 2 jours",
            "Bonjour " + prenom + ",\n\nRappel : retournez l'ouvrage dans 2 jours.\n\n📖 " + titre + "\n📅 " + dateRetour + "\n\nCordialement,\n" + NOM_APP);
    }

    public void envoyerNotificationRetard(Emprunt emprunt, double penalite) {
        String prenom = emprunt.getEtudiant().getPrenom();
        String titre = emprunt.getOuvrage().getTitre();
        envoyerEmail(emprunt.getEtudiant().getEmail(),
            "[" + NOM_APP + "] 🔴 Retard détecté",
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
            "[" + NOM_APP + "] ✅ Votre ouvrage est disponible !",
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
