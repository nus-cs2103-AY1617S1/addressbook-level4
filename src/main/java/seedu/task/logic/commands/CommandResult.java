package seedu.task.logic.commands;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public final String feedbackToUser;
    public final boolean isSuccessful;

    public CommandResult(boolean isSuccessful, String feedbackToUser) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
        this.isSuccessful = isSuccessful;
    }
}
