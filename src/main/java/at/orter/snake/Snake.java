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

    public void move(Direction direction){
        moveHead(direction);
        // DE: Bei normaler Bewegung bleibt die Laenge gleich.
        // EN: During normal movement, the length stays the same.
        snakePosition.removeLast();
    }

    public void grow(Direction direction){
        // DE: Beim Wachsen bleibt der Schwanz erhalten.
        // EN: When growing, the tail stays in the list.
        moveHead(direction);
    }

    private void moveHead(Direction direction) {
        Position head = snakePosition.getFirst();

        // DE: Aus der aktuellen Kopfposition wird die neue Kopfposition berechnet.
        // EN: The new head position is calculated from the current head position.
        Position newHead = switch (direction){
            case UP -> new Position(head.getX(), head.getY() -1);
            case DOWN -> new Position(head.getX(), head.getY() +1);
            case RIGHT -> new Position(head.getX() + 1, head.getY());
            case LEFT -> new Position(head.getX() - 1, head.getY());
        };

        // DE: Der neue Kopf wird immer vorne in die Liste gesetzt.
        // EN: The new head is always added to the front of the list.
        snakePosition.addFirst(newHead);
    }
}
