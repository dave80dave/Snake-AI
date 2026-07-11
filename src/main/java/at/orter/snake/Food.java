package at.orter.snake;

import java.util.ArrayList;
import java.util.List;

public class Food {
    private List<Position> applePosition;

    public Food(Position startApple) {
        applePosition = new ArrayList<>();
        applePosition.add(startApple);
    }

    public List<Position> getApplePosition() {
        return applePosition;
    }

    public void setPosition(List<Position> applePosition) {
        this.applePosition = applePosition;
    }
}
