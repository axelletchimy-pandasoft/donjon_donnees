-- =====================================================================
--  Schéma de la base "guilde" — créé automatiquement par docker-compose.
--  3 tables = 3 modules = 3 équipes.
--  (Vos DAO écriront le SQL ; ce fichier crée seulement les tables.)
-- =====================================================================

CREATE TABLE IF NOT EXISTS aventuriers (
    id          UUID         DEFAULT gen_random_uuid() PRIMARY KEY,
    pseudo      VARCHAR(20)  NOT NULL UNIQUE,
    classe      VARCHAR(20)  NOT NULL,
    niveau      INT          NOT NULL DEFAULT 1,
    xp          INT          NOT NULL DEFAULT 0,
    or_total    INT          NOT NULL DEFAULT 0,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS quetes (
    id            UUID         DEFAULT gen_random_uuid() PRIMARY KEY,
    titre         VARCHAR(100) NOT NULL UNIQUE,
    difficulte    VARCHAR(20)  NOT NULL,
    biome         VARCHAR(30)  NOT NULL,
    recompense_xp INT          NOT NULL DEFAULT 0,
    recompense_or INT          NOT NULL DEFAULT 0,
    active        BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS expeditions (
    id              UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    aventurier_id   UUID      NOT NULL REFERENCES aventuriers(id) ON DELETE CASCADE,
    quete_id        UUID      NOT NULL REFERENCES quetes(id)      ON DELETE CASCADE,
    succes          BOOLEAN   NOT NULL,
    butin_or        INT       NOT NULL DEFAULT 0,
    xp_gagne        INT       NOT NULL DEFAULT 0,
    duree_secondes  INT       NOT NULL DEFAULT 0,
    date_expedition TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_aventuriers_xp         ON aventuriers (xp DESC);
CREATE INDEX IF NOT EXISTS idx_expeditions_aventurier ON expeditions (aventurier_id);
CREATE INDEX IF NOT EXISTS idx_expeditions_quete      ON expeditions (quete_id);
