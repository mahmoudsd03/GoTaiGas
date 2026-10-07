# GoTaigas

GoTaigas ist eine webbasierte Anwendung zur Verwaltung und Präsentation von Gründungsideen. Studierende können eigene Ideen erstellen und verwalten, während Investoren vorhandene Ideen ansehen und nach passenden Projekten suchen können.

## Funktionen

- Registrierung und Anmeldung für unterschiedliche Benutzertypen
- Verwaltung von Benutzerprofilen
- Erstellen und Verwalten von Gründungsideen
- Anzeigen und Filtern vorhandener Ideen
- Investor-Ansicht für interessante Projekte
- Persistente Speicherung mit PostgreSQL und JPA
- Weboberfläche mit Vaadin
- Optionale KI-Anbindung über einen konfigurierbaren API-Endpunkt

## Technologien

- Java 17
- Spring Boot
- Vaadin 24
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit

## Voraussetzungen

Für die lokale Ausführung werden benötigt:

- Java 17 oder neuer
- Maven oder der enthaltene Maven Wrapper
- PostgreSQL
- eine lokale Datenbank für GoTaigas

## Konfiguration

Sensible Zugangsdaten werden nicht im Repository gespeichert. Die Anwendung liest Datenbank- und API-Konfiguration aus Umgebungsvariablen.

Eine Vorlage befindet sich in `.env.example`.

Benötigte Variablen:

```text
DB_URL=jdbc:postgresql://localhost:5432/gotaigas
DB_USERNAME=your_database_user
DB_PASSWORD=your_database_password
DB_SCHEMA=gotaigas
KICONNECT_API_KEY=your_api_key
KICONNECT_API_URL=your_api_url
KICONNECT_MODEL=your_model
```

Verwende niemals echte Passwörter oder API-Keys in Dateien, die auf GitHub committed werden.

## Projekt starten

Repository klonen:

```bash
git clone https://github.com/DEIN-BENUTZERNAME/GoTaigas.git
cd GoTaigas
```

Unter Linux/macOS:

```bash
./mvnw spring-boot:run
```

Unter Windows:

```powershell
mvnw.cmd spring-boot:run
```

Anschließend ist die Anwendung normalerweise erreichbar unter:

```text
http://localhost:8080
```

## Tests ausführen

Unter Linux/macOS:

```bash
./mvnw test
```

Unter Windows:

```powershell
mvnw.cmd test
```

## Projektstruktur

```text
GoTaigas/
├── src/
│   ├── main/
│   │   ├── java/com/gotaigas/
│   │   └── resources/
│   └── test/
│       └── java/com/gotaigas/
├── frontend/
├── pom.xml
├── .env.example
└── README.md
```

## Sicherheit

- Keine Zugangsdaten im Repository speichern.
- `.env` und lokale Konfigurationsdateien nicht committen.
- API-Keys ausschließlich über Umgebungsvariablen bereitstellen.
- Bereits veröffentlichte oder versehentlich committed Keys sollten sofort widerrufen und neu erstellt werden.

## Lizenz

Vor einer öffentlichen Weitergabe kann bei Bedarf eine passende Open-Source-Lizenz ergänzt werden.
