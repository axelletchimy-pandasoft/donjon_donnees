package com.esgi.donjons.aventurier.exception;

public class AventurierAlreadyExistsException extends RuntimeException {
    public AventurierAlreadyExistsException(String pseudo) {
        super("Un aventurier existe déjà avec le pseudo : " + pseudo);
    }

    public AventurierAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
