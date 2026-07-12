package at.orter.snake;

public class Game {
    // DE: Game verbindet die wichtigsten Teile des Spiels.
    // EN: Game connects the most important parts of the game.
    private Playground playground;
    private Snake snake;
    private Food food;

    public Game(Playground playground, Snake snake, Food food) {
        // DE: Die Objekte werden von aussen uebergeben, damit Game mit ihnen arbeiten kann.
        // EN: The objects are passed in from the outside so Game can work with them.
        this.playground = playground;
        this.snake = snake;
        this.food = food;
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
