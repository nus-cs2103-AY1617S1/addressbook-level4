package seedu.taskitty.logic.commands;

public class ViewCommand extends Command {
    
    public static final String COMMAND_WORD = "view";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View all tasks for the specified date "
            + "and deadlines up to the specified date.\n"
            + "Parameters: view [DATE]"
            + "Example: " + COMMAND_WORD + " 12 Oct 2016";
    
    public static final String VIEW_DEFAULT_MESSAGE = " ";
    
    public ViewCommand(String date) {
        
    }
    
    @Override
    public CommandResult execute() {
        return new CommandResult(VIEW_DEFAULT_MESSAGE);
    }

    @Override
    public void saveStateIfNeeded(String commandText) {
        
    }

}
