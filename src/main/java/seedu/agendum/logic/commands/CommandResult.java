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
     * 
     * @param tasks             List of tasks where each task is be prepended by an index
     * @param originalIndices   List of corresponding index for each task
     * @return                  String containing all tasks labeled with their corresponding index
     */
    public static String tasksToString(List<ReadOnlyTask> tasks, List<Integer> originalIndices) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n");
        for (int i = 0; i < tasks.size(); i++) {
            builder.append("#").append(originalIndices.get(i)).append(": ");
            builder.append(tasks.get(i).getAsText());
        }
        return builder.toString();
    }

}
