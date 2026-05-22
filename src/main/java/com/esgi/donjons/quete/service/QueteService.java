package com.esgi.donjons.quete.service;

import com.esgi.donjons.quete.dto.QueteDTO;
import java.util.UUID;

public interface QueteService {

    QueteDTO getById(UUID id);
}
