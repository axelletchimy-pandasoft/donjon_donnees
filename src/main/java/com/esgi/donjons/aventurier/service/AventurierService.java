package com.esgi.donjons.aventurier.service;

import com.esgi.donjons.aventurier.dto.AventurierDTO;

import java.util.List;

public interface AventurierService {
    List<AventurierDTO> getClassement(int limit);
}
