package harmony.mastermind.logic.commands;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public final String feedbackToUser;
    
    public final String title;
    

    public CommandResult(String title, String feedbackToUser) {
        assert feedbackToUser != null;
        this.title = title;
        this.feedbackToUser = feedbackToUser;
    }
    
    @Override
    public String toString() {
        return this.feedbackToUser;
    }

}
