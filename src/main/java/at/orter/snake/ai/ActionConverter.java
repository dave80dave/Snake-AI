package at.orter.snake.ai;

import at.orter.snake.Direction;

public class ActionConverter {

    public Direction convert(Direction currentDirection, RelativeAction action) {
        return switch (action) {
            // DE: Geradeaus bedeutet: Die bisherige absolute Richtung bleibt gleich.
            // EN: Straight means that the previous absolute direction stays the same.
            case STRAIGHT -> currentDirection;

            // DE: Links und rechts sind relative Aktionen. Deshalb muss zusätzlich
            //     geprüft werden, wohin die Schlange gerade schaut.
            // EN: Left and right are relative actions. Therefore, the snake's
            //     current facing direction must also be checked.
            case TURN_LEFT -> switch (currentDirection) {
                case UP -> Direction.LEFT;
                case RIGHT -> Direction.UP;
                case DOWN -> Direction.RIGHT;
                case LEFT -> Direction.DOWN;
            };
            case TURN_RIGHT -> switch (currentDirection) {
                case UP -> Direction.RIGHT;
                case RIGHT -> Direction.DOWN;
                case DOWN -> Direction.LEFT;
                case LEFT -> Direction.UP;
            };
        };
    }
}
