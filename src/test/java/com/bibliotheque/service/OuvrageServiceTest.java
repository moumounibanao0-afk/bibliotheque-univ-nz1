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
public class OuvrageServiceTest {

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
        ouvrage.setExemplairesDisponibles(5);
    }

    @Test
    void testGetTousLesOuvrages() {
        when(ouvrageRepository.findAll()).thenReturn(Arrays.asList(ouvrage));
        List<Ouvrage> ouvrages = ouvrageService.getTousLesOuvrages();
        assertEquals(1, ouvrages.size());
        assertEquals("Java Spring Boot", ouvrages.get(0).getTitre());
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
        assertEquals(5, result.getExemplairesDisponibles());
        verify(ouvrageRepository, times(1)).save(ouvrage);
    }

    @Test
    void testSupprimerOuvrage() {
        doNothing().when(ouvrageRepository).deleteById(1L);
        ouvrageService.supprimerOuvrage(1L);
        verify(ouvrageRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRechercherParTitre() {
        when(ouvrageRepository.findByTitreContainingIgnoreCase("Java"))
            .thenReturn(Arrays.asList(ouvrage));
        List<Ouvrage> result = ouvrageService.rechercherParTitre("Java");
        assertEquals(1, result.size());
    }

    @Test
    void testGetOuvragesDisponibles() {
        when(ouvrageRepository.findByExemplairesDisponiblesGreaterThan(0))
            .thenReturn(Arrays.asList(ouvrage));
        List<Ouvrage> result = ouvrageService.getOuvragesDisponibles();
        assertEquals(1, result.size());
    }
}
