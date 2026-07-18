package ai;

import at.orter.snake.Direction;
import at.orter.snake.Game;

import java.util.List;
import java.util.Random;

public class RandomAi {
    private final Random random = new Random();
    private final SnakeAi snakeAi = new SnakeAi();

    public Direction chooseDirection(Game game) {
        // DE: RandomAi fragt zuerst, welche Richtungen aktuell erlaubt sind.
        // EN: RandomAi first asks which directions are currently allowed.
        List<Direction> possibleDirections = snakeAi.getPossibleDirections(game.getCurrentDirection());
        int randomIndex = random.nextInt(possibleDirections.size());

        return possibleDirections.get(randomIndex);
    }
}
