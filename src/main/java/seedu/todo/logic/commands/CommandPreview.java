package seedu.todo.logic.commands;

//@@author A0139021U

/**
 * Represents the preview of a command execution.
 */
public class CommandPreview {
    private final String feedback;

    public CommandPreview(String feedback) {
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }
}
