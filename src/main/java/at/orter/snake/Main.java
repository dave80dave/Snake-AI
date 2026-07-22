package at.orter.snake;

import at.orter.snake.ai.QTable;
import at.orter.snake.ai.RelativeAction;
import at.orter.snake.ai.SnakeState;

public class Main {
    public static void main(String[] args) {
        QTable qTable = new QTable();

        // DE: Dieser Beispiel-State beschreibt eine Situation mit Gefahr geradeaus.
        //     Links und rechts sind frei, der Apfel liegt oberhalb und rechts.
        // EN: This example state describes danger straight ahead. Left and right
        //     are safe, while the apple is above and to the right.
        SnakeState state = new SnakeState(
                true,
                false,
                false,
                true,
                false,
                false,
                true
        );

        System.out.println("=== Q-Table Demo ===");
        System.out.println();
        System.out.println("Situation:");
        printState(state);

        // DE: Ein unbekannter State startet fuer jede Action mit dem Q-Wert 0.0.
        // EN: An unknown state starts with a Q-value of 0.0 for every action.
        System.out.println();
        System.out.println("Q-values before learning:");
        printQValues(qTable, state);

        // DE: Diese Beispielwerte stellen bereits gesammelte Lernerfahrungen dar.
        //     Geradeaus fuehrte zum Tod, links war gut und rechts nur leicht negativ.
        // EN: These example values represent learned experience. Going straight
        //     caused death, left was good, and right was only slightly negative.
        qTable.setQValue(state, RelativeAction.STRAIGHT, -100.0);
        qTable.setQValue(state, RelativeAction.TURN_LEFT, 10.0);
        qTable.setQValue(state, RelativeAction.TURN_RIGHT, -0.1);

        System.out.println();
        System.out.println("Q-values after learning:");
        printQValues(qTable, state);

        System.out.println();
        System.out.println("Highest Q-value: " + qTable.getMaxQValue(state));
        System.out.println("Best action: " + qTable.getBestAction(state));
    }

    private static void printState(SnakeState state) {
        System.out.println("dangerStraight = " + state.isDangerStraight());
        System.out.println("dangerLeft     = " + state.isDangerLeft());
        System.out.println("dangerRight    = " + state.isDangerRight());
        System.out.println("foodUp         = " + state.isFoodUp());
        System.out.println("foodDown       = " + state.isFoodDown());
        System.out.println("foodLeft       = " + state.isFoodLeft());
        System.out.println("foodRight      = " + state.isFoodRight());
    }

    private static void printQValues(QTable qTable, SnakeState state) {
        for (RelativeAction action : RelativeAction.values()) {
            double qValue = qTable.getQValue(state, action);
            System.out.println(action + " = " + qValue);
        }
    }
}
