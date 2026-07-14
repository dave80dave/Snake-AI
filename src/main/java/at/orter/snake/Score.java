package at.orter.snake;

public class Score {
    // DE: Speichert die erreichten Punkte eines Spiels.
    // EN: Stores the points reached in one game.
    private int score;

    public Score(int score) {
        this.score = score;
    }

    public void growScore() {
        // DE: Ein gegessener Apfel erhoeht den Score um 1.
        // EN: One eaten apple increases the score by 1.
        score++;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
