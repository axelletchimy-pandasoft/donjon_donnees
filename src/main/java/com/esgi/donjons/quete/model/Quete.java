import java.time.LocalDateTime;
import java.util.UUID;

public class Quete {

    private UUID id;
    private String titre;
    private String difficulte;
    private String biome;
    private int recompenseXp;
    private int recompenseOr;
    private boolean active;
    private LocalDateTime createdAt;

    // ─── Constructeurs ────────────────────────────────────────────────────────

    public Quete() {
        this.id = UUID.randomUUID();
        this.recompenseXp = 0;
        this.recompenseOr = 0;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    public Quete(String titre, String difficulte, String biome) {
        this();
        this.titre = titre;
        this.difficulte = difficulte;
        this.biome = biome;
    }

    public Quete(UUID id, String titre, String difficulte, String biome,
                 int recompenseXp, int recompenseOr, boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.titre = titre;
        this.difficulte = difficulte;
        this.biome = biome;
        this.recompenseXp = recompenseXp;
        this.recompenseOr = recompenseOr;
        this.active = active;
        this.createdAt = createdAt;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    public String getBiome() {
        return biome;
    }

    public void setBiome(String biome) {
        this.biome = biome;
    }

    public int getRecompenseXp() {
        return recompenseXp;
    }

    public void setRecompenseXp(int recompenseXp) {
        this.recompenseXp = recompenseXp;
    }

    public int getRecompenseOr() {
        return recompenseOr;
    }

    public void setRecompenseOr(int recompenseOr) {
        this.recompenseOr = recompenseOr;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ─── toString ─────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Quete{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", difficulte='" + difficulte + '\'' +
                ", biome='" + biome + '\'' +
                ", recompenseXp=" + recompenseXp +
                ", recompenseOr=" + recompenseOr +
                ", active=" + active +
                ", createdAt=" + createdAt +
                '}';
    }
}
