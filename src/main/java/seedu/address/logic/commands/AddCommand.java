package seedu.address.logic.commands;

import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.DateParser;
import seedu.address.model.item.Task;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.model.item.UniqueTaskList.DuplicateTaskException;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item to To-Do List. "
            + "Parameters: NAME [from/at START_DATE START_TIME][to/by END_DATE END_TIME][repeat every RECURRING_INTERVAL][-PRIORITY]\n"
            + "Example: " + COMMAND_WORD
            + " read Harry Potter and the Akshay -low";
    
    public static final String TOOL_TIP = "add NAME [from/at START_DATE START_TIME][to/by END_DATE END_TIME][repeat every RECURRING_INTERVAL][-PRIORITY]";
    
    public static final String MESSAGE_DUPLICATE_FLOATING_TASK = "This task already exists in the task manager";

    public static final String MESSAGE_SUCCESS = "New item added: %1$s";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName) throws IllegalValueException {
        this.toAdd = new Task(new Name(taskName));
    }
    
    public AddCommand(String taskNameString, String startDateString, String endDateString, 
            String recurrenceRateString, String priorityString) throws IllegalValueException {
        
        Name taskName = new Name(taskNameString);
        Date startDate = null;
        Date endDate = null;
        RecurrenceRate recurrenceRate;
        Priority priority;
        
        if (startDateString != null) {
            DateParser dp = new DateParser(startDateString);
            startDate = dp.parseDate();
        }
        
        if (endDateString != null) {
            DateParser dp = new DateParser(endDateString);
            endDate = dp.parseDate();
        }
        
        if (recurrenceRateString == null) {
            recurrenceRate = new RecurrenceRate(0);
        } else { //TODO: Should I put else-if here instead? Seems ridiculous though.
            recurrenceRate = new RecurrenceRate(Integer.valueOf(recurrenceRateString));
            System.out.println(Integer.valueOf(recurrenceRateString));
        }
        
        //TODO: Throw IllegalValueException for default cases? Should have detected at parser.
        switch (priorityString) {
            case ("low"): case ("l"): priority = Priority.LOW; break;
            case ("high"): case ("h"): priority = Priority.HIGH; break;
            case ("medium"): case ("m"): case ("med"):
            default: priority = Priority.MEDIUM;
        }
        this.toAdd = new Task(taskName, startDate, endDate, recurrenceRate, priority);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
        } catch (DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_FLOATING_TASK);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
