package at.orter.snake;


public class Game {
    // DE: Game verbindet die wichtigsten Teile des Spiels.
    // EN: Game connects the most important parts of the game.
    private final Playground playground;
    private final Snake snake;
    private final Food food;
    // DE: Speichert die Richtung, die beim naechsten Tick ausgefuehrt wird.
    // EN: Stores the direction that will be used on the next tick.
    private Direction currentDirection = Direction.RIGHT;

    // DE: Merkt sich, ob das Spiel durch eine Kollision beendet wurde.
    // EN: Stores whether the game ended because of a collision.
    private boolean gameOver = false;

    public Game(Playground playground, Snake snake, Food food) {
        // DE: Die Objekte werden von aussen uebergeben, damit Game mit ihnen arbeiten kann.
        // EN: The objects are passed in from the outside so Game can work with them.
        this.playground = playground;
        this.snake = snake;
        this.food = food;
    }

    public void changeDirection(Direction direction) {
        // DE: Direkte Gegenrichtungen werden ignoriert, damit die Schlange nicht in sich selbst faehrt.
        // EN: Direct opposite directions are ignored so the snake does not turn into itself.
        if (currentDirection == Direction.UP && direction == Direction.DOWN) {
            return;
        }
        if (currentDirection == Direction.RIGHT && direction == Direction.LEFT) {
            return;
        }
        if (currentDirection == Direction.DOWN && direction == Direction.UP) {
            return;
        }
        if (currentDirection == Direction.LEFT && direction == Direction.RIGHT) {
            return;
        }
        currentDirection = direction;
    }

    public void tick() {
        // DE: Ein Tick prueft zuerst Kollisionen und entscheidet danach zwischen Wachsen und Bewegen.
        // EN: One tick checks collisions first and then decides between growing and moving.
        Position nextHead = snake.getNextHeadPosition(currentDirection);
        Position applePosition = food.getApplePosition();
        boolean willGrow = nextHead.equals(applePosition);

        if (isOutsidePlayground(nextHead)) {
            gameOver = true;
            return;
        }

        // DE: Selbst-Kollision muss wissen, ob der Schwanz in diesem Tick verschwindet.
        // EN: Self-collision must know whether the tail disappears during this tick.
        if (collidesWithSnake(nextHead, willGrow)) {
            gameOver = true;
            return;
        }

        if (willGrow) {
            snake.grow(currentDirection);
        } else {
            snake.move(currentDirection);
        }
    }

    private boolean isOutsidePlayground(Position position) {
        // DE: Wandkollision entsteht, wenn die naechste Position ausserhalb der Spielfeldgrenzen liegt.
        // EN: Wall collision happens when the next position is outside the playground limits.
        int x = position.getX();
        int y = position.getY();

        if (x < 0 || x >= playground.getWidth()) {
            return true;
        }

        if (y < 0 || y >= playground.getHeight()) {
            return true;
        }

        return false;
    }

    private boolean collidesWithSnake(Position position, boolean willGrow) {
        // DE: Beim Wachsen bleibt der Schwanz liegen, deshalb zaehlt die ganze Schlange.
        // EN: When growing, the tail stays, so the whole snake counts.
        if (willGrow) {
            return snake.getSnakePosition().contains(position);
        }
        // DE: Ohne Wachstum wird der Schwanz entfernt, deshalb wird das letzte Element ignoriert.
        // EN: Without growth, the tail is removed, so the last element is ignored.
        for (int i = 0; i < snake.getSnakePosition().size() - 1; i++) {
            Position bodyPart = snake.getSnakePosition().get(i);
            if (bodyPart.equals(position)) {
                return true;
            }
        }
        return false;
    }

    public Playground getPlayground() {
        return playground;
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }
}
