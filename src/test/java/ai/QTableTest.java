package ai;

import at.orter.snake.ai.QTable;
import at.orter.snake.ai.RelativeAction;
import at.orter.snake.ai.SnakeState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QTableTest {

    // DE: Die Tests pruefen Initialisierung, Speicherung und Auswertung der Q-Tabelle.
    // EN: These tests verify Q-table initialization, storage, and evaluation.

    @Test
    void unknownStateStartsWithZeroForEveryAction() {
        QTable qTable = new QTable();
        SnakeState state = createState(false);

        for (RelativeAction action : RelativeAction.values()) {
            assertEquals(0.0, qTable.getQValue(state, action), 0.0001);
        }
    }

    @Test
    void setQValueStoresValueForStateAndAction() {
        QTable qTable = new QTable();
        SnakeState state = createState(false);

        qTable.setQValue(state, RelativeAction.TURN_LEFT, 3.52);

        assertEquals(3.52, qTable.getQValue(state, RelativeAction.TURN_LEFT), 0.0001);
    }

    @Test
    void settingOneActionDoesNotChangeOtherActions() {
        QTable qTable = new QTable();
        SnakeState state = createState(false);

        qTable.setQValue(state, RelativeAction.TURN_LEFT, 3.52);

        assertEquals(0.0, qTable.getQValue(state, RelativeAction.STRAIGHT), 0.0001);
        assertEquals(0.0, qTable.getQValue(state, RelativeAction.TURN_RIGHT), 0.0001);
    }

    @Test
    void differentStatesHaveSeparateQValues() {
        QTable qTable = new QTable();
        SnakeState safeState = createState(false);
        SnakeState dangerousState = createState(true);

        qTable.setQValue(safeState, RelativeAction.STRAIGHT, 5.0);

        assertEquals(5.0, qTable.getQValue(safeState, RelativeAction.STRAIGHT), 0.0001);
        assertEquals(0.0, qTable.getQValue(dangerousState, RelativeAction.STRAIGHT), 0.0001);
    }

    @Test
    void equalStatesUseTheSameStoredQValues() {
        QTable qTable = new QTable();
        SnakeState firstState = createState(false);
        SnakeState equalState = createState(false);

        qTable.setQValue(firstState, RelativeAction.TURN_RIGHT, 7.1);

        assertEquals(7.1, qTable.getQValue(equalState, RelativeAction.TURN_RIGHT), 0.0001);
    }

    @Test
    void getMaxQValueFindsHighestNegativeValue() {
        QTable qTable = new QTable();
        SnakeState state = createState(false);
        qTable.setQValue(state, RelativeAction.STRAIGHT, -10.0);
        qTable.setQValue(state, RelativeAction.TURN_LEFT, -3.0);
        qTable.setQValue(state, RelativeAction.TURN_RIGHT, -7.0);

        assertEquals(-3.0, qTable.getMaxQValue(state), 0.0001);
    }

    @Test
    void getBestActionReturnsActionWithHighestQValue() {
        QTable qTable = new QTable();
        SnakeState state = createState(false);
        qTable.setQValue(state, RelativeAction.STRAIGHT, 1.0);
        qTable.setQValue(state, RelativeAction.TURN_LEFT, 4.0);
        qTable.setQValue(state, RelativeAction.TURN_RIGHT, 2.0);

        assertEquals(RelativeAction.TURN_LEFT, qTable.getBestAction(state));
    }

    private SnakeState createState(boolean dangerStraight) {
        // DE: Nur dangerStraight wird variiert, damit gleiche und verschiedene
        //     States in den Tests gezielt erzeugt werden koennen.
        // EN: Only dangerStraight varies so the tests can deliberately create
        //     equal and different states.
        return new SnakeState(
                dangerStraight,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }
}
