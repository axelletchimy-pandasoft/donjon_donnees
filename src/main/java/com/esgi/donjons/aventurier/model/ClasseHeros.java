package com.esgi.donjons.aventurier.model;

public enum ClasseHeros {
    GUERRIER, MAGE, VOLEUR, ARCHER, PALADIN, BARDE;

    public static ClasseHeros from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Classe cannot be null");
        }
        try {
            return ClasseHeros.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown classe: " + value);
        }
    }
}
