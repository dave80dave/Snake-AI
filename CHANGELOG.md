# Changelog

## Deutsch

### v0.2.0 - RandomAI Demo

- Erste AI-Struktur im Package `at.orter.snake.ai`
- `SnakeAi` als Helfer fuer moegliche Richtungen, Wandkollision, Selbstkollision und Wachstum
- `RandomAi` waehlt zufaellig eine sichere Richtung, wenn eine sichere Richtung existiert
- `Main` zeigt eine kleine RandomAI-Demo mit Ausgabe nur bei gegessenem Apfel und Game Over
- JUnit-Test fuer `RandomAi`
- AI-Klassen in das Snake-Package verschoben

### v0.1.0 - Core Game Logic

- Grundlegende Snake-Spiellogik
- `Position`, `Direction`, `Snake`, `Food`, `Playground`, `Score` und `Game`
- Bewegung ohne Wachstum und Wachstum beim Essen
- Wandkollision und Selbstkollision
- Score-Zaehler
- neuer Apfel nach dem Essen
- Reset-Logik fuer neue Runden
- erste JUnit-Tests fuer die Spiellogik

---

## English

### v0.2.0 - RandomAI Demo

- First AI structure in package `at.orter.snake.ai`
- `SnakeAi` as helper for possible directions, wall collision, self-collision, and growth
- `RandomAi` randomly chooses a safe direction if a safe direction exists
- `Main` shows a small RandomAI demo with output only when an apple is eaten or game over happens
- JUnit test for `RandomAi`
- AI classes moved into the Snake package

### v0.1.0 - Core Game Logic

- Basic Snake game logic
- `Position`, `Direction`, `Snake`, `Food`, `Playground`, `Score`, and `Game`
- Movement without growth and growth when eating
- Wall collision and self-collision
- Score counter
- new apple after eating
- Reset logic for new rounds
- first JUnit tests for game logic
