package seedu.address.logic.commands;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import seedu.address.commons.util.DateTimeUtil;

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
