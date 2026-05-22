package com.esgi.donjons.aventurier.exception;

public class AventurierNotFoundException extends RuntimeException {

    public AventurierNotFoundException(Long id) {
        super("Aucun aventurier trouvé avec l'identifiant : " + id);
    }

    public AventurierNotFoundException(String message) {
        super(message);
    }
}