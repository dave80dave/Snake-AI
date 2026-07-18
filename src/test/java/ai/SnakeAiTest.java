package ai;

import at.orter.snake.Direction;
import at.orter.snake.Food;
import at.orter.snake.Playground;
import at.orter.snake.Position;
import at.orter.snake.Snake;
import at.orter.snake.ai.SnakeAi;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SnakeAiTest {

    @Test
    void detectsWallCollision() {
        SnakeAi snakeAi = new SnakeAi();
        Playground playground = new Playground(10, 10);
        // DE: x = 9 ist das letzte erlaubte Feld bei Breite 10.
        // EN: x = 9 is the last allowed field with width 10.
        Snake snake = new Snake(new Position(9, 5));

        boolean collidesWithWall = snakeAi.collidesWithWall(playground, Direction.RIGHT, snake);

        assertTrue(collidesWithWall);
    }

    @Test
    void detectsNoWallCollisionInsidePlayground() {
        SnakeAi snakeAi = new SnakeAi();
        Playground playground = new Playground(10, 10);
        // DE: Von der Mitte aus bleibt der naechste Schritt nach rechts im Spielfeld.
        // EN: From the middle, the next step to the right stays inside the playground.
        Snake snake = new Snake(new Position(5, 5));

        boolean collidesWithWall = snakeAi.collidesWithWall(playground, Direction.RIGHT, snake);

        assertFalse(collidesWithWall);
    }

    @Test
    void detectsSnakeBodyCollision() {
        SnakeAi snakeAi = new SnakeAi();
        Snake snake = new Snake(new Position(1, 1));
        snake.setSnake(List.of(
                new Position(1, 1),
                new Position(2, 1),
                new Position(3, 1)
        ));
        Food food = new Food(new Position(9, 9));

        boolean collidesWithSnake = snakeAi.collidesWithSnake(Direction.RIGHT, snake, food);

        assertTrue(collidesWithSnake);
    }

    @Test
    void allowsMovingIntoTailWhenSnakeDoesNotGrow() {
        SnakeAi snakeAi = new SnakeAi();
        Snake snake = new Snake(new Position(1, 1));
        snake.setSnake(List.of(
                new Position(1, 1),
                new Position(1, 0),
                new Position(2, 1)
        ));
        Food food = new Food(new Position(9, 9));

        boolean collidesWithSnake = snakeAi.collidesWithSnake(Direction.RIGHT, snake, food);

        assertFalse(collidesWithSnake);
    }

    @Test
    void detectsTailCollisionWhenSnakeGrows() {
        SnakeAi snakeAi = new SnakeAi();
        Snake snake = new Snake(new Position(1, 1));
        snake.setSnake(List.of(
                new Position(1, 1),
                new Position(1, 0),
                new Position(2, 1)
        ));
        Food food = new Food(new Position(2, 1));

        boolean collidesWithSnake = snakeAi.collidesWithSnake(Direction.RIGHT, snake, food);

        assertTrue(collidesWithSnake);
    }
}
