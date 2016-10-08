package seedu.address.logic.commands;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import seedu.address.commons.util.DateTimeUtil;

/**
 * Lists all persons in the address book to the user.
 */
public class SeeCommand extends Command {

    public static final String COMMAND_WORD = "see";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": See tasks on the specified date"
            + "Parameters: DATE (format must follow predefined format)\n"
            + "Example: " + COMMAND_WORD + " 12/12/2013";
    
    public static final String MESSAGE_SUCCESS = "List all tasks";
    public static final String MESSAGE_INVALID_DATE = "Invalid date string";

    private LocalDate date;
    
    public SeeCommand(String dateString) {
    	this.date = DateTimeUtil.parseDateString(dateString);
    	if(this.date == null) throw new DateTimeParseException(MESSAGE_INVALID_DATE, dateString, 0);
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
