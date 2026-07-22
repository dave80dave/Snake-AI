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
- JUnit-Tests fuer zentrale Spiellogik, AI-Hilfslogik und Q-Tabelle
- AI-Package `at.orter.snake.ai`
- `SnakeAi` prueft moegliche Richtungen, Wandkollision, Selbstkollision und Wachstum
- `RandomAi` waehlt zufaellig eine sichere Richtung, wenn eine sichere Richtung existiert
- `StateReader` erzeugt aus einem laufenden Spiel einen kompakten `SnakeState`
- Erster Q-Learning-Baustein `SnakeState` speichert Gefahren- und Apfelinformationen
- `SnakeState` besitzt Wertevergleich und Hashcode fuer die Verwendung als Q-Tabellen-Schluessel
- `RelativeAction` definiert `STRAIGHT`, `TURN_LEFT` und `TURN_RIGHT`
- `QTable` speichert Q-Werte pro State und Action und findet Maximum sowie beste Action
- Sieben QTable-Tests pruefen Startwerte, getrennte States, Speicherung und Auswertung
- `Main` zeigt eine QTable-Demo mit Werten vor und nach beispielhaften Lernerfahrungen

### Wichtige Lernidee

Die Schlange ist eine Liste von Positionen:

```text
[ Kopf, Koerper, Koerper, Schwanz ]
```

Bei normaler Bewegung wird vorne ein neuer Kopf eingefuegt und hinten der Schwanz entfernt. Beim Wachsen wird vorne ein neuer Kopf eingefuegt, aber der Schwanz bleibt erhalten.

### Q-Learning-Grundlage

```text
Game -> StateReader -> SnakeState
SnakeState + RelativeAction -> QTable -> Q-Wert
QTable -> hoechster Q-Wert und beste RelativeAction
```

Die Q-Tabelle ist das Gedaechtnis der zukuenftigen lernenden AI. Die aktuelle Demo traegt Beispielwerte noch manuell ein; Reward, Q-Learning-Formel und Trainingsschleife folgen als naechste Bausteine.

### Naechste Schritte

- `ActionConverter` fuer die Uebersetzung von `RelativeAction` in `Direction` erstellen
- Epsilon-Greedy fuer Exploration und Exploitation umsetzen
- Reward-Berechnung und Q-Learning-Formel implementieren
- Trainer fuer Ticks und Episoden erstellen
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
- JUnit tests for core game logic, AI helper logic, and the Q-table
- AI package `at.orter.snake.ai`
- `SnakeAi` checks possible directions, wall collision, self-collision, and growth
- `RandomAi` randomly chooses a safe direction if a safe direction exists
- `StateReader` creates a compact `SnakeState` from a running game
- First Q-learning building block `SnakeState` stores danger and apple information
- `SnakeState` provides value equality and a hash code for use as a Q-table key
- `RelativeAction` defines `STRAIGHT`, `TURN_LEFT`, and `TURN_RIGHT`
- `QTable` stores Q-values per state and action and finds the maximum and best action
- Seven QTable tests verify initial values, separate states, storage, and evaluation
- `Main` shows a QTable demo with values before and after example learning experiences

### Important Learning Idea

The snake is a list of positions:

```text
[ head, body, body, tail ]
```

During normal movement, a new head is added to the front and the tail is removed from the back. When the snake grows, a new head is added to the front, but the tail stays in the list.

### Q-Learning Foundation

```text
Game -> StateReader -> SnakeState
SnakeState + RelativeAction -> QTable -> Q-value
QTable -> highest Q-value and best RelativeAction
```

The Q-table is the memory of the future learning AI. The current demo still inserts example values manually; rewards, the Q-learning formula, and the training loop are the next building blocks.

### Next Steps

- Create an `ActionConverter` that translates `RelativeAction` into `Direction`
- Implement epsilon-greedy for exploration and exploitation
- Implement reward calculation and the Q-learning formula
- Create a trainer for ticks and episodes
- Later: plan Spring Boot backend, MySQL, and React frontend

---

## Ausfuehren / Run

Deutsch:

```bash
mvn package
java -jar target/Snake-AI-1.0-SNAPSHOT.jar
```

English:

```bash
mvn package
java -jar target/Snake-AI-1.0-SNAPSHOT.jar
```

---

## Releases

Deutsch: Die bisherigen Meilensteine stehen in `CHANGELOG.md`. Aktuelle Tags: `v0.1.0` fuer die Spiellogik, `v0.2.0` fuer die RandomAI-Demo und `v0.2.1` fuer die direkt startbare JAR.

English: The current milestones are listed in `CHANGELOG.md`. Current tags: `v0.1.0` for game logic, `v0.2.0` for the RandomAI demo, and `v0.2.1` for the directly executable JAR.

---

## Lizenz / License

Deutsch: Dieses Projekt steht unter der MIT-Lizenz. Details stehen in der Datei `LICENSE`.

English: This project is licensed under the MIT License. Details are available in the `LICENSE` file.
