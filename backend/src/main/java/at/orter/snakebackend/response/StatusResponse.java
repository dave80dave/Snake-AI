package at.orter.snakebackend.response;

public class StatusResponse {
    private final String message;
    private final int availableActions;

    public StatusResponse(String message, int availableActions) {
        this.message = message;
        this.availableActions = availableActions;
    }

    public int getAvailableActions() {
        return availableActions;
    }

    public String getMessage() {
        return message;
    }
}
