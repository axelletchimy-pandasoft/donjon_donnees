package dto;

/**
 * DTO utilisé pour la création d'une nouvelle quête.
 * Ne contient pas id, active ni createdAt (gérés automatiquement).
 */
public class QueteCreateDTO {

    private String titre;
    private String difficulte;
    private String biome;
    private int recompenseXp;
    private int recompenseOr;

    // ─── Constructeurs ────────────────────────────────────────────────────────

    public QueteCreateDTO() {}

    public QueteCreateDTO(String titre, String difficulte, String biome,
                          int recompenseXp, int recompenseOr) {
        this.titre = titre;
        this.difficulte = difficulte;
        this.biome = biome;
        this.recompenseXp = recompenseXp;
        this.recompenseOr = recompenseOr;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDifficulte() { return difficulte; }
    public void setDifficulte(String difficulte) { this.difficulte = difficulte; }

    public String getBiome() { return biome; }
    public void setBiome(String biome) { this.biome = biome; }

    public int getRecompenseXp() { return recompenseXp; }
    public void setRecompenseXp(int recompenseXp) { this.recompenseXp = recompenseXp; }

    public int getRecompenseOr() { return recompenseOr; }
    public void setRecompenseOr(int recompenseOr) { this.recompenseOr = recompenseOr; }

    @Override
    public String toString() {
        return "QueteCreateDTO{" +
                "titre='" + titre + '\'' +
                ", difficulte='" + difficulte + '\'' +
                ", biome='" + biome + '\'' +
                ", recompenseXp=" + recompenseXp +
                ", recompenseOr=" + recompenseOr +
                '}';
    }
}
