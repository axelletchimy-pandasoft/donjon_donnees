package com.esgi.donjons.aventurier.service;

import com.esgi.donjons.aventurier.dto.AventurierDTO;
import com.esgi.donjons.aventurier.dto.CreateAventurierDTO;

import java.util.List;

public interface AventurierService {
    AventurierDTO recruter(CreateAventurierDTO createAventurierDTO);

    List<AventurierDTO> getClassement(int limit);
}
