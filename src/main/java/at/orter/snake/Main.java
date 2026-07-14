package at.orter.snake;

public class Main {
    public static void main(String[] args) {
        // DE: Dieses kleine Demo-Spiel laeuft automatisch, damit wir die Logik im Terminal sehen.
        // EN: This small demo game runs automatically so we can see the logic in the terminal.
        Playground playground = new Playground(30, 30);
        Snake snake = new Snake(new Position(5, 5));
        Food food = new Food(new Position(7, 5));
        Game game = new Game(playground, snake, food);

        runDemoRound(game);

        // DE: Nach Game Over entscheidet Main, wann ein Reset passiert.
        // EN: After game over, Main decides when a reset happens.

        System.out.println("Game Over: " + game.isGameOver());
        System.out.println("Score before reset: " + game.getScore());
        System.out.println("Head before reset: " + game.getSnake().getSnakePosition().getFirst());

        game.resetGame();

        System.out.println("--- Reset ---");
        System.out.println("Game Over: " + game.isGameOver());
        System.out.println("Score after reset: " + game.getScore());
        System.out.println("Head after reset: " + game.getSnake().getSnakePosition().getFirst());
        System.out.println("Food after reset: " + game.getFood().getApplePosition());
    }

    private static void runDemoRound(Game game) {
        // DE: Die Schleife ruft tick() auf, bis Game eine Kollision meldet.
        // EN: The loop calls tick() until Game reports a collision.
        int tickCount = 0;

        while (!game.isGameOver()) {
            game.tick();
            tickCount++;

            System.out.println("Tick: " + tickCount);
            System.out.println("Head: " + game.getSnake().getSnakePosition().getFirst());
            System.out.println("Length: " + game.getSnake().getSnakePosition().size());
            System.out.println("Score: " + game.getScore());
            System.out.println("Food: " + game.getFood().getApplePosition());
            System.out.println();
        }
    }
}
