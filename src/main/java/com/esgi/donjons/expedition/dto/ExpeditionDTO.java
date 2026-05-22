package com.esgi.donjons.expedition.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ExpeditionDTO {

    private final UUID id;
    private final String pseudo;
    private final String titre;
    private final boolean succes;
    private final int butinOr;
    private final int xpGagne;
    private final long dureeSecondes;
    private final LocalDateTime date;

    public ExpeditionDTO(UUID id, String pseudo, String titre, boolean succes,
                         int butinOr, int xpGagne, long dureeSecondes, LocalDateTime date) {
        this.id = id;
        this.pseudo = pseudo;
        this.titre = titre;
        this.succes = succes;
        this.butinOr = butinOr;
        this.xpGagne = xpGagne;
        this.dureeSecondes = dureeSecondes;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getTitre() {
        return titre;
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
    public String toString() {
        return "ExpeditionDTO{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", titre='" + titre + '\'' +
                ", succes=" + succes +
                ", butinOr=" + butinOr +
                ", xpGagne=" + xpGagne +
                ", dureeSecondes=" + dureeSecondes +
                ", date=" + date +
                '}';
    }
}
