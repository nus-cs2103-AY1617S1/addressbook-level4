package seedu.inbx0.logic.commands;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.task.Date;

/**
 * Lists all tasks in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks associated with the date and "
            + "displays them as a list with index numbers.\n"
            + "Without any parameters, it will display all tasks.\n"
            + "Parameters: DATE\n"
            + "Example: " + COMMAND_WORD + " today";
    
    private final String checkDate;
    
    public ListCommand() {
        this.checkDate = null;
    }

    public ListCommand(String arguments) throws IllegalValueException {
        Date checkDate = new Date (arguments);
        
        this.checkDate = checkDate.value;
        
    }

    @Override
    public CommandResult execute() {
        if(checkDate == null) {
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS);
        }
        else {
            model.updateFilteredTaskList(checkDate);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        }
    }
}
