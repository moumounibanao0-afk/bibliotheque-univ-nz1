package com.bibliotheque.service;

import com.bibliotheque.model.Ouvrage;
import com.bibliotheque.repository.OuvrageRepository;
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
public class OuvrageServiceAvanceeTest {

    @Mock
    private OuvrageRepository ouvrageRepository;

    @InjectMocks
    private OuvrageService ouvrageService;

    private Ouvrage ouvrage;

    @BeforeEach
    void setUp() {
        ouvrage = new Ouvrage();
        ouvrage.setId(1L);
        ouvrage.setTitre("Java Spring Boot");
        ouvrage.setAuteur("John Doe");
        ouvrage.setIsbn("123456789");
        ouvrage.setNombreExemplaires(5);
        ouvrage.setExemplairesDisponibles(3);
    }

    @Test
    void testRechercherParAuteur() {
        when(ouvrageRepository.findByAuteurContainingIgnoreCase("John"))
                .thenReturn(Arrays.asList(ouvrage));
        List<Ouvrage> result = ouvrageService.rechercherParAuteur("John");
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getAuteur());
    }

    @Test
    void testRechercherParIsbn() {
        when(ouvrageRepository.findByIsbn("123456789"))
                .thenReturn(Optional.of(ouvrage));
        Optional<Ouvrage> result = ouvrageService.rechercherParIsbn("123456789");
        assertTrue(result.isPresent());
        assertEquals("123456789", result.get().getIsbn());
    }

    @Test
    void testRechercherParIsbnNonTrouve() {
        when(ouvrageRepository.findByIsbn("000")).thenReturn(Optional.empty());
        Optional<Ouvrage> result = ouvrageService.rechercherParIsbn("000");
        assertFalse(result.isPresent());
    }

    @Test
    void testRechercheAvancee() {
        when(ouvrageRepository.rechercheAvancee("Java"))
                .thenReturn(Arrays.asList(ouvrage));
        List<Ouvrage> result = ouvrageService.rechercheAvancee("Java");
        assertEquals(1, result.size());
    }

    @Test
    void testRechercherParCategorie() {
        when(ouvrageRepository.findByCategorieNomContaining("Informatique"))
                .thenReturn(Arrays.asList(ouvrage));
        List<Ouvrage> result = ouvrageService.rechercherParCategorie("Informatique");
        assertEquals(1, result.size());
    }

    @Test
    void testRechercherParRayon() {
        when(ouvrageRepository.findByCategorieRayonIgnoreCase("A1"))
                .thenReturn(Arrays.asList(ouvrage));
        List<Ouvrage> result = ouvrageService.rechercherParRayon("A1");
        assertEquals(1, result.size());
    }

    @Test
    void testModifierOuvrage() {
        when(ouvrageRepository.save(any(Ouvrage.class))).thenReturn(ouvrage);
        Ouvrage result = ouvrageService.modifierOuvrage(1L, ouvrage);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(ouvrageRepository, times(1)).save(ouvrage);
    }

    @Test
    void testGetTousLesOuvrages() {
        when(ouvrageRepository.findAll()).thenReturn(Arrays.asList(ouvrage));
        List<Ouvrage> result = ouvrageService.getTousLesOuvrages();
        assertEquals(1, result.size());
    }

    @Test
    void testGetOuvrageParId() {
        when(ouvrageRepository.findById(1L)).thenReturn(Optional.of(ouvrage));
        Optional<Ouvrage> result = ouvrageService.getOuvrageParId(1L);
        assertTrue(result.isPresent());
        assertEquals("Java Spring Boot", result.get().getTitre());
    }

    @Test
    void testGetOuvrageParIdNonTrouve() {
        when(ouvrageRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Ouvrage> result = ouvrageService.getOuvrageParId(99L);
        assertFalse(result.isPresent());
    }

    @Test
    void testAjouterOuvrage() {
        when(ouvrageRepository.save(any(Ouvrage.class))).thenReturn(ouvrage);
        Ouvrage result = ouvrageService.ajouterOuvrage(ouvrage);
        assertNotNull(result);
        assertEquals(5, result.getNombreExemplaires());
        verify(ouvrageRepository, times(1)).save(ouvrage);
    }

    @Test
    void testSupprimerOuvrage() {
        doNothing().when(ouvrageRepository).deleteById(1L);
        ouvrageService.supprimerOuvrage(1L);
        verify(ouvrageRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetOuvragesDisponibles() {
        when(ouvrageRepository.findByExemplairesDisponiblesGreaterThan(0))
                .thenReturn(Arrays.asList(ouvrage));
        List<Ouvrage> result = ouvrageService.getOuvragesDisponibles();
        assertEquals(1, result.size());
        assertTrue(result.get(0).getExemplairesDisponibles() > 0);
    }

}