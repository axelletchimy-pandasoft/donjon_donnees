package com.esgi.donjons.quete.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO utilisé pour exposer les données d'une quête (lecture / réponse API).
 */
public class QueteResponseDTO {

    private UUID id;
    private String titre;
    private String difficulte;
    private String biome;
    private int recompenseXp;
    private int recompenseOr;
    private boolean active;
    private LocalDateTime createdAt;

    // ─── Constructeurs ────────────────────────────────────────────────────────

    public QueteResponseDTO() {}

    public QueteResponseDTO(UUID id, String titre, String difficulte, String biome,
                            int recompenseXp, int recompenseOr, boolean active,
                            LocalDateTime createdAt) {
        this.id = id;
        this.titre = titre;
        this.difficulte = difficulte;
        this.biome = biome;
        this.recompenseXp = recompenseXp;
        this.recompenseOr = recompenseOr;
        this.active = active;
        this.createdAt = createdAt;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDifficulte() { return difficulte; }
    public void setDifficulte(String difficulte) { this.difficulte = difficulte; }

    public String getBiome() { return biome; }
    public void setBiome(String biome) { this.biome = biome; }

    public int getRecompenseXp() { return recompenseXp; }
    public void setRecompenseXp(int recompenseXp) { this.recompenseXp = recompenseXp; }

    public int getRecompenseOr() { return recompenseOr; }
    public void setRecompenseOr(int recompenseOr) { this.recompenseOr = recompenseOr; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "QueteResponseDTO{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", difficulte='" + difficulte + '\'' +
                ", biome='" + biome + '\'' +
                ", recompenseXp=" + recompenseXp +
                ", recompenseOr=" + recompenseOr +
                ", active=" + active +
                ", createdAt=" + createdAt +
                '}';
    }
}
