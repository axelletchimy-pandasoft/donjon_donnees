package com.esgi.donjons.aventurier.model;

import java.util.Objects;

public record Aventurier(
        Long id,
        String nom,
        ClasseHeros classe,
        int niveau
) {

    public Aventurier {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("L'identifiant de l'aventurier est invalide");
        }

        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("Le nom de l'aventurier est obligatoire");
        }

        if (classe == null) {
            throw new IllegalArgumentException("La classe du héros est obligatoire");
        }

        if (niveau < 1) {
            throw new IllegalArgumentException("Le niveau doit être supérieur ou égal à 1");
        }

        nom = nom.trim();
    }
}
