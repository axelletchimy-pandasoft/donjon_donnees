package dao;

import model.Quete;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface DAO définissant les opérations CRUD pour les quêtes.
 */
public interface QueteDAO {

    /**
     * Insère une nouvelle quête en base de données.
     * @param quete la quête à persister
     * @return la quête persistée
     */
    Quete save(Quete quete);

    /**
     * Recherche une quête par son identifiant UUID.
     * @param id l'UUID de la quête
     * @return un Optional contenant la quête si elle existe
     */
    Optional<Quete> findById(UUID id);

    /**
     * Retourne toutes les quêtes.
     * @return la liste de toutes les quêtes
     */
    List<Quete> findAll();

    /**
     * Retourne uniquement les quêtes actives.
     * @return la liste des quêtes actives
     */
    List<Quete> findAllActive();

    /**
     * Retourne les quêtes filtrées par difficulté.
     * @param difficulte le niveau de difficulté
     * @return la liste correspondante
     */
    List<Quete> findByDifficulte(String difficulte);

    /**
     * Retourne les quêtes filtrées par biome.
     * @param biome le biome ciblé
     * @return la liste correspondante
     */
    List<Quete> findByBiome(String biome);

    /**
     * Met à jour une quête existante.
     * @param quete la quête avec les nouvelles valeurs
     * @return la quête mise à jour
     */
    Quete update(Quete quete);

    /**
     * Supprime une quête par son identifiant.
     * @param id l'UUID de la quête à supprimer
     */
    void deleteById(UUID id);

    /**
     * Vérifie si une quête existe pour un identifiant donné.
     * @param id l'UUID à vérifier
     * @return true si la quête existe
     */
    boolean existsById(UUID id);
}
