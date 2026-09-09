package at.orter.snakebackend.response;

import at.orter.snake.Direction;
import at.orter.snake.Game;
import at.orter.snake.Position;
import java.util.List;

public record GameResponse(int width, int height, List<PositionResponse> snake,
                           PositionResponse food, Direction direction, int score,
                           boolean gameOver) {
    public static GameResponse from(Game game) {
        List<PositionResponse> snake = game.getSnake().getSnakePosition().stream()
                .map(PositionResponse::from).toList();
        return new GameResponse(game.getPlayground().getWidth(), game.getPlayground().getHeight(),
                snake, PositionResponse.from(game.getFood().getApplePosition()),
                game.getCurrentDirection(), game.getScore(), game.isGameOver());
    }

    public record PositionResponse(int x, int y) {
        public static PositionResponse from(Position position) {
            return new PositionResponse(position.getX(), position.getY());
        }
    }
}
