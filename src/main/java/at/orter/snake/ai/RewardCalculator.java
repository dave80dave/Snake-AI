package at.orter.snake.ai;

public class RewardCalculator {

    public double calculateReward(
            int oldScore,
            int newScore,
            boolean gameOver
    ) {
        // DE: Game Over wird zuerst geprueft, weil der Tod immer die feste
        //     negative Belohnung erhalten soll.
        // EN: Game over is checked first because death must always receive
        //     the fixed negative reward.
        if (gameOver) {
            return -100.0;
        }

        // DE: Ein gestiegener Score bedeutet, dass die Schlange zwischen dem
        //     alten und dem neuen Spielzustand einen Apfel gefressen hat.
        // EN: An increased score means that the snake ate an apple between
        //     the old and the new game state.
        if (newScore > oldScore) {
            return 10.0;
        }

        // DE: Ein normaler Schritt bekommt eine kleine Strafe. Dadurch wird
        //     zielloses Kreisen schlechter bewertet als ein schneller Apfelfund.
        // EN: A regular step receives a small penalty. This makes aimless
        //     circling less valuable than reaching an apple quickly.
        return -0.1;
    }
}
