package com.esgi.donjons.quete.exception;

/**
 * Levée lorsque les données d'une quête sont invalides (validation métier).
 */
public class QueteInvalidException extends RuntimeException {

    public QueteInvalidException(String message) {
        super(message);
    }

    public QueteInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
