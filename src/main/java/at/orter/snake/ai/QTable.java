package at.orter.snake.ai;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class QTable {
    // DE: Die aeussere Map ordnet jedem SnakeState eine eigene Action-Tabelle zu.
    //     Die innere EnumMap speichert fuer jede RelativeAction genau einen Q-Wert.
    // EN: The outer map assigns a separate action table to every SnakeState.
    //     The inner EnumMap stores one Q-value for each RelativeAction.
    private final Map<SnakeState, EnumMap<RelativeAction, Double>> qValues = new HashMap<>();

    public double getQValue(SnakeState state, RelativeAction action) {
        // DE: Zuerst wird die innere Action-Tabelle fuer den uebergebenen State gesucht.
        //     Ist der State noch unbekannt, liefert Map.get(...) den Wert null.
        // EN: First, look up the inner action table for the supplied state.
        //     If the state is still unknown, Map.get(...) returns null.
        EnumMap<RelativeAction, Double> actionMap = qValues.get(state);

        if (actionMap == null) {
            // DE: Jeder neue State erhaelt seine eigene EnumMap. RelativeAction.class
            //     sagt der EnumMap, welches Enum als Schluessel verwendet wird.
            // EN: Every new state receives its own EnumMap. RelativeAction.class
            //     tells the EnumMap which enum is used for its keys.
            actionMap = new EnumMap<>(RelativeAction.class);

            // DE: RelativeAction.values() liefert alle drei erlaubten Actions.
            //     Die Schleife traegt STRAIGHT, TURN_LEFT und TURN_RIGHT jeweils
            //     mit dem neutralen Startwert 0.0 in die neue Action-Tabelle ein.
            // EN: RelativeAction.values() returns all three allowed actions.
            //     The loop adds STRAIGHT, TURN_LEFT, and TURN_RIGHT to the new
            //     action table, each with the neutral initial value 0.0.
            for (RelativeAction relativeAction : RelativeAction.values()) {
                actionMap.put(relativeAction, 0.0);
            }

            // DE: Die fertig vorbereitete Action-Tabelle wird unter dem State
            //     gespeichert. Ohne diesen Schritt waere sie nach dem Methodenaufruf
            //     vergessen und wuerde beim naechsten Aufruf erneut erzeugt werden.
            // EN: Store the prepared action table under the state. Without this step,
            //     it would be forgotten after the method call and recreated next time.
            qValues.put(state, actionMap);
        }

        // DE: Aus der gefundenen oder neu erstellten Action-Tabelle wird nun
        //     der Q-Wert der angefragten Action zurueckgegeben.
        // EN: Return the Q-value for the requested action from the existing or
        //     newly created action table.
        return actionMap.get(action);
    }

    public void setQValue(SnakeState state, RelativeAction action, double value) {
        // DE: getQValue(...) legt einen unbekannten State bei Bedarf zuerst an.
        //     Danach kann der neue Wert sicher in seiner Action-Tabelle gespeichert werden.
        // EN: getQValue(...) initializes an unknown state when needed. The new value
        //     can then safely be stored in that state's action table.
        getQValue(state, action);
        qValues.get(state).put(action, value);
    }

    public double getMaxQValue(SnakeState state) {
        // DE: Negative Unendlichkeit ist kleiner als jeder normale Q-Wert. Dadurch
        //     funktioniert die Maximumsuche auch, wenn alle drei Werte negativ sind.
        // EN: Negative infinity is smaller than every regular Q-value. This allows
        //     the maximum search to work even when all three values are negative.
        double maxQValue = Double.NEGATIVE_INFINITY;

        // DE: Jede Action wird gelesen und mit dem bisher groessten Wert verglichen.
        // EN: Read every action and compare it with the highest value found so far.
        for (RelativeAction action : RelativeAction.values()) {
            double qValue = getQValue(state, action);

            if (qValue > maxQValue) {
                maxQValue = qValue;
            }
        }

        return maxQValue;
    }

    public RelativeAction getBestAction(SnakeState state) {
        // DE: Neben dem groessten Wert wird hier auch die dazugehoerige Action gemerkt.
        //     Bei gleichen Startwerten bleibt STRAIGHT als erster Enum-Wert ausgewaehlt.
        // EN: This method remembers both the highest value and its associated action.
        //     When initial values are tied, STRAIGHT remains selected as the first enum value.
        RelativeAction bestAction = RelativeAction.STRAIGHT;
        double maxQValue = Double.NEGATIVE_INFINITY;

        for (RelativeAction action : RelativeAction.values()) {
            double qValue = getQValue(state, action);

            if (qValue > maxQValue) {
                maxQValue = qValue;
                bestAction = action;
            }
        }

        return bestAction;
    }
}
