package com.esgi.donjons.expedition.dao;

import com.esgi.donjons.expedition.dto.ExpeditionDTO;
import com.esgi.donjons.expedition.model.Expedition;

import java.util.Optional;
import java.util.UUID;

public interface ExpeditionDAO {

    Expedition save(Expedition expedition);

    Optional<ExpeditionDTO> findById(UUID id);
}
