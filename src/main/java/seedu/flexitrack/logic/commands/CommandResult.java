package seedu.flexitrack.logic.commands;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public final String feedbackToUser;

    public CommandResult(String feedbackToUser) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
    }
    
    public String getString(){
        return feedbackToUser;
    }

}
