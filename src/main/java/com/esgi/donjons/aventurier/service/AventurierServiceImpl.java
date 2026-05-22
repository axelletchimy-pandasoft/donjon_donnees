package com.esgi.donjons.aventurier.service;

import com.esgi.donjons.aventurier.dao.AventurierDAO;
import com.esgi.donjons.aventurier.dto.AventurierDTO;
import com.esgi.donjons.aventurier.model.Aventurier;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class AventurierServiceImpl implements AventurierService {
    private final AventurierDAO aventurierDAO;

    public AventurierServiceImpl(AventurierDAO aventurierDAO) {
        this.aventurierDAO = Objects.requireNonNull(aventurierDAO, "Le DAO aventurier est obligatoire");
    }

    @Override
    public List<AventurierDTO> getClassement(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("La limite du classement ne peut pas etre negative");
        }
        if (limit == 0) {
            return List.of();
        }

        return aventurierDAO.findTopByXp(limit).stream()
                .sorted(Comparator.comparingInt(Aventurier::xp).reversed())
                .limit(limit)
                .map(this::toDTO)
                .toList();
    }

    private AventurierDTO toDTO(Aventurier aventurier) {
        return new AventurierDTO(
                aventurier.id(),
                aventurier.pseudo(),
                aventurier.classe(),
                aventurier.niveau(),
                aventurier.xp(),
                aventurier.orTotal()
        );
    }
}
