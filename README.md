# Algooo - Sortieralgorithmus Web-App

Algooo ist eine Fullstack-Webanwendung, mit der verschiedene Sortieralgorithmen (wie BubbleSort, ShakerSort, MergeSort und TimSort) visuell und interaktiv getestet werden können. Die Anwendung besteht aus einem React-Frontend und einem Spring Boot-Backend mit MySQL-Datenbank und wird über Netlify und Railway automatisch deployed.

---

## Inhaltsverzeichnis

- [Algooo - Sortieralgorithmus Web-App](#algooo---sortieralgorithmus-web-app)
  - [Inhaltsverzeichnis](#inhaltsverzeichnis)
  - [Features](#features)
  - [Projektstruktur](#projektstruktur)
  - [Lokale Installation](#lokale-installation)
    - [Voraussetzungen](#voraussetzungen)
  - [Lokale Inbetriebnahme](#lokale-inbetriebnahme)
    - [Backend](#backend)
    - [Frontend](#frontend)
  - [Beispiel-Datenbankeinträge](#beispiel-datenbankeinträge)
  - [Dockerfile (Backend)](#dockerfile-backend)
  - [REST API Endpoints](#rest-api-endpoints)
  - [Algorithmen in SortService](#algorithmen-in-sortservice)
    - [• BubbleSort](#-bubblesort)
    - [• ShakerSort](#-shakersort)
      - [Implementierung im Projekt:](#implementierung-im-projekt)
      - [Erklärung:](#erklärung)
      - [Vorteil:](#vorteil)
    - [• MergeSort](#-mergesort)
    - [• TimSort](#-timsort)
  - [Frontend-Anbindung](#frontend-anbindung)
  - [CI/CD (GitLab)](#cicd-gitlab)
  - [Klassendiagramm](#klassendiagramm)
  - [Deployment](#deployment)
  - [Autoren](#autoren)

---

## Features

Diese Webanwendung ermöglicht es Benutzer\:innen, verschiedene Sortieralgorithmen auf interaktive Weise kennenzulernen und zu testen. Durch die visuelle Darstellung wird der Lernprozess unterstützt.

* Auswahl von Sortieralgorithmen
* Eingabe eigener Zahlenlisten
* Visuelle Sortierung (GIF-Animationen)
* Beschreibung jedes Algorithmus
* REST-API zum Abrufen und Sortieren der Algorithmen
* Responsive Design mit Material UI

## Projektstruktur

```
algorithmen/
├── src/main/java/com/example/demo/                # Spring​-Boot 
│   │   ├── controller/
│   │   │   ├── AlgorithmController.java
│   │   │   └── SortController.java
│   │   ├── model/
│   │   │   └── Algorithm.java
│   │   ├── repository/
│   │   │   └── AlgorithmRepository.java
│   │   ├── service/
│   │   │   └── SortService.java
│   │   ├── config/
│   │   │   └── CorsConfig.java
│   │   └── DemoApplication.java
│   ├── pom.xml
│   └── Dockerfile
└── frontend/               # React + MUI App
    ├── public/
    │   ├── algooo.png
    │   └── gifs/
    │       ├── bubblesort.gif
    │       ├── shakersort.gif
    │       ├── mergesort.gif
    │       └── timsort.gif
    └── src/
        └── App.jsx
.gitlab-ci.yml             # CI/CD Pipeline
README.md
```

---

## Lokale Installation

### Voraussetzungen

* Java 21
* Maven
* Node.js + npm
* MySQL oder Railway-Datenbank

## Lokale Inbetriebnahme

### Backend

1. `application.properties` (Railway Konfiguration):

```properties
spring.datasource.url=jdbc:mysql://mysql.railway.internal:3306/railway
spring.datasource.username=root
spring.datasource.password=faWaHSUMTwvPwpxQCjemCvJVaymiBzEO
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

2. Build & Run:

```bash
cd backend
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

3. Testen mit Insomnia:

* GET [http://localhost:8080/algorithms](http://localhost:8080/algorithms)
* POST [http://localhost:8080/sort/bubblesort](http://localhost:8080/sort/bubblesort) mit Body:

```json
[5, 3, 9]
```

### Frontend

1. Abhängigkeiten installieren:

```bash
cd frontend
npm install
npm install @mui/material @emotion/react @emotion/styled axios react-router-dom
```

2. Lokalen Entwicklungsserver starten:

```bash
npm run dev
```

---

## Beispiel-Datenbankeinträge

```sql
USE railway;

INSERT INTO algorithm (name, description) VALUES
('BubbleSort', 'Vergleicht benachbarte Elemente und tauscht sie bei Bedarf.'),
('ShakerSort', 'Bidirektionale Variante von BubbleSort.'),
('MergeSort', 'Teilt die Liste rekursiv und mergen sortiert.'),
('TimSort', 'Hybrider Sortieralgorithmus, nutzt Merge & Insertion Sort.');
```

---

## Dockerfile (Backend)

```dockerfile
# Build Stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime Stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## REST API Endpoints

```http
GET     /algorithms
POST    /algorithms
POST    /sort/{algorithm}
```

---

## Algorithmen in SortService

### • BubbleSort

* Vergleicht und tauscht benachbarte Elemente.

### • ShakerSort

Der ShakerSort (auch CocktailSort genannt) ist eine verbesserte Variante des BubbleSort-Algorithmus. Im Gegensatz zum einfachen BubbleSort, der sich nur in eine Richtung durch die Liste bewegt, durchläuft der ShakerSort die Liste bidirektional: zuerst vorwärts, dann rückwärts.

#### Implementierung im Projekt:

Die Methode befindet sich in `SortService.java`:

```java
private List<Integer> shakerSort(List<Integer> list) {
    // Wird verwendet, um festzustellen, ob während eines Durchgangs ein Tausch stattgefunden hat
    boolean swapped = true;

    // Start- und Endgrenzen für den Bereich, der durchlaufen wird
    int start = 0;
    int end = list.size() - 1;

    // Solange ein Tausch stattfindet, soll weiter sortiert werden
    while (swapped) {
        swapped = false;

        // Vorwärtsdurchlauf: von links nach rechts
        for (int i = start; i < end; i++) {
            if (list.get(i) > list.get(i + 1)) {
                // Wenn das aktuelle Element grösser als das nächste ist, tauschen
                Collections.swap(list, i, i + 1);
                swapped = true; // Es wurde ein Tausch durchgeführt
            }
        }

        // Wenn kein Tausch stattfand, ist das Array bereits sortiert
        if (!swapped) break;

        // Da das grösste Element jetzt rechts steht, kann das Ende reduziert werden
        swapped = false;
        end--;

        // Rückwärtsdurchlauf: von rechts nach links
        for (int i = end - 1; i >= start; i--) {
            if (list.get(i) > list.get(i + 1)) {
                // Tausch wie im Vorwärtsdurchlauf
                Collections.swap(list, i, i + 1);
                swapped = true;
            }
        }

        // Da das kleinste Element jetzt links steht, kann der Start erhöht werden
        start++;
    }

    // Gibt die sortierte Liste zurück
    return list;
}

```

#### Erklärung:

* **Start-/Endgrenzen** (`start`, `end`) definieren den Bereich, in dem Elemente noch getauscht werden können.
* **Vorwärtsdurchlauf**: Grössere Werte „wandern“ nach rechts.
* **Rückwärtsdurchlauf**: Kleinere Werte „wandern“ nach links.
* Der Bereich verkleinert sich nach jedem Durchlauf.
* `swapped` wird verwendet, um zu prüfen, ob noch Änderungen vorgenommen wurden – ist kein Tausch nötig, endet die Schleife.

#### Vorteil:

ShakerSort erkennt schneller, wenn das Array bereits (teilweise) sortiert ist, da in beiden Richtungen verglichen wird. Dadurch kann er bei bestimmten Datensätzen effizienter sein als BubbleSort.

### • MergeSort

* Teilt Liste rekursiv in Hälften, sortiert und merged.

### • TimSort

* Kombiniert MergeSort mit InsertionSort. Hoch performant.

---

## Frontend-Anbindung

* Automatische URL-Erkennung:

```js
const backendUrl = window.location.hostname === "localhost"
  ? "http://localhost:8080"
  : "https://authentic-stillness-production.up.railway.app";
```

* CORS Freigabe in `CorsConfig.java`:

```java
.allowedOrigins("http://localhost:5173", "https://algooo.netlify.app")
```

---

## CI/CD (GitLab)

```yaml
stages:
  - build
  - docker
  - deploy

variables:
  MAVEN_CLI_OPTS: "-s .m2/settings.xml --batch-mode"
  DOCKER_IMAGE: registry.gitlab.com/$CI_PROJECT_PATH:latest

build-backend:
  stage: build
  image: maven:3.9.6-eclipse-temurin-21
  script:
    - mvn clean package -DskipTests
  artifacts:
    paths:
      - target/*.jar

dockerize:
  stage: docker
  image: docker:24.0.6
  services:
    - docker:dind
  before_script:
    - docker login -u "$CI_REGISTRY_USER" -p "$CI_REGISTRY_PASSWORD" $CI_REGISTRY
  script:
    - docker build -t $DOCKER_IMAGE .
    - docker push $DOCKER_IMAGE

deploy:
  stage: deploy
  image: node:18
  before_script:
    - npm install -g @railway/cli
  script:
    - railway up --service authentic-stillness --detach
  only:
    - master
```

---

## Klassendiagramm


## Deployment

* **Backend:** Railway `railway up`
* **Frontend:** Netlify (build command: `npm run build`, dir: `dist`)

---

## Autoren

Hasan Bytyqi <br/>
Mazlum Raimi <br/>
Kyriakos Amanatidis <br/>

# 🧭 Anleitung: Projekt „Algooo“ lokal mit Ubuntu Server betreiben

## 📦 Voraussetzungen

- Ubuntu Server (z. B. auf Laptop oder VPS)
- Docker & Docker Compose installiert
- GitLab-Projekt vorhanden mit Backend
- Frontend ist auf Netlify gehostet
- [`cloudflared`](https://developers.cloudflare.com/cloudflare-one/connections/connect-apps/install-and-setup/installation/) installiert (optional für HTTPS ohne Domain)

---

## 📁 Projektstruktur

- **Spring Boot Backend** (Java + Maven)
- **MySQL** (als Docker-Container)
- **Frontend**: React, wird auf Netlify gehostet

---

## 🛠️ Schritte zur lokalen Einrichtung auf Ubuntu

### 1. GitLab-Repo clonen

```bash
git clone git@gitlab.com:USERNAME/REPO.git
cd REPO
```

---

### 2. Dockerfile prüfen (bereits vorhanden)

```dockerfile
# Build Stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime Stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### 3. `docker-compose.yml` erstellen

```yaml
version: '3.8'
services:
  backend:
    build: .
    ports:
      - '8080:8080'
    depends_on:
      - mysql
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/algooo
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: DEIN_PASSWORT
    networks:
      - algo-net

  mysql:
    image: mysql:8
    ports:
      - '3306:3306'
    environment:
      MYSQL_ROOT_PASSWORD: DEIN_PASSWORT
      MYSQL_DATABASE: algooo
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - algo-net

volumes:
  mysql_data:

networks:
  algo-net:
```

---

### 4. `application.properties` im Backend anpassen

```properties
spring.datasource.url=jdbc:mysql://mysql:3306/algooo
spring.datasource.username=root
spring.datasource.password=DEIN_PASSWORT
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

---

### 5. Projekt starten

```bash
docker compose up --build -d
```

---

### 6. Cloudflare Tunnel (für öffentliches HTTPS)

```bash
cloudflared tunnel --url http://localhost:8080
```

📎 Die URL wird dann z. B. `https://tunnelname.trycloudflare.com` sein.

---

### 7. Frontend anpassen (`App.jsx`)

```js
const backendUrl =
  window.location.hostname === 'localhost'
    ? 'http://localhost:8080'
    : 'https://DEIN-TUNNEL.trycloudflare.com';
```

Anschließend neu bauen und auf Netlify pushen.

---

## ✅ Fertig!

- Das Backend läuft lokal auf deinem Server
- Das Frontend holt die Daten per HTTPS vom Tunnel
- Alles läuft DSGVO-konform und kostenlos auf deiner Infrastruktur

---