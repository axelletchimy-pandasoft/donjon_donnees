package com.esgi.donjons.aventurier;

import com.esgi.donjons.aventurier.model.Aventurier;
import com.esgi.donjons.aventurier.model.ClasseHeros;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AventurierModelTest {
    @Test
    void shouldCreateValidAventurier() {
        UUID id = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();

        Aventurier aventurier = new Aventurier(id, "Arthas", ClasseHeros.GUERRIER, 1, 0, 0, createdAt);

        assertEquals(id, aventurier.id());
        assertEquals("Arthas", aventurier.pseudo());
        assertEquals(ClasseHeros.GUERRIER, aventurier.classe());
        assertEquals(1, aventurier.niveau());
        assertEquals(0, aventurier.xp());
        assertEquals(0, aventurier.orTotal());
        assertEquals(createdAt, aventurier.createdAt());
    }

    @Test
    void shouldRejectInvalidPseudo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Aventurier(UUID.randomUUID(), "Al ix", ClasseHeros.MAGE, 1, 0, 0, LocalDateTime.now())
        );
    }

    @Test
    void shouldRefreshLevelFromXp() {
        Aventurier aventurier = new Aventurier(
                UUID.randomUUID(),
                "Jaina",
                ClasseHeros.MAGE,
                1,
                250,
                30,
                LocalDateTime.now()
        );

        Aventurier refreshed = aventurier.rafraichirNiveau();

        assertEquals(3, refreshed.niveau());
        assertEquals(250, refreshed.xp());
        assertEquals(30, refreshed.orTotal());
    }
}
