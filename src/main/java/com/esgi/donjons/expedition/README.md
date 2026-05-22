# Equipe 3 — Module Expedition  (le point d'integration)

**Sujet :** une expedition = un aventurier qui tente une quete. C'est la
**table de liaison** entre les deux autres modules (cles etrangeres
`aventurier_id` et `quete_id`). C'est ICI que les 3 modules se rencontrent.

## Fichiers a creer

- `model/`
  - [ ] `Expedition.java` — entite fail-fast (aventurierId, queteId, succes, butinOr, xpGagne, dureeSecondes, date)
- `dto/`
  - [ ] `ExpeditionDTO.java` — DTO de lecture **enrichi** (contient le `pseudo` de l'aventurier et le `titre` de la quete, via une **jointure SQL**)
  - [ ] `CreateExpeditionDTO.java` — DTO d'ecriture
- `dao/`
  - [ ] **`ExpeditionDAO.java` — INTERFACE (le contrat)**
  - [ ] `ExpeditionDAOImpl.java` — implementation JDBC avec **JOIN** sur aventuriers + quetes
- `service/`
  - [ ] **`ExpeditionService.java` — INTERFACE**
  - [ ] `ExpeditionServiceImpl.java` — voir la regle d'integration ci-dessous
- `seeder/`
  - [ ] `ExpeditionSeeder.java` — pioche aventuriers + quetes existants (a lancer APRES les 2 autres seeders)
- `exception/`
  - [ ] `ExpeditionNotFoundException.java`, `InvalidExpeditionException.java`

## Regle d'integration (le coeur du TP)

Le `ExpeditionServiceImpl` recoit dans son constructeur **les interfaces**
`AventurierService` et `QueteService` (jamais leurs implementations). La methode
`lancer(aventurierId, queteId)` doit :

1. verifier que l'aventurier **et** la quete existent (via les autres Services) ;
2. refuser si la quete est archivee (`InvalidExpeditionException`) ;
3. resoudre l'issue (succes/echec) selon la difficulte ;
4. persister l'expedition (DAO) ;
5. **recompenser l'aventurier** en appelant `aventurierService.recompenser(...)`.

> C'est l'etape 5 qui prouve que les 3 modules fonctionnent ensemble.
