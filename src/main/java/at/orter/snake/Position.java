package at.orter.snake;

public class Position {
    // DE: x beschreibt die Spalte, y beschreibt die Zeile im Spielfeld.
    // EN: x describes the column, y describes the row on the playground.
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        // DE: Gleiches Objekt im Speicher ist immer gleich.
        // EN: The same object in memory is always equal.
        if (this == obj) {
            return true;
        }

        // DE: Nur eine andere Position kann die gleiche Spielfeldposition sein.
        // EN: Only another Position can represent the same playground field.
        if (!(obj instanceof Position other)) {
            return false;
        }

        // DE: Zwei Positionen sind gleich, wenn x und y gleich sind.
        // EN: Two positions are equal when x and y are equal.
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        // DE: hashCode gehoert zu equals und verwendet dieselben Werte.
        // EN: hashCode belongs with equals and uses the same values.
        return 31 * x + y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        // DE: Hilft beim Debuggen, weil Positionen lesbar ausgegeben werden.
        // EN: Helps with debugging because positions are printed in a readable way.
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
