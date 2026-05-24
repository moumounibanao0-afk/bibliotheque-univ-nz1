package com.bibliotheque.service;

import com.bibliotheque.model.*;
import com.bibliotheque.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private OuvrageRepository ouvrageRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ReservationService reservationService;

    private Etudiant etudiant;
    private Ouvrage ouvrage;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setId(1L);
        etudiant.setNom("Moumouni");
        etudiant.setPrenom("Test");
        etudiant.setEmail("test@gmail.com");

        ouvrage = new Ouvrage();
        ouvrage.setId(1L);
        ouvrage.setTitre("Java Spring Boot");

        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setEtudiant(etudiant);
        reservation.setOuvrage(ouvrage);
        reservation.setStatut(StatutReservation.EN_ATTENTE);
    }

    @Test
    void testCreerReservation() {
        when(reservationRepository.existsByEtudiantIdAndOuvrageIdAndStatut(
                1L, 1L, StatutReservation.EN_ATTENTE)).thenReturn(false);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        doNothing().when(notificationService).envoyerConfirmationReservation(any());

        Reservation result = reservationService.creerReservation(etudiant, ouvrage);

        assertNotNull(result);
        assertEquals(StatutReservation.EN_ATTENTE, result.getStatut());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void testCreerReservationDejaExistante() {
        when(reservationRepository.existsByEtudiantIdAndOuvrageIdAndStatut(
                1L, 1L, StatutReservation.EN_ATTENTE)).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            reservationService.creerReservation(etudiant, ouvrage);
        });
    }

    @Test
    void testAnnulerReservation() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        doNothing().when(notificationService).envoyerAnnulationReservation(any());

        Reservation result = reservationService.annulerReservation(1L);

        assertNotNull(result);
        assertEquals(StatutReservation.ANNULEE, result.getStatut());
    }

    @Test
    void testAnnulerReservationNonTrouvee() {
        when(reservationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            reservationService.annulerReservation(99L);
        });
    }

    @Test
    void testGetToutesReservations() {
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation));

        List<Reservation> result = reservationService.getToutesReservations();

        assertEquals(1, result.size());
        assertEquals(StatutReservation.EN_ATTENTE, result.get(0).getStatut());
    }

    @Test
    void testGetReservationsEtudiant() {
        when(reservationRepository.findByEtudiantId(1L)).thenReturn(Arrays.asList(reservation));

        List<Reservation> result = reservationService.getReservationsEtudiant(1L);

        assertEquals(1, result.size());
    }
}