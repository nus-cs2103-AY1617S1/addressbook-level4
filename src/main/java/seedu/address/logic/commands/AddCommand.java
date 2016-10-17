package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.history.ReversibleEffect;
import seedu.address.model.item.DateTime;
import seedu.address.model.item.Task;
import seedu.address.model.item.TimePeriod;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.RecurrenceRate;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    private final Logger logger = LogsCenter.getLogger(AddCommand.class);
    
    private static final String STRING_CONSTANT_ONE = "1";

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item to To-Do List. "
            + "Parameters: [add] NAME, [from/at/start DATE_TIME] [to/by/end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]\n"
            + "Example: " + COMMAND_WORD
            + " feed cat, by today 11:30am repeat every day -high";
    
    public static final String TOOL_TIP = "add NAME, [from/at/start DATE_TIME] [to/by/end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]\n";
    
    public static final String MESSAGE_SUCCESS = "New item added: %1$s";
    
    public static final String MESSAGE_RECUR_DATE_TIME_CONSTRAINTS = "For recurring tasks to be valid, "
            + "at least one DATE_TIME must be provided";
    
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

    public AddCommand(String taskNameString, String startDateString, String endDateString, String rateString,
            String timePeriodString, String priorityString) throws IllegalValueException {
        assert taskNameString != null;
        
    	Name taskName = new Name(taskNameString);
        Date startDate = null;
        Date endDate = null;
        RecurrenceRate recurrenceRate = null;
        Priority priority;

        if (startDateString != null) {
            assert DateTime.isValidDate(startDateString);
            startDate = DateTime.convertStringToStartDate(startDateString);
        }

        if (endDateString != null) {
            assert DateTime.isValidDate(endDateString);
            endDate = DateTime.convertStringToEndDate(endDateString, startDate);
        }

        if (rateString != null && timePeriodString != null) {
            recurrenceRate = new RecurrenceRate(rateString, timePeriodString);
        } else if (rateString == null && timePeriodString != null) {
            recurrenceRate = new RecurrenceRate(STRING_CONSTANT_ONE, timePeriodString);
        } else if (rateString != null && timePeriodString == null) {
            throw new IllegalValueException(RecurrenceRate.MESSAGE_VALUE_CONSTRAINTS);
        } 
        
        if (recurrenceRate != null && recurrenceRate.timePeriod != TimePeriod.DAY && 
                recurrenceRate.timePeriod.toString().toLowerCase().contains("day") &&
                startDate == null && endDate == null) {
            startDate = DateTime.assignStartDateToSpecifiedWeekday(recurrenceRate.timePeriod.toString());
        }
        
        if (recurrenceRate != null && startDate == null && endDate == null) {
            throw new IllegalValueException(MESSAGE_RECUR_DATE_TIME_CONSTRAINTS);
        }

        priority = stringToPriority(priorityString);
            
        this.toAdd = new Task(taskName, startDate, endDate, recurrenceRate, priority);
    }

    //TODO: Comments suck
    /**
     * Converts given String into Priority
     */
    private Priority stringToPriority(String priorityString) {
        Priority priority;
        switch (priorityString) {
        case ("low"):
        case ("l"):
            priority = Priority.LOW;
            break;
        case ("high"):
        case ("h"):
            priority = Priority.HIGH;
            break;
        case ("medium"):
        case ("med"):
        case ("m"):
        default:
            priority = Priority.MEDIUM;
        }
        return priority;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.addTask(toAdd);
        
        List<Task> affectedTasks = new ArrayList<Task>();
        affectedTasks.add(toAdd);
        history.update(new ReversibleEffect(COMMAND_WORD, affectedTasks));
        history.resetRedo();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
