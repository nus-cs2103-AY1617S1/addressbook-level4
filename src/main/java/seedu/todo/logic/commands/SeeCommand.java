package seedu.todo.logic.commands;

/**
 * Lists all persons in the address book to the user.
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
