package com.esgi.donjons.aventurier.dto;

import com.esgi.donjons.aventurier.model.ClasseHeros;
import java.util.UUID;

public class AventurierDTO {

    private final UUID id;
    private final String pseudo;
    private final ClasseHeros classe;
    private final int niveau;
    private final int xp;
    private final int or;

    public AventurierDTO(UUID id, String pseudo, ClasseHeros classe, int niveau, int xp, int or) {
        this.id = id;
        this.pseudo = pseudo;
        this.classe = classe;
        this.niveau = niveau;
        this.xp = xp;
        this.or = or;
    }

    public UUID getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public ClasseHeros getClasse() {
        return classe;
    }

    public int getNiveau() {
        return niveau;
    }

    public int getXp() {
        return xp;
    }

    public int getOr() {
        return or;
    }
}
