package ai;

import at.orter.snake.ai.RewardCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardCalculatorTest {

    private final RewardCalculator rewardCalculator = new RewardCalculator();

    @Test
    void gameOverReturnsDeathPenalty() {
        // DE: Bei Game Over muss immer die feste Todesstrafe gelten.
        //     Der alte und der neue Score duerfen das Ergebnis nicht veraendern.
        // EN: Game over must always return the fixed death penalty.
        //     The old and new scores must not change the result.
        double reward = rewardCalculator.calculateReward(3, 3, true);

        assertEquals(-100.0, reward, 0.0001);
    }

    @Test
    void increasedScoreReturnsAppleReward() {
        // DE: Ein gestiegener Score bedeutet, dass die Schlange einen Apfel
        //     gefressen hat und dafuer eine positive Belohnung erhaelt.
        // EN: An increased score means that the snake ate an apple and
        //     receives a positive reward.
        double reward = rewardCalculator.calculateReward(2, 3, false);

        assertEquals(10.0, reward, 0.0001);
    }

    @Test
    void unchangedScoreReturnsStepPenalty() {
        // DE: Wenn die Schlange lebt und der Score gleich bleibt, war es ein
        //     normaler Schritt. Die kleine Strafe verhindert endloses Kreisen.
        // EN: If the snake is alive and the score stays unchanged, it was a
        //     regular step. The small penalty discourages endless circling.
        double reward = rewardCalculator.calculateReward(2, 2, false);

        assertEquals(-0.1, reward, 0.0001);
    }
}
