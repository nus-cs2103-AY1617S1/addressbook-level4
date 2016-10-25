package seedu.agendum.logic.commands;

import java.util.List;

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

    //@@author A0133367E
    /**
     * Pre-condition: tasks and originalIndices must be of the same size.
     * Returns a string containing each task in tasks
     * with the corresponding number in originalIndices prepended
     */
    public static String tasksToString(List<ReadOnlyTask> tasks, List<Integer> originalIndices) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n");
        for (int i = 0; i < tasks.size(); i++) {
            builder.append("#" + originalIndices.get(i) + ": ");
            builder.append(tasks.get(i).getAsText());
        }
        return builder.toString();
    }

}
