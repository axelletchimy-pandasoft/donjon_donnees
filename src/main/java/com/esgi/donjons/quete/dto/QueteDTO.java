package com.esgi.donjons.quete.dto;

import com.esgi.donjons.quete.model.Difficulte;
import java.util.UUID;

public class QueteDTO {

    private final UUID id;
    private final String titre;
    private final Difficulte difficulte;
    private final int xp;
    private final int or;
    private final boolean active;

    public QueteDTO(UUID id, String titre, Difficulte difficulte, int xp, int or, boolean active) {
        this.id = id;
        this.titre = titre;
        this.difficulte = difficulte;
        this.xp = xp;
        this.or = or;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public Difficulte getDifficulte() {
        return difficulte;
    }

    public int getXp() {
        return xp;
    }

    public int getOr() {
        return or;
    }

    public boolean isActive() {
        return active;
    }
}
