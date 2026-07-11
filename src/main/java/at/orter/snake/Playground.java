package at.orter.snake;

public class Playground {

    private final int[][] playGround;
    private final int width;
    private final int height;

    public Playground(int width, int height) {
        this.width = width;
        this.height = height;
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