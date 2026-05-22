package com.esgi.donjons.expedition.service;

import com.esgi.donjons.aventurier.dto.AventurierDTO;
import com.esgi.donjons.aventurier.model.ClasseHeros;
import com.esgi.donjons.aventurier.service.AventurierService;
import com.esgi.donjons.expedition.dao.ExpeditionDAO;
import com.esgi.donjons.expedition.dto.ExpeditionDTO;
import com.esgi.donjons.expedition.exception.InvalidExpeditionException;
import com.esgi.donjons.expedition.model.Expedition;
import com.esgi.donjons.quete.dto.QueteDTO;
import com.esgi.donjons.quete.model.Difficulte;
import com.esgi.donjons.quete.service.QueteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ExpeditionServiceImplTest {

    private static final UUID AVENTURIER_ID = UUID.randomUUID();
    private static final UUID QUETE_ID = UUID.randomUUID();

    private FakeAventurierService aventurierService;
    private FakeQueteService queteService;
    private FakeExpeditionDAO expeditionDAO;
    private ExpeditionServiceImpl service;

    @BeforeEach
    void setUp() {
        aventurierService = new FakeAventurierService();
        queteService = new FakeQueteService();
        expeditionDAO = new FakeExpeditionDAO();
    }

    @Test
    void lancer_avecAventurierInexistant_leveException() {
        service = new ExpeditionServiceImpl(expeditionDAO, aventurierService, queteService);

        assertThrows(InvalidExpeditionException.class,
                () -> service.lancer(AVENTURIER_ID, QUETE_ID));
    }

    @Test
    void lancer_avecQueteInexistante_leveException() {
        aventurierService.ajouter(new AventurierDTO(AVENTURIER_ID, "Test", ClasseHeros.GUERRIER, 10, 0, 0));
        service = new ExpeditionServiceImpl(expeditionDAO, aventurierService, queteService);

        assertThrows(InvalidExpeditionException.class,
                () -> service.lancer(AVENTURIER_ID, QUETE_ID));
    }

    @Test
    void lancer_avecQueteArchivee_leveException() {
        aventurierService.ajouter(new AventurierDTO(AVENTURIER_ID, "Test", ClasseHeros.GUERRIER, 10, 0, 0));
        queteService.ajouter(new QueteDTO(QUETE_ID, "Quete test", Difficulte.FACILE, 100, 50, false));
        service = new ExpeditionServiceImpl(expeditionDAO, aventurierService, queteService);

        assertThrows(InvalidExpeditionException.class,
                () -> service.lancer(AVENTURIER_ID, QUETE_ID));
    }

    @Test
    void lancer_avecSucces_recompenseComplet() {
        aventurierService.ajouter(new AventurierDTO(AVENTURIER_ID, "Test", ClasseHeros.GUERRIER, 100, 0, 0));
        queteService.ajouter(new QueteDTO(QUETE_ID, "Quete facile", Difficulte.FACILE, 100, 50, true));
        expeditionDAO.nextFindResult = new ExpeditionDTO(
                UUID.randomUUID(), "Test", "Quete facile", true, 50, 100, 30, LocalDateTime.now());

        Random forceSucces = new Random() {
            @Override public double nextDouble() { return 0.0; }
        };
        service = new ExpeditionServiceImpl(expeditionDAO, aventurierService, queteService, forceSucces);

        service.lancer(AVENTURIER_ID, QUETE_ID);

        assertTrue(expeditionDAO.lastSaved.isSucces());
        assertEquals(100, expeditionDAO.lastSaved.getXpGagne());
        assertEquals(50, expeditionDAO.lastSaved.getButinOr());
        assertEquals(AVENTURIER_ID, aventurierService.lastRecompenserId);
        assertEquals(100, aventurierService.lastXp);
        assertEquals(50, aventurierService.lastOr);
    }

    @Test
    void lancer_avecEchec_consolation() {
        aventurierService.ajouter(new AventurierDTO(AVENTURIER_ID, "Test", ClasseHeros.GUERRIER, 1, 0, 0));
        queteService.ajouter(new QueteDTO(QUETE_ID, "Quete difficile", Difficulte.LEGENDAIRE, 200, 100, true));
        expeditionDAO.nextFindResult = new ExpeditionDTO(
                UUID.randomUUID(), "Test", "Quete difficile", false, 0, 100, 60, LocalDateTime.now());

        Random forceEchec = new Random() {
            @Override public double nextDouble() { return 1.0; }
        };
        service = new ExpeditionServiceImpl(expeditionDAO, aventurierService, queteService, forceEchec);

        service.lancer(AVENTURIER_ID, QUETE_ID);

        assertFalse(expeditionDAO.lastSaved.isSucces());
        assertEquals(100, expeditionDAO.lastSaved.getXpGagne());
        assertEquals(0, expeditionDAO.lastSaved.getButinOr());
        assertEquals(AVENTURIER_ID, aventurierService.lastRecompenserId);
        assertEquals(100, aventurierService.lastXp);
        assertEquals(0, aventurierService.lastOr);
    }

    @Test
    void lancer_valide_enregistreExpedition() {
        aventurierService.ajouter(new AventurierDTO(AVENTURIER_ID, "Test", ClasseHeros.GUERRIER, 50, 0, 0));
        queteService.ajouter(new QueteDTO(QUETE_ID, "Quete normale", Difficulte.NORMALE, 100, 50, true));
        expeditionDAO.nextFindResult = new ExpeditionDTO(
                UUID.randomUUID(), "Test", "Quete normale", true, 50, 100, 30, LocalDateTime.now());

        Random forceSucces = new Random() {
            @Override public double nextDouble() { return 0.0; }
        };
        service = new ExpeditionServiceImpl(expeditionDAO, aventurierService, queteService, forceSucces);

        service.lancer(AVENTURIER_ID, QUETE_ID);

        assertNotNull(expeditionDAO.lastSaved);
        assertEquals(AVENTURIER_ID, expeditionDAO.lastSaved.getAventurierId());
        assertEquals(QUETE_ID, expeditionDAO.lastSaved.getQueteId());
    }

    static class FakeAventurierService implements AventurierService {
        final Map<UUID, AventurierDTO> aventuriers = new HashMap<>();
        UUID lastRecompenserId;
        int lastXp;
        int lastOr;

        void ajouter(AventurierDTO dto) {
            aventuriers.put(dto.getId(), dto);
        }

        @Override
        public AventurierDTO getById(UUID id) {
            return aventuriers.get(id);
        }

        @Override
        public void recompenser(UUID id, int xp, int or) {
            this.lastRecompenserId = id;
            this.lastXp = xp;
            this.lastOr = or;
        }
    }

    static class FakeQueteService implements QueteService {
        final Map<UUID, QueteDTO> quetes = new HashMap<>();

        void ajouter(QueteDTO dto) {
            quetes.put(dto.getId(), dto);
        }

        @Override
        public QueteDTO getById(UUID id) {
            return quetes.get(id);
        }
    }

    static class FakeExpeditionDAO implements ExpeditionDAO {
        Expedition lastSaved;
        ExpeditionDTO nextFindResult;

        @Override
        public Expedition save(Expedition expedition) {
            this.lastSaved = expedition;
            UUID generatedId = UUID.randomUUID();
            return new Expedition(generatedId, expedition.getAventurierId(), expedition.getQueteId(),
                    expedition.isSucces(), expedition.getButinOr(), expedition.getXpGagne(),
                    expedition.getDureeSecondes(), expedition.getDate());
        }

        @Override
        public Optional<ExpeditionDTO> findById(UUID id) {
            return Optional.ofNullable(nextFindResult);
        }
    }
}
