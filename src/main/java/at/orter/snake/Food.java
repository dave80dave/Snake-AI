package at.orter.snake;

public class Food {
    // DE: Die Position des Apfels auf dem Spielfeld.
    // EN: The position of the apple on the playground.
    private Position foodPosition;

    public Food(Position foodPosition) {
        this.foodPosition = foodPosition;
    }

    public Position getApplePosition() {
        return foodPosition;
    }

    public void setApplePosition(Position foodPosition) {
        this.foodPosition = foodPosition;
    }

}
