package com.esgi.donjons.quete.model;

public enum Difficulte {
    FACILE(1),
    NORMALE(2),
    DIFFICILE(3),
    CAUCHEMAR(5),
    LEGENDAIRE(8);

    private final int multiplicateur;

    Difficulte(int multiplicateur) {
        this.multiplicateur = multiplicateur;
    }

    public int getMultiplicateur() {
        return multiplicateur;
    }
}
