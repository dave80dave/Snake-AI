package ai;

import at.orter.snake.Direction;
import at.orter.snake.Playground;
import at.orter.snake.Position;
import at.orter.snake.Snake;
import org.junit.jupiter.api.Test;

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
}
