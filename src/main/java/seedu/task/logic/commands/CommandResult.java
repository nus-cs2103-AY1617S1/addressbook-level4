package seedu.task.logic.commands;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public String feedbackToUser;

    public CommandResult(String feedbackToUser) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
    }
    
    public void preAppendToResult(String preText){
    	feedbackToUser = preText+feedbackToUser; 
    }

}
