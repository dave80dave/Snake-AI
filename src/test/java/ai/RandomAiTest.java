package ai;

import at.orter.snake.Direction;
import at.orter.snake.Food;
import at.orter.snake.Game;
import at.orter.snake.Playground;
import at.orter.snake.Position;
import at.orter.snake.Snake;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomAiTest {

    @Test
    void choosesOnlyPossibleDirections() {
        RandomAi randomAi = new RandomAi();
        SnakeAi snakeAi = new SnakeAi();
        Game game = new Game(
                new Playground(10, 10),
                new Snake(new Position(5, 5)),
                new Food(new Position(7, 5))
        );

        List<Direction> possibleDirections = snakeAi.getPossibleDirections(game.getCurrentDirection());
        Direction direction = randomAi.chooseDirection(game);

        assertTrue(possibleDirections.contains(direction));
    }
}
