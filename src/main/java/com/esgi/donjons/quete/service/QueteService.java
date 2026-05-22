package com.esgi.donjons.quete.service;

import dto.QueteCreateDTO;
import dto.QueteResponseDTO;

import java.util.List;
import java.util.UUID;

/**
 * Interface de service définissant la logique métier des quêtes.
 */
public interface QueteService {

    /**
     * Crée une nouvelle quête à partir des données du DTO.
     * @param dto les données de création
     * @return la quête créée sous forme de DTO de réponse
     */
    QueteResponseDTO creerQuete(QueteCreateDTO dto);

    /**
     * Retourne une quête par son identifiant.
     * @param id l'UUID de la quête
     * @return le DTO de réponse correspondant
     */
    QueteResponseDTO getQueteById(UUID id);

    /**
     * Retourne toutes les quêtes.
     * @return la liste de tous les DTOs de réponse
     */
    List<QueteResponseDTO> getAllQuetes();

    /**
     * Retourne uniquement les quêtes actives.
     * @return la liste des quêtes actives
     */
    List<QueteResponseDTO> getQuetesActives();

    /**
     * Retourne les quêtes d'une difficulté donnée.
     * @param difficulte le niveau de difficulté
     * @return la liste filtrée
     */
    List<QueteResponseDTO> getQuetesByDifficulte(String difficulte);

    /**
     * Retourne les quêtes d'un biome donné.
     * @param biome le biome ciblé
     * @return la liste filtrée
     */
    List<QueteResponseDTO> getQuetesByBiome(String biome);

    /**
     * Met à jour une quête existante.
     * @param id  l'UUID de la quête à modifier
     * @param dto les nouvelles données
     * @return la quête mise à jour sous forme de DTO
     */
    QueteResponseDTO mettreAJourQuete(UUID id, QueteCreateDTO dto);

    /**
     * Désactive (soft delete) une quête.
     * @param id l'UUID de la quête à désactiver
     */
    void desactiverQuete(UUID id);

    /**
     * Supprime définitivement une quête.
     * @param id l'UUID de la quête à supprimer
     */
    void supprimerQuete(UUID id);
}
