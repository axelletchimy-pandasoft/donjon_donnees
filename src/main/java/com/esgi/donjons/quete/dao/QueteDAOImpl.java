package com.esgi.donjons.quete.dao;

import model.Quete;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implémentation JDBC du QueteDAO.
 * Utilise une connexion PostgreSQL passée en constructeur.
 */
public class QueteDAOImpl implements QueteDAO {

    private final Connection connection;

    public QueteDAOImpl(Connection connection) {
        this.connection = connection;
    }

    // ─── Mapper ResultSet → Quete ─────────────────────────────────────────────

    private Quete mapRow(ResultSet rs) throws SQLException {
        return new Quete(
                UUID.fromString(rs.getString("id")),
                rs.getString("titre"),
                rs.getString("difficulte"),
                rs.getString("biome"),
                rs.getInt("recompense_xp"),
                rs.getInt("recompense_or"),
                rs.getBoolean("active"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }

    // ─── CRUD ─────────────────────────────────────────────────────────────────

    @Override
    public Quete save(Quete quete) {
        String sql = """
                INSERT INTO quete (id, titre, difficulte, biome, recompense_xp, recompense_or, active, created_at)
                VALUES (?::uuid, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, quete.getId().toString());
            stmt.setString(2, quete.getTitre());
            stmt.setString(3, quete.getDifficulte());
            stmt.setString(4, quete.getBiome());
            stmt.setInt(5, quete.getRecompenseXp());
            stmt.setInt(6, quete.getRecompenseOr());
            stmt.setBoolean(7, quete.isActive());
            stmt.setTimestamp(8, Timestamp.valueOf(quete.getCreatedAt()));
            stmt.executeUpdate();
            return quete;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de la quête", e);
        }
    }

    @Override
    public Optional<Quete> findById(UUID id) {
        String sql = "SELECT * FROM quete WHERE id = ?::uuid";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la quête par id", e);
        }
    }

    @Override
    public List<Quete> findAll() {
        String sql = "SELECT * FROM quete ORDER BY created_at DESC";
        List<Quete> quetes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                quetes.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des quêtes", e);
        }
        return quetes;
    }

    @Override
    public List<Quete> findAllActive() {
        String sql = "SELECT * FROM quete WHERE active = true ORDER BY created_at DESC";
        List<Quete> quetes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                quetes.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des quêtes actives", e);
        }
        return quetes;
    }

    @Override
    public List<Quete> findByDifficulte(String difficulte) {
        String sql = "SELECT * FROM quete WHERE difficulte = ? ORDER BY created_at DESC";
        List<Quete> quetes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, difficulte);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                quetes.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par difficulté", e);
        }
        return quetes;
    }

    @Override
    public List<Quete> findByBiome(String biome) {
        String sql = "SELECT * FROM quete WHERE biome = ? ORDER BY created_at DESC";
        List<Quete> quetes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, biome);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                quetes.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par biome", e);
        }
        return quetes;
    }

    @Override
    public Quete update(Quete quete) {
        String sql = """
                UPDATE quete
                SET titre = ?, difficulte = ?, biome = ?,
                    recompense_xp = ?, recompense_or = ?, active = ?
                WHERE id = ?::uuid
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, quete.getTitre());
            stmt.setString(2, quete.getDifficulte());
            stmt.setString(3, quete.getBiome());
            stmt.setInt(4, quete.getRecompenseXp());
            stmt.setInt(5, quete.getRecompenseOr());
            stmt.setBoolean(6, quete.isActive());
            stmt.setString(7, quete.getId().toString());
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Aucune quête trouvée avec l'id : " + quete.getId());
            }
            return quete;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la quête", e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM quete WHERE id = ?::uuid";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la quête", e);
        }
    }

    @Override
    public boolean existsById(UUID id) {
        String sql = "SELECT 1 FROM quete WHERE id = ?::uuid";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id.toString());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification d'existence", e);
        }
    }
}
