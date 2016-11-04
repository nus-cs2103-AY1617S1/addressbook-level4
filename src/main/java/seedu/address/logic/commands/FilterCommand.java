package seedu.address.logic.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.TypesUtil;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.EventDate;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Recurring;

//@@author A0146123R
/**
 * Filter the filtered task list to filter by the given attribute.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filter list for specified attributes "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: [s/START_DATE] [e/END_DATE] [d/DEADLINE] [r/RECURRING] [p/PRIORITY] [t/TAG]...\n" + "Example: "
            + COMMAND_WORD + " s/23.10.2016 r/daily";

    private final Optional<String> deadline;
    private final Optional<String> startDate;
    private final Optional<String> endDate;
    private final Optional<String> recurring;
    private final Set<String> tags;
    private final Optional<String> priority;

    public FilterCommand(Optional<String> deadline, Optional<String> startDate, Optional<String> endDate,
            Optional<String> recurring, Set<String> tags, Optional<String> priority) {
        this.deadline = deadline;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recurring = recurring;
        this.tags = tags;
        this.priority = priority;
    }

    @Override
    public CommandResult execute() {
        Map<String, String> filterQualifications = new HashMap<>();
        try {
            if (deadline.isPresent()) {
                String deadlineString = Deadline.getValidDate(deadline.get());
                filterQualifications.put(TypesUtil.DEADLINE, deadlineString);
            }
            if (startDate.isPresent()) {
                String startDateString = EventDate.getValidDate(startDate.get());
                filterQualifications.put(TypesUtil.START_DATE, startDateString);
            }
            if (endDate.isPresent()) {
                String endDateString = EventDate.getValidDate(endDate.get());
                filterQualifications.put(TypesUtil.END_DATE, endDateString);
            }
            if (recurring.isPresent()) {
                if (Recurring.isValidFrequency(recurring.get())) {
                    filterQualifications.put(TypesUtil.RECURRING, recurring.get());
                } else {
                    throw new IllegalValueException(Recurring.MESSAGE_RECURRING_CONSTRAINTS);
                }
            }
            if (priority.isPresent()) {
                if (Priority.isValidPriorityLevel(Integer.parseInt(priority.get())))
                    filterQualifications.put(TypesUtil.PRIORITY, priority.get());
                else
                    throw new IllegalValueException(Priority.MESSAGE_INVALID_PRIORITY_LEVEL);
            }
        } catch (IllegalValueException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(e.getMessage());
        }
        model.updateFilteredTaskList(filterQualifications, tags);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
