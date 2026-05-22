package com.esgi.donjons.aventurier.dao;

import com.esgi.donjons.aventurier.model.Aventurier;
import com.esgi.donjons.aventurier.model.ClasseHeros;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AventurierDaoIntegrationTest {

    private static Connection connection;
    private AventurierDaoPostgres dao;

    @BeforeAll
    static void initDatabase() throws Exception {
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/donjon",
                "postgres",
                "postgres"
        );
    }

    @BeforeEach
    void setUp() throws Exception {
        dao = new AventurierDaoPostgres(connection);

        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM aventurier");
        }
    }

    @AfterAll
    static void closeDatabase() throws Exception {
        connection.close();
    }

    @Test
    void devraitSauvegarderUnAventurier() {
        Aventurier aventurier = new Aventurier(
                1L,
                "Medivh",
                ClasseHeros.MAGE,
                60
        );

        dao.save(aventurier);

        Optional<Aventurier> resultat = dao.findById(1L);

        assertTrue(resultat.isPresent());
        assertEquals("Medivh", resultat.get().nom());
    }
}