package com.esgi.donjons.expedition.service;

import com.esgi.donjons.expedition.dto.ExpeditionDTO;
import java.util.UUID;

public interface ExpeditionService {

    ExpeditionDTO lancer(UUID aventurierId, UUID queteId);
}
