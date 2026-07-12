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
