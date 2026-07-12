package at.orter.snake;


import java.util.ArrayList;
import java.util.List;

public class Snake {
    // DE: Erstes Element = Kopf, letztes Element = Schwanz.
    // EN: First element = head, last element = tail.
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

    public void move(Direction direction) {
        moveHead(direction);
        // DE: Bei normaler Bewegung bleibt die Laenge gleich.
        // EN: During normal movement, the length stays the same.
        snakePosition.removeLast();
    }

    public void grow(Direction direction) {
        // DE: Beim Wachsen bleibt der Schwanz erhalten.
        // EN: When growing, the tail stays in the list.
        moveHead(direction);
    }

    private void moveHead(Direction direction) {
        // DE: Die Vorschau-Methode berechnet den neuen Kopf, danach wird er wirklich eingefuegt.
        // EN: The preview method calculates the new head, then it is actually inserted.
        Position newHead = getNextHeadPosition(direction);
        snakePosition.addFirst(newHead);
    }

    public Position getNextHeadPosition(Direction direction) {
        // DE: Diese Methode berechnet nur die naechste Kopfposition und veraendert die Schlange nicht.
        // EN: This method only calculates the next head position and does not change the snake.
        Position head = snakePosition.getFirst();

        // DE: Je nach Richtung wird x oder y um ein Feld veraendert.
        // EN: Depending on the direction, x or y is changed by one field.
        return switch (direction) {
            case UP -> new Position(head.getX(), head.getY() - 1);
            case DOWN -> new Position(head.getX(), head.getY() + 1);
            case RIGHT -> new Position(head.getX() + 1, head.getY());
            case LEFT -> new Position(head.getX() - 1, head.getY());
        };
    }
}
