package at.orter.snake;

import ai.RandomAi;

public class Main {
    public static void main(String[] args) {
        RandomAi randomAi = new RandomAi();
        Game game = new Game(
                new Playground(20, 20),
                new Snake(new Position(10, 10)),
                new Food(new Position(11, 10))
        );

        // DE: Der erste Apfel liegt direkt vor der Snake, damit Wachstum in der Demo sichtbar wird.
        // EN: The first apple is directly in front of the snake so growth is visible in the demo.
        game.tick();
        printGameState(1, game.getCurrentDirection(), game);

        // DE: Danach uebernimmt RandomAi fuer maximal 100 weitere Ticks oder bis Game Over erreicht ist.
        // EN: After that, RandomAi takes over for at most 100 more ticks or until game over is reached.
        for (int i = 0; i < 100 && !game.isGameOver(); i++) {
            Direction direction = randomAi.chooseDirection(game);

            // DE: Main uebergibt die AI-Entscheidung an Game, danach passiert ein Tick.
            // EN: Main passes the AI decision to Game, then one tick happens.
            game.changeDirection(direction);
            game.tick();

            printGameState(i + 2, direction, game);
        }
    }

    private static void printGameState(int tick, Direction direction, Game game) {
        System.out.println("Tick: " + tick);
        System.out.println("Direction: " + direction);
        System.out.println("Head: " + game.getSnake().getSnakePosition().getFirst());
        System.out.println("Length: " + game.getSnake().getSnakePosition().size());
        System.out.println("Score: " + game.getScore());
        System.out.println("Food: " + game.getFood().getApplePosition());
        System.out.println("Game Over: " + game.isGameOver());
        System.out.println();
    }
}
