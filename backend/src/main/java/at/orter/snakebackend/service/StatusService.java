package at.orter.snakebackend.service;

import at.orter.snakebackend.response.StatusResponse;
import at.orter.snake.ai.RelativeAction;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    public StatusResponse getStatus() {
        return new StatusResponse("Snake ist Started on Backend!", RelativeAction.values().length);
    }
}
