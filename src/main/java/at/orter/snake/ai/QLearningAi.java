package at.orter.snake.ai;

import java.util.List;
import java.util.Random;

public class QLearningAi {
    // DE: Die Q-Tabelle ist das Gedaechtnis der AI. Sie speichert, wie gut
    //     jede relative Aktion in einem bestimmten SnakeState bisher war.
    // EN: The Q-table is the AI's memory. It stores how good each relative
    //     action has been in a particular SnakeState.
    private final QTable qTable;

    // DE: Random wird fuer das zufaellige Ausprobieren neuer Aktionen verwendet.
    // EN: Random is used to explore new actions randomly.
    private final Random random;

    // DE: epsilon bestimmt die Wahrscheinlichkeit fuer Exploration.
    //     Beispiel: 0.1 bedeutet ungefaehr 10 Prozent zufaellige Aktionen.
    // EN: epsilon controls the probability of exploration.
    //     Example: 0.1 means approximately 10 percent random actions.
    private final double epsilon;

    // DE: alpha ist die Lernrate. Sie bestimmt, wie stark eine neue Erfahrung
    //     den bisher gespeicherten Q-Wert veraendert.
    // EN: alpha is the learning rate. It controls how strongly a new experience
    //     changes the previously stored Q-value.
    private final double alpha;

    // DE: gamma ist der Zukunftsfaktor. Er bestimmt, wie wichtig die beste
    //     zukuenftige Handlungsmoeglichkeit fuer das aktuelle Lernen ist.
    // EN: gamma is the discount factor. It controls how important the best
    //     future action value is for the current learning step.
    private final double gamma;

    public QLearningAi(QTable qTable, double epsilon, double alpha, double gamma) {
        // DE: Die Lernparameter werden einmal beim Erzeugen der AI festgelegt.
        // EN: The learning parameters are set once when the AI is created.
        this.qTable = qTable;
        this.random = new Random();
        this.epsilon = epsilon;
        this.alpha = alpha;
        this.gamma = gamma;
    }

    public RelativeAction chooseAction(SnakeState state) {
        // DE: nextDouble(...) erzeugt eine Zufallszahl von 0.0 bis kleiner als 1.0.
        //     Liegt sie unter epsilon, probiert die AI eine zufaellige Aktion aus.
        // EN: nextDouble(...) creates a random number from 0.0 up to less than 1.0.
        //     If it is below epsilon, the AI explores a random action.
        double randomValue = random.nextDouble(0, 1);

        if (randomValue < epsilon) {
            // DE: values() liefert alle drei Aktionen. nextInt(...) waehlt einen
            //     gueltigen zufaelligen Listenindex aus.
            // EN: values() returns all three actions. nextInt(...) selects a
            //     valid random list index.
            List<RelativeAction> randomActions = List.of(RelativeAction.values());
            return randomActions.get(random.nextInt(randomActions.size()));
        }

        // DE: Ohne Exploration nutzt die AI ihr Wissen und waehlt die Aktion
        //     mit dem hoechsten gespeicherten Q-Wert.
        // EN: Without exploration, the AI uses its knowledge and selects the
        //     action with the highest stored Q-value.
        return qTable.getBestAction(state);
    }

    public void learn(
            SnakeState oldState,
            RelativeAction action,
            double reward,
            SnakeState newState,
            boolean gameOver
    ) {
        // DE: oldQ ist die bisherige Bewertung genau der Aktion, die im alten
        //     State ausgefuehrt wurde.
        // EN: oldQ is the previous value of the exact action that was executed
        //     in the old state.
        double oldQ = qTable.getQValue(oldState, action);
        double targetQ;

        if (gameOver) {
            // DE: Nach Game Over gibt es keine naechste Aktion. Deshalb besteht
            //     das Lernziel nur aus der erhaltenen Belohnung.
            // EN: After game over, there is no next action. Therefore, the
            //     learning target consists only of the received reward.
            targetQ = reward;
        } else {
            // DE: Wenn das Spiel weitergeht, beruecksichtigt die AI zusaetzlich
            //     die beste bekannte Moeglichkeit im neuen State.
            // EN: If the game continues, the AI also considers the best known
            //     opportunity in the new state.
            double maxFutureQ = qTable.getMaxQValue(newState);
            targetQ = reward + (gamma * maxFutureQ);
        }

        // DE: Q-Learning-Formel:
        //     newQ = oldQ + alpha * (targetQ - oldQ)
        //     alpha sorgt dafuer, dass sich der alte Wert schrittweise dem
        //     Lernziel annaehert, statt sofort vollstaendig ersetzt zu werden.
        // EN: Q-learning formula:
        //     newQ = oldQ + alpha * (targetQ - oldQ)
        //     alpha makes the old value approach the learning target gradually
        //     instead of replacing it immediately.
        double newQ = oldQ + alpha * (targetQ - oldQ);

        // DE: Gelernt wird rueckwirkend fuer die im oldState ausgefuehrte Aktion.
        // EN: Learning updates the action that was executed in oldState.
        qTable.setQValue(oldState, action, newQ);
    }

    public QTable getQTable() {
        // DE: Erlaubt Tests und spaeteren Komponenten den Zugriff auf das Gedaechtnis.
        // EN: Gives tests and later components access to the AI's memory.
        return qTable;
    }

    public Random getRandom() {
        // DE: Im Test kann damit ein fester Seed fuer wiederholbaren Zufall gesetzt werden.
        // EN: Tests can use this to set a fixed seed for repeatable randomness.
        return random;
    }

    public double getEpsilon() {
        // DE: Liefert die eingestellte Wahrscheinlichkeit fuer Exploration.
        // EN: Returns the configured exploration probability.
        return epsilon;
    }

    public double getAlpha() {
        // DE: Liefert die eingestellte Lernrate.
        // EN: Returns the configured learning rate.
        return alpha;
    }

    public double getGamma() {
        // DE: Liefert den eingestellten Zukunftsfaktor.
        // EN: Returns the configured discount factor.
        return gamma;
    }
}
