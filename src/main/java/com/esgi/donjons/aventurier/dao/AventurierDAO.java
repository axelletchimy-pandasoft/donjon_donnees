package com.esgi.donjons.aventurier.dao;

import com.esgi.donjons.aventurier.model.Aventurier;

import java.util.List;
import java.util.Optional;

public interface AventurierDAO {
    Aventurier save(Aventurier aventurier);

    Optional<Aventurier> findByPseudo(String pseudo);

    List<Aventurier> findTopByXp(int limit);
}
