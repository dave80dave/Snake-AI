package at.orter.snake;

public class Food {
    // DE: Die Position des Apfels auf dem Spielfeld.
    // EN: The position of the apple on the playground.
    private Position applePosition;

    public Food(Position applePosition) {
        this.applePosition = applePosition;
    }

    public Position getApplePosition() {
        return applePosition;
    }

    public void setApplePosition(Position applePosition) {
        this.applePosition = applePosition;
    }
}
