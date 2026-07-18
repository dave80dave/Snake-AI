package at.orter.snake.ai;

import at.orter.snake.Direction;
import at.orter.snake.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAi {
    private final Random random = new Random();
    private final SnakeAi snakeAi = new SnakeAi();

    public Direction chooseDirection(Game game) {
        // DE: Zuerst werden nur Richtungen geholt, die keine direkte Gegenrichtung sind.
        // EN: First get only directions that are not direct opposites.
        List<Direction> possibleDirections = snakeAi.getPossibleDirections(game.getCurrentDirection());

        // DE: In diese Liste kommen nur Richtungen ohne Wand- und Selbstkollision.
        // EN: This list only stores directions without wall or self-collision.
        List<Direction> safeDirections = new ArrayList<>();

        for (Direction direction : possibleDirections) {
            boolean wallCollision = snakeAi.collidesWithWall(game.getPlayground(), direction, game.getSnake());
            boolean snakeCollision = snakeAi.collidesWithSnake(direction, game.getSnake(), game.getFood());

            if (!wallCollision && !snakeCollision) {
                safeDirections.add(direction);
            }
        }

        // DE: Wenn sichere Richtungen existieren, waehlt RandomAi nur aus diesen.
        // EN: If safe directions exist, RandomAi only chooses from them.
        if (!safeDirections.isEmpty()) {
            int randomIndex = random.nextInt(safeDirections.size());
            return safeDirections.get(randomIndex);
        }

        // DE: Falls keine sichere Richtung existiert, wird eine moegliche Richtung als Notfall gewaehlt.
        // EN: If no safe direction exists, choose a possible direction as a fallback.
        int randomIndex = random.nextInt(possibleDirections.size());
        return possibleDirections.get(randomIndex);
    }
}
