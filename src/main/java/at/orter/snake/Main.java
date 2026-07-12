package at.orter.snake;


public class Main {
    static void main(String[] args) {
        testMoveWithoutApple();
        testGrowWithApple();
        testWallCollision();
        testGame();
        testGameWithTurnBeforeWall();
    }

    private static void testMoveWithoutApple() {
        Playground playground = new Playground(26, 25);
        Snake snake = new Snake(new Position(5, 5));
        Food food = new Food(new Position(9, 9));
        Game game = new Game(playground, snake, food);

        game.tick();

        System.out.println("Test 1 - Move without apple");
        System.out.println("Expected head: Position{x=6, y=5}");
        System.out.println("Actual head:   " + snake.getSnakePosition().getFirst());
        System.out.println("Expected length: 1");
        System.out.println("Actual length:   " + snake.getSnakePosition().size());
        System.out.println();
    }

    private static void testGrowWithApple() {
        Playground playground = new Playground(26, 25);
        Snake snake = new Snake(new Position(5, 5));
        Food food = new Food(new Position(6, 5));
        Game game = new Game(playground, snake, food);

        game.tick();

        System.out.println("Test 2 - Grow with apple");
        System.out.println("Expected head: Position{x=6, y=5}");
        System.out.println("Actual head:   " + snake.getSnakePosition().getFirst());
        System.out.println("Expected length: 2");
        System.out.println("Actual length:   " + snake.getSnakePosition().size());
    }

    private static void testWallCollision() {
        System.out.println("Test 3 - Test Wall Collision");
        Playground playground = new Playground(26, 25);
        Snake snake = new Snake(new Position(25, 5));
        Food food = new Food(new Position(9, 9));
        Game game = new Game(playground, snake, food);

        game.tick();

        System.out.println();
        System.out.println("Test 3 - Wall collision");
        System.out.println("Expected gameOver: true");
        System.out.println("Actual gameOver:   " + game.isGameOver());
        System.out.println("Expected head: Position{x=25, y=5}");
        System.out.println("Actual head:   " + snake.getSnakePosition().getFirst());
        System.out.println("Expected length: 1");
        System.out.println("Actual length:   " + snake.getSnakePosition().size());
    }

    private static void testGame() {
        Playground playground = new Playground(50, 50);
        Snake snake = new Snake(new Position(25, 25));
        Food food = new Food(new Position(27, 31));
        Game game = new Game(playground,snake,food);
        while (!game.isGameOver()){
            game.tick();
            System.out.println(("Head Position: " + snake.getSnakePosition().getFirst()));
        }
        System.out.println("Kollision");
    }

    private static void testGameWithTurnBeforeWall() {
        Playground playground = new Playground(50, 50);
        Snake snake = new Snake(new Position(47, 25));
        Food food = new Food(new Position(10, 10));
        Game game = new Game(playground, snake, food);
        System.out.println("Test 4 - Befor Wall");
        while (!game.isGameOver() && snake.getSnakePosition().getFirst().getY() > 0) {
            Position head = snake.getSnakePosition().getFirst();
            if (head.getX() == 49) {
                game.changeDirection(Direction.UP);
            }
            game.tick();
            System.out.println("Head Position: " + snake.getSnakePosition().getFirst());
        }
        System.out.println("Game Over: " + game.isGameOver());
    }
}
