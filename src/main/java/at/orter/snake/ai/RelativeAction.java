package at.orter.snake.ai;

public enum RelativeAction {
    // DE: Die Aktionen gelten relativ zur aktuellen Blickrichtung der Schlange.
    // EN: The actions are relative to the snake's current facing direction.

    // DE: Aktuelle Richtung beibehalten.
    // EN: Keep moving in the current direction.
    STRAIGHT,

    // DE: Von der aktuellen Blickrichtung aus nach links abbiegen.
    // EN: Turn left relative to the current facing direction.
    TURN_LEFT,

    // DE: Von der aktuellen Blickrichtung aus nach rechts abbiegen.
    // EN: Turn right relative to the current facing direction.
    TURN_RIGHT
}
