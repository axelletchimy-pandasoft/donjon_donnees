package exception;

import java.util.UUID;

/**
 * Levée lorsqu'une quête est introuvable en base de données.
 */
public class QueteNotFoundException extends RuntimeException {

    private final UUID queteId;

    public QueteNotFoundException(UUID queteId) {
        super("Quête introuvable avec l'identifiant : " + queteId);
        this.queteId = queteId;
    }

    public QueteNotFoundException(String message) {
        super(message);
        this.queteId = null;
    }

    public UUID getQueteId() {
        return queteId;
    }
}
