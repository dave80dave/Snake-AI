package at.orter.snakebackend.service;

import at.orter.snake.*;
import at.orter.snake.ai.RandomAi;
import at.orter.snakebackend.response.GameResponse;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class GameService {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private final Random random = new Random();
    private final RandomAi randomAi = new RandomAi();
    private Game game;

    public GameService() { game = createGame(); }

    public synchronized GameResponse getGame() { return GameResponse.from(game); }

    public synchronized GameResponse newGame() {
        game = createGame();
        return GameResponse.from(game);
    }

    public synchronized GameResponse move(Direction direction) {
        if (!game.isGameOver()) {
            game.changeDirection(direction);
            game.tick();
        }
        return GameResponse.from(game);
    }

    public synchronized GameResponse aiStep() {
        if (!game.isGameOver()) {
            game.changeDirection(randomAi.chooseDirection(game));
            game.tick();
        }
        return GameResponse.from(game);
    }

    private Game createGame() {
        Playground playground = new Playground(WIDTH, HEIGHT);
        Position start = new Position(WIDTH / 2, HEIGHT / 2);
        Snake snake = new Snake(start);
        Position food;
        do {
            food = new Position(random.nextInt(WIDTH), random.nextInt(HEIGHT));
        } while (food.equals(start));
        return new Game(playground, snake, new Food(food));
    }
}
