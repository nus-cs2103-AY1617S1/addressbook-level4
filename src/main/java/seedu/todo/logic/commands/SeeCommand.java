package seedu.todo.logic.commands;

/**
 * Lists all tasks and display them to the user
 */
public class SeeCommand extends Command {

    public static final String COMMAND_WORD = "see";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    
    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
        
   
}
