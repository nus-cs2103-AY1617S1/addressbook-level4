package seedu.task.logic.commands;

/**
 * Represents the result of a command execution.
 * @@author generated
 */
public class CommandResult {

    public final String feedbackToUser;

    public CommandResult(String feedbackToUser) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
    }

}
