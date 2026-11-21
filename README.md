# UrgenceFlow

Applicazione desktop JavaFX per la gestione del Pronto Soccorso: accessi, triage a 5 codici, coda d'attesa prioritaria, box, personale sanitario e statistiche.

Progetto di fine corso di Programmazione Java – Università di Ferrara.

## Tecnologie

- Java 25, JavaFX 25 (FXML + Scene Builder)
- SQLite tramite JDBC
- Maven, JUnit 5

## Avvio

```bash
mvn clean javafx:run
```

## Test

```bash
mvn test
```

## Struttura

| Package | Ruolo |
|---|---|
| `model` | classi del dominio |
| `exception` | eccezioni personalizzate |
| `dao` | accesso al database |
| `service` | logica applicativa |
| `controller` | controller JavaFX |
| `util` | classi di supporto |
