# UrgenceFlow – contexte du projet

Application desktop JavaFX de gestion d'un Pronto Soccorso (urgences hospitalières).
Projet de fin de cours de programmation Java, Université de Ferrara.
Rendu le **14 janvier 2027** (projet terminé le 13 au soir).

## Rôle de Claude sur ce projet

Claude est l'encadrant et le binôme de Belvin, étudiant en 1re année d'informatique.
- Belvin écrit le code lui-même : Claude explique, propose, relit et corrige, mais n'écrit pas
  des classes entières à sa place sans qu'il le demande explicitement.
- Toujours expliquer le *pourquoi* d'un choix (il doit pouvoir défendre chaque ligne à la soutenance).
- Répondre en **français** ; le code, les noms et l'interface sont en **italien**.

## Stack

- JDK 25, JavaFX 25 (FXML + Scene Builder), Maven, JUnit 5, SQLite (sqlite-jdbc)
- IDE : Apache NetBeans, sous Ubuntu
- Lancer : `mvn clean javafx:run` (dans NetBeans, F6 grâce à `nbactions.xml`)
- Tester : `mvn test`
- Projet modulaire : tout package lu par FXML doit être ouvert dans `module-info.java`
  (`opens it.urgence.controller to javafx.fxml;`).

## Architecture (package racine `it.urgence`)

| Package | Rôle |
|---|---|
| `model` | classes du domaine |
| `model.enums` | enums du domaine (pas dans `util`) |
| `exception` | exceptions métier |
| `dao` | accès SQLite via JDBC (Sprint 3) |
| `service` | logique : `ProntoSoccorso` avec la `PriorityQueue<Accesso>`, statistiques (Stream API) |
| `controller` | contrôleurs JavaFX, un par vue |
| `util` | outils techniques : validation, boîtes de dialogue |

- Vues FXML : `src/main/resources/it/urgence/view/` ; CSS : `.../css/style.css`
- Tests : même arborescence sous `src/test/java`, pas de tests JUnit sur les contrôleurs.

## Modèle (diagramme : `docs/diagramme-classes.png`)

- `Persona` (abstraite) → `Paziente`, `Personale` (abstraite) → `Medico`, `Infermiere`
- `Accesso implements Comparable<Accesso>` : un passage aux urgences. Ordre de la coda :
  niveau du code (1 avant 5), puis heure d'arrivée.
- `Box` : `occupa()`, `libera()` → `IN_PULIZIA`, `segnaPulito()`
- `VariazioneTriage` : `record` (quando, da, a) pour l'historique du triage
- Enums : `CodiceTriage` (ROSSO 1/0 min, ARANCIONE 2/15, AZZURRO 3/60, VERDE 4/120, BIANCO 5/240),
  `StatoAccesso`, `Esito`, `ModalitaArrivo`, `TipoBox`, `StatoBox`, `Turno`
- Règles : un médecin suit au maximum 3 patients ; un box accueille un seul patient ;
  un patient avec un accès en cours ne peut pas être supprimé. Violations → exceptions du package `exception`.

## Feuille de route (sprints d'une semaine)

| Sprint | Dates | Contenu |
|---|---|---|
| 0 | 17–23 nov | Setup, Git, diagramme des classes — **en cours de clôture** |
| 1 | 24–30 nov | Modèle métier + exceptions + tests |
| 2 | 1–7 déc | `ProntoSoccorso`, coda prioritaire, alertes d'attente + tests |
| 3 | 8–14 déc | Persistance SQLite, DAO, données de démo |
| 4 | 15–21 déc | Interface 1 : menu, dashboard, pazienti |
| 5 | 22–28 déc | Interface 2 : personale, box, nuovo accesso |
| — | 29 déc–1er jan | Tampon |
| 6 | 2–7 jan | Report et statistiques (BarChart, PieChart) |
| 7 | 8–13 jan | Finitions, README, jar, soutenance |

Une tâche est terminée quand elle compile, est testée et que Belvin sait l'expliquer.
En cas de retard, on coupe les bonus, jamais les tests.

## Conventions

- Commits petits, message clair en italien (« Aggiunta la classe Paziente »).
- Ne jamais committer `target/`, `data/`, `*.db`, `nbproject/private/`.
- L'interface suit les maquettes : menu latéral sombre `#13212B`, accent `#0B6B74`,
  couleurs vives réservées aux codes de triage.
