# Equipe 2 — Module Quete

**Sujet :** les quetes proposees par la guilde. Une quete a un `titre` unique,
une `difficulte`, un `biome`, des recompenses (`xp`, `or`) et un drapeau
`active` (on n'efface pas une quete : on la **desactive** = soft-delete).

## Fichiers a creer

- `model/`
  - [ ] `Difficulte.java` — enum : `FACILE, NORMALE, DIFFICILE, CAUCHEMAR, LEGENDAIRE` (avec un multiplicateur)
  - [ ] `Biome.java` — enum : `FORET, DONJON, MONTAGNE, MARAIS, DESERT, CITE, ABYSSES`
  - [ ] `Quete.java` — entite **immuable + fail-fast** ; methode `desactiver()`
- `dto/`
  - [ ] `QueteDTO.java` (lecture) · [ ] `CreateQueteDTO.java` (ecriture)
- `dao/`
  - [ ] **`QueteDAO.java` — INTERFACE (le contrat)**
  - [ ] `QueteDAOImpl.java` — implementation JDBC
- `service/`
  - [ ] **`QueteService.java` — INTERFACE**
  - [ ] `QueteServiceImpl.java`
- `seeder/`
  - [ ] `QueteSeeder.java`
- `exception/`
  - [ ] `QueteNotFoundException.java`, `QueteAlreadyExistsException.java`

## Methodes attendues

DAO : `save`, `findById`, `findByTitre`, `findAll(page, size)`, `findActives`,
`desactiver(id)`, `count`.
Service : `publier`, `getById`, `getActives`, `archiver(id)`, `printStats`.

> Rappel : `publier` refuse un titre deja pris. `archiver` fait un soft-delete
> (`active = false`), pas un DELETE.
