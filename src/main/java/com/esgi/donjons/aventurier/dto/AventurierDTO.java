package com.esgi.donjons.aventurier.dto;

import com.esgi.donjons.aventurier.model.ClasseHeros;

public record AventurierDTO(
        Long id,
        String nom,
        ClasseHeros classe,
        int niveau
) {
}