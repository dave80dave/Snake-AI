package ai;

import at.orter.snake.Direction;
import at.orter.snake.ai.ActionConverter;
import at.orter.snake.ai.RelativeAction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActionConverterTest {

    private final ActionConverter actionConverter = new ActionConverter();

    @Test
    void straightKeepsCurrentDirection() {
        // STRAIGHT dreht die Schlange nicht. Deshalb muss jede der vier
        // möglichen Ausgangsrichtungen unverändert zurückgegeben werden.
        assertEquals(Direction.UP,
                actionConverter.convert(Direction.UP, RelativeAction.STRAIGHT));
        assertEquals(Direction.RIGHT,
                actionConverter.convert(Direction.RIGHT, RelativeAction.STRAIGHT));
        assertEquals(Direction.DOWN,
                actionConverter.convert(Direction.DOWN, RelativeAction.STRAIGHT));
        assertEquals(Direction.LEFT,
                actionConverter.convert(Direction.LEFT, RelativeAction.STRAIGHT));
    }

    @Test
    void turnLeftUsesCurrentDirection() {
        // TURN_LEFT bedeutet nicht immer Direction.LEFT. Das Ergebnis hängt
        // davon ab, wohin die Schlange vor dem Abbiegen schaut.
        assertEquals(Direction.LEFT,
                actionConverter.convert(Direction.UP, RelativeAction.TURN_LEFT));
        assertEquals(Direction.UP,
                actionConverter.convert(Direction.RIGHT, RelativeAction.TURN_LEFT));
        assertEquals(Direction.RIGHT,
                actionConverter.convert(Direction.DOWN, RelativeAction.TURN_LEFT));
        assertEquals(Direction.DOWN,
                actionConverter.convert(Direction.LEFT, RelativeAction.TURN_LEFT));
    }

    @Test
    void turnRightUsesCurrentDirection() {
        // Auch TURN_RIGHT beschreibt eine Drehung aus Sicht der Schlange.
        // Geprüft werden wieder alle vier möglichen Ausgangsrichtungen.
        assertEquals(Direction.RIGHT,
                actionConverter.convert(Direction.UP, RelativeAction.TURN_RIGHT));
        assertEquals(Direction.DOWN,
                actionConverter.convert(Direction.RIGHT, RelativeAction.TURN_RIGHT));
        assertEquals(Direction.LEFT,
                actionConverter.convert(Direction.DOWN, RelativeAction.TURN_RIGHT));
        assertEquals(Direction.UP,
                actionConverter.convert(Direction.LEFT, RelativeAction.TURN_RIGHT));
    }
}
