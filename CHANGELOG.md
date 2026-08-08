# Changelog

## Deutsch

### Unveroeffentlicht - Persistente Q-Tabelle

- `QTableStorage` speichert und laedt States und Q-Werte als lokale CSV-Datei
- `Main` setzt vorhandenes Wissen beim naechsten Start fort und speichert regelmaessig
- Drei neue Tests pruefen fehlende, gueltige und ungueltige Speicherdateien

### v0.3.0 - Q-Learning Training

- `SnakeState` und `StateReader` beschreiben Gefahren sowie die Richtung des Apfels
- `RelativeAction` und `ActionConverter` uebersetzen relative Entscheidungen in echte Bewegungsrichtungen
- `QTable` speichert Q-Werte und findet die beste bekannte Aktion
- `QLearningAi` verwendet Epsilon-Greedy und aktualisiert Werte mit der Q-Learning-Formel
- `RewardCalculator` bewertet Tod, Apfel und normale Bewegung
- `Trainer` verbindet alle AI-Bausteine und trainiert einzelne Schritte sowie vollstaendige Episoden
- `Main` fuehrt 1.000 Trainings-Episoden aus und zeigt den Lernfortschritt
- 35 erfolgreiche JUnit-Tests fuer Spiel- und AI-Logik

### v0.2.1 - Executable JAR

- JAR kann direkt mit `java -jar target/Snake-AI-1.0-SNAPSHOT.jar` gestartet werden
- README enthaelt den Build- und Startbefehl

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

### Unreleased - Persistent Q-table

- `QTableStorage` saves and loads states and Q-values as a local CSV file
- `Main` continues existing knowledge on the next start and saves regularly
- Three new tests cover missing, valid, and invalid storage files

### v0.3.0 - Q-Learning Training

- `SnakeState` and `StateReader` describe dangers and the apple direction
- `RelativeAction` and `ActionConverter` translate relative decisions into actual movement directions
- `QTable` stores Q-values and finds the best known action
- `QLearningAi` uses epsilon-greedy and updates values with the Q-learning formula
- `RewardCalculator` evaluates death, apples, and regular movement
- `Trainer` connects all AI components and trains individual steps and complete episodes
- `Main` runs 1,000 training episodes and displays learning progress
- 35 passing JUnit tests for game and AI logic

### v0.2.1 - Executable JAR

- JAR can be started directly with `java -jar target/Snake-AI-1.0-SNAPSHOT.jar`
- README contains the build and run command

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
