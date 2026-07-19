package at.orter.snake.ai;

import java.util.Objects;

public class SnakeState {
    // DE: Die drei Gefahrenwerte gelten relativ zur aktuellen Blickrichtung der Schlange.
    // EN: The three danger values are relative to the snake's current direction.
    private final boolean dangerStraight;
    private final boolean dangerLeft;
    private final boolean dangerRight;

    // DE: Die vier Futterwerte zeigen die Position des Apfels relativ zum Schlangenkopf.
    // EN: The four food values describe the apple's position relative to the snake's head.
    private final boolean foodUp;
    private final boolean foodDown;
    private final boolean foodLeft;
    private final boolean foodRight;

    public SnakeState(boolean dangerStraight, boolean dangerLeft, boolean dangerRight, boolean foodUp, boolean foodDown, boolean foodLeft, boolean foodRight) {
        this.dangerStraight = dangerStraight;
        this.dangerLeft = dangerLeft;
        this.dangerRight = dangerRight;
        this.foodUp = foodUp;
        this.foodDown = foodDown;
        this.foodLeft = foodLeft;
        this.foodRight = foodRight;
    }

    @Override
    public boolean equals(Object obj) {
        // DE: Ein State ist gleich, wenn alle sieben Zustandswerte uebereinstimmen.
        // EN: A state is equal when all seven state values match.
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SnakeState that = (SnakeState) obj;
        return dangerStraight == that.dangerStraight &&
                dangerLeft == that.dangerLeft &&
                dangerRight == that.dangerRight &&
                foodUp == that.foodUp &&
                foodDown == that.foodDown &&
                foodLeft == that.foodLeft &&
                foodRight == that.foodRight;
    }

    @Override
    public int hashCode() {
        // DE: Gleiche States brauchen denselben Hashcode fuer die spaetere Q-Tabelle.
        // EN: Equal states need the same hash code for the future Q-table.
        return Objects.hash(
                dangerStraight,
                dangerLeft,
                dangerRight,
                foodUp,
                foodDown,
                foodLeft,
                foodRight
        );
    }

    public boolean isFoodRight() {
        return foodRight;
    }

    public boolean isFoodLeft() {
        return foodLeft;
    }

    public boolean isFoodDown() {
        return foodDown;
    }

    public boolean isFoodUp() {
        return foodUp;
    }

    public boolean isDangerRight() {
        return dangerRight;
    }

    public boolean isDangerLeft() {
        return dangerLeft;
    }

    public boolean isDangerStraight() {
        return dangerStraight;
    }
}
