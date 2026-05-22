package com.esgi.donjons.quete.model;

import java.util.Objects;
import java.util.UUID;

public class Quete {

    private final UUID id;
    private final String titre;
    private final Difficulte difficulte;
    private final Biome biome;
    private final int xp;
    private final int or;
    private final boolean active;

    public Quete(UUID id, String titre, Difficulte difficulte, Biome biome, int xp, int or, boolean active) {
        if (titre == null || titre.isBlank()) {
            throw new IllegalArgumentException("titre must not be blank");
        }
        if (difficulte == null) {
            throw new IllegalArgumentException("difficulte must not be null");
        }
        if (biome == null) {
            throw new IllegalArgumentException("biome must not be null");
        }
        if (xp < 0) {
            throw new IllegalArgumentException("xp must be non-negative");
        }
        if (or < 0) {
            throw new IllegalArgumentException("or must be non-negative");
        }
        this.id = id;
        this.titre = titre;
        this.difficulte = difficulte;
        this.biome = biome;
        this.xp = xp;
        this.or = or;
        this.active = active;
    }

    public Quete(String titre, Difficulte difficulte, Biome biome, int xp, int or) {
        this(UUID.randomUUID(), titre, difficulte, biome, xp, or, true);
    }

    public Quete desactiver() {
        return new Quete(id, titre, difficulte, biome, xp, or, false);
    }

    public UUID getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public Difficulte getDifficulte() {
        return difficulte;
    }

    public Biome getBiome() {
        return biome;
    }

    public int getXp() {
        return xp;
    }

    public int getOr() {
        return or;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quete quete = (Quete) o;
        return id == quete.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Quete{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", difficulte=" + difficulte +
                ", biome=" + biome +
                ", xp=" + xp +
                ", or=" + or +
                ", active=" + active +
                '}';
    }
}
