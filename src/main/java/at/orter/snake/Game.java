package at.orter.snake;

public class Game {
    private Playground playground;
    private Snake snake;
    private Food food;

    public Game(Playground playground, Snake snake, Food food) {
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
