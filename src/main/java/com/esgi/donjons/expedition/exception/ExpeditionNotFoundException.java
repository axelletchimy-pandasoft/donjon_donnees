package com.esgi.donjons.expedition.exception;

import java.util.UUID;

public class ExpeditionNotFoundException extends RuntimeException {

    public ExpeditionNotFoundException(UUID id) {
        super("Expédition introuvable avec l'id : " + id);
    }

    public ExpeditionNotFoundException(String message) {
        super(message);
    }
}
