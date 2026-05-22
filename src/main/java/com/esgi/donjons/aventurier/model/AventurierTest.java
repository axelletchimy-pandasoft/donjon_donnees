package com.esgi.donjons.aventurier.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AventurierTest {

    @Test
    void devraitCreerUnAventurierValide() {
        Aventurier aventurier = new Aventurier(
                1L,
                "Arthas",
                ClasseHeros.GUERRIER,
                10
        );

        assertEquals(1L, aventurier.id());
        assertEquals("Arthas", aventurier.nom());
        assertEquals(ClasseHeros.GUERRIER, aventurier.classe());
        assertEquals(10, aventurier.niveau());
    }

    @Test
    void devraitLeverUneExceptionSiIdInvalide() {
        assertThrows(IllegalArgumentException.class, () ->
                new Aventurier(0L, "Arthas", ClasseHeros.GUERRIER, 10)
        );
    }

    @Test
    void devraitLeverUneExceptionSiNomVide() {
        assertThrows(IllegalArgumentException.class, () ->
                new Aventurier(1L, "   ", ClasseHeros.MAGE, 5)
        );
    }

    @Test
    void devraitLeverUneExceptionSiClasseNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Aventurier(1L, "Jaina", null, 5)
        );
    }

    @Test
    void devraitLeverUneExceptionSiNiveauInvalide() {
        assertThrows(IllegalArgumentException.class, () ->
                new Aventurier(1L, "Valeera", ClasseHeros.VOLEUR, 0)
        );
    }
}