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
- `ActionConverter` uebersetzt eine relative Aktion anhand der aktuellen Blickrichtung in eine absolute `Direction`
- `QTable` speichert Q-Werte pro State und Action und findet Maximum sowie beste Action
- `QLearningAi` waehlt mit Epsilon-Greedy zwischen Ausprobieren und der besten bekannten Action
- `QLearningAi.learn(...)` aktualisiert Q-Werte mit Lernrate, Zukunftsfaktor, Belohnung und Game-Over-Sonderfall
- `RewardCalculator` vergibt `-100.0` fuer Tod, `10.0` fuer einen Apfel und `-0.1` fuer einen normalen Schritt
- `Trainer` verbindet StateReader, QLearningAi, ActionConverter, Game und RewardCalculator zu einem vollstaendigen Trainingsschritt
- Episoden besitzen ein Schrittlimit, werden nach Abschluss zurueckgesetzt und behalten ihre gemeinsame Q-Tabelle
- Sieben QTable-Tests pruefen Startwerte, getrennte States, Speicherung und Auswertung
- Drei ActionConverter-Tests pruefen alle zwoelf Kombinationen aus vier Richtungen und drei relativen Aktionen
- Fuenf QLearningAi-Tests pruefen Aktionsauswahl, Lernformel, Game Over und getrennte Action-Werte
- Drei RewardCalculator-Tests pruefen Tod, Apfel und normale Bewegung
- Vier Trainer-Tests pruefen Bewegung, Rewards, Q-Wert-Aktualisierung, Reset und Schrittlimit
- Insgesamt pruefen 35 JUnit-Tests die bisherige Spiel- und AI-Logik
- `Main` trainiert die Q-Learning-AI ueber 1.000 Episoden und zeigt Fortschritt, Durchschnitt, Bestwert und Q-Werte

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
aktuelle Direction + RelativeAction -> ActionConverter -> neue Direction
QLearningAi -> Epsilon-Greedy -> zufaellige oder beste RelativeAction
oldState + Action + Reward + newState -> Q-Learning-Formel -> neuer Q-Wert
alter Score + neuer Score + Game Over -> RewardCalculator -> Reward
Trainer -> Aktion ausfuehren -> Reward berechnen -> Q-Wert lernen -> naechste Episode
```

Die Q-Tabelle ist das Gedaechtnis der lernenden AI. `QLearningAi` waehlt Aktionen und rechnet Erfahrungen in neue Q-Werte um. Der `RewardCalculator` bewertet Spielschritte und der `Trainer` verbindet alle Bausteine mit dem laufenden Spiel. Die `Main` fuehrt damit erstmals ein echtes automatisches Training aus.

Die verwendete Lernformel lautet:

```text
targetQ = reward + gamma * maxFutureQ
newQ = oldQ + alpha * (targetQ - oldQ)
```

Bei Game Over besteht `targetQ` nur aus `reward`, weil danach keine zukuenftige Aktion mehr existiert.

### Naechste Schritte

- Q-Tabelle speichern und laden, damit das Training spaeter fortgesetzt werden kann
- Trainingsparameter und Fortschrittsdaten fuer ein Backend zugaenglich machen
- Ausfuehrliche farbige Lerndokumentation fuer den gesamten Q-Learning-Ablauf erstellen
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
- `ActionConverter` translates a relative action into an absolute `Direction` based on the current facing direction
- `QTable` stores Q-values per state and action and finds the maximum and best action
- `QLearningAi` uses epsilon-greedy to choose between exploration and the best known action
- `QLearningAi.learn(...)` updates Q-values using the learning rate, discount factor, reward, and game-over case
- `RewardCalculator` returns `-100.0` for death, `10.0` for an apple, and `-0.1` for a regular step
- `Trainer` connects StateReader, QLearningAi, ActionConverter, Game, and RewardCalculator into a complete training step
- Episodes use a step limit, reset after completion, and retain their shared Q-table
- Seven QTable tests verify initial values, separate states, storage, and evaluation
- Three ActionConverter tests verify all twelve combinations of four directions and three relative actions
- Five QLearningAi tests verify action selection, the learning formula, game over, and separate action values
- Three RewardCalculator tests verify death, apple, and regular movement
- Four Trainer tests verify movement, rewards, Q-value updates, reset, and the step limit
- A total of 35 JUnit tests verify the current game and AI logic
- `Main` trains the Q-learning AI for 1,000 episodes and displays progress, average, best score, and Q-values

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
current Direction + RelativeAction -> ActionConverter -> new Direction
QLearningAi -> epsilon-greedy -> random or best RelativeAction
oldState + action + reward + newState -> Q-learning formula -> new Q-value
old score + new score + game over -> RewardCalculator -> reward
Trainer -> execute action -> calculate reward -> learn Q-value -> next episode
```

The Q-table is the learning AI's memory. `QLearningAi` selects actions and turns experiences into new Q-values. The `RewardCalculator` evaluates game steps, and the `Trainer` connects every component to the running game. `Main` now performs real automatic training for the first time.

The learning formula is:

```text
targetQ = reward + gamma * maxFutureQ
newQ = oldQ + alpha * (targetQ - oldQ)
```

At game over, `targetQ` consists only of `reward` because no future action exists.

### Next Steps

- Save and load the Q-table so training can later be continued
- Expose training parameters and progress data to a backend
- Create detailed visual learning documentation for the complete Q-learning flow
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

Deutsch: Die bisherigen Meilensteine stehen in `CHANGELOG.md`. Aktuelle Tags: `v0.1.0` fuer die Spiellogik, `v0.2.0` fuer die RandomAI-Demo, `v0.2.1` fuer die direkt startbare JAR und `v0.3.0` fuer das erste vollstaendige Q-Learning-Training.

English: The current milestones are listed in `CHANGELOG.md`. Current tags: `v0.1.0` for game logic, `v0.2.0` for the RandomAI demo, `v0.2.1` for the directly executable JAR, and `v0.3.0` for the first complete Q-learning training loop.

---

## Lizenz / License

Deutsch: Dieses Projekt steht unter der MIT-Lizenz. Details stehen in der Datei `LICENSE`.

English: This project is licensed under the MIT License. Details are available in the `LICENSE` file.
