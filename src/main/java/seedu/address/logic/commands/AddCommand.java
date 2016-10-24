package seedu.address.logic.commands;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Task;
import seedu.address.model.item.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.item.DateTime;
import seedu.address.model.item.TimePeriod;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.RecurrenceRate;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoableCommand {

    private final Logger logger = LogsCenter.getLogger(AddCommand.class);
    
    private static final String STRING_CONSTANT_ONE = "1";

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item to To-Do List. "
            + "Parameters: [add] NAME [from/at/start DATE_TIME] [to/by/end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]\n"
            + "Example: " + COMMAND_WORD
            + " feed cat by today 11:30am repeat every day -high";
    public static final String MESSAGE_SUCCESS = "New item added: %1$s";
    public static final String MESSAGE_UNDO_SUCCESS = "Undid add item: %1$s";
    public static final String MESSAGE_RECUR_DATE_TIME_CONSTRAINTS = "For recurring tasks to be valid, "
            + "at least one DATE_TIME must be provided";
    
    public static final String TOOL_TIP = "[add] NAME [start DATE_TIME] [end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]";

    private Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String taskName) {
        this.toAdd = new Task(new Name(taskName));
    }

    public AddCommand(Optional<String> taskNameString, Optional<String> startDateString, Optional<String> endDateString, 
            Optional<String> rateString, Optional<String> timePeriodString, Optional<String> priorityString) 
                    throws IllegalValueException {
        assert taskNameString != null;
        
    	Name taskName = new Name(taskNameString.get());
        Date startDate = generateStartDateIfPresent(startDateString);
        Date endDate = generateEndDateIfPresent(endDateString, startDate);
        RecurrenceRate recurrenceRate = generateRecurrenceRateIfPresent(rateString, timePeriodString); 
        Priority priority = Priority.convertStringToPriority(priorityString.get());

        if (recurWeekdaysButDateNotGiven(startDate, endDate, recurrenceRate)) {
            startDate = DateTime.assignStartDateToSpecifiedWeekday(recurrenceRate.timePeriod.toString());
        } else if (otherRecurrenceButDateNotGiven(startDate, endDate, recurrenceRate)) {
            throw new IllegalValueException(MESSAGE_RECUR_DATE_TIME_CONSTRAINTS);
        }

        this.toAdd = new Task(taskName, startDate, endDate, recurrenceRate, priority);
    }

    private boolean otherRecurrenceButDateNotGiven(Date startDate, Date endDate, RecurrenceRate recurrenceRate) {
        return recurrenceRate != null && startDate == null && endDate == null;
    }

    private boolean recurWeekdaysButDateNotGiven(Date startDate, Date endDate, RecurrenceRate recurrenceRate) {
        return recurrenceRate != null && recurrenceRate.timePeriod != TimePeriod.DAY && 
                recurrenceRate.timePeriod.toString().toLowerCase().contains("day") &&
                startDate == null && endDate == null;
    }

    private RecurrenceRate generateRecurrenceRateIfPresent(Optional<String> rateString,
            Optional<String> timePeriodString) throws IllegalValueException {
        RecurrenceRate recurrenceRate = null;
        
        if (rateString.isPresent() && timePeriodString.isPresent()) {
            recurrenceRate = new RecurrenceRate(rateString.get(), timePeriodString.get());
        } else if (!rateString.isPresent() && timePeriodString.isPresent()) {
            recurrenceRate = new RecurrenceRate(STRING_CONSTANT_ONE, timePeriodString.get());
        } else if (rateString.isPresent() && !timePeriodString.isPresent()) {
            throw new IllegalValueException(RecurrenceRate.MESSAGE_VALUE_CONSTRAINTS);
        }
        return recurrenceRate;
    }

    private Date generateEndDateIfPresent(Optional<String> endDateString, Date startDate) {
        Date endDate = null;
        
        if (endDateString.isPresent()) {
            endDate = DateTime.convertStringToDate(endDateString.get());
            if (startDate != null && !DateTime.hasDateValue(endDateString.get())) {
                endDate = DateTime.setEndDateToStartDate(startDate, endDate);
            }
            if (!DateTime.hasTimeValue(endDateString.get())) {
                endDate = DateTime.setTimeToEndOfDay(endDate);
            }
        }
        return endDate;
    }

    private Date generateStartDateIfPresent(Optional<String> startDateString) {
        Date startDate = null;
        
        if (startDateString.isPresent()) {
            startDate = DateTime.convertStringToDate(startDateString.get());
            if (!DateTime.hasTimeValue(startDateString.get())) {
                startDate = DateTime.setTimeToStartOfDay(startDate);
            }
        }
        return startDate;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.addTask(toAdd);
        
        // TODO: i don't like updating the history here, to refactor further
        // not sure if EVERY command should access history, or i pass history to parser then parser prepareUndoCmd together with the history
        updateHistory();
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public CommandResult undo() {
        assert model != null;
        try {
            model.deleteTask(toAdd);
        } catch (TaskNotFoundException e) {
            return new CommandResult("Failed to undo last add command: add " + toAdd);
        }
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, toAdd));
    }

}
