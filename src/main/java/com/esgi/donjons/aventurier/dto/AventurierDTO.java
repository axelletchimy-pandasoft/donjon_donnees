package com.esgi.donjons.aventurier.dto;

import com.esgi.donjons.aventurier.model.ClasseHeros;
import java.util.UUID;

public record AventurierDTO(
        UUID id,
        String pseudo,
        ClasseHeros classe,
        int niveau,
        int xp,
        int orTotal
) {
}

