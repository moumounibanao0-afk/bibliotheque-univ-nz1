package com.bibliotheque.service;

import com.bibliotheque.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.time.LocalDate;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private NotificationService notificationService;

    private Etudiant etudiant;
    private Ouvrage ouvrage;
    private Emprunt emprunt;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setNom("Moumouni");
        etudiant.setPrenom("Test");
        etudiant.setEmail("test@gmail.com");

        ouvrage = new Ouvrage();
        ouvrage.setTitre("Java Spring Boot");

        emprunt = new Emprunt();
        emprunt.setEtudiant(etudiant);
        emprunt.setOuvrage(ouvrage);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(14));
        emprunt.setStatut(StatutEmprunt.EN_COURS);

        reservation = new Reservation();
        reservation.setEtudiant(etudiant);
        reservation.setOuvrage(ouvrage);
        reservation.setStatut(StatutReservation.EN_ATTENTE);
    }

    @Test
    void testEnvoyerConfirmationEmprunt() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        notificationService.envoyerConfirmationEmprunt(emprunt);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testEnvoyerRappelRetour() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        notificationService.envoyerRappelRetour(emprunt);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testEnvoyerConfirmationReservation() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        notificationService.envoyerConfirmationReservation(reservation);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testEnvoyerAnnulationReservation() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        notificationService.envoyerAnnulationReservation(reservation);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testEnvoyerNotificationDisponibilite() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        notificationService.envoyerNotificationDisponibilite(reservation);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testEnvoyerBienvenueEtudiant() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        notificationService.envoyerBienvenueEtudiant("test@gmail.com", "Test");
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testEnvoyerNotificationRetard() {
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        notificationService.envoyerNotificationRetard(emprunt, 500.0);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}