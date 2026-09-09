package at.orter.snakebackend.controller;

import at.orter.snakebackend.response.StatusResponse;
import at.orter.snakebackend.service.StatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/api/status")
    public StatusResponse status() {
        return statusService.getStatus();
    }
}
