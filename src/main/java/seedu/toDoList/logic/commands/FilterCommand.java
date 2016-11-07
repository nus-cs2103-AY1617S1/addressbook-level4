package seedu.toDoList.logic.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import seedu.toDoList.commons.exceptions.IllegalValueException;
import seedu.toDoList.commons.util.Types;
import seedu.toDoList.model.task.Deadline;
import seedu.toDoList.model.task.EventDate;
import seedu.toDoList.model.task.Priority;
import seedu.toDoList.model.task.Recurring;

//@@author A0146123R
/**
 * Filter the list by given attributes.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filter list for specified attributes "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: [s/START_DATE] [e/END_DATE] [d/DEADLINE] [r/RECURRING] [p/PRIORITY] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " s/today r/daily";

    private final Optional<String> deadline;
    private final Optional<String> startDate;
    private final Optional<String> endDate;
    private final Optional<String> recurring;
    private final Set<String> tags;
    private final Optional<String> priority;

    /**
     * Convenience constructor using raw values.
     */
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
        Map<Types, String> filterQualifications = new HashMap<>();
        try {
            prepareDeadline(filterQualifications);
            prepareStartDate(filterQualifications);
            prepareEndDate(filterQualifications);
            prepareRecurring(filterQualifications);
            preparePriority(filterQualifications);
        } catch (IllegalValueException e) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(e.getMessage());
        }
        model.updateFilteredTaskList(filterQualifications, tags);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    /**
     * Parse the given deadline qualification.
     * 
     * @throws IllegalValueException
     *             if it is invalid
     */
    private void prepareDeadline(Map<Types, String> qualifications) throws IllegalValueException {
        if (deadline.isPresent()) {
            String deadlineString = Deadline.getValidDate(deadline.get());
            qualifications.put(Types.DEADLINE, deadlineString);
        }
    }

    /**
     * Parse the given start date qualification.
     * 
     * @throws IllegalValueException
     *             if it is invalid
     */
    private void prepareStartDate(Map<Types, String> qualifications) throws IllegalValueException {
        if (startDate.isPresent()) {
            String startDateString = EventDate.getValidDate(startDate.get());
            qualifications.put(Types.START_DATE, startDateString);
        }
    }

    /**
     * Parse the given end date qualification.
     * 
     * @throws IllegalValueException
     *             if it is invalid
     */
    private void prepareEndDate(Map<Types, String> qualifications) throws IllegalValueException {
        if (endDate.isPresent()) {
            String endDateString = EventDate.getValidDate(endDate.get());
            qualifications.put(Types.END_DATE, endDateString);
        }
    }

    /**
     * Parse the given priority qualification.
     * 
     * @throws IllegalValueException
     *             if it is invalid
     */
    private void prepareRecurring(Map<Types, String> qualifications) throws IllegalValueException {
        if (recurring.isPresent()) {
            if (Recurring.isValidFrequency(recurring.get())) {
                qualifications.put(Types.RECURRING, recurring.get());
            } else {
                throw new IllegalValueException(Recurring.MESSAGE_RECURRING_CONSTRAINTS);
            }
        }
    }

    // @@author A0138717X
    /**
     * Parse the given priority qualification.
     * 
     * @throws IllegalValueException
     *             if it is invalid
     */
    private void preparePriority(Map<Types, String> qualifications) throws IllegalValueException {
        if (priority.isPresent()) {
            if (Priority.isValidPriorityLevel(Integer.parseInt(priority.get()))) {
                qualifications.put(Types.PRIORITY_LEVEL, priority.get());
            } else {
                throw new IllegalValueException(Priority.MESSAGE_INVALID_PRIORITY_LEVEL);
            }
        }
    }

}