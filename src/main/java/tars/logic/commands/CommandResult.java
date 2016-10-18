package tars.logic.commands;

import java.util.ArrayList;

import tars.model.task.ReadOnlyTask;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public final String feedbackToUser;

    public CommandResult(String feedbackToUser) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
    }
    
    /**
     * Formats an ArrayList of tasks to return it as a string
     */
    public static String formatTasksList(ArrayList<ReadOnlyTask> taskList) {
        String toReturn = "";
        for (ReadOnlyTask t : taskList) {
            toReturn += t.toString() +"\n";
        }
        return toReturn.trim();
    }

}
