package com.esgi.donjons.aventurier.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public record Aventurier(
        UUID id,
        String pseudo,
        ClasseHeros classe,
        int niveau,
        int xp,
        int orTotal,
        LocalDateTime createdAt
) {
    public Aventurier {
        Objects.requireNonNull(id, "L'id est obligatoire");
        Objects.requireNonNull(classe, "La classe est obligatoire");
        Objects.requireNonNull(createdAt, "La date de creation est obligatoire");

        if (pseudo == null || pseudo.isBlank()) {
            throw new IllegalArgumentException("Le pseudo est obligatoire");
        }
        if (pseudo.length() < 3 || pseudo.length() > 20) {
            throw new IllegalArgumentException("Le pseudo doit contenir entre 3 et 20 caracteres");
        }
        if (niveau < 1) {
            throw new IllegalArgumentException("Le niveau doit etre superieur ou egal a 1");
        }
        if (xp < 0) {
            throw new IllegalArgumentException("L'xp ne peut pas etre negative");
        }
        if (orTotal < 0) {
            throw new IllegalArgumentException("L'or ne peut pas etre negatif");
        }
    }

    public Aventurier rafraichirNiveau() {
        return new Aventurier(id, pseudo, classe, (xp / 100) + 1, xp, orTotal, createdAt);
    }
}
