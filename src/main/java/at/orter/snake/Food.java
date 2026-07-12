package at.orter.snake;

import java.util.ArrayList;
import java.util.List;

public class Food {
    // DE: Aktuell werden Apfelpositionen als Liste gespeichert.
    // EN: Apple positions are currently stored as a list.
    private List<Position> applePosition;

    public Food(Position startApple) {
        // DE: Beim Erstellen bekommt Food seine erste Apfelposition.
        // EN: When Food is created, it gets its first apple position.
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
