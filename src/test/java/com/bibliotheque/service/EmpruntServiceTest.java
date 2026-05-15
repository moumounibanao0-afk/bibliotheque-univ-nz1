package com.bibliotheque.service;

import com.bibliotheque.model.*;
import com.bibliotheque.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmpruntServiceTest {

    @Mock
    private EmpruntRepository empruntRepository;

    @Mock
    private OuvrageRepository ouvrageRepository;

    @InjectMocks
    private EmpruntService empruntService;

    private Etudiant etudiant;
    private Ouvrage ouvrage;
    private Emprunt emprunt;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setId(1L);
        etudiant.setNom("Moumouni");
        etudiant.setPrenom("Test");

        ouvrage = new Ouvrage();
        ouvrage.setId(1L);
        ouvrage.setTitre("Java Spring Boot");
        ouvrage.setExemplairesDisponibles(3);

        emprunt = new Emprunt();
        emprunt.setId(1L);
        emprunt.setEtudiant(etudiant);
        emprunt.setOuvrage(ouvrage);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(14));
        emprunt.setStatut(StatutEmprunt.EN_COURS);
    }

    @Test
    void testCreerEmprunt() {
        when(ouvrageRepository.save(any(Ouvrage.class))).thenReturn(ouvrage);
        when(empruntRepository.save(any(Emprunt.class))).thenReturn(emprunt);

        Emprunt result = empruntService.creerEmprunt(etudiant, ouvrage);

        assertNotNull(result);
        assertEquals(StatutEmprunt.EN_COURS, result.getStatut());
        verify(empruntRepository, times(1)).save(any(Emprunt.class));
    }

    @Test
    void testCreerEmpruntOuvrageIndisponible() {
        ouvrage.setExemplairesDisponibles(0);
        assertThrows(RuntimeException.class, () -> {
            empruntService.creerEmprunt(etudiant, ouvrage);
        });
    }

    @Test
    void testRetournerEmprunt() {
        when(empruntRepository.findById(1L)).thenReturn(Optional.of(emprunt));
        when(ouvrageRepository.save(any(Ouvrage.class))).thenReturn(ouvrage);
        when(empruntRepository.save(any(Emprunt.class))).thenReturn(emprunt);

        Emprunt result = empruntService.retournerEmprunt(1L);

        assertNotNull(result);
        assertEquals(StatutEmprunt.RETOURNE, result.getStatut());
    }

    @Test
    void testRetournerEmpruntNonTrouve() {
        when(empruntRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            empruntService.retournerEmprunt(99L);
        });
    }

    @Test
    void testCalculerPenaliteSansRetard() {
        emprunt.setDateRetourReelle(LocalDate.now());
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(5));
        when(empruntRepository.findById(1L)).thenReturn(Optional.of(emprunt));

        double penalite = empruntService.calculerPenalite(1L);
        assertEquals(0.0, penalite);
    }

    @Test
    void testCalculerPenaliteAvecRetard() {
        emprunt.setDateRetourPrevue(LocalDate.now().minusDays(5));
        emprunt.setDateRetourReelle(LocalDate.now());
        when(empruntRepository.findById(1L)).thenReturn(Optional.of(emprunt));

        double penalite = empruntService.calculerPenalite(1L);
        assertEquals(500.0, penalite);
    }
}
