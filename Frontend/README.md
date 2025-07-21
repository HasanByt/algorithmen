# MagicSort Frontend

Dies ist das **React-Frontend** für das Projekt [MagicSort](https://github.com/HasanByt/magicsort), das eine visuelle und interaktive Darstellung von Sortieralgorithmen ermöglicht. Es kommuniziert über eine REST-Schnittstelle mit dem Spring Boot Backend.

## ✨ Features

- Auswahl verschiedener Sortieralgorithmen (z. B. Bubble Sort, Merge Sort, Quick Sort etc.)
- Eingabe von Zahlen zur Sortierung
- Visualisierung der Sortierabläufe (optional animiert)
- Anzeige von:
  - Sortierdauer (ms)
  - Anzahl der Vergleiche
  - Sortierte Liste
- Moderne UI mit [Material UI (MUI)](https://mui.com/)
- Deployment-ready für [Netlify](https://www.netlify.com/)

## 🚀 Installation & Start

### 🔧 Voraussetzungen

- Node.js (empfohlen: v18+)
- Paketmanager wie `npm` oder `yarn`

### 🛠️ Projekt installieren

```bash
git clone https://github.com/DEIN-USERNAME/frontend.git
cd frontend
npm install
npm install @mui/material @emotion/react @emotion/styled
```

### ▶️ Projekt starten

```bash
npm run dev
```

Die App läuft dann unter [http://localhost:5173](http://localhost:5173)

## 🔗 Backend-Anbindung

Die App erwartet ein laufendes Spring Boot Backend unter einer URL wie:

```text
http://localhost:8080
```

Du kannst dies in der `.env` Datei konfigurieren:

```env
VITE_API_BASE_URL=http://localhost:8080
```

> Hinweis: In der Produktionsumgebung sollte diese URL in Netlify korrekt gesetzt werden.

## 🧪 Tests

Aktuell keine automatisierten Tests vorhanden – geplant für spätere Releases.

## 📁 Projektstruktur

```text
```bash
src/
├── App.jsx          # Hauptkomponente mit Routing & Logik
├── index.js         # Einstiegspunkt der React-App
├── index.css        # Zentrales Styling

public/
├── gifs/            # Visualisierungen der Sortieralgorithmen
├── index.html       # HTML-Template
```

## 🌐 Deployment

### 📦 Netlify

Für ein einfaches Deployment kannst du dieses Projekt direkt mit [Netlify](https://www.netlify.com/) verbinden.

Build-Einstellungen:

```text
Build Command: npm run build
Publish Directory: dist
Environment Variables:
  VITE_API_BASE_URL=https://DEINE-BACKEND-URL
```

## 📄 Lizenz

Dieses Projekt steht unter der MIT-Lizenz. Siehe LICENSE.

> Entwickelt mit ❤️ von Hasan Bytyqi – für das Schulprojekt im Modul M324.