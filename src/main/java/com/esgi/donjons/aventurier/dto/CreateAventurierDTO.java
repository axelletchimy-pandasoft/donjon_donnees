package com.esgi.donjons.aventurier.dto;

import com.esgi.donjons.aventurier.model.ClasseHeros;

public record CreateAventurierDTO(
        String pseudo,
        ClasseHeros classe
) {
    public CreateAventurierDTO {
        if (pseudo == null || pseudo.isBlank()) {
            throw new IllegalArgumentException("Le pseudo de l'aventurier est obligatoire");
        }

        if (classe == null) {
            throw new IllegalArgumentException("La classe du heros est obligatoire");
        }

        pseudo = pseudo.trim();
    }
}
