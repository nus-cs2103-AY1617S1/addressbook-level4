package seedu.address.logic.commands;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Task;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.RecurrenceRate;

import com.joestelmach.natty.*;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    private final Logger logger = LogsCenter.getLogger(AddCommand.class);
    
    private static final String STRING_CONSTANT_ONE = "1";
    private static final int BASE_INDEX = 0;

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item to To-Do List. "
            + "Parameters: NAME [from/at START_DATE START_TIME] [to/by END_DATE END_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]\n"
            + "Example: " + COMMAND_WORD
            + " read Harry Potter and the Akshay -low";
    
    public static final String TOOL_TIP = "add NAME [from/at START_DATE START_TIME] [to/by END_DATE END_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]";
    
    public static final String MESSAGE_SUCCESS = "New item added: %1$s";

    private static final String INVALID_START_DATE = "Invalid start date";
    private static final String INVALID_END_DATE = "Invalid end date";
    
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
            startDate = verifyDate(startDateString, INVALID_START_DATE);
        }

        if (endDateString != null) {
            endDate = verifyDate(endDateString, INVALID_END_DATE);
        }

        if (rateString != null && timePeriodString != null) {
            recurrenceRate = new RecurrenceRate(rateString, timePeriodString);
        } else if (rateString == null && timePeriodString != null) {
            recurrenceRate = new RecurrenceRate(STRING_CONSTANT_ONE, timePeriodString);
        } else if (rateString != null && timePeriodString == null) {
            throw new IllegalValueException(RecurrenceRate.MESSAGE_VALUE_CONSTRAINTS);
        } 

        priority = verifyPriority(priorityString);
            
        this.toAdd = new Task(taskName, startDate, endDate, recurrenceRate, priority);
    }

    private Priority verifyPriority(String priorityString) {
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

    /**
     * Converts given String into a Date object
     *
     * @throws IllegalValueException
     *             if given String cannot be converted into a valid Date object
     */
    private Date verifyDate(String dateString, String errorMessage)
            throws IllegalValueException {
        Date date;
        List<DateGroup> dates = new Parser().parse(dateString);
        try {
            date = dates.get(BASE_INDEX).getDates().get(BASE_INDEX);
            String syntaxTree = dates.get(BASE_INDEX).getSyntaxTree().toStringTree();
            if(!syntaxTree.contains("EXPLICIT_TIME")) {
                date = setTimeToEndOfDay(date);
            }
            //TODO: Should I not catch exception instead?
        } catch (IndexOutOfBoundsException ioobe) {
            throw new IllegalValueException(errorMessage);
        }
        
        
        return date;
    }

    /**
     * Sets time of Date object to end of the day i.e "23:59:59"
     */
    private Date setTimeToEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        date = calendar.getTime();
        return date;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.addTask(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
