package com.esgi.donjons.quete.seeder;

import dao.QueteDAO;
import model.Quete;

import java.util.List;

/**
 * Seeder pour peupler la table quête avec des données de test.
 * À utiliser uniquement en développement.
 */
public class QueteSeeder {

    private final QueteDAO queteDAO;

    public QueteSeeder(QueteDAO queteDAO) {
        this.queteDAO = queteDAO;
    }

    /**
     * Insère un jeu de données de démonstration en base.
     */
    public void seed() {
        List<Quete> quetes = List.of(

            // ── Forêt ────────────────────────────────────────────────────────
            new QueteBuilder()
                .titre("La Forêt Maudite")
                .difficulte("FACILE")
                .biome("FORET")
                .recompenseXp(100)
                .recompenseOr(50)
                .build(),

            new QueteBuilder()
                .titre("Les Racines de l'Ombre")
                .difficulte("MOYEN")
                .biome("FORET")
                .recompenseXp(250)
                .recompenseOr(120)
                .build(),

            // ── Désert ───────────────────────────────────────────────────────
            new QueteBuilder()
                .titre("La Tempête de Sable")
                .difficulte("DIFFICILE")
                .biome("DESERT")
                .recompenseXp(500)
                .recompenseOr(300)
                .build(),

            new QueteBuilder()
                .titre("L'Oasis Perdue")
                .difficulte("FACILE")
                .biome("DESERT")
                .recompenseXp(80)
                .recompenseOr(40)
                .build(),

            // ── Montagne ─────────────────────────────────────────────────────
            new QueteBuilder()
                .titre("Le Sommet Glacé")
                .difficulte("LEGENDAIRE")
                .biome("MONTAGNE")
                .recompenseXp(1000)
                .recompenseOr(750)
                .build(),

            new QueteBuilder()
                .titre("La Mine Effondrée")
                .difficulte("MOYEN")
                .biome("MONTAGNE")
                .recompenseXp(300)
                .recompenseOr(150)
                .build(),

            // ── Marais ───────────────────────────────────────────────────────
            new QueteBuilder()
                .titre("Les Brumes du Marais")
                .difficulte("DIFFICILE")
                .biome("MARAIS")
                .recompenseXp(450)
                .recompenseOr(200)
                .build(),

            // ── Donjon ───────────────────────────────────────────────────────
            new QueteBuilder()
                .titre("Le Donjon des Âmes")
                .difficulte("LEGENDAIRE")
                .biome("DONJON")
                .recompenseXp(1500)
                .recompenseOr(1000)
                .build()
        );

        int count = 0;
        for (Quete quete : quetes) {
            try {
                queteDAO.save(quete);
                count++;
                System.out.println("[Seeder] Quête insérée : " + quete.getTitre());
            } catch (Exception e) {
                System.err.println("[Seeder] Erreur pour : " + quete.getTitre() + " → " + e.getMessage());
            }
        }
        System.out.println("[Seeder] Terminé : " + count + "/" + quetes.size() + " quête(s) insérée(s).");
    }

    // ─── Builder interne pour la lisibilité du seeder ─────────────────────────

    private static class QueteBuilder {
        private String titre;
        private String difficulte;
        private String biome;
        private int recompenseXp = 0;
        private int recompenseOr = 0;

        public QueteBuilder titre(String titre) { this.titre = titre; return this; }
        public QueteBuilder difficulte(String difficulte) { this.difficulte = difficulte; return this; }
        public QueteBuilder biome(String biome) { this.biome = biome; return this; }
        public QueteBuilder recompenseXp(int xp) { this.recompenseXp = xp; return this; }
        public QueteBuilder recompenseOr(int or) { this.recompenseOr = or; return this; }

        public Quete build() {
            Quete q = new Quete(titre, difficulte, biome);
            q.setRecompenseXp(recompenseXp);
            q.setRecompenseOr(recompenseOr);
            return q;
        }
    }
}
