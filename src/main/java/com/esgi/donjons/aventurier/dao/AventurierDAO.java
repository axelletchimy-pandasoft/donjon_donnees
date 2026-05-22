package com.esgi.donjons.aventurier.dao;

import com.esgi.donjons.aventurier.model.Aventurier;

import java.util.List;

public interface AventurierDAO {
    List<Aventurier> findTopByXp(int limit);
}
