# Snake Frontend

## Lokal starten

```bash
npm install
npm run dev
```

Vite leitet lokale `/api`-Aufrufe an das Backend auf Port 8080 weiter.

## Produktions-Build

```bash
npm run build
```

Falls das Backend unter einer anderen Adresse läuft:

```bash
VITE_API_URL=https://api.example.com npm run build
```

Der fertige Inhalt liegt danach im Ordner `dist`.
