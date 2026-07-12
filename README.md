# Snake-AI

## Deutsch

Snake-AI ist ein Lernprojekt in Java. Ziel ist zuerst ein funktionierendes Snake-Spiel mit sauberer Spiellogik. Danach soll eine selbst gebaute AI entstehen, ohne TensorFlow oder ein anderes Machine-Learning-Framework. Später soll das Projekt um ein Spring-Boot-Backend, MySQL und ein React-Frontend erweitert werden.

### Aktueller Stand

- Maven/Java-Projekt mit Package `at.orter.snake`
- Spielfeldklasse `Playground`
- Positionsklasse `Position`
- Schlange als Liste von Positionen in `Snake`
- Futterklasse `Food`
- Spielcontainer `Game`
- Bewegungsrichtungen als `Direction` enum
- Zweisprachige Lernkommentare im Quellcode
- Erste Bewegungslogik fuer die Schlange:
  - `move(direction)` bewegt die Schlange ohne Wachstum
  - `grow(direction)` bewegt die Schlange und laesst den Schwanz erhalten

### Wichtige Lernidee

Die Schlange ist eine Liste von Positionen:

```text
[ Kopf, Koerper, Koerper, Schwanz ]
```

Bei normaler Bewegung wird vorne ein neuer Kopf eingefuegt und hinten der Schwanz entfernt. Beim Wachsen wird vorne ein neuer Kopf eingefuegt, aber der Schwanz bleibt erhalten.

### Naechste Schritte

- `Game` soll die aktuelle Richtung speichern
- `tick()` soll die Schlange einmal bewegen
- `Game` soll entscheiden, ob `move` oder `grow` verwendet wird
- Apfelposition mit neuer Kopfposition vergleichen
- Kollision mit Wand pruefen
- Kollision mit dem eigenen Koerper pruefen
- Spielfeld im Terminal anzeigen
- Danach: AI mit selbst gebautem Reinforcement Learning oder Q-Learning

---

## English

Snake-AI is a Java learning project. The first goal is to build a working Snake game with clean game logic. After that, the project should get a self-built AI, without TensorFlow or another machine-learning framework. Later, the project should be extended with a Spring Boot backend, MySQL, and a React frontend.

### Current Status

- Maven/Java project with package `at.orter.snake`
- Playground class `Playground`
- Position class `Position`
- Snake stored as a list of positions in `Snake`
- Food class `Food`
- Game container `Game`
- Movement directions as a `Direction` enum
- Bilingual learning comments in the source code
- First movement logic for the snake:
  - `move(direction)` moves the snake without growing
  - `grow(direction)` moves the snake and keeps the tail

### Important Learning Idea

The snake is a list of positions:

```text
[ head, body, body, tail ]
```

During normal movement, a new head is added to the front and the tail is removed from the back. When the snake grows, a new head is added to the front, but the tail stays in the list.

### Next Steps

- `Game` should store the current direction
- `tick()` should move the snake one step
- `Game` should decide whether to use `move` or `grow`
- Compare the apple position with the new head position
- Check wall collision
- Check collision with the snake body
- Print the playground in the terminal
- Later: AI with self-built reinforcement learning or Q-learning
