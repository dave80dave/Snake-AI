package at.orter.snake;

import at.orter.snake.ai.ActionConverter;
import at.orter.snake.ai.QLearningAi;
import at.orter.snake.ai.QTable;
import at.orter.snake.ai.RelativeAction;
import at.orter.snake.ai.RewardCalculator;
import at.orter.snake.ai.SnakeState;
import at.orter.snake.ai.StateReader;
import at.orter.snake.ai.Trainer;

public class Main {

    private static final int PLAYGROUND_WIDTH = 12;
    private static final int PLAYGROUND_HEIGHT = 12;
    private static final int EPISODES = 11_000;
    private static final int MAX_STEPS_PER_EPISODE = 500;
    private static final int PROGRESS_INTERVAL = 100;

    public static void main(String[] args) {
        // DE: Zuerst wird ein normales Snake-Spiel aufgebaut. Die AI arbeitet
        //     spaeter mit genau diesem Game und verwendet keine eigene Spiellogik.
        // EN: First, a regular Snake game is created. The AI later works with
        //     this exact Game and does not use separate game logic.
        Playground playground = new Playground(PLAYGROUND_WIDTH, PLAYGROUND_HEIGHT);
        Position startPosition = new Position(PLAYGROUND_WIDTH / 2, PLAYGROUND_HEIGHT / 2);
        Snake snake = new Snake(startPosition);
        Food food = new Food(new Position(startPosition.getX() + 2, startPosition.getY()));
        Game game = new Game(playground, snake, food);

        // DE: qTable bleibt waehrend aller Episoden dasselbe Gedaechtnis.
        //     epsilon = 0.1: 10 Prozent ausprobieren
        //     alpha   = 0.1: schrittweise lernen
        //     gamma   = 0.9: zukuenftige Moeglichkeiten stark beachten
        // EN: qTable remains the same memory throughout all episodes.
        //     epsilon = 0.1: explore 10 percent of the time
        //     alpha   = 0.1: learn gradually
        //     gamma   = 0.9: strongly consider future opportunities
        QTable qTable = new QTable();
        QLearningAi qLearningAi = new QLearningAi(qTable, 0.1, 0.1, 0.9);
        StateReader stateReader = new StateReader();

        Trainer trainer = new Trainer(
                game,
                qLearningAi,
                new ActionConverter(),
                new RewardCalculator(),
                stateReader
        );

        System.out.println("=== Snake Q-Learning Training ===");
        System.out.println("Episodes: " + EPISODES);
        System.out.println("Maximum steps per episode: " + MAX_STEPS_PER_EPISODE);
        System.out.println();

        int bestScore = 0;
        int totalScore = 0;
        int intervalScore = 0;

        // DE: Jede Episode startet nach dem Reset mit einer kurzen Schlange.
        //     Die Q-Tabelle wird nicht zurueckgesetzt und sammelt weiter Erfahrung.
        // EN: Every episode starts with a short snake after reset. The Q-table
        //     is not reset and continues accumulating experience.
        for (int episode = 1; episode <= EPISODES; episode++) {
            int score = trainer.trainEpisode(MAX_STEPS_PER_EPISODE);
            bestScore = Math.max(bestScore, score);
            totalScore += score;
            intervalScore += score;

            if (episode % PROGRESS_INTERVAL == 0) {
                double intervalAverage = (double) intervalScore / PROGRESS_INTERVAL;
                System.out.printf(
                        "Episode %4d | average last %3d: %5.2f | best: %d%n",
                        episode,
                        PROGRESS_INTERVAL,
                        intervalAverage,
                        bestScore
                );
                intervalScore = 0;
            }
        }

        double totalAverage = (double) totalScore / EPISODES;
        SnakeState currentState = stateReader.readState(game);

        System.out.println();
        System.out.println("=== Training Result ===");
        System.out.printf("Average score: %.2f%n", totalAverage);
        System.out.println("Best score: " + bestScore);
        System.out.println();
        System.out.println("Q-values for the current reset state:");

        for (RelativeAction action : RelativeAction.values()) {
            System.out.printf(
                    "%-10s = %.4f%n",
                    action,
                    qTable.getQValue(currentState, action)
            );
        }

        System.out.println("Best known action: " + qTable.getBestAction(currentState));
        System.out.println();
        System.out.println("Note: Q-table persistence will be added next.");
    }
}
