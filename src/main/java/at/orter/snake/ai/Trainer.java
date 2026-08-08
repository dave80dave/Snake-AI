package at.orter.snake.ai;

import at.orter.snake.Direction;
import at.orter.snake.Game;

public class Trainer {
    // DE: Der Trainer verbindet das Spiel mit allen bereits fertigen AI-Bausteinen.
    // EN: The trainer connects the game with all previously completed AI components.
    private final Game game;
    private final QLearningAi qLearningAi;
    private final ActionConverter actionConverter;
    private final RewardCalculator rewardCalculator;
    private final StateReader stateReader;

    public Trainer(
            Game game,
            QLearningAi qLearningAi,
            ActionConverter actionConverter,
            RewardCalculator rewardCalculator,
            StateReader stateReader
    ) {
        this.game = game;
        this.qLearningAi = qLearningAi;
        this.actionConverter = actionConverter;
        this.rewardCalculator = rewardCalculator;
        this.stateReader = stateReader;
    }

    public void trainStep() {
        // DE: Vor der Bewegung werden State und Score gespeichert. Nur dadurch
        //     kann die AI spaeter Vorher und Nachher miteinander vergleichen.
        // EN: State and score are stored before movement. This allows the AI
        //     to compare the situation before and after the action.
        SnakeState oldState = stateReader.readState(game);
        int oldScore = game.getScore();

        // DE: Die AI waehlt eine relative Aktion. Der Converter uebersetzt sie
        //     anhand der aktuellen Blickrichtung in eine absolute Direction.
        // EN: The AI chooses a relative action. The converter translates it
        //     into an absolute Direction using the current facing direction.
        RelativeAction action = qLearningAi.chooseAction(oldState);
        Direction newDirection = actionConverter.convert(game.getCurrentDirection(), action);

        // DE: Erst wird die Richtung gesetzt, danach fuehrt tick() genau einen
        //     Spielschritt samt Bewegung, Apfel- und Kollisionspruefung aus.
        // EN: First the direction is set, then tick() performs exactly one game
        //     step including movement, apple detection, and collision checks.
        game.changeDirection(newDirection);
        game.tick();

        // DE: Nach dem Tick wird die neue Situation gelesen und bewertet.
        // EN: After the tick, the new situation is read and evaluated.
        SnakeState newState = stateReader.readState(game);
        int newScore = game.getScore();
        boolean gameOver = game.isGameOver();
        double reward = rewardCalculator.calculateReward(oldScore, newScore, gameOver);

        // DE: Die gesamte Erfahrung wird an die Q-Learning-Formel uebergeben.
        // EN: The complete experience is passed to the Q-learning formula.
        qLearningAi.learn(oldState, action, reward, newState, gameOver);
    }

    public int trainEpisode(int maxSteps) {
        if (maxSteps <= 0) {
            throw new IllegalArgumentException("maxSteps must be greater than zero");
        }

        // DE: Das Schrittlimit verhindert, dass eine anfangs unerfahrene AI
        //     endlos im Kreis laeuft und eine Episode niemals beendet.
        // EN: The step limit prevents an inexperienced AI from circling forever
        //     and never finishing an episode.
        int steps = 0;
        while (!game.isGameOver() && steps < maxSteps) {
            trainStep();
            steps++;
        }

        // DE: Der erreichte Score wird vor dem Reset gespeichert und zurueckgegeben.
        // EN: The achieved score is stored and returned before the game is reset.
        int episodeScore = game.getScore();
        game.resetGame();
        return episodeScore;
    }

    public int trainEpisodes(int episodeCount, int maxStepsPerEpisode) {
        if (episodeCount <= 0) {
            throw new IllegalArgumentException("episodeCount must be greater than zero");
        }

        // DE: Jede Episode verwendet dieselbe Q-Tabelle. Dadurch bleiben die
        //     Erfahrungen beim Reset des Spiels erhalten.
        // EN: Every episode uses the same Q-table, so learned experiences remain
        //     available when the game itself is reset.
        int bestScore = 0;
        for (int episode = 0; episode < episodeCount; episode++) {
            int episodeScore = trainEpisode(maxStepsPerEpisode);
            bestScore = Math.max(bestScore, episodeScore);
        }

        return bestScore;
    }
}
