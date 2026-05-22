package com.esgi.donjons.registry;

import com.esgi.donjons.aventurier.dto.AventurierDTO;
import com.esgi.donjons.aventurier.service.AventurierService;
import com.esgi.donjons.expedition.dto.ExpeditionDTO;
import com.esgi.donjons.expedition.service.ExpeditionService;
import com.esgi.donjons.quete.dto.QueteDTO;
import com.esgi.donjons.quete.service.QueteService;

import java.util.List;

/**
 * "Le Grand Registre de la Guilde" — la vue d'integration finale.
 * Ne contient AUCUNE logique metier : il se contente d'appeler les 3
 * services et d'afficher. C'est la preuve visible que les 3 modules
 * s'assemblent et fonctionnent ensemble.
 */
public final class GuildRegistry {

    private final AventurierService aventurierService;
    private final QueteService      queteService;
    private final ExpeditionService expeditionService;

    public GuildRegistry(AventurierService aventurierService,
                         QueteService queteService,
                         ExpeditionService expeditionService) {
        this.aventurierService = aventurierService;
        this.queteService      = queteService;
        this.expeditionService = expeditionService;
    }

    public void afficher() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("============================================================\n");
        sb.append("        LE GRAND REGISTRE DE LA GUILDE  -  Donjons & Donnees \n");
        sb.append("============================================================\n");

        // --- Classement des aventuriers ---
        sb.append("\n  CLASSEMENT DES AVENTURIERS (top 10 par XP)\n");
        sb.append("  ----------------------------------------------------------\n");
        sb.append(String.format("  %-3s %-22s %-9s %-7s %-7s%n", "#", "PSEUDO", "CLASSE", "NIVEAU", "OR"));
        List<AventurierDTO> top = aventurierService.getClassement(10);
        int rang = 1;
        for (AventurierDTO a : top) {
            sb.append(String.format("  %-3d %-22s %-9s %-7d %-7d%n",
                    rang++, tronquer(a.getPseudo(), 22), a.getClasse(), a.getNiveau(), a.getOr()));
        }
        if (top.isEmpty()) sb.append("  (aucun aventurier — lancez le seeder)\n");

        // --- Quetes ---
        List<QueteDTO> actives = queteService.getActives();
        sb.append("\n  QUETES ACTIVES : ").append(actives.size()).append("\n");
        sb.append("  ----------------------------------------------------------\n");
        int max = Math.min(5, actives.size());
        for (int i = 0; i < max; i++) {
            QueteDTO q = actives.get(i);
            sb.append(String.format("   - %-40s [%s, %s] %d xp%n",
                    tronquer(q.getTitre(), 40), q.getDifficulte(), q.getBiome(), q.getRecompenseXp()));
        }

        // --- Dernieres expeditions ---
        sb.append("\n  DERNIERES EXPEDITIONS\n");
        sb.append("  ----------------------------------------------------------\n");
        List<ExpeditionDTO> dernieres = expeditionService.getDernieres(8);
        for (ExpeditionDTO e : dernieres) {
            sb.append(String.format("   %-3s %-18s -> %-32s (%d or)%n",
                    e.isSucces() ? "[V]" : "[X]",
                    tronquer(e.getAventurierPseudo(), 18),
                    tronquer(e.getQueteTitre(), 32),
                    e.getButinOr()));
        }
        if (dernieres.isEmpty()) sb.append("  (aucune expedition — lancez le seeder)\n");

        sb.append("\n============================================================\n");
        System.out.println(sb);
    }

    private static String tronquer(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 1) + "~";
    }
}
