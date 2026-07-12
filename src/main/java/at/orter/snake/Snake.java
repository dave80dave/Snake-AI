package at.orter.snake;


import java.util.ArrayList;
import java.util.List;

public class Snake {
    // Erstes Element = Kopf, letztes Element = Schwanz.
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
        // Bei normaler Bewegung bleibt die Laenge gleich.
        snakePosition.removeLast();
    }

    public void grow(Direction direction){
        // Beim Wachsen bleibt der Schwanz erhalten.
        moveHead(direction);
    }

    private void moveHead(Direction direction) {
        Position head = snakePosition.getFirst();

        // Aus der aktuellen Kopfposition wird die neue Kopfposition berechnet.
        Position newHead = switch (direction){
            case UP -> new Position(head.getX(), head.getY() -1);
            case DOWN -> new Position(head.getX(), head.getY() +1);
            case RIGHT -> new Position(head.getX() + 1, head.getY());
            case LEFT -> new Position(head.getX() - 1, head.getY());
        };

        // Der neue Kopf wird immer vorne in die Liste gesetzt.
        snakePosition.addFirst(newHead);
    }
}
