package com.esgi.donjons.expedition.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Expedition {

    private final UUID id;
    private final UUID aventurierId;
    private final UUID queteId;
    private final boolean succes;
    private final int butinOr;
    private final int xpGagne;
    private final long dureeSecondes;
    private final LocalDateTime date;

    public Expedition(UUID aventurierId, UUID queteId, boolean succes,
                      int butinOr, int xpGagne, long dureeSecondes) {
        this(null, aventurierId, queteId, succes, butinOr, xpGagne, dureeSecondes, LocalDateTime.now());
    }

    public Expedition(UUID id, UUID aventurierId, UUID queteId, boolean succes,
                      int butinOr, int xpGagne, long dureeSecondes, LocalDateTime date) {
        if (aventurierId == null) {
            throw new IllegalArgumentException("aventurierId must not be null");
        }
        if (queteId == null) {
            throw new IllegalArgumentException("queteId must not be null");
        }
        if (butinOr < 0) {
            throw new IllegalArgumentException("butinOr must be non-negative");
        }
        if (xpGagne < 0) {
            throw new IllegalArgumentException("xpGagne must be non-negative");
        }
        if (dureeSecondes <= 0) {
            throw new IllegalArgumentException("dureeSecondes must be positive");
        }
        if (date == null) {
            throw new IllegalArgumentException("date must not be null");
        }
        this.id = id;
        this.aventurierId = aventurierId;
        this.queteId = queteId;
        this.succes = succes;
        this.butinOr = butinOr;
        this.xpGagne = xpGagne;
        this.dureeSecondes = dureeSecondes;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public UUID getAventurierId() {
        return aventurierId;
    }

    public UUID getQueteId() {
        return queteId;
    }

    public boolean isSucces() {
        return succes;
    }

    public int getButinOr() {
        return butinOr;
    }

    public int getXpGagne() {
        return xpGagne;
    }

    public long getDureeSecondes() {
        return dureeSecondes;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expedition that = (Expedition) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Expedition{" +
                "id=" + id +
                ", aventurierId=" + aventurierId +
                ", queteId=" + queteId +
                ", succes=" + succes +
                ", butinOr=" + butinOr +
                ", xpGagne=" + xpGagne +
                ", dureeSecondes=" + dureeSecondes +
                ", date=" + date +
                '}';
    }
}
