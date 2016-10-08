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
            + ": See tasks on the specified date. \n"
            + "  Parameters: [DATE] (format must follow predefined format)\n"
            + "  See tasks that are done \n"
            + "  Parameters: done\n"
            + "  See tasks that are belongs to a tag \n"
            + "  Parameters: tag [TAGNAME]}\n"
            + "  If nothing is specified, see everything."
            + "Example: " + COMMAND_WORD + " 12/12/2013";
    
    public static final String MESSAGE_SUCCESS = "List all tasks";
    public static final String MESSAGE_INVALID_DATE = "Invalid date string";

    public static final int SEE_EVERYTHING_INDEX = 0;
    public static final int SEE_DONE_INDEX = 1;
    public static final int SEE_TAG_INDEX = 2;
    public static final int SEE_DATE_INDEX = 3;
    private int option;
    private Object data;
    
    public SeeCommand(String dataString) {
        dataString = dataString.trim();
        System.out.println(dataString);
        if (dataString.equals("")) {
            this.option = SEE_EVERYTHING_INDEX;
            this.data = null;
        } else if (dataString.equals("done")) {
            this.option = SEE_DONE_INDEX;
            this.data = null;
        } else if (dataString.contains("tag")) {
            this.option = SEE_TAG_INDEX;
            
            data = dataString.split("tag")[1].trim();
            System.out.println(dataString.split("tag")[1].trim());
        } else {
            this.option = SEE_DATE_INDEX;
            this.data = DateTimeUtil.parseDateString(dataString);
        }
    }

    @Override
    public CommandResult execute() {
        
        switch(this.option) {

            case SEE_DONE_INDEX :
                model.updateFilteredListToShowAllCompleted();
                return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
            
            case SEE_TAG_INDEX :
                model.updateFilteredTaskList((String) data);
                return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
            
            case SEE_DATE_INDEX :    
                model.updateFilteredTaskList((LocalDate) data);
                return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));

            case SEE_EVERYTHING_INDEX :
            default :
                model.updateFilteredListToShowAll();
                return new CommandResult(MESSAGE_SUCCESS);
        }

    }
}
