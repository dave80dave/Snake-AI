package at.orter.snake;


import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<Position> snakePosition;

    public Snake(Position startPosition) {
        snakePosition = new ArrayList<>();
        snakePosition.add(startPosition);
    }

    public List<Position> getSnakePosition() {
        return snakePosition;
    }

    public void setSnake(List<Position> snakePosition) {
        this.snakePosition = snakePosition;
    }
}
