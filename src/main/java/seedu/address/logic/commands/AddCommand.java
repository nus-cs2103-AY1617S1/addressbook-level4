package seedu.address.logic.commands;

import java.util.Date;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Task;
import seedu.address.model.item.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.RecurrenceRate;

import com.joestelmach.natty.*;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoableCommand {

    private static final int BASE_INDEX = 0;

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item to To-Do List. "
            + "Parameters: NAME [from/at START_DATE START_TIME][to/by END_DATE END_TIME][repeat every RECURRING_INTERVAL][-PRIORITY]\n"
            + "Example: " + COMMAND_WORD
            + " read Harry Potter and the Akshay -low";
    
    public static final String TOOL_TIP = "add NAME [from/at START_DATE START_TIME][to/by END_DATE END_TIME][repeat every RECURRING_INTERVAL][-PRIORITY]";

    public static final String MESSAGE_DUPLICATE_FLOATING_TASK = "This task already exists in the task manager";

    public static final String MESSAGE_SUCCESS = "New item added: %1$s";
    
    public static final String MESSAGE_UNDO_SUCCESS = "Undid add item: %1$s";

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

    public AddCommand(String taskNameString, String startDateString, String endDateString, String recurrenceRateString,
            String timePeriodString, String priorityString) throws IllegalValueException {
        com.joestelmach.natty.Parser dateParser = new com.joestelmach.natty.Parser();
    	Name taskName = new Name(taskNameString);
        Date startDate = null;
        Date endDate = null;
        RecurrenceRate recurrenceRate;
        Priority priority;

        if (startDateString != null) {
            List<DateGroup> startDateGroup = dateParser.parse(startDateString);
            startDate = startDateGroup.get(BASE_INDEX).getDates().get(BASE_INDEX);
        }

        if (endDateString != null) {
            List<DateGroup> endDateGroup = dateParser.parse(endDateString);
            endDate = endDateGroup.get(BASE_INDEX).getDates().get(BASE_INDEX);
        }

        if (recurrenceRateString == null && timePeriodString == null) {
            recurrenceRate = null;
        } else if (recurrenceRateString == null) {
            recurrenceRate = new RecurrenceRate("1", timePeriodString);
        } else {
            recurrenceRate = new RecurrenceRate(recurrenceRateString, timePeriodString);
        }

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
        default:
            priority = Priority.MEDIUM;
        }
            
        this.toAdd = new Task(taskName, startDate, endDate, recurrenceRate, priority);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.addTask(toAdd);
        
        // TODO: i don't like updating the history here, to refactor further
        // not sure if EVERY command should access history, or i pass history to parser then parser prepareUndoCmd together with the history
        history.updateCommandHistory(this);
        
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
