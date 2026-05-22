package com.esgi.donjons.aventurier.dto;

import com.esgi.donjons.aventurier.model.ClasseHeros;

public record CreateAventurierDTO(
        String pseudo,
        ClasseHeros classe
) {
}
