package com.esgi.donjons.aventurier.exception;

public class AventurierAlreadyExistsException extends RuntimeException {
    public AventurierAlreadyExistsException(String nom) {
        super("Un aventurier existe déjà avec le nom : " + nom);
    }

    public AventurierAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
