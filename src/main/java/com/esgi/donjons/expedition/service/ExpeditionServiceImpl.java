package com.esgi.donjons.expedition.service;

import com.esgi.donjons.aventurier.dto.AventurierDTO;
import com.esgi.donjons.aventurier.service.AventurierService;
import com.esgi.donjons.expedition.dao.ExpeditionDAO;
import com.esgi.donjons.expedition.dto.ExpeditionDTO;
import com.esgi.donjons.expedition.exception.ExpeditionNotFoundException;
import com.esgi.donjons.expedition.exception.InvalidExpeditionException;
import com.esgi.donjons.expedition.model.Expedition;
import com.esgi.donjons.quete.dto.QueteDTO;
import com.esgi.donjons.quete.service.QueteService;

import java.util.Random;
import java.util.UUID;

public class ExpeditionServiceImpl implements ExpeditionService {

    private final ExpeditionDAO expeditionDAO;
    private final AventurierService aventurierService;
    private final QueteService queteService;
    private final Random random;

    public ExpeditionServiceImpl(ExpeditionDAO expeditionDAO,
                                 AventurierService aventurierService,
                                 QueteService queteService) {
        this.expeditionDAO = expeditionDAO;
        this.aventurierService = aventurierService;
        this.queteService = queteService;
        this.random = new Random();
    }

    @Override
    public ExpeditionDTO lancer(UUID aventurierId, UUID queteId) {
        AventurierDTO aventurier = aventurierService.getById(aventurierId);
        if (aventurier == null) {
            throw new InvalidExpeditionException(
                    "Impossible de lancer l'expédition : aventurier " + aventurierId + " introuvable");
        }

        QueteDTO quete = queteService.getById(queteId);
        if (quete == null) {
            throw new InvalidExpeditionException(
                    "Impossible de lancer l'expédition : quête " + queteId + " introuvable");
        }
        if (!quete.isActive()) {
            throw new InvalidExpeditionException(
                    "Impossible de lancer l'expédition : la quête " + queteId + " est archivée");
        }

        int niveau = aventurier.getNiveau();
        int multiplicateur = quete.getDifficulte().getMultiplicateur();
        double probabiliteSucces = (double) niveau / (multiplicateur * 10.0);
        boolean succes = random.nextDouble() < probabiliteSucces;

        int xpGagne = succes ? quete.getXp() : quete.getXp() / 2;
        int butinOr = succes ? quete.getOr() : 0;

        long dureeSecondes = multiplicateur * 30L + random.nextLong(60);

        Expedition expedition = new Expedition(
                aventurierId, queteId, succes, butinOr, xpGagne, dureeSecondes);
        Expedition saved = expeditionDAO.save(expedition);

        aventurierService.recompenser(aventurierId, xpGagne, butinOr);

        return expeditionDAO.findById(saved.getId())
                .orElseThrow(() -> new ExpeditionNotFoundException(saved.getId()));
    }
}
