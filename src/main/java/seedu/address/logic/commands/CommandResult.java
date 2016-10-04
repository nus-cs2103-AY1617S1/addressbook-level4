package seedu.address.logic.commands;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedback;
    private boolean hasError;

    /**
     * Result of a command
     * @param feedback feedback to be shown to user
     * @param hasError if there was an error and command failed
     */
    public CommandResult(String feedback, boolean hasError) {
        assert feedback != null;

        this.feedback = feedback;
        this.hasError = hasError;
    }

    /**
     * Result of successful command
     */
    public CommandResult(String feedback) {
       this(feedback, false);
    }

    public String getFeedback() {
        return feedback;
    }

    public boolean hasError() {
        return hasError;
    }
}
