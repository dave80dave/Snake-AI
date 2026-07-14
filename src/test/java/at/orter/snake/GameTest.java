package at.orter.snake;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {

    @Test
    void tickMovesSnakeWithoutApple() {
        Playground playground = new Playground(26, 25);
        Snake snake = new Snake(new Position(5, 5));
        Food food = new Food(new Position(9, 9));
        Game game = new Game(playground, snake, food);

        game.tick();

        assertEquals(new Position(6, 5), snake.getSnakePosition().getFirst());
        assertEquals(1, snake.getSnakePosition().size());
        assertFalse(game.isGameOver());
    }

    @Test
    void tickGrowsSnakeWhenAppleIsOnNextHeadPosition() {
        Playground playground = new Playground(26, 25);
        Snake snake = new Snake(new Position(5, 5));
        Food food = new Food(new Position(6, 5));
        Game game = new Game(playground, snake, food);

        game.tick();

        assertEquals(new Position(6, 5), snake.getSnakePosition().getFirst());
        assertEquals(2, snake.getSnakePosition().size());
        assertFalse(game.isGameOver());
    }

    @Test
    void tickSetsGameOverWhenSnakeWouldHitWall() {
        Playground playground = new Playground(26, 25);
        Snake snake = new Snake(new Position(25, 5));
        Food food = new Food(new Position(9, 9));
        Game game = new Game(playground, snake, food);

        game.tick();

        assertTrue(game.isGameOver());
        assertEquals(new Position(25, 5), snake.getSnakePosition().getFirst());
        assertEquals(1, snake.getSnakePosition().size());
    }

    @Test
    void changingDirectionBeforeWallAvoidsGameOver() {
        Playground playground = new Playground(50, 50);
        Snake snake = new Snake(new Position(49, 25));
        Food food = new Food(new Position(10, 10));
        Game game = new Game(playground, snake, food);

        game.changeDirection(Direction.UP);
        game.tick();

        assertFalse(game.isGameOver());
        assertEquals(new Position(49, 24), snake.getSnakePosition().getFirst());
    }

    @Test
    void tickSetsGameOverWhenSnakeWouldHitItself() {
        Playground playground = new Playground(10, 10);
        Snake snake = new Snake(new Position(5, 5));
        snake.setSnake(List.of(
                new Position(5, 5),
                new Position(6, 5),
                new Position(6, 6),
                new Position(5, 6),
                new Position(4, 6)
        ));
        Food food = new Food(new Position(9, 9));
        Game game = new Game(playground, snake, food);

        game.changeDirection(Direction.DOWN);
        game.tick();

        assertTrue(game.isGameOver());
        assertEquals(new Position(5, 5), snake.getSnakePosition().getFirst());
    }

}
