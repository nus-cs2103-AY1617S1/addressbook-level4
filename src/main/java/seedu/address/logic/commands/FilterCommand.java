package seedu.address.logic.commands;

import java.util.Optional;
import java.util.Set;

import seedu.address.model.task.Date;

//@@author A0146123R
/**
 * Filter the filtered task list to filter by the given attribute.
 */
public class FilterCommand extends Command {
    
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filter list for specified attributes "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [s/START_DATE] [e/END_DATE] [d/DEADLINE] [r/RECURRING] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " s/23.10.2016 r/daily";
    
    // Temporary
    public static final String MESSAGE_DATE_CONSTRAINTS = "Date should follow DD.MM.YYYY[-Time(in 24 hrs)]";

    private static final String DEADLINE = "deadline";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String RECURRING = "recurring";
    
    private final Optional<String> deadline;
    private final Optional<String> startDate;
    private final Optional<String> endDate;
    private final Optional<String> recurring;
    private final Set<String> tags;

    public FilterCommand(Optional<String> deadline, Optional<String> startDate, Optional<String> endDate, Optional<String> recurring, Set<String> tags) {
        this.deadline = deadline;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recurring = recurring;
        this.tags = tags;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll(); // clear previous filtered results
        if (deadline.isPresent()) {
            if (!deadline.get().matches(Date.DATE_VALIDATION_REGEX)) { 
                // Temporary, it will be updated if the date parser changes. So omit the date validation for others.
                return new CommandResult(MESSAGE_DATE_CONSTRAINTS); 
            }
            model.updateFilteredTaskList(deadline.get(), DEADLINE);
        }
        if (startDate.isPresent()) {
            model.updateFilteredTaskList(startDate.get(), START_DATE);
        }
        if (endDate.isPresent()) {
            model.updateFilteredTaskList(endDate.get(), END_DATE);
        }
        if (recurring.isPresent()) {
            model.updateFilteredTaskList(recurring.get(), RECURRING);
        }
        if (!tags.isEmpty()) {
            model.updateFilteredTaskListByTags(tags);
        }
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }


}
