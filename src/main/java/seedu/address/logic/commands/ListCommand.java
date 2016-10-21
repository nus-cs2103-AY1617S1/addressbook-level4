package seedu.address.logic.commands;

import seedu.address.model.task.ReadOnlyTaskFilter;

/**
 * Lists all tasks in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks filtered by specified parameters\n"
            + "Event Parameters: [TASK_TYPE] [done| not-done] [dd-mm-yy] [hh:mm]\n"
            + "Event Example: " + COMMAND_WORD
            + " someday not-done\n";  
    
    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    
    public ListCommand() {}

    @Override
    public CommandResult execute() {
    	model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
