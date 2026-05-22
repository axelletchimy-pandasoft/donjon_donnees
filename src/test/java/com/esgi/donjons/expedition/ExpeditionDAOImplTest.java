package com.esgi.donjons.expedition;

import com.esgi.donjons.expedition.dao.ExpeditionDAOImpl;
import com.esgi.donjons.expedition.dto.ExpeditionDTO;
import com.esgi.donjons.expedition.model.Expedition;

import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExpeditionDAOImplTest {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/guilde";
    private static final String DB_USER = "donjons";
    private static final String DB_PASSWORD = "donjons";

    private DataSource dataSource;
    private ExpeditionDAOImpl dao;

    @BeforeAll
    void setupDatabase() {
        dataSource = new org.postgresql.ds.PGSimpleDataSource();
        ((org.postgresql.ds.PGSimpleDataSource) dataSource).setUrl(DB_URL);
        ((org.postgresql.ds.PGSimpleDataSource) dataSource).setUser(DB_USER);
        ((org.postgresql.ds.PGSimpleDataSource) dataSource).setPassword(DB_PASSWORD);
        dao = new ExpeditionDAOImpl(dataSource);
    }

    @BeforeEach
    void cleanTable() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM expeditions");
            stmt.execute("DELETE FROM quetes");
            stmt.execute("DELETE FROM aventuriers");
        }
    }

    @Test
    @Disabled("Requiert PostgreSQL accessible sur localhost:5432")
    void saveEtFindById_avecJointure_retourneDTOEnrichi() {
        UUID aventurierId = UUID.randomUUID();
        UUID queteId = UUID.randomUUID();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT INTO aventuriers (id, pseudo, classe, niveau, xp, or_total) VALUES (" +
                    "'" + aventurierId + "', 'TestHero', 'GUERRIER', 10, 0, 0)");
            stmt.execute("INSERT INTO quetes (id, titre, difficulte, biome, recompense_xp, recompense_or, active) VALUES (" +
                    "'" + queteId + "', 'TestQuete', 'FACILE', 'FORET', 100, 50, true)");

        } catch (SQLException e) {
            throw new RuntimeException("Erreur setup test DB", e);
        }

        Expedition expedition = new Expedition(aventurierId, queteId, true, 50, 100, 30);
        Expedition saved = dao.save(expedition);

        assertNotNull(saved.getId());
        assertEquals(aventurierId, saved.getAventurierId());
        assertEquals(queteId, saved.getQueteId());

        Optional<ExpeditionDTO> result = dao.findById(saved.getId());
        assertTrue(result.isPresent());
        assertEquals("TestHero", result.get().getPseudo());
        assertEquals("TestQuete", result.get().getTitre());
    }
}
