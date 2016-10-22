package seedu.agendum.logic.commands;

import java.util.ArrayList;

import seedu.agendum.model.task.ReadOnlyTask;


/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public final String feedbackToUser;

    public CommandResult(String feedbackToUser) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
    }

    public static String tasksToString(ArrayList<ReadOnlyTask> tasks, ArrayList<Integer> originalIndices) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n");
        for (int i=0; i < tasks.size(); i++) {
            builder.append("#" + originalIndices.get(i) + ": ");
            builder.append(tasks.get(i).getAsText());
        }
        return builder.toString();
    }

}
