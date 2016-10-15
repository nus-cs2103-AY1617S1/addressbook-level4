package seedu.oneline.logic.commands;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public final String feedbackToUser;
    public final boolean success;

    public CommandResult(String feedbackToUser, boolean success) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
        this.success = success;
    }

}
