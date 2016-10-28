package seedu.address.logic.commands;
//@@author A0142325R
/**
 * Refresh tasks or events in the task manager to the user.
 * For outdated recurring tasks, its date and time will be shown based on its next occurrence 
 * from today's date and time
 */
public class RefreshCommand extends Command {

    public static final String COMMAND_WORD = "refresh";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Refresh tasks and events"
            + "Parameters: \n"
            + "Example: "
            + " refresh \n";

    public static final String MESSAGE_SUCCESS = "Refreshed all tasks and events";
    
    public static final String MESSAGE_INVALID_REFRESH_COMMAND = "The refresh command argument provided is invalid."
            + MESSAGE_USAGE;
    

    
    public RefreshCommand() {}
    
    @Override
    public CommandResult execute() {    
        model.refreshTask();
        return new CommandResult(MESSAGE_SUCCESS);
        
    }
}
