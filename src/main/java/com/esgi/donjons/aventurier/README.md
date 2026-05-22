# Equipe 1 — Module Aventurier

**Sujet :** les heros de la guilde. Un aventurier a un `pseudo` unique, une
`classe`, un `niveau`, de l'`xp` et de l'`or`. Le niveau se recalcule a partir
de l'xp (1 niveau tous les 100 xp).

## Fichiers a creer (cochez au fur et a mesure)

- `model/`
  - [ ] `ClasseHeros.java` — enum : `GUERRIER, MAGE, VOLEUR, ARCHER, PALADIN, BARDE` (+ methode `from(String)`)
  - [ ] `Aventurier.java` — entite **immuable + fail-fast** (validation dans le constructeur) ; methode `rafraichirNiveau()`
- `dto/`
  - [ ] `AventurierDTO.java` — DTO de **lecture**
  - [ ] `CreateAventurierDTO.java` — DTO d'**ecriture** (pseudo, classe)
- `dao/`
  - [ ] **`AventurierDAO.java` — INTERFACE (le contrat, lu par les autres)**
  - [ ] `AventurierDAOImpl.java` — implementation **JDBC** (le seul endroit avec du SQL)
- `service/`
  - [ ] **`AventurierService.java` — INTERFACE**
  - [ ] `AventurierServiceImpl.java` — logique metier (recoit le DAO par le constructeur)
- `seeder/`
  - [ ] `AventurierSeeder.java` — remplit la guilde **via le Service**
- `exception/`
  - [ ] `AventurierNotFoundException.java`, `AventurierAlreadyExistsException.java`

## Methodes attendues (a definir dans l'interface)

DAO : `save`, `findById`, `findByPseudo`, `findAll(page, size)`, `findTopByXp(limit)`,
`gagnerRecompense(id, xp, or)`, `count`, `deleteById`.
Service : `recruter`, `getById`, `getClassement(limit)`, `recompenser(id, xp, or)`, `printStats`.

> Rappel : `recruter` doit refuser un pseudo deja pris. `recompenser` met a jour
> xp + or puis recalcule le niveau. Le DAO renvoie `Optional<T>` / `Page<T>`.
