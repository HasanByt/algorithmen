# 🧠 Algorithmen – Spring Boot Backend

Dieses Spring Boot Backend stellt eine REST-API zur Verfügung, um Sortieralgorithmen der MagicSort-Bibliothek zu verwenden. Es dient als Vermittlung zwischen einem Frontend (z. B. React) und der Java-Bibliothek `magicsort`.

## 🚀 Features

- API-Endpunkte zur Sortierung von Zahlen mit einem bestimmten Algorithmus
- Integration der externen `.jar`-Library `magicsort`
- Rückgabe von Sortierergebnissen inkl.:
  - Sortierte Liste
  - Dauer in Millisekunden
  - Anzahl Vergleiche
- Dockerized für Deployment

## 📦 Voraussetzungen

- Java 21
- Maven
- Docker (für Deployment)
- GitHub Token (für Zugriff auf magicsort über GitHub Packages)

## 🛠️ Lokale Entwicklung

```bash
# Repository klonen
git clone https://github.com/HasanByt/algorithmen.git
cd algorithmen

# .jar-Dependency (magicsort) lokal bauen, falls nicht über GitHub geladen
# (Optional, falls du magicsort bearbeitest)
cd ../magicsort
mvn clean install
cd ../algorithmen

# Backend starten
mvn spring-boot:run
```

## 🔌 Beispiel-API-Aufruf

```http
POST /sort/{algorithm}

Body:
{
  "numbers": [5, 2, 8, 1]
}
```

Antwort:
```json
{
  "algorithm": "bubble",
  "input": [5, 2, 8, 1],
  "sorted": [1, 2, 5, 8],
  "durationMs": 0.115,
  "comparisons": 8
}
```

## 🐳 Docker

### Build & Start (lokal)

```bash
docker compose build --no-cache
docker compose up -d
```

### Dockerfile nutzt Maven Multi-Stage Build:

```Dockerfile
# 1. Build-Stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder
...

# 2. Runtime
FROM eclipse-temurin:21-jre
...
```

### Hinweis zu GitHub Token

GitHub Push Protection verhindert, dass Tokens in `settings.xml` hochgeladen werden. Nutze das Token nur lokal oder per CI/CD.

## 📁 Projektstruktur

```bash
src/
  ├── controller/
  │   └── SortController.java
  ├── service/
  │   └── SortService.java
  └── DemoApplication.java
```

## 🧪 Tests

Aktuell sind keine automatisierten Tests integriert – vorgesehen für zukünftige Erweiterung.

## 🔐 Sicherheit

Dieses Projekt nutzt keine Authentifizierung, da es für Schulzwecke entwickelt wurde. Für produktive Nutzung sollte ein Auth-Mechanismus ergänzt werden.

## 📄 Lizenz

Dieses Projekt steht unter der MIT-Lizenz. Siehe LICENSE.

Entwickelt mit ❤️ von Hasan Bytyqi – im Rahmen des Moduls M324.
