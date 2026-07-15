package ai;

import at.orter.snake.Direction;
import at.orter.snake.Playground;
import at.orter.snake.Position;
import at.orter.snake.Snake;

import java.util.List;

public class SnakeAi {

    public List<Direction> getPossibleDirections(Direction currentDirection) {
        // DE: Die AI darf nicht direkt in die Gegenrichtung der aktuellen Bewegung fahren.
        // EN: The AI must not move directly opposite to the current movement direction.
        if (currentDirection == Direction.RIGHT) {
            return List.of(Direction.UP, Direction.DOWN, Direction.RIGHT);
        }
        if (currentDirection == Direction.LEFT) {
            return List.of(Direction.UP, Direction.DOWN, Direction.LEFT);
        }
        if (currentDirection == Direction.UP) {
            return List.of(Direction.UP, Direction.RIGHT, Direction.LEFT);
        }
        return List.of(Direction.DOWN, Direction.RIGHT, Direction.LEFT);
    }

    public boolean collidesWithWall(Playground playground, Direction direction, Snake snake) {
        // DE: Erst wird berechnet, wo der Kopf nach dem naechsten Schritt waere.
        // EN: First calculate where the head would be after the next step.
        Position nextHead = snake.getNextHeadPosition(direction);

        // DE: x prueft die linke und rechte Wand.
        // EN: x checks the left and right wall.
        if (nextHead.getX() < 0 || nextHead.getX() >= playground.getWidth()) {
            return true;
        }

        // DE: y prueft die obere und untere Wand.
        // EN: y checks the top and bottom wall.
        if (nextHead.getY() < 0 || nextHead.getY() >= playground.getHeight()) {
            return true;
        }

        return false;
    }
}
