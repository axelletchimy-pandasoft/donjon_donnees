package com.esgi.donjons.aventurier.dto;

import com.esgi.donjons.aventurier.model.ClasseHeros;

public record CreateAventurierDTO(
        String nom,
        ClasseHeros classe,
        int niveau
) {

    public CreateAventurierDTO {
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