package com.esgi.donjons.aventurier.service;

import com.esgi.donjons.aventurier.dto.AventurierDTO;
import java.util.UUID;

public interface AventurierService {

    AventurierDTO getById(UUID id);

    void recompenser(UUID id, int xp, int or);
}
