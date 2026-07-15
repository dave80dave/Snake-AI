# Snake-AI

## Deutsch

Snake-AI ist ein Lernprojekt in Java. Ziel ist zuerst ein funktionierendes Snake-Spiel mit sauberer Spiellogik. Danach soll eine selbst gebaute AI entstehen, ohne TensorFlow oder ein anderes Machine-Learning-Framework. Später soll das Projekt um ein Spring-Boot-Backend, MySQL und ein React-Frontend erweitert werden.

### Aktueller Stand

- Maven/Java-Projekt mit Package `at.orter.snake`
- Spielfeldklasse `Playground`
- Positionsklasse `Position` mit Wertevergleich ueber `equals(...)`
- Schlange als Liste von Positionen in `Snake`
- Futterklasse `Food` mit einer einzelnen Apfelposition
- Spielcontainer `Game`
- Bewegungsrichtungen als `Direction` enum
- Erste AI-Klasse `SnakeAi`
- Zweisprachige Lernkommentare im Quellcode
- JUnit-Teststruktur fuer zentrale Spiellogik
- Erste Bewegungslogik fuer die Schlange:
  - `move(direction)` bewegt die Schlange ohne Wachstum
  - `grow(direction)` bewegt die Schlange und laesst den Schwanz erhalten
  - `getNextHeadPosition(direction)` berechnet die naechste Kopfposition, ohne die Schlange zu veraendern
- Erste AI-Logik:
  - `getPossibleDirections(currentDirection)` gibt die Richtungen zurueck, die keine direkte Gegenrichtung sind
  - `collidesWithWall(playground, direction, snake)` prueft, ob die naechste Kopfposition ausserhalb des Spielfelds waere
  - `willGrow(direction, snake, food)` prueft, ob die Schlange im naechsten Schritt den Apfel essen wuerde
  - `collidesWithSnake(direction, snake, food)` prueft Selbstkollision inklusive Schwanz-Ausnahme

### Wichtige Lernidee

Die Schlange ist eine Liste von Positionen:

```text
[ Kopf, Koerper, Koerper, Schwanz ]
```

Bei normaler Bewegung wird vorne ein neuer Kopf eingefuegt und hinten der Schwanz entfernt. Beim Wachsen wird vorne ein neuer Kopf eingefuegt, aber der Schwanz bleibt erhalten.

### Naechste Schritte

- `Game` speichert die aktuelle Richtung
- `tick()` prueft Wandkollision und entscheidet danach zwischen Wachstum und normaler Bewegung
- direkte Gegenrichtungen werden in `changeDirection(...)` blockiert
- Apfelposition wird mit der naechsten Kopfposition verglichen
- Wandkollision setzt `gameOver` und verhindert Bewegung aus dem Spielfeld
- Selbst-Kollision setzt `gameOver`, wenn die naechste Kopfposition den Koerper trifft
- Score startet bei 0 und steigt beim Essen eines Apfels
- Nach dem Essen wird ein neuer Apfel auf einer freien Position gesetzt
- `resetGame()` setzt Snake, Score, Richtung, Game Over und Apfel zurueck
- Kleines Demo-Spiel in `Main`, um Tick, Score, Food und Reset im Terminal zu beobachten
- Spielfeld im Terminal grafisch anzeigen
- Danach: AI kombiniert moegliche Richtungen mit Wand- und Koerperkollision
- Danach: AI bewertet sichere Richtungen nach Apfelnaehe
- Spaeter: AI mit selbst gebautem Reinforcement Learning oder Q-Learning

---

## English

Snake-AI is a Java learning project. The first goal is to build a working Snake game with clean game logic. After that, the project should get a self-built AI, without TensorFlow or another machine-learning framework. Later, the project should be extended with a Spring Boot backend, MySQL, and a React frontend.

### Current Status

- Maven/Java project with package `at.orter.snake`
- Playground class `Playground`
- Position class `Position` with value comparison through `equals(...)`
- Snake stored as a list of positions in `Snake`
- Food class `Food` with one single apple position
- Game container `Game`
- Movement directions as a `Direction` enum
- First AI class `SnakeAi`
- Bilingual learning comments in the source code
- JUnit test setup for core game logic
- First movement logic for the snake:
  - `move(direction)` moves the snake without growing
  - `grow(direction)` moves the snake and keeps the tail
  - `getNextHeadPosition(direction)` calculates the next head position without changing the snake
- First AI logic:
  - `getPossibleDirections(currentDirection)` returns the directions that are not direct opposites
  - `collidesWithWall(playground, direction, snake)` checks whether the next head position would be outside the playground
  - `willGrow(direction, snake, food)` checks whether the snake would eat the apple on the next step
  - `collidesWithSnake(direction, snake, food)` checks self-collision including the tail exception

### Important Learning Idea

The snake is a list of positions:

```text
[ head, body, body, tail ]
```

During normal movement, a new head is added to the front and the tail is removed from the back. When the snake grows, a new head is added to the front, but the tail stays in the list.

### Next Steps

- `Game` stores the current direction
- `tick()` checks wall collision and then decides between growth and normal movement
- direct opposite directions are blocked in `changeDirection(...)`
- Apple position is compared with the next head position
- Wall collision sets `gameOver` and prevents movement outside the playground
- Self-collision sets `gameOver` when the next head position hits the snake body
- Score starts at 0 and increases when an apple is eaten
- After eating, a new apple is placed on a free position
- `resetGame()` resets snake, score, direction, game over, and apple
- Small demo game in `Main` to observe tick, score, food, and reset in the terminal
- Print the playground graphically in the terminal
- Next: AI combines possible directions with wall and body collision
- Next: AI scores safe directions by apple distance
- Later: AI with self-built reinforcement learning or Q-learning


---

## Lizenz / License

Deutsch: Dieses Projekt steht unter der MIT-Lizenz. Details stehen in der Datei `LICENSE`.

English: This project is licensed under the MIT License. Details are available in the `LICENSE` file.
