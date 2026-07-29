package ai;

import at.orter.snake.ai.QLearningAi;
import at.orter.snake.ai.QTable;
import at.orter.snake.ai.RelativeAction;
import at.orter.snake.ai.SnakeState;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QLearningAiTest {

    @Test
    void learnUpdatesQValue() {
        // DE: Die Q-Tabelle ist das Gedaechtnis, das durch learn(...) veraendert wird.
        // EN: The Q-table is the memory that is changed by learn(...).
        QTable qTable = new QTable();

        // DE: oldState beschreibt die Situation vor der ausgefuehrten Aktion.
        // EN: oldState describes the situation before the action was executed.
        SnakeState oldState = new SnakeState(
                false,
                false,
                false,
                false,
                false,
                false,
                true
        );

        // DE: newState beschreibt die Situation nach der ausgefuehrten Aktion.
        //     dangerStraight unterscheidet ihn bewusst vom oldState.
        // EN: newState describes the situation after the action was executed.
        //     dangerStraight deliberately makes it different from oldState.
        SnakeState newState = new SnakeState(
                true,
                false,
                false,
                false,
                false,
                false,
                true
        );

        RelativeAction action = RelativeAction.TURN_LEFT;

        // DE: Die getestete Aktion besitzt im alten State bisher den Q-Wert 2.0.
        // EN: The tested action currently has a Q-value of 2.0 in the old state.
        qTable.setQValue(oldState, action, 2.0);

        // DE: Im neuen State ist 5.0 die beste zukuenftige Handlungsmoeglichkeit.
        // EN: In the new state, 5.0 is the best future action value.
        qTable.setQValue(newState, RelativeAction.STRAIGHT, 5.0);

        // DE: epsilon beeinflusst learn(...) nicht. alpha ist die Lernrate und
        //     gamma bestimmt die Bedeutung des besten zukuenftigen Q-Werts.
        // EN: epsilon does not affect learn(...). alpha is the learning rate,
        //     and gamma controls the importance of the best future Q-value.
        QLearningAi qLearningAi = new QLearningAi(
                qTable,
                0.0,
                0.1,
                0.9
        );

        qLearningAi.learn(oldState, action, 10.0, newState, false);

        // DE: targetQ = 10.0 + (0.9 * 5.0) = 14.5
        //     newQ    = 2.0 + 0.1 * (14.5 - 2.0) = 3.25
        // EN: targetQ = 10.0 + (0.9 * 5.0) = 14.5
        //     newQ    = 2.0 + 0.1 * (14.5 - 2.0) = 3.25
        assertEquals(
                3.25,
                qTable.getQValue(oldState, action),
                0.0001
        );
    }

    @Test
    void learnIgnoresFutureQValueWhenGameIsOver() {
        QTable qTable = new QTable();
        SnakeState oldState = createState(false);
        SnakeState newState = createState(true);
        RelativeAction action = RelativeAction.TURN_LEFT;

        qTable.setQValue(oldState, action, 2.0);

        // DE: Der absichtlich sehr hohe zukuenftige Wert darf bei Game Over
        //     keinen Einfluss auf die Berechnung haben.
        // EN: This deliberately high future value must not influence the
        //     calculation when the game is over.
        qTable.setQValue(newState, RelativeAction.STRAIGHT, 999.0);

        QLearningAi qLearningAi = new QLearningAi(
                qTable,
                0.0,
                0.1,
                0.9
        );

        qLearningAi.learn(oldState, action, -100.0, newState, true);

        // DE: targetQ = -100.0, weil nach Game Over keine Zukunft existiert.
        //     newQ = 2.0 + 0.1 * (-100.0 - 2.0) = -8.2
        // EN: targetQ = -100.0 because no future exists after game over.
        //     newQ = 2.0 + 0.1 * (-100.0 - 2.0) = -8.2
        assertEquals(
                -8.2,
                qTable.getQValue(oldState, action),
                0.0001
        );
    }

    @Test
    void chooseActionUsesBestKnownActionWhenEpsilonIsZero() {
        QTable qTable = new QTable();
        SnakeState state = createState(false);

        qTable.setQValue(state, RelativeAction.STRAIGHT, 1.0);
        qTable.setQValue(state, RelativeAction.TURN_LEFT, 8.0);
        qTable.setQValue(state, RelativeAction.TURN_RIGHT, 3.0);

        // DE: epsilon = 0.0 schaltet zufaelliges Ausprobieren aus.
        // EN: epsilon = 0.0 disables random exploration.
        QLearningAi qLearningAi = new QLearningAi(
                qTable,
                0.0,
                0.1,
                0.9
        );

        assertEquals(
                RelativeAction.TURN_LEFT,
                qLearningAi.chooseAction(state)
        );
    }

    @Test
    void chooseActionExploresWhenEpsilonIsOne() {
        QTable qTable = new QTable();
        SnakeState state = createState(false);

        // DE: Ohne Exploration waere TURN_LEFT immer die beste bekannte Action.
        // EN: Without exploration, TURN_LEFT would always be the best known action.
        qTable.setQValue(state, RelativeAction.STRAIGHT, 1.0);
        qTable.setQValue(state, RelativeAction.TURN_LEFT, 8.0);
        qTable.setQValue(state, RelativeAction.TURN_RIGHT, 3.0);

        // DE: epsilon = 1.0 bedeutet, dass immer zufaellig ausprobiert wird.
        // EN: epsilon = 1.0 means that exploration is always random.
        QLearningAi qLearningAi = new QLearningAi(
                qTable,
                1.0,
                0.1,
                0.9
        );

        // DE: Ein fester Seed macht die Zufallsfolge im Test wiederholbar.
        // EN: A fixed seed makes the random sequence repeatable in the test.
        qLearningAi.getRandom().setSeed(12345L);

        Set<RelativeAction> chosenActions = new HashSet<>();
        for (int i = 0; i < 30; i++) {
            chosenActions.add(qLearningAi.chooseAction(state));
        }

        // DE: Mehr als eine gewaehlte Action zeigt, dass nicht immer nur die
        //     beste bekannte Action TURN_LEFT verwendet wurde.
        // EN: More than one chosen action proves that the AI did not always
        //     use only the best known action, TURN_LEFT.
        assertTrue(chosenActions.size() > 1);
    }

    @Test
    void learnChangesOnlyExecutedAction() {
        QTable qTable = new QTable();
        SnakeState oldState = createState(false);
        SnakeState newState = createState(true);

        qTable.setQValue(oldState, RelativeAction.STRAIGHT, 7.0);
        qTable.setQValue(oldState, RelativeAction.TURN_LEFT, 2.0);
        qTable.setQValue(oldState, RelativeAction.TURN_RIGHT, -4.0);
        qTable.setQValue(newState, RelativeAction.STRAIGHT, 5.0);

        QLearningAi qLearningAi = new QLearningAi(
                qTable,
                0.0,
                0.1,
                0.9
        );

        qLearningAi.learn(
                oldState,
                RelativeAction.TURN_LEFT,
                10.0,
                newState,
                false
        );

        // DE: Nur TURN_LEFT wurde ausgefuehrt und darf deshalb gelernt werden.
        //     Die Werte der beiden anderen Actions muessen unveraendert bleiben.
        // EN: Only TURN_LEFT was executed and may therefore be learned.
        //     The values of the other two actions must remain unchanged.
        assertEquals(
                7.0,
                qTable.getQValue(oldState, RelativeAction.STRAIGHT),
                0.0001
        );
        assertEquals(
                -4.0,
                qTable.getQValue(oldState, RelativeAction.TURN_RIGHT),
                0.0001
        );
    }

    private SnakeState createState(boolean dangerStraight) {
        // DE: Nur dangerStraight wird veraendert, damit oldState und newState
        //     sicher zwei verschiedene Schluessel in der Q-Tabelle sind.
        // EN: Only dangerStraight changes so oldState and newState are
        //     guaranteed to be different keys in the Q-table.
        return new SnakeState(
                dangerStraight,
                false,
                false,
                false,
                false,
                false,
                true
        );
    }
}
