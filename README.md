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

* Bewegt sich vorwärts und rückwärts durch das Array.

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
