package com.esgi.donjons;

import com.esgi.donjons.aventurier.dao.AventurierDAO;
import com.esgi.donjons.aventurier.dao.AventurierDAOImpl;
import com.esgi.donjons.aventurier.seeder.AventurierSeeder;
import com.esgi.donjons.aventurier.service.AventurierService;
import com.esgi.donjons.aventurier.service.AventurierServiceImpl;
import com.esgi.donjons.expedition.dao.ExpeditionDAO;
import com.esgi.donjons.expedition.dao.ExpeditionDAOImpl;
import com.esgi.donjons.expedition.seeder.ExpeditionSeeder;
import com.esgi.donjons.expedition.service.ExpeditionService;
import com.esgi.donjons.expedition.service.ExpeditionServiceImpl;
import com.esgi.donjons.quete.dao.QueteDAO;
import com.esgi.donjons.quete.dao.QueteDAOImpl;
import com.esgi.donjons.quete.seeder.QueteSeeder;
import com.esgi.donjons.quete.service.QueteService;
import com.esgi.donjons.quete.service.QueteServiceImpl;
import com.esgi.donjons.registry.GuildRegistry;
import com.esgi.donjons.util.AppLogger;

import java.util.logging.Logger;

/**
 * Point d'entree. C'est ici qu'on "cable" les couches entre elles
 * (injection de dependances a la main) — comme le faisait EpiScore.
 *
 * Usage :
 *   java -jar donjons-et-donnees.jar           (affiche le registre, seed si base vide)
 *   java -jar donjons-et-donnees.jar --seed     (force un seed avant affichage)
 */
public class Main {

    private static final Logger log = AppLogger.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("=== Donjons & Donnees demarre ===");

        // --- Cablage des 3 modules ---
        AventurierDAO     avDao     = new AventurierDAOImpl();
        AventurierService avService = new AventurierServiceImpl(avDao);

        QueteDAO          qDao      = new QueteDAOImpl();
        QueteService      qService  = new QueteServiceImpl(qDao);

        ExpeditionDAO     eDao      = new ExpeditionDAOImpl();
        ExpeditionService eService  = new ExpeditionServiceImpl(eDao, avService, qService);

        boolean forceSeed = false;
        for (String arg : args) {
            if ("--seed".equalsIgnoreCase(arg)) forceSeed = true;
        }

        try {
            boolean baseVide = avService.getAll(0, 1).getTotalElements() == 0;
            if (forceSeed || baseVide) {
                log.info("Remplissage de la guilde (seeders)...");
                new AventurierSeeder(avService).seed();
                new QueteSeeder(qService).seed();
                new ExpeditionSeeder(eService, avService, qService).seed();
            }

            new GuildRegistry(avService, qService, eService).afficher();
            log.info("=== Termine ===");

        } catch (RuntimeException e) {
            System.err.println();
            System.err.println("!! Impossible de joindre la base de donnees.");
            System.err.println("   Verifiez qu'elle tourne :  docker compose up -d");
            System.err.println("   Detail technique : " + e.getMessage());
            System.exit(1);
        }
    }
}
