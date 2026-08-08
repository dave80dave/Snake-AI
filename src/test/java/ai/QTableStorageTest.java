package ai;

import at.orter.snake.ai.QTable;
import at.orter.snake.ai.QTableStorage;
import at.orter.snake.ai.RelativeAction;
import at.orter.snake.ai.SnakeState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QTableStorageTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    void missingFileLoadsEmptyQTable() throws IOException {
        QTableStorage storage = new QTableStorage(temporaryDirectory.resolve("qtable.csv"));

        QTable loadedTable = storage.load();

        assertEquals(0, loadedTable.size());
    }

    @Test
    void saveAndLoadPreserveStatesActionsAndValues() throws IOException {
        Path file = temporaryDirectory.resolve("data/qtable.csv");
        QTableStorage storage = new QTableStorage(file);
        QTable originalTable = new QTable();
        SnakeState firstState = createState(false, true);
        SnakeState secondState = createState(true, false);

        originalTable.setQValue(firstState, RelativeAction.STRAIGHT, 2.75);
        originalTable.setQValue(firstState, RelativeAction.TURN_LEFT, -4.5);
        originalTable.setQValue(firstState, RelativeAction.TURN_RIGHT, 9.25);
        originalTable.setQValue(secondState, RelativeAction.STRAIGHT, -100.0);

        storage.save(originalTable);
        QTable loadedTable = storage.load();

        assertEquals(2, loadedTable.size());
        assertEquals(2.75, loadedTable.getQValue(firstState, RelativeAction.STRAIGHT), 0.0001);
        assertEquals(-4.5, loadedTable.getQValue(firstState, RelativeAction.TURN_LEFT), 0.0001);
        assertEquals(9.25, loadedTable.getQValue(firstState, RelativeAction.TURN_RIGHT), 0.0001);
        assertEquals(-100.0, loadedTable.getQValue(secondState, RelativeAction.STRAIGHT), 0.0001);
        assertEquals(0.0, loadedTable.getQValue(secondState, RelativeAction.TURN_LEFT), 0.0001);
    }

    @Test
    void invalidFileIsRejectedInsteadOfSilentlyLosingKnowledge() throws IOException {
        Path file = temporaryDirectory.resolve("qtable.csv");
        Files.writeString(file, "not-a-valid-qtable");
        QTableStorage storage = new QTableStorage(file);

        assertThrows(IOException.class, storage::load);
    }

    private SnakeState createState(boolean dangerStraight, boolean foodRight) {
        // DE: Unterschiedliche Werte erzeugen gezielt zwei getrennte Schluessel.
        // EN: Different values deliberately create two separate keys.
        return new SnakeState(
                dangerStraight,
                false,
                false,
                false,
                false,
                false,
                foodRight
        );
    }
}
