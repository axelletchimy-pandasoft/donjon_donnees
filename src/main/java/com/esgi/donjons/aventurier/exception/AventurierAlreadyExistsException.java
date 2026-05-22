package com.esgi.donjons.aventurier.exception;

public class AventurierAlreadyExistsException extends RuntimeException {
    public AventurierAlreadyExistsException(String pseudo) {
        super("Un aventurier existe deja avec le pseudo : " + pseudo);
    }
}
