package com.esgi.donjons.aventurier.model;

import java.util.Locale;

public enum ClasseHeros {
    GUERRIER,
    MAGE,
    VOLEUR,
    ARCHER,
    PALADIN,
    BARDE;

    public static ClasseHeros from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("La classe du heros est obligatoire");
        }

        try {
            return ClasseHeros.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Classe de heros inconnue : " + value, exception);
        }
    }
}
