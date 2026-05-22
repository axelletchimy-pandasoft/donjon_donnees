package com.esgi.donjons.expedition.dao;

import com.esgi.donjons.config.DatabaseManager;
import com.esgi.donjons.expedition.dto.ExpeditionDTO;
import com.esgi.donjons.expedition.model.Expedition;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class ExpeditionDAOImpl implements ExpeditionDAO {

    private final DataSource dataSource;

    public ExpeditionDAOImpl() {
        this(DatabaseManager.getInstance());
    }

    public ExpeditionDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Expedition save(Expedition expedition) {
        String sql = "INSERT INTO expeditions (aventurier_id, quete_id, succes, butin_or, xp_gagne, duree_secondes, date_expedition) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, expedition.getAventurierId(), Types.OTHER);
            stmt.setObject(2, expedition.getQueteId(), Types.OTHER);
            stmt.setBoolean(3, expedition.isSucces());
            stmt.setInt(4, expedition.getButinOr());
            stmt.setInt(5, expedition.getXpGagne());
            stmt.setLong(6, expedition.getDureeSecondes());
            stmt.setTimestamp(7, Timestamp.valueOf(expedition.getDate()));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UUID generatedId = rs.getObject("id", UUID.class);
                    return new Expedition(
                            generatedId,
                            expedition.getAventurierId(),
                            expedition.getQueteId(),
                            expedition.isSucces(),
                            expedition.getButinOr(),
                            expedition.getXpGagne(),
                            expedition.getDureeSecondes(),
                            expedition.getDate()
                    );
                }
                throw new RuntimeException("Échec de l'insertion de l'expédition : aucun ID généré");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de l'expédition", e);
        }
    }

    @Override
    public Optional<ExpeditionDTO> findById(UUID id) {
        String sql = "SELECT e.id, a.pseudo, q.titre, e.succes, e.butin_or, e.xp_gagne, " +
                     "e.duree_secondes, e.date_expedition " +
                     "FROM expeditions e " +
                     "JOIN aventuriers a ON e.aventurier_id = a.id " +
                     "JOIN quetes q ON e.quete_id = q.id " +
                     "WHERE e.id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id, Types.OTHER);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapToDTO(rs));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de l'expédition " + id, e);
        }
    }

    private ExpeditionDTO mapToDTO(ResultSet rs) throws SQLException {
        return new ExpeditionDTO(
                rs.getObject("id", UUID.class),
                rs.getString("pseudo"),
                rs.getString("titre"),
                rs.getBoolean("succes"),
                rs.getInt("butin_or"),
                rs.getInt("xp_gagne"),
                rs.getLong("duree_secondes"),
                rs.getTimestamp("date_expedition").toLocalDateTime()
        );
    }
}
