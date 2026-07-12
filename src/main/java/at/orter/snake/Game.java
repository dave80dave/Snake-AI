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
        // DE: Ein Tick bewegt die Schlange genau einen Schritt in der aktuellen Richtung.
        // EN: One tick moves the snake exactly one step in the current direction.
        snake.move(currentDirection);
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
}
