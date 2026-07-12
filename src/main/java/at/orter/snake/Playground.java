package at.orter.snake;

public class Playground {

    // DE: Das Spielfeld ist ein zweidimensionales Array: [Zeile][Spalte].
    // EN: The playground is a two-dimensional array: [row][column].
    private final int[][] playGround;
    private final int width;
    private final int height;

    public Playground(int width, int height) {
        this.width = width;
        this.height = height;
        // DE: Arrays werden mit height zuerst erstellt, weil die erste Dimension die Zeilen sind.
        // EN: Arrays are created with height first because the first dimension represents rows.
        this.playGround = new int[height][width];
    }

    public int[][] getPlayGround() {
        return playGround;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
