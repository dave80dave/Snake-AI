package at.orter.snakebackend;

import at.orter.snake.Direction;
import at.orter.snakebackend.response.GameResponse;
import at.orter.snakebackend.service.GameService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private final GameService gameService = new GameService();

    @Test
    void createsPlayableGame() {
        GameResponse game = gameService.newGame();

        assertEquals(20, game.width());
        assertEquals(20, game.height());
        assertEquals(1, game.snake().size());
        assertEquals(0, game.score());
        assertFalse(game.gameOver());
        assertNotEquals(game.snake().getFirst(), game.food());
    }

    @Test
    void movesSnakeOneCell() {
        GameResponse before = gameService.newGame();
        GameResponse after = gameService.move(Direction.UP);

        assertEquals(before.snake().getFirst().x(), after.snake().getFirst().x());
        assertEquals(before.snake().getFirst().y() - 1, after.snake().getFirst().y());
        assertEquals(Direction.UP, after.direction());
    }

    @Test
    void aiCanPerformOneStep() {
        GameResponse before = gameService.newGame();
        GameResponse after = gameService.aiStep();

        int distance = Math.abs(before.snake().getFirst().x() - after.snake().getFirst().x())
                + Math.abs(before.snake().getFirst().y() - after.snake().getFirst().y());
        assertEquals(1, distance);
        assertFalse(after.gameOver());
    }
}
