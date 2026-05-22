package com.esgi.donjons.expedition.dto;

import java.util.UUID;

public class CreateExpeditionDTO {

    private final UUID aventurierId;
    private final UUID queteId;

    public CreateExpeditionDTO(UUID aventurierId, UUID queteId) {
        this.aventurierId = aventurierId;
        this.queteId = queteId;
    }

    public UUID getAventurierId() {
        return aventurierId;
    }

    public UUID getQueteId() {
        return queteId;
    }
}
