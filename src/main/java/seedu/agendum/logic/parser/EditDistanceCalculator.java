package seedu.agendum.logic.parser;

import org.reflections.Reflections;
import seedu.agendum.commons.core
        .LogsCenter;
import seedu.agendum.logic.commands.Command;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;

//@@author A0003878Y

/**
 * A static class for calculating levenshtein distance between two strings
 */
public class EditDistanceCalculator {

    private static final Logger logger = LogsCenter.getLogger(EditDistanceCalculator.class);
    private static final int EDIT_DISTANCE_THRESHOLD = 3;

    /**
     * Attempts to find the 'closest' command for an input String
     * @param input user inputted command
     * @return Optional string that's the closest command to input. Null if not found.
     */
    public static Optional<String> closestCommandMatch(String input) {
        final String[] bestCommand = {""};
        final int[] bestCommandDistance = {Integer.MAX_VALUE};

        Consumer<String> consumer = (commandWord) -> {
            int commandWordDistance = distance(input, commandWord);

            if (commandWordDistance < bestCommandDistance[0]) {
                bestCommand[0] = commandWord;
                bestCommandDistance[0] = commandWordDistance;
            }
        };
        executeOnAllCommands(consumer);

        if (bestCommandDistance[0] < EDIT_DISTANCE_THRESHOLD) {
            return Optional.of(bestCommand[0]);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Attempts to 'complete' the input String into an actual command
     * @param input user inputted command
     * @return Optional string that's command that best completes the input. If input matches more than
     * one command, null ire returned. Null is also returned if a command is not found.
     */
    public static Optional<String> findCommandCompletion(String input) {
        ArrayList<String> matchedCommands = new ArrayList<>();

        Consumer<String> consumer = (commandWord) -> {
            if (commandWord.startsWith(input)) {
                matchedCommands.add(commandWord);
            }
        };
        executeOnAllCommands(consumer);

        if (matchedCommands.size() == 1) {
            return Optional.of(matchedCommands.get(0));
        } else {
            return Optional.empty();
        }
    }

    /**
     * A higher order method that takes in an operation to perform on all Commands using
     * Java reflection and functional programming paradigm.
     * @param f A closure that takes a String as input that executes on all Commands.
     */
    private static void executeOnAllCommands(Consumer<String> f) {
        new Reflections("seedu.agendum").getSubTypesOf(Command.class)
                .stream()
                .map(s -> {
                    try {
                        return s.getMethod("getName").invoke(null).toString();
                    } catch (NullPointerException e) {
                        return ""; // Suppress this exception are we expect some Commands to not conform to getName()
                    } catch (Exception e) {
                        logger.severe("Java reflection for Command class failed");
                        throw new RuntimeException();
                    }
                })
                .filter(p -> p != "") // remove empty
                .forEach(f); // execute given lambda on each nonnull String.
    }


    /**
     * Calculates levenshtein distnace between two strings.
     * Code from https://rosettacode.org/wiki/Levenshtein_distance#Java
     * @param a
     * @param b
     * @return
     */
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
