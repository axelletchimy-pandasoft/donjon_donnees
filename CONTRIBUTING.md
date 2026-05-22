# Contribuer — règles de branches, commits et merge

## Règle d'or
- On ne pousse **jamais** directement sur `main`.
- `main` doit **toujours compiler et passer les tests**.
- Tout passe par : une **branche** → une **Pull Request** relue → un **merge**.

---

## 1. Choisir une issue
Sur le board : prends une issue, **assigne-toi** dessus (*Assignees*) et passe-la
en **In Progress**. Une personne = une issue à la fois.

## 2. Créer ta branche (depuis `main` à jour)

**Convention de nommage :**
```
<type>/<numéro-issue>-<description-courte>
```
- `type` : `feat` (story), `fix` (bug), `test`, `chore` (technique), `devops`, `docs`
- minuscules, **sans accent ni espace**, mots séparés par des tirets, court (3–5 mots)
- **toujours** le numéro de l'issue

**Exemples :**
| Issue | Branche |
|-------|---------|
| #7 `[Aventurier] Recruter un aventurier` | `feat/7-recruter-aventurier` |
| #12 `[Quête] Archiver une quête` | `feat/12-archiver-quete` |
| #20 bug de tri du classement | `fix/20-tri-classement` |

**Commandes :**
```bash
git switch main          # se placer sur main
git pull                 # récupérer la dernière version
git switch -c feat/7-recruter-aventurier   # créer + basculer sur la branche
```
> Alternative : sur la page de l'issue (GitHub), bouton **Create a branch** —
> il crée et nomme la branche automatiquement (ex. `7-recruter-un-aventurier`).

## 3. Coder et committer
Des commits **petits et clairs**. Message : `<type>: <ce que ça fait>` + référence à l'issue.
```bash
git add -A
git commit -m "feat: recruter un aventurier (refs #7)"
git push -u origin feat/7-recruter-aventurier
```

## 4. Ouvrir la Pull Request
- **base** : `main`  ←  **compare** : ta branche
- Dans la **description**, écris `Closes #7` : l'issue se fermera et la carte du
  board passera en **Done** automatiquement au merge.
- Demande une **revue** au **Tech Lead** de ton équipe.

## 5. Fusionner (merge) — quand et comment
Une PR n'est fusionnable **que si** :
- [ ] la **CI est verte** (build + tests),
- [ ] au moins **1 review approuvée** (Tech Lead),
- [ ] les conversations sont **résolues**.

Méthode de merge : **« Squash and merge »** (un seul commit propre par story).
Après le merge : **supprime la branche** (bouton *Delete branch*).

## 6. Garder ta branche à jour (si elle traîne dans le temps)
Pour éviter les gros conflits, récupère régulièrement `main` :
```bash
git switch main && git pull
git switch feat/7-recruter-aventurier
git merge main          # (ou: git rebase main)
# résous les conflits, puis : git add -A && git commit && git push
```

---

## Pourquoi `main` est protégée
Le dépôt **interdit le push direct sur `main`** : il faut passer par une PR,
avec CI verte et revue. C'est volontaire — c'est ce qui garantit que `main`
reste toujours fonctionnel et que personne n'écrase le travail des autres.
