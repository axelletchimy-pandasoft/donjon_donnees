# IHM web — Le Grand Registre de la Guilde

Un **tableau de bord web statique** (lecture seule) qui affiche le classement
des aventuriers, les quêtes actives et les dernières expéditions.

## Voir tout de suite (données d'exemple)
Ouvrez **`registre.html`** dans un navigateur (double-clic). La page est
**auto-suffisante** : les données sont embarquées dedans, aucun serveur requis.

## Rafraîchir avec les vraies données
Une fois les 3 modules implémentés et la base lancée :
```bash
cd resources && docker compose up -d && cd ..      # la base
mvn -q compile exec:java -Dexec.mainClass="com.esgi.donjons.web.RegistreExporter"
```
L'exporteur lit la base via les Services, régénère `registre.html`, puis
ré-ouvrez-le.

## Fichiers
- `registre.template.html` : le gabarit (placeholder `__GUILD_DATA__`).
- `registre.html` : la page prête à voir (régénérée par l'exporteur).
- `../src/main/java/com/esgi/donjons/web/RegistreExporter.java` : l'exporteur.

> Comme `Main`, l'exporteur dépend des Services des 3 modules : il se compile
> et tourne une fois les modules en place.
