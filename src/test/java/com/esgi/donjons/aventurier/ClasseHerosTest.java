package com.esgi.donjons.aventurier;

import com.esgi.donjons.aventurier.model.ClasseHeros;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClasseHerosTest {
    @Test
    void shouldParseClasseIgnoringCaseAndSpaces() {
        assertEquals(ClasseHeros.MAGE, ClasseHeros.from(" mage "));
    }

    @Test
    void shouldRejectUnknownClasse() {
        assertThrows(IllegalArgumentException.class, () -> ClasseHeros.from("druide"));
    }
}
