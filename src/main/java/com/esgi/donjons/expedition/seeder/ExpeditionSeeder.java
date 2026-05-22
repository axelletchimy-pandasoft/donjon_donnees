package com.esgi.donjons.expedition.seeder;

import com.esgi.donjons.expedition.service.ExpeditionService;

import java.util.UUID;
import java.util.logging.Logger;

public class ExpeditionSeeder {

    private static final Logger LOG = Logger.getLogger(ExpeditionSeeder.class.getName());

    private final ExpeditionService expeditionService;

    public ExpeditionSeeder(ExpeditionService expeditionService) {
        this.expeditionService = expeditionService;
    }

    public void seed() {
        LOG.info("--- Seed des expéditions ---");

        UUID aventurier1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID aventurier2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
        UUID aventurier3 = UUID.fromString("00000000-0000-0000-0000-000000000003");
        UUID quete1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID quete2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
        UUID quete3 = UUID.fromString("00000000-0000-0000-0000-000000000003");
        UUID quete4 = UUID.fromString("00000000-0000-0000-0000-000000000004");

        Object[][] tentatives = {
                {aventurier1, quete1},
                {aventurier2, quete2},
                {aventurier3, quete3},
                {aventurier1, quete4},
        };

        for (Object[] t : tentatives) {
            try {
                var dto = expeditionService.lancer((UUID) t[0], (UUID) t[1]);
                LOG.info("Expédition créée : aventurier=" + dto.getPseudo()
                        + ", quête=" + dto.getTitre()
                        + ", succès=" + dto.isSucces());
            } catch (Exception e) {
                LOG.warning("Expédition échouée (aventurier=" + t[0] + ", quête=" + t[1]
                        + ") : " + e.getMessage());
            }
        }

        LOG.info("--- Seed des expéditions terminé ---");
    }
}
