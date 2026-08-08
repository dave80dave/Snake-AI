package at.orter.snake.ai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.EnumMap;
import java.util.Map;

public class QTableStorage {
    private static final String HEADER =
            "dangerStraight,dangerLeft,dangerRight,foodUp,foodDown,foodLeft,foodRight,STRAIGHT,TURN_LEFT,TURN_RIGHT";

    private final Path filePath;

    public QTableStorage(Path filePath) {
        // DE: Der Pfad wird von aussen uebergeben. Dadurch kann Main die echte
        //     Speicherdatei und ein Test eine temporaere Datei verwenden.
        // EN: The path is supplied from outside. Main can therefore use the real
        //     storage file while a test can use a temporary file.
        this.filePath = filePath;
    }

    public QTable load() throws IOException {
        QTable qTable = new QTable();

        // DE: Beim allerersten Start existiert noch keine Datei. Das ist kein
        //     Fehler: Die AI beginnt dann absichtlich mit einer leeren Q-Tabelle.
        // EN: No file exists on the very first run. This is not an error: the AI
        //     deliberately starts with an empty Q-table in that case.
        if (Files.notExists(filePath)) {
            return qTable;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String header = reader.readLine();

            if (!HEADER.equals(header)) {
                throw new IOException("Unknown Q-table file format: " + filePath);
            }

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                loadRow(qTable, line, lineNumber);
            }
        }

        return qTable;
    }

    private void loadRow(QTable qTable, String line, int lineNumber) throws IOException {
        String[] values = line.split(",", -1);

        if (values.length != 10) {
            throw new IOException("Invalid Q-table row at line " + lineNumber);
        }

        try {
            SnakeState state = new SnakeState(
                    parseBoolean(values[0]),
                    parseBoolean(values[1]),
                    parseBoolean(values[2]),
                    parseBoolean(values[3]),
                    parseBoolean(values[4]),
                    parseBoolean(values[5]),
                    parseBoolean(values[6])
            );

            qTable.setQValue(state, RelativeAction.STRAIGHT, Double.parseDouble(values[7]));
            qTable.setQValue(state, RelativeAction.TURN_LEFT, Double.parseDouble(values[8]));
            qTable.setQValue(state, RelativeAction.TURN_RIGHT, Double.parseDouble(values[9]));
        } catch (IllegalArgumentException exception) {
            throw new IOException("Invalid Q-table value at line " + lineNumber, exception);
        }
    }

    private boolean parseBoolean(String value) {
        if ("true".equals(value)) {
            return true;
        }
        if ("false".equals(value)) {
            return false;
        }
        throw new IllegalArgumentException("Expected true or false but got: " + value);
    }

    public void save(QTable qTable) throws IOException {
        Path absolutePath = filePath.toAbsolutePath();
        Path parent = absolutePath.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        // DE: Zuerst wird eine temporaere Datei geschrieben. Erst wenn sie fertig
        //     ist, ersetzt sie die alte Datei. So bleibt bei einem Abbruch moeglichst
        //     die letzte vollstaendige Q-Tabelle erhalten.
        // EN: A temporary file is written first. It replaces the old file only after
        //     completion, preserving the last complete Q-table if writing is interrupted.
        Path temporaryFile = absolutePath.resolveSibling(absolutePath.getFileName() + ".tmp");
        Map<SnakeState, EnumMap<RelativeAction, Double>> snapshot = qTable.createSnapshot();

        try (BufferedWriter writer = Files.newBufferedWriter(temporaryFile)) {
            writer.write(HEADER);
            writer.newLine();

            for (Map.Entry<SnakeState, EnumMap<RelativeAction, Double>> entry : snapshot.entrySet()) {
                writeRow(writer, entry.getKey(), entry.getValue());
            }
        }

        try {
            Files.move(
                    temporaryFile,
                    absolutePath,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE
            );
        } catch (IOException exception) {
            // DE: Nicht jedes Dateisystem unterstuetzt atomisches Verschieben.
            // EN: Not every file system supports atomic moves.
            Files.move(temporaryFile, absolutePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void writeRow(
            BufferedWriter writer,
            SnakeState state,
            EnumMap<RelativeAction, Double> actionValues
    ) throws IOException {
        writer.write(String.join(",",
                Boolean.toString(state.isDangerStraight()),
                Boolean.toString(state.isDangerLeft()),
                Boolean.toString(state.isDangerRight()),
                Boolean.toString(state.isFoodUp()),
                Boolean.toString(state.isFoodDown()),
                Boolean.toString(state.isFoodLeft()),
                Boolean.toString(state.isFoodRight()),
                Double.toString(actionValues.get(RelativeAction.STRAIGHT)),
                Double.toString(actionValues.get(RelativeAction.TURN_LEFT)),
                Double.toString(actionValues.get(RelativeAction.TURN_RIGHT))
        ));
        writer.newLine();
    }

    public Path getFilePath() {
        return filePath;
    }
}
