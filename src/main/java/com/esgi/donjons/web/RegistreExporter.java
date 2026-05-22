package com.esgi.donjons.web;

import com.esgi.donjons.aventurier.dao.AventurierDAOImpl;
import com.esgi.donjons.aventurier.dto.AventurierDTO;
import com.esgi.donjons.aventurier.seeder.AventurierSeeder;
import com.esgi.donjons.aventurier.service.AventurierService;
import com.esgi.donjons.aventurier.service.AventurierServiceImpl;
import com.esgi.donjons.expedition.dao.ExpeditionDAOImpl;
import com.esgi.donjons.expedition.dto.ExpeditionDTO;
import com.esgi.donjons.expedition.seeder.ExpeditionSeeder;
import com.esgi.donjons.expedition.service.ExpeditionService;
import com.esgi.donjons.expedition.service.ExpeditionServiceImpl;
import com.esgi.donjons.quete.dao.QueteDAOImpl;
import com.esgi.donjons.quete.dto.QueteDTO;
import com.esgi.donjons.quete.seeder.QueteSeeder;
import com.esgi.donjons.quete.service.QueteService;
import com.esgi.donjons.quete.service.QueteServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Exporte l'etat de la guilde dans une page web statique auto-suffisante.
 *
 * Il lit les donnees via les SERVICES (comme Main), construit un JSON a la main,
 * l'injecte dans web/registre.template.html, et ecrit web/registre.html.
 * La page resultante embarque ses donnees : on l'ouvre par double-clic, sans
 * serveur ni reseau. Relancer cet exporteur pour rafraichir.
 *
 * Lancer :  mvn -q compile exec:java -Dexec.mainClass="com.esgi.donjons.web.RegistreExporter"
 */
public class RegistreExporter {

    private static final Path TEMPLATE = Path.of("web/registre.template.html");
    private static final Path SORTIE   = Path.of("web/registre.html");

    public static void main(String[] args) throws Exception {
        AventurierService avService = new AventurierServiceImpl(new AventurierDAOImpl());
        QueteService      qService  = new QueteServiceImpl(new QueteDAOImpl());
        ExpeditionService eService  = new ExpeditionServiceImpl(new ExpeditionDAOImpl(), avService, qService);

        try {
            // Remplit la base si elle est vide, pour une demo immediate.
            if (avService.getAll(0, 1).getTotalElements() == 0) {
                new AventurierSeeder(avService).seed();
                new QueteSeeder(qService).seed();
                new ExpeditionSeeder(eService, avService, qService).seed();
            }

            List<AventurierDTO> tous   = avService.getAll(0, 100000).getContent();
            List<AventurierDTO> top    = avService.getClassement(10);
            List<QueteDTO>      quetes = qService.getActives();
            List<ExpeditionDTO> dern   = eService.getDernieres(10);
            long nbExpeditions = eService.getAll(0, 1).getTotalElements();
            long orTotal = tous.stream().mapToLong(AventurierDTO::getOr).sum();

            String json = construireJson(tous.size(), quetes.size(), nbExpeditions, orTotal, top, quetes, dern);

            String template = Files.readString(TEMPLATE);
            Files.writeString(SORTIE, template.replace("__GUILD_DATA__", json));
            System.out.println("OK -> " + SORTIE.toAbsolutePath() + " (ouvrez-le dans un navigateur)");

        } catch (RuntimeException e) {
            System.err.println("Impossible de joindre la base. Lancez 'docker compose up -d' (dossier resources).");
            System.err.println("Detail : " + e.getMessage());
            System.exit(1);
        }
    }

    private static String construireJson(long nbAv, int nbQuetes, long nbExp, long orTotal,
                                         List<AventurierDTO> top, List<QueteDTO> quetes, List<ExpeditionDTO> dern) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        StringBuilder cl = new StringBuilder("[");
        int rang = 1;
        for (AventurierDTO a : top) {
            if (rang > 1) cl.append(",");
            cl.append("{\"rang\":").append(rang)
              .append(",\"pseudo\":").append(str(a.getPseudo()))
              .append(",\"classe\":").append(str(a.getClasse().name()))
              .append(",\"niveau\":").append(a.getNiveau())
              .append(",\"xp\":").append(a.getXp())
              .append(",\"or\":").append(a.getOr()).append("}");
            rang++;
        }
        cl.append("]");

        StringBuilder qa = new StringBuilder("[");
        for (int i = 0; i < quetes.size(); i++) {
            QueteDTO q = quetes.get(i);
            if (i > 0) qa.append(",");
            qa.append("{\"titre\":").append(str(q.getTitre()))
              .append(",\"difficulte\":").append(str(q.getDifficulte().name()))
              .append(",\"biome\":").append(str(q.getBiome().name()))
              .append(",\"recompenseXp\":").append(q.getRecompenseXp())
              .append(",\"recompenseOr\":").append(q.getRecompenseOr()).append("}");
        }
        qa.append("]");

        StringBuilder ex = new StringBuilder("[");
        for (int i = 0; i < dern.size(); i++) {
            ExpeditionDTO e = dern.get(i);
            if (i > 0) ex.append(",");
            ex.append("{\"succes\":").append(e.isSucces())
              .append(",\"aventurierPseudo\":").append(str(e.getAventurierPseudo()))
              .append(",\"queteTitre\":").append(str(e.getQueteTitre()))
              .append(",\"butinOr\":").append(e.getButinOr())
              .append(",\"xpGagne\":").append(e.getXpGagne()).append("}");
        }
        ex.append("]");

        return "{\"genereLe\":" + str(date)
             + ",\"stats\":{\"aventuriers\":" + nbAv + ",\"quetesActives\":" + nbQuetes
             + ",\"expeditions\":" + nbExp + ",\"orDistribue\":" + orTotal + "}"
             + ",\"classement\":" + cl
             + ",\"quetes\":" + qa
             + ",\"expeditions\":" + ex + "}";
    }

    private static String str(String v) {
        if (v == null) return "\"\"";
        String e = v.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", " ").replace("\r", " ");
        return "\"" + e + "\"";
    }
}
