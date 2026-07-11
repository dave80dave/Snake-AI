package at.orter.snake;

public class Playground {
    private int[][] playGround;
    private int x;
    private int y;

    public Playground(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[][] getPlayGround() {
        return playGround;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPlayGround(int[][] playGround) {
        this.playGround = playGround;
    }
}
