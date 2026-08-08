package ai;

import at.orter.snake.Food;
import at.orter.snake.Game;
import at.orter.snake.Playground;
import at.orter.snake.Position;
import at.orter.snake.Snake;
import at.orter.snake.ai.ActionConverter;
import at.orter.snake.ai.QLearningAi;
import at.orter.snake.ai.QTable;
import at.orter.snake.ai.RelativeAction;
import at.orter.snake.ai.RewardCalculator;
import at.orter.snake.ai.SnakeState;
import at.orter.snake.ai.StateReader;
import at.orter.snake.ai.Trainer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TrainerTest {

    @Test
    void trainStepMovesSnakeAndLearnsStepPenalty() {
        Game game = createGame(
                new Playground(5, 5),
                new Position(1, 1),
                new Position(4, 4)
        );
        StateReader stateReader = new StateReader();
        SnakeState oldState = stateReader.readState(game);
        QTable qTable = new QTable();
        Trainer trainer = createTrainer(game, stateReader, qTable);

        trainer.trainStep();

        // DE: Bei gleichen Startwerten waehlt die Q-Tabelle STRAIGHT. Die
        //     Schlange bewegt sich deshalb von (1,1) nach rechts auf (2,1).
        // EN: With equal initial values, the Q-table selects STRAIGHT. The snake
        //     therefore moves right from (1,1) to (2,1).
        assertEquals(
                new Position(2, 1),
                game.getSnake().getSnakePosition().getFirst()
        );

        // DE: alpha = 1.0 und gamma = 0.0 uebernehmen die Schrittstrafe direkt.
        // EN: alpha = 1.0 and gamma = 0.0 apply the step penalty directly.
        assertEquals(
                -0.1,
                qTable.getQValue(oldState, RelativeAction.STRAIGHT),
                0.0001
        );
    }

    @Test
    void trainStepLearnsAppleReward() {
        Game game = createGame(
                new Playground(5, 5),
                new Position(1, 1),
                new Position(2, 1)
        );
        StateReader stateReader = new StateReader();
        SnakeState oldState = stateReader.readState(game);
        QTable qTable = new QTable();
        Trainer trainer = createTrainer(game, stateReader, qTable);

        trainer.trainStep();

        assertEquals(1, game.getScore());
        assertEquals(
                10.0,
                qTable.getQValue(oldState, RelativeAction.STRAIGHT),
                0.0001
        );
    }

    @Test
    void trainEpisodeResetsGameAfterGameOver() {
        Game game = createGame(
                new Playground(2, 2),
                new Position(1, 0),
                new Position(0, 1)
        );
        Trainer trainer = createTrainer(game, new StateReader(), new QTable());

        int score = trainer.trainEpisode(10);

        // DE: Der erste Geradeaus-Schritt trifft die rechte Wand. Anschliessend
        //     setzt trainEpisode(...) das Spiel fuer die naechste Episode zurueck.
        // EN: The first straight step hits the right wall. Afterwards,
        //     trainEpisode(...) resets the game for the next episode.
        assertEquals(0, score);
        assertFalse(game.isGameOver());
        assertEquals(
                new Position(1, 1),
                game.getSnake().getSnakePosition().getFirst()
        );
    }

    @Test
    void trainEpisodeRejectsInvalidStepLimit() {
        Game game = createGame(
                new Playground(5, 5),
                new Position(1, 1),
                new Position(4, 4)
        );
        Trainer trainer = createTrainer(game, new StateReader(), new QTable());

        assertThrows(IllegalArgumentException.class, () -> trainer.trainEpisode(0));
    }

    private Trainer createTrainer(Game game, StateReader stateReader, QTable qTable) {
        // DE: epsilon = 0.0 macht die Aktionswahl vorhersehbar. alpha = 1.0
        //     uebernimmt den Reward sofort, gamma = 0.0 ignoriert die Zukunft.
        // EN: epsilon = 0.0 makes action selection predictable. alpha = 1.0
        //     applies the reward immediately, and gamma = 0.0 ignores the future.
        QLearningAi qLearningAi = new QLearningAi(qTable, 0.0, 1.0, 0.0);

        return new Trainer(
                game,
                qLearningAi,
                new ActionConverter(),
                new RewardCalculator(),
                stateReader
        );
    }

    private Game createGame(
            Playground playground,
            Position snakePosition,
            Position foodPosition
    ) {
        return new Game(
                playground,
                new Snake(snakePosition),
                new Food(foodPosition)
        );
    }
}
