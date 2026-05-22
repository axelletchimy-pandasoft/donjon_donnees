package com.esgi.donjons.aventurier;

import com.esgi.donjons.aventurier.dao.AventurierDAO;
import com.esgi.donjons.aventurier.dto.AventurierDTO;
import com.esgi.donjons.aventurier.model.Aventurier;
import com.esgi.donjons.aventurier.model.ClasseHeros;
import com.esgi.donjons.aventurier.service.AventurierService;
import com.esgi.donjons.aventurier.service.AventurierServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AventurierServiceImplTest {
    @Test
    void getClassementReturnsTopNOrderedByXpDescending() {
        AventurierService service = new AventurierServiceImpl(limit -> List.of(
                aventurier("Bran", ClasseHeros.GUERRIER, 1, 50, 8),
                aventurier("Cyra", ClasseHeros.MAGE, 4, 340, 40),
                aventurier("Alix", ClasseHeros.VOLEUR, 3, 210, 15)
        ));

        List<AventurierDTO> classement = service.getClassement(2);

        assertEquals(2, classement.size());
        assertEquals("Cyra", classement.get(0).pseudo());
        assertEquals(ClasseHeros.MAGE, classement.get(0).classe());
        assertEquals(4, classement.get(0).niveau());
        assertEquals(40, classement.get(0).orTotal());
        assertEquals("Alix", classement.get(1).pseudo());
    }

    @Test
    void getClassementReturnsEmptyListWhenDaoHasNoAventuriers() {
        AventurierService service = new AventurierServiceImpl(limit -> List.of());

        List<AventurierDTO> classement = service.getClassement(5);

        assertTrue(classement.isEmpty());
    }

    @Test
    void getClassementReturnsEmptyListForZeroLimit() {
        AventurierDAO dao = limit -> {
            throw new AssertionError("Le DAO ne doit pas etre appele pour une limite a zero");
        };
        AventurierService service = new AventurierServiceImpl(dao);

        List<AventurierDTO> classement = service.getClassement(0);

        assertTrue(classement.isEmpty());
    }

    @Test
    void getClassementRejectsNegativeLimit() {
        AventurierService service = new AventurierServiceImpl(limit -> List.of());

        assertThrows(IllegalArgumentException.class, () -> service.getClassement(-1));
    }

    private Aventurier aventurier(String pseudo, ClasseHeros classe, int niveau, int xp, int orTotal) {
        return new Aventurier(UUID.randomUUID(), pseudo, classe, niveau, xp, orTotal, LocalDateTime.now());
    }
}
