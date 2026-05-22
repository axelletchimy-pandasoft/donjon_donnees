# Donjons & Donnees — depot starter

> TP Java OOP — ESGI Bachelor 2 — Trimestre 2, Cours 2
> Gestion de projet, integration multi-equipes, tests & CI/CD

Ce depot est **un point de depart vide** : aucune classe Java n'est fournie.
C'est **a vous, en equipe, de remplir la structure**. L'arborescence des
packages est deja en place pour vous indiquer **ou poser votre code** et
**quoi y mettre**.

Le but : une mini-application de **guilde d'aventuriers**. Des heros
(`aventuriers`) partent en `expeditions` sur des `quetes`, gagnent de l'XP et
de l'or, et grimpent au **classement de la guilde** affiche par *Le Grand
Registre de la Guilde* (votre futur `Main`).

---

## 1. Les 3 equipes = les 3 modules

| Equipe | Package a remplir | Table SQL | Sujet |
|--------|-------------------|-----------|-------|
| Equipe 1 | `com.esgi.donjons.aventurier` | `aventuriers` | Les heros (pseudo, classe, niveau, xp, or) |
| Equipe 2 | `com.esgi.donjons.quete`      | `quetes`      | Les quetes (difficulte, biome, recompenses) |
| Equipe 3 | `com.esgi.donjons.expedition` | `expeditions` | La liaison : un aventurier tente une quete |

Chaque equipe possede son package. Le module **Expedition** depend des deux
autres : il les appelle **via leurs interfaces** (voir plus bas).

Un `README.md` dans chaque package module vous donne la liste precise des
fichiers a creer.

---

## 2. Architecture en couches — ou mettre quoi

On reprend l'architecture du Cours 1 (**Model -> DTO -> DAO -> Service**).
Chaque module contient les memes sous-packages. Voici ce que chacun accueille :

| Sous-package | Ce que vous y mettez | Role |
|--------------|----------------------|------|
| `model`     | L'entite metier (ex: `Aventurier`) + ses enums | Donnees + validation **fail-fast** (constructeur). Ne connait pas la base. |
| `dto`       | `XxxDTO` (lecture) et `CreateXxxDTO` (ecriture) | Objets de transport entre couches. Pas de logique. |
| `dao`       | **l'interface `XxxDAO`** + son implementation `XxxDAOImpl` | Acces aux donnees. **Seul endroit qui ecrit du SQL.** |
| `service`   | **l'interface `XxxService`** + son implementation `XxxServiceImpl` | Logique metier (unicite, regles). Orchestre le DAO. **Jamais de SQL.** |
| `seeder`    | `XxxSeeder` | Remplit la base via le **Service** (donnees de demo). |
| `exception` | `XxxNotFoundException`, etc. | Erreurs metier claires. |

Packages partages (a la racine `com.esgi.donjons`) :

| Package | A creer | Role |
|---------|---------|------|
| `config`   | `DatabaseConnection` | Ouvre la connexion JDBC. **Lit la config dans les variables d'environnement — aucun secret en dur.** |
| `util`     | `Page<T>`, `AppLogger` | Pagination generique + logger. |
| `registry` | `GuildRegistry` | La vue d'integration : affiche le classement. |
| (racine)   | `Main` | Point d'entree : cable les couches et lance le Registre. |

### Le point cle : les INTERFACES sont le contrat entre equipes

Dans `dao/` et `service/`, vous creez **d'abord l'interface**, **puis**
l'implementation `...Impl`.

- L'**interface** (`AventurierService`, `QueteService`...) = la liste des
  methodes qu'une equipe **s'engage** a fournir. C'est ce que les autres
  equipes lisent et utilisent.
- Tant qu'une interface ne change pas, les 3 equipes avancent **en parallele**.
  Le module Expedition peut coder "contre le contrat" sans attendre que les
  autres aient fini leur implementation.
- Changer une interface = **prevenir les autres equipes** (role du Tech Lead).

---

## 3. La base de donnees (Docker)

La base PostgreSQL tourne **en local** : aucun reseau ESGI requis. Le schema
(`resources/init.sql`) est applique automatiquement au demarrage.

```bash
cd resources
docker compose up -d        # demarre PostgreSQL + cree les tables
docker compose ps           # verifie que la base tourne
docker compose down -v      # tout arreter et remettre a zero
```

Connexion par defaut : `localhost:5432/guilde`, utilisateur/mot de passe `donjons` / `donjons`.

Votre `DatabaseConnection` doit lire ces valeurs depuis l'environnement
(avec ces valeurs par defaut) :

| Variable | Defaut |
|----------|--------|
| `DB_URL`      | `jdbc:postgresql://localhost:5432/guilde` |
| `DB_USER`     | `donjons` |
| `DB_PASSWORD` | `donjons` |

---

## 4. Construire et lancer

```bash
mvn clean compile           # compiler
mvn test                    # lancer vos tests
mvn exec:java               # lancer le Grand Registre (une fois Main cree)
```

Le `pom.xml` fournit deja Java 21, le driver PostgreSQL et JUnit 5.

---

## 5. Intégration continue (a completer)

Le DevOps de chaque equipe complete `.github/workflows/ci.yml` :
chaque `# TODO` doit etre remplace (les indices sont entre crochets `[ ... ]`).
Objectif : a chaque push / pull request, la CI compile et teste le projet
contre une base PostgreSQL de test.

Bonnes pratiques attendues :
- Build + tests a chaque push **et** chaque pull request.
- Branche `main` **protegee** : on passe par une Pull Request relue.
- Une PR ne se fusionne que si **la CI est verte**.

---

## 6. Workflow Git en equipe

```bash
git checkout -b feature/ma-story    # une branche par user story
# ... code + tests ...
git push -u origin feature/ma-story
# puis ouvrir une Pull Request sur GitHub ; le Tech Lead relit ; on fusionne
```

**Definition of Done** (pour chaque story) : le code compile, les tests
passent, la revue de code est faite, la CI est verte.
