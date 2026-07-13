package at.orter.snake;


public class Main {
    static void main(String[] args) {
        Playground playground = new Playground(10, 10);
        Snake snake = new Snake(new Position(5, 5));
        Food food = new Food(new Position(7, 5));
        Game game = new Game(playground, snake, food);

        while (!game.isGameOver() && snake.getSnakePosition().getFirst().getX() < 9) {
            game.tick();
            System.out.println("Head Position: " + snake.getSnakePosition().getFirst());
            System.out.println("Snake Length: " + snake.getSnakePosition().size());
        }
    }
}
