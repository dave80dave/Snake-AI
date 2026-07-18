package at.orter.snake.ai;

import at.orter.snake.Direction;
import at.orter.snake.Food;
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

    public boolean collidesWithSnake(Direction direction, Snake snake, Food food) {
        // DE: nextHead ist die Position, die der Kopf im naechsten Schritt erreichen wuerde.
        // EN: nextHead is the position the head would reach on the next step.
        Position nextHead = snake.getNextHeadPosition(direction);
        List<Position> snakeList = snake.getSnakePosition();
        boolean grows = willGrow(direction, snake, food);

        // DE: Beim Wachsen bleibt der Schwanz liegen, deshalb wird die ganze Schlange geprueft.
        // EN: When growing, the tail stays in place, so the whole snake is checked.
        int lastIndexToCheck = snakeList.size();

        // DE: Ohne Wachstum verschwindet der Schwanz, deshalb wird das letzte Element ausgelassen.
        // EN: Without growth, the tail moves away, so the last element is skipped.
        if (!grows) {
            lastIndexToCheck = snakeList.size() - 1;
        }

        for (int i = 0; i < lastIndexToCheck; i++) {
            Position bodyPart = snakeList.get(i);

            if (bodyPart.equals(nextHead)) {
                return true;
            }
        }
        return false;
    }

    public boolean willGrow(Direction direction, Snake snake, Food food) {
        // DE: Wachstum passiert, wenn der naechste Kopf genau auf dem Apfel landet.
        // EN: Growth happens when the next head lands exactly on the apple.
        Position nextHead = snake.getNextHeadPosition(direction);
        Position foodApplePosition = food.getApplePosition();

        return nextHead.equals(foodApplePosition);
    }
}
