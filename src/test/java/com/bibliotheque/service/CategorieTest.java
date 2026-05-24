package com.bibliotheque.service;

import com.bibliotheque.model.Categorie;
import com.bibliotheque.model.Ouvrage;
import com.bibliotheque.repository.CategorieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategorieTest {

    @Mock
    private CategorieRepository categorieRepository;

    private Categorie categorie;

    @BeforeEach
    void setUp() {
        categorie = new Categorie();
        categorie.setId(1L);
        categorie.setNom("Informatique");
        categorie.setDescription("Livres d'informatique");
        categorie.setRayon("A1");
    }

    @Test
    void testCreerCategorie() {
        when(categorieRepository.save(any(Categorie.class))).thenReturn(categorie);
        Categorie result = categorieRepository.save(categorie);
        assertNotNull(result);
        assertEquals("Informatique", result.getNom());
        assertEquals("A1", result.getRayon());
    }

    @Test
    void testTrouverParNom() {
        when(categorieRepository.findByNom("Informatique"))
                .thenReturn(Optional.of(categorie));
        Optional<Categorie> result = categorieRepository.findByNom("Informatique");
        assertTrue(result.isPresent());
        assertEquals("Informatique", result.get().getNom());
    }

    @Test
    void testTrouverParRayon() {
        when(categorieRepository.findByRayon("A1"))
                .thenReturn(Arrays.asList(categorie));
        List<Categorie> result = categorieRepository.findByRayon("A1");
        assertEquals(1, result.size());
        assertEquals("A1", result.get(0).getRayon());
    }

    @Test
    void testSupprimerCategorie() {
        doNothing().when(categorieRepository).deleteById(1L);
        categorieRepository.deleteById(1L);
        verify(categorieRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetToutesCategories() {
        when(categorieRepository.findAll()).thenReturn(Arrays.asList(categorie));
        List<Categorie> result = categorieRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void testCategorieAvecOuvrages() {
        Ouvrage ouvrage = new Ouvrage();
        ouvrage.setTitre("Java");
        ouvrage.setCategorie(categorie);
        assertEquals("Informatique", ouvrage.getCategorie().getNom());
    }
}