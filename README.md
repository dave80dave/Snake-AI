# Snake-AI

## Deutsch

Snake-AI ist ein Lernprojekt in Java. Ziel ist zuerst ein funktionierendes Snake-Spiel mit sauberer Spiellogik. Danach soll eine selbst gebaute AI entstehen, ohne TensorFlow oder ein anderes Machine-Learning-Framework. Später soll das Projekt um ein Spring-Boot-Backend, MySQL und ein React-Frontend erweitert werden.

### Aktueller Stand

- Maven/Java-Projekt mit Package `at.orter.snake`
- Spielfeldklasse `Playground` mit Breite und Hoehe
- Positionsklasse `Position` mit Wertevergleich ueber `equals(...)` und `hashCode()`
- Schlange als Liste von Positionen in `Snake`
- Futterklasse `Food` mit einer einzelnen Apfelposition
- Spielcontainer `Game` fuer Tick, Richtung, Kollisionen, Score, Food und Reset
- Bewegungsrichtungen als `Direction` enum
- Score startet bei 0 und steigt beim Essen eines Apfels
- Nach dem Essen wird ein neuer Apfel auf einer freien Position gesetzt
- `resetGame()` setzt Snake, Score, Richtung, Game Over und Apfel zurueck
- Wandkollision und Selbst-Kollision setzen `gameOver`
- Direkte Gegenrichtungen werden in `changeDirection(...)` blockiert
- Zweisprachige Lernkommentare im Quellcode
- JUnit-Tests fuer zentrale Spiellogik und AI-Hilfslogik
- AI-Package `at.orter.snake.ai`
- `SnakeAi` prueft moegliche Richtungen, Wandkollision, Selbstkollision und Wachstum
- `RandomAi` waehlt zufaellig eine sichere Richtung, wenn eine sichere Richtung existiert
- Kleines RandomAI-Demo-Spiel in `Main`, das nur Essen und Game Over ausgibt

### Wichtige Lernidee

Die Schlange ist eine Liste von Positionen:

```text
[ Kopf, Koerper, Koerper, Schwanz ]
```

Bei normaler Bewegung wird vorne ein neuer Kopf eingefuegt und hinten der Schwanz entfernt. Beim Wachsen wird vorne ein neuer Kopf eingefuegt, aber der Schwanz bleibt erhalten.

### AI-Ablauf

```text
RandomAi waehlt Direction
Main ruft game.changeDirection(direction) auf
Main ruft game.tick() auf
Game prueft Regeln und bewegt die Snake
```

Die AI bewegt die Snake nicht direkt. Sie entscheidet nur eine Richtung. Die Spielregeln bleiben in `Game`.

### Naechste Schritte

- Spielfeld im Terminal grafisch anzeigen
- Neue `AppleSeekingAi` erstellen, die sichere Richtungen nach Apfelnaehe bewertet
- Tests fuer `AppleSeekingAi` schreiben
- Danach: erste lernende AI vorbereiten mit State, Reward und Q-Learning
- Spaeter: Spring-Boot-Backend, MySQL und React-Frontend planen

---

## English

Snake-AI is a Java learning project. The first goal is to build a working Snake game with clean game logic. After that, the project should get a self-built AI, without TensorFlow or another machine-learning framework. Later, the project should be extended with a Spring Boot backend, MySQL, and a React frontend.

### Current Status

- Maven/Java project with package `at.orter.snake`
- Playground class `Playground` with width and height
- Position class `Position` with value comparison through `equals(...)` and `hashCode()`
- Snake stored as a list of positions in `Snake`
- Food class `Food` with one single apple position
- Game container `Game` for tick, direction, collisions, score, food, and reset
- Movement directions as a `Direction` enum
- Score starts at 0 and increases when an apple is eaten
- After eating, a new apple is placed on a free position
- `resetGame()` resets snake, score, direction, game over, and apple
- Wall collision and self-collision set `gameOver`
- Direct opposite directions are blocked in `changeDirection(...)`
- Bilingual learning comments in the source code
- JUnit tests for core game logic and AI helper logic
- AI package `at.orter.snake.ai`
- `SnakeAi` checks possible directions, wall collision, self-collision, and growth
- `RandomAi` randomly chooses a safe direction if a safe direction exists
- Small RandomAI demo game in `Main` that only prints eating events and game over

### Important Learning Idea

The snake is a list of positions:

```text
[ head, body, body, tail ]
```

During normal movement, a new head is added to the front and the tail is removed from the back. When the snake grows, a new head is added to the front, but the tail stays in the list.

### AI Flow

```text
RandomAi chooses Direction
Main calls game.changeDirection(direction)
Main calls game.tick()
Game checks the rules and moves the snake
```

The AI does not move the snake directly. It only decides one direction. The game rules stay in `Game`.

### Next Steps

- Print the playground graphically in the terminal
- Create a new `AppleSeekingAi` that scores safe directions by apple distance
- Write tests for `AppleSeekingAi`
- After that: prepare the first learning AI with state, reward, and Q-learning
- Later: plan Spring Boot backend, MySQL, and React frontend

---

## Lizenz / License

Deutsch: Dieses Projekt steht unter der MIT-Lizenz. Details stehen in der Datei `LICENSE`.

English: This project is licensed under the MIT License. Details are available in the `LICENSE` file.
