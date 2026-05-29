package com.bibliotheque.facade;

import com.bibliotheque.model.*;
import com.bibliotheque.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BibliothecaireFacadeTest {

    @Mock private EmpruntService empruntService;
    @Mock private ReservationService reservationService;
    @Mock private NotificationService notificationService;

    @InjectMocks
    private BibliothecaireFacade facade;

    private Etudiant etudiant;
    private Ouvrage ouvrage;
    private Emprunt emprunt;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setId(1L);
        etudiant.setNom("Kaboré");
        etudiant.setPrenom("Fatima");

        ouvrage = new Ouvrage();
        ouvrage.setId(1L);
        ouvrage.setTitre("Java Spring Boot");
        ouvrage.setExemplairesDisponibles(3);

        emprunt = new Emprunt();
        emprunt.setId(1L);
        emprunt.setEtudiant(etudiant);
        emprunt.setOuvrage(ouvrage);
        emprunt.setStatut(StatutEmprunt.EN_COURS);

        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setEtudiant(etudiant);
        reservation.setOuvrage(ouvrage);
        reservation.setStatut(StatutReservation.ANNULEE);
    }

    @Test
    void testEnregistrerEmprunt() {
        when(empruntService.creerEmprunt(etudiant, ouvrage)).thenReturn(emprunt);
        Emprunt result = facade.enregistrerEmprunt(etudiant, ouvrage);
        assertNotNull(result);
        assertEquals(StatutEmprunt.EN_COURS, result.getStatut());
        verify(empruntService, times(1)).creerEmprunt(etudiant, ouvrage);
    }

    @Test
    void testEnregistrerRetour() {
        emprunt.setStatut(StatutEmprunt.RETOURNE);
        when(empruntService.retournerEmprunt(1L)).thenReturn(emprunt);
        Emprunt result = facade.enregistrerRetour(1L);
        assertNotNull(result);
        assertEquals(StatutEmprunt.RETOURNE, result.getStatut());
    }

    @Test
    void testAnnulerReservation() {
        when(reservationService.annulerReservation(1L)).thenReturn(reservation);
        Reservation result = facade.annulerReservation(1L);
        assertNotNull(result);
        assertEquals(StatutReservation.ANNULEE, result.getStatut());
    }

    @Test
    void testObtenirPenalite() {
        when(empruntService.calculerPenalite(1L)).thenReturn(500.0);
        double penalite = facade.obtenirPenalite(1L);
        assertEquals(500.0, penalite);
    }
}
