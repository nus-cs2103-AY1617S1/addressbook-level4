//@@author A0003878Y

package seedu.agendum.logic.parser;

import org.reflections.Reflections;
import seedu.agendum.commons.core.LogsCenter;
import seedu.agendum.logic.commands.Command;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

public class EditDistanceCalculator {

    private static final Logger logger = LogsCenter.getLogger(EditDistanceCalculator.class);
    private static final int EDIT_DISTANCE_THRESHOLD = 3;

    public static Optional<String> parseString(String input) {
        Reflections reflections = new Reflections("seedu.agendum");
        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);

        String bestCommand = "";
        int bestCommandDistance = Integer.MAX_VALUE;

        for (Class<? extends Command> c :classes) {
            try {
                String commandWord = c.getMethod("getName").invoke(null).toString();
                int commandWordDistance = distance(input, commandWord);

                if (commandWordDistance < bestCommandDistance) {
                    bestCommand = commandWord;
                    bestCommandDistance = commandWordDistance;
                }
            } catch (NullPointerException e) {
                continue;
            } catch (Exception e) {
                logger.severe("Java reflection for Command class failed");
            }
        }

        if (bestCommandDistance < EDIT_DISTANCE_THRESHOLD) {
            return Optional.of(bestCommand);
        } else {
            return Optional.empty();
        }
    }

    // Code from https://rosettacode.org/wiki/Levenshtein_distance#Java
    private static int distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

}
