package at.orter.snake;

import at.orter.snake.ai.RandomAi;

public class Main {
    public static void main(String[] args) {
        RandomAi randomAi = new RandomAi();
        Game game = new Game(
                new Playground(10, 10),
                new Snake(new Position(1, 1)),
                new Food(new Position(2, 1))
        );

        int lastScore = game.getScore();

        // DE: Der erste Apfel liegt direkt vor der Snake, damit Wachstum in der Demo sichtbar wird.
        // EN: The first apple is directly in front of the snake so growth is visible in the demo.
        game.tick();
        lastScore = printImportantGameState(1, game.getCurrentDirection(), game, lastScore);

        // DE: Danach uebernimmt RandomAi fuer maximal 100000 weitere Ticks oder bis Game Over erreicht ist.
        // EN: After that, RandomAi takes over for at most 100000 more ticks or until game over is reached.
        for (int i = 0; i < 100000 && !game.isGameOver(); i++) {
            Direction direction = randomAi.chooseDirection(game);

            // DE: Main uebergibt die AI-Entscheidung an Game, danach passiert ein Tick.
            // EN: Main passes the AI decision to Game, then one tick happens.
            game.changeDirection(direction);
            game.tick();

            lastScore = printImportantGameState(i + 2, direction, game, lastScore);
        }
    }

    private static int printImportantGameState(int tick, Direction direction, Game game, int lastScore) {
        // DE: Ausgabe erfolgt nur, wenn ein Apfel gegessen wurde oder das Spiel endet.
        // EN: Output only happens when an apple was eaten or the game ends.
        boolean hasEaten = game.getScore() > lastScore;

        if (!hasEaten && !game.isGameOver()) {
            return lastScore;
        }

        if (hasEaten) {
            System.out.println("Apple eaten");
        }

        if (game.isGameOver()) {
            System.out.println("Game Over");
        }

        System.out.println("Tick: " + tick);
        System.out.println("Direction: " + direction);
        System.out.println("Head: " + game.getSnake().getSnakePosition().getFirst());
        System.out.println("Length: " + game.getSnake().getSnakePosition().size());
        System.out.println("Score: " + game.getScore());
        System.out.println("Food: " + game.getFood().getApplePosition());
        System.out.println();

        return game.getScore();
    }
}
