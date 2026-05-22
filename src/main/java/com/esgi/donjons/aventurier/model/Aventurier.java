package com.esgi.donjons.aventurier.model;

import java.util.Objects;
import java.util.UUID;

public class Aventurier {

    private final UUID id;
    private final String pseudo;
    private final ClasseHeros classe;
    private final int niveau;
    private final int xp;
    private final int or;

    public Aventurier(UUID id, String pseudo, ClasseHeros classe, int niveau, int xp, int or) {
        if (pseudo == null || pseudo.isBlank()) {
            throw new IllegalArgumentException("pseudo must not be blank");
        }
        if (classe == null) {
            throw new IllegalArgumentException("classe must not be null");
        }
        if (niveau < 0) {
            throw new IllegalArgumentException("niveau must be non-negative");
        }
        if (xp < 0) {
            throw new IllegalArgumentException("xp must be non-negative");
        }
        if (or < 0) {
            throw new IllegalArgumentException("or must be non-negative");
        }
        this.id = id;
        this.pseudo = pseudo;
        this.classe = classe;
        this.niveau = niveau;
        this.xp = xp;
        this.or = or;
    }

    public Aventurier(String pseudo, ClasseHeros classe) {
        this(UUID.randomUUID(), pseudo, classe, 1, 0, 0);
    }

    public Aventurier rafraichirNiveau() {
        int nouveauNiveau = 1 + (xp / 100);
        return new Aventurier(id, pseudo, classe, nouveauNiveau, xp, or);
    }

    public UUID getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public ClasseHeros getClasse() {
        return classe;
    }

    public int getNiveau() {
        return niveau;
    }

    public int getXp() {
        return xp;
    }

    public int getOr() {
        return or;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aventurier that = (Aventurier) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Aventurier{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", classe=" + classe +
                ", niveau=" + niveau +
                ", xp=" + xp +
                ", or=" + or +
                '}';
    }
}
