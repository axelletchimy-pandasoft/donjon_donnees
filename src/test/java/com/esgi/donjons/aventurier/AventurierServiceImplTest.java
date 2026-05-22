package com.esgi.donjons.aventurier;

import com.esgi.donjons.aventurier.dao.AventurierDAO;
import com.esgi.donjons.aventurier.dto.AventurierDTO;
import com.esgi.donjons.aventurier.dto.CreateAventurierDTO;
import com.esgi.donjons.aventurier.exception.AventurierAlreadyExistsException;
import com.esgi.donjons.aventurier.model.Aventurier;
import com.esgi.donjons.aventurier.model.ClasseHeros;
import com.esgi.donjons.aventurier.service.AventurierService;
import com.esgi.donjons.aventurier.service.AventurierServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AventurierServiceImplTest {
    @Test
    void recruterCreatesNewAventurierWithInitialStats() {
        FakeAventurierDAO dao = new FakeAventurierDAO();
        AventurierService service = new AventurierServiceImpl(dao);

        AventurierDTO aventurier = service.recruter(new CreateAventurierDTO("Alix", ClasseHeros.VOLEUR));

        assertEquals("Alix", aventurier.pseudo());
        assertEquals(ClasseHeros.VOLEUR, aventurier.classe());
        assertEquals(1, aventurier.niveau());
        assertEquals(0, aventurier.xp());
        assertEquals(0, aventurier.orTotal());
        assertEquals(1, dao.aventuriers.size());
    }

    @Test
    void recruterRejectsAlreadyUsedPseudo() {
        FakeAventurierDAO dao = new FakeAventurierDAO();
        dao.save(aventurier("Alix", ClasseHeros.MAGE, 1, 0, 0));
        AventurierService service = new AventurierServiceImpl(dao);

        assertThrows(
                AventurierAlreadyExistsException.class,
                () -> service.recruter(new CreateAventurierDTO("Alix", ClasseHeros.VOLEUR))
        );
    }

    @Test
    void recruterChecksUniquenessAfterPseudoTrim() {
        FakeAventurierDAO dao = new FakeAventurierDAO();
        dao.save(aventurier("Alix", ClasseHeros.MAGE, 1, 0, 0));
        AventurierService service = new AventurierServiceImpl(dao);

        assertThrows(
                AventurierAlreadyExistsException.class,
                () -> service.recruter(new CreateAventurierDTO(" Alix ", ClasseHeros.VOLEUR))
        );
    }

    @Test
    void recruterRejectsInvalidPseudoFormat() {
        AventurierService service = new AventurierServiceImpl(new FakeAventurierDAO());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.recruter(new CreateAventurierDTO("Al ix", ClasseHeros.VOLEUR))
        );
    }

    @Test
    void getClassementReturnsTopNOrderedByXpDescending() {
        FakeAventurierDAO dao = new FakeAventurierDAO(List.of(
                aventurier("Bran", ClasseHeros.GUERRIER, 1, 50, 8),
                aventurier("Cyra", ClasseHeros.MAGE, 4, 340, 40),
                aventurier("Alix", ClasseHeros.VOLEUR, 3, 210, 15)
        ));
        AventurierService service = new AventurierServiceImpl(dao);

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
        AventurierService service = new AventurierServiceImpl(new FakeAventurierDAO());

        List<AventurierDTO> classement = service.getClassement(5);

        assertTrue(classement.isEmpty());
    }

    @Test
    void getClassementReturnsEmptyListForZeroLimit() {
        AventurierService service = new AventurierServiceImpl(new FakeAventurierDAO());

        List<AventurierDTO> classement = service.getClassement(0);

        assertTrue(classement.isEmpty());
    }

    @Test
    void getClassementRejectsNegativeLimit() {
        AventurierService service = new AventurierServiceImpl(new FakeAventurierDAO());

        assertThrows(IllegalArgumentException.class, () -> service.getClassement(-1));
    }

    private Aventurier aventurier(String pseudo, ClasseHeros classe, int niveau, int xp, int orTotal) {
        return new Aventurier(UUID.randomUUID(), pseudo, classe, niveau, xp, orTotal, LocalDateTime.now());
    }

    private static class FakeAventurierDAO implements AventurierDAO {
        private final List<Aventurier> aventuriers = new ArrayList<>();

        FakeAventurierDAO() {
        }

        FakeAventurierDAO(List<Aventurier> aventuriers) {
            this.aventuriers.addAll(aventuriers);
        }

        @Override
        public Aventurier save(Aventurier aventurier) {
            aventuriers.add(aventurier);
            return aventurier;
        }

        @Override
        public Optional<Aventurier> findByPseudo(String pseudo) {
            return aventuriers.stream()
                    .filter(aventurier -> aventurier.pseudo().equals(pseudo))
                    .findFirst();
        }

        @Override
        public List<Aventurier> findTopByXp(int limit) {
            return aventuriers.stream()
                    .sorted((first, second) -> Integer.compare(second.xp(), first.xp()))
                    .limit(limit)
                    .toList();
        }
    }
}
