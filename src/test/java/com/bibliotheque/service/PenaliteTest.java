package com.bibliotheque.service;

import com.bibliotheque.strategy.CalculPenalite;
import com.bibliotheque.strategy.PenaliteBoursier;
import com.bibliotheque.strategy.PenaliteStandard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PenaliteTest {

    @Test
    void testPenaliteStandard() {
        CalculPenalite penalite = new PenaliteStandard();
        assertEquals(500.0, penalite.calculer(5));
        assertEquals(0.0, penalite.calculer(0));
        assertEquals(1000.0, penalite.calculer(10));
    }

    @Test
    void testPenaliteBoursier() {
        CalculPenalite penalite = new PenaliteBoursier();
        assertEquals(250.0, penalite.calculer(5));
        assertEquals(0.0, penalite.calculer(0));
        assertEquals(500.0, penalite.calculer(10));
    }

    @Test
    void testPenaliteBoursierMoinsChereQueStandard() {
        CalculPenalite standard = new PenaliteStandard();
        CalculPenalite boursier = new PenaliteBoursier();
        assertTrue(boursier.calculer(5) < standard.calculer(5));
    }
}
