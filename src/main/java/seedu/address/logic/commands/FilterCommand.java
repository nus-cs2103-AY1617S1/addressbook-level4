package seedu.address.logic.commands;

import java.util.Set;

import seedu.address.model.task.Date;

/**
 * Filter the filtered task list to filter by the given attribute.
 */
public class FilterCommand extends Command {
    
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filter list for specified attributes "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [s/START_DATE] [d/DEADLINE]...\n"
            + "Example: " + COMMAND_WORD + " s/23.10.2016";

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date should follow DD.MM.YYYY[-Time(in 24 hrs)]";

    private final String dateValue;
    private final boolean isEventDate;

    public FilterCommand(String dateValue, boolean isEventDate) {
        this.dateValue = dateValue;
        this.isEventDate = isEventDate;
    }

    @Override
    public CommandResult execute() {
        if (!dateValue.matches(Date.DATE_VALIDATION_REGEX)) {
            return new CommandResult(MESSAGE_DATE_CONSTRAINTS); 
        }
        model.updateFilteredTaskList(dateValue, isEventDate);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }


}
