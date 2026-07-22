package at.orter.snake.ai;

import at.orter.snake.Direction;
import at.orter.snake.Game;
import at.orter.snake.Position;

public class StateReader {
    // DE: SnakeAi enthaelt die vorhandenen Pruefungen fuer Wand- und Koerperkollisionen.
    // EN: SnakeAi provides the existing checks for wall and body collisions.
    private final SnakeAi snakeAi = new SnakeAi();

    public SnakeState readState(Game game) {
        // DE: Geradeaus entspricht der aktuellen Richtung. Links und rechts sind davon abhaengig.
        // EN: Straight matches the current direction. Left and right depend on that direction.
        Direction straightDirection = game.getCurrentDirection();
        Direction leftDirection = getLeftDirection(straightDirection);
        Direction rightDirection = getRightDirection(straightDirection);

        Position head = game.getSnake().getSnakePosition().getFirst();
        Position food = game.getFood().getApplePosition();

        boolean foodUp = food.getY() < head.getY();
        boolean foodDown = food.getY() > head.getY();
        boolean foodLeft = food.getX() < head.getX();
        boolean foodRight = food.getX() > head.getX();

        // DE: Jede relative Richtung wird getrennt auf Wand- und Koerperkollision geprueft.
        // EN: Each relative direction is checked separately for wall and body collisions.
        boolean wallDangerStraight = snakeAi.collidesWithWall(
                game.getPlayground(),
                straightDirection,
                game.getSnake()
        );

        boolean bodyDangerStraight = snakeAi.collidesWithSnake(
                straightDirection,
                game.getSnake(),
                game.getFood()
        );

        boolean wallDangerLeft = snakeAi.collidesWithWall(
                game.getPlayground(),
                leftDirection,
                game.getSnake()
        );

        boolean bodyDangerLeft = snakeAi.collidesWithSnake(
                leftDirection,
                game.getSnake(),
                game.getFood()
        );

        boolean wallDangerRight = snakeAi.collidesWithWall(
                game.getPlayground(),
                rightDirection,
                game.getSnake()
        );

        boolean bodyDangerRight = snakeAi.collidesWithSnake(
                rightDirection,
                game.getSnake(),
                game.getFood()
        );

        // DE: Bereits eine der beiden Kollisionsarten macht die Richtung gefaehrlich.
        // EN: Either collision type is enough to make the direction dangerous.
        boolean dangerStraight = wallDangerStraight || bodyDangerStraight;
        boolean dangerLeft = wallDangerLeft || bodyDangerLeft;
        boolean dangerRight = wallDangerRight || bodyDangerRight;

        return new SnakeState(dangerStraight, dangerLeft, dangerRight, foodUp, foodDown, foodLeft, foodRight);
    }

    private Direction getLeftDirection(Direction currentDirection) {
        // DE: Eine Linksdrehung ist relativ zur Blickrichtung, nicht immer Direction.LEFT.
        // EN: A left turn is relative to the facing direction, not always Direction.LEFT.
        return switch (currentDirection) {
            case UP -> Direction.LEFT;
            case RIGHT -> Direction.UP;
            case DOWN -> Direction.RIGHT;
            case LEFT -> Direction.DOWN;
        };
    }

    private Direction getRightDirection(Direction currentDirection) {
        // DE: Eine Rechtsdrehung wird ebenfalls in eine absolute Spielfeldrichtung uebersetzt.
        // EN: A right turn is also converted into an absolute playground direction.
        return switch (currentDirection) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
        };
    }
}
