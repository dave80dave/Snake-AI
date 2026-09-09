package at.orter.snakebackend.controller;

import at.orter.snake.Direction;
import at.orter.snakebackend.response.GameResponse;
import at.orter.snakebackend.service.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public GameResponse getGame() { return gameService.getGame(); }

    @PostMapping("/new")
    public GameResponse newGame() { return gameService.newGame(); }

    @PostMapping("/move")
    public GameResponse move(@RequestBody MoveRequest request) {
        return gameService.move(request.direction());
    }

    @PostMapping("/ai-step")
    public GameResponse aiStep() { return gameService.aiStep(); }

    public record MoveRequest(Direction direction) {}
}
