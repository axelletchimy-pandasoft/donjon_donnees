package com.esgi.donjons.quete.service;

import dao.QueteDAO;
import dto.QueteCreateDTO;
import dto.QueteResponseDTO;
import exception.QueteInvalidException;
import exception.QueteNotFoundException;
import model.Quete;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implémentation de la logique métier des quêtes.
 */
public class QueteServiceImpl implements QueteService {

    private final QueteDAO queteDAO;

    public QueteServiceImpl(QueteDAO queteDAO) {
        this.queteDAO = queteDAO;
    }

    // ─── Mapper DTO → Model et Model → DTO ───────────────────────────────────

    private Quete toModel(QueteCreateDTO dto) {
        Quete quete = new Quete();
        quete.setTitre(dto.getTitre());
        quete.setDifficulte(dto.getDifficulte());
        quete.setBiome(dto.getBiome());
        quete.setRecompenseXp(dto.getRecompenseXp());
        quete.setRecompenseOr(dto.getRecompenseOr());
        return quete;
    }

    private QueteResponseDTO toDTO(Quete quete) {
        return new QueteResponseDTO(
                quete.getId(),
                quete.getTitre(),
                quete.getDifficulte(),
                quete.getBiome(),
                quete.getRecompenseXp(),
                quete.getRecompenseOr(),
                quete.isActive(),
                quete.getCreatedAt()
        );
    }

    // ─── Validation métier ────────────────────────────────────────────────────

    private void valider(QueteCreateDTO dto) {
        if (dto.getTitre() == null || dto.getTitre().isBlank()) {
            throw new QueteInvalidException("Le titre de la quête est obligatoire.");
        }
        if (dto.getTitre().length() > 100) {
            throw new QueteInvalidException("Le titre ne peut pas dépasser 100 caractères.");
        }
        if (dto.getDifficulte() == null || dto.getDifficulte().isBlank()) {
            throw new QueteInvalidException("La difficulté est obligatoire.");
        }
        if (dto.getDifficulte().length() > 20) {
            throw new QueteInvalidException("La difficulté ne peut pas dépasser 20 caractères.");
        }
        if (dto.getBiome() == null || dto.getBiome().isBlank()) {
            throw new QueteInvalidException("Le biome est obligatoire.");
        }
        if (dto.getBiome().length() > 30) {
            throw new QueteInvalidException("Le biome ne peut pas dépasser 30 caractères.");
        }
        if (dto.getRecompenseXp() < 0) {
            throw new QueteInvalidException("La récompense XP ne peut pas être négative.");
        }
        if (dto.getRecompenseOr() < 0) {
            throw new QueteInvalidException("La récompense Or ne peut pas être négative.");
        }
    }

    // ─── Implémentation ───────────────────────────────────────────────────────

    @Override
    public QueteResponseDTO creerQuete(QueteCreateDTO dto) {
        valider(dto);
        Quete quete = toModel(dto);
        Quete sauvegarde = queteDAO.save(quete);
        return toDTO(sauvegarde);
    }

    @Override
    public QueteResponseDTO getQueteById(UUID id) {
        Quete quete = queteDAO.findById(id)
                .orElseThrow(() -> new QueteNotFoundException(id));
        return toDTO(quete);
    }

    @Override
    public List<QueteResponseDTO> getAllQuetes() {
        return queteDAO.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<QueteResponseDTO> getQuetesActives() {
        return queteDAO.findAllActive().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<QueteResponseDTO> getQuetesByDifficulte(String difficulte) {
        if (difficulte == null || difficulte.isBlank()) {
            throw new QueteInvalidException("La difficulté de filtre est obligatoire.");
        }
        return queteDAO.findByDifficulte(difficulte).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<QueteResponseDTO> getQuetesByBiome(String biome) {
        if (biome == null || biome.isBlank()) {
            throw new QueteInvalidException("Le biome de filtre est obligatoire.");
        }
        return queteDAO.findByBiome(biome).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QueteResponseDTO mettreAJourQuete(UUID id, QueteCreateDTO dto) {
        valider(dto);
        Quete existante = queteDAO.findById(id)
                .orElseThrow(() -> new QueteNotFoundException(id));
        existante.setTitre(dto.getTitre());
        existante.setDifficulte(dto.getDifficulte());
        existante.setBiome(dto.getBiome());
        existante.setRecompenseXp(dto.getRecompenseXp());
        existante.setRecompenseOr(dto.getRecompenseOr());
        Quete mise_a_jour = queteDAO.update(existante);
        return toDTO(mise_a_jour);
    }

    @Override
    public void desactiverQuete(UUID id) {
        Quete quete = queteDAO.findById(id)
                .orElseThrow(() -> new QueteNotFoundException(id));
        quete.setActive(false);
        queteDAO.update(quete);
    }

    @Override
    public void supprimerQuete(UUID id) {
        if (!queteDAO.existsById(id)) {
            throw new QueteNotFoundException(id);
        }
        queteDAO.deleteById(id);
    }
}
