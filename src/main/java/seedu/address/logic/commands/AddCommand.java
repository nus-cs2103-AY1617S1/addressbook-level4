package seedu.address.logic.commands;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Task;
import seedu.address.model.item.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.item.DateTime;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.RecurrenceRate;

//@@author A0139655U
/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoableCommand {

    private final Logger logger = LogsCenter.getLogger(AddCommand.class);
    
    public static final String COMMAND_WORD = "add";
    private static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item to To-Do List.\n"
            + "Parameters: [add] NAME [from/at/start DATE_TIME] [to/by/end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]\n"
            + "Example: " + COMMAND_WORD
            + " feed cat by today 11:30am repeat every day -high";
    //@@author
    public static final String MESSAGE_SUCCESS = "New item added: %1$s";
    public static final String MESSAGE_UNDO_SUCCESS = "Undid add item: %1$s";
    
    public static final String TOOL_TIP = "[add] NAME [start DATE_TIME] [end DATE_TIME] [repeat every RECURRING_INTERVAL] [-PRIORITY]";

    private Task toAdd;
    
    //@@author A0139655U
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(HashMap<String, Optional<String>> mapOfStrings) 
                    throws IllegalValueException {
        HashMap<String, Object> mapOfTaskParameters = AddCommandHelper.convertStringToObjects(mapOfStrings);
        assert mapOfTaskParameters.get(Name.getMapNameKey()) != null;
        
        Name taskName = (Name) mapOfTaskParameters.get(Name.getMapNameKey());
        Date startDate = (Date) mapOfTaskParameters.get(DateTime.getMapStartDateKey());
        Date endDate = (Date) mapOfTaskParameters.get(DateTime.getMapEndDateKey());
        RecurrenceRate recurrenceRate = (RecurrenceRate) mapOfTaskParameters.get(RecurrenceRate.getMapRecurrenceRateKey());
        Priority priority = (Priority) mapOfTaskParameters.get(Priority.getMapPriorityKey());
        
        logger.log(Level.FINEST, "taskName is " + taskName + "\nstartDate is " + startDate + "\nendDate is " + endDate
                + "\n recurrenceRate is " + recurrenceRate + "\npriority is " + priority);
        this.toAdd = new Task(taskName, startDate, endDate, recurrenceRate, priority);
    }
    
    public AddCommand(String taskName) {
        this.toAdd = new Task(new Name(taskName));
    }

    //@@author
    @Override
    public CommandResult execute() {
        assert model != null && toAdd != null;
        
        // check if viewing done list
        // cannot add to done list, return an incorrect command msg
        if (model.isCurrentListDoneList()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(String.format(Messages.MESSAGE_DONE_LIST_RESTRICTION));
        }
        
        // add this task to the model
        model.addTask(toAdd);     
        
        // update the history with this command
        updateHistory();
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    //@@author A0093960X
    @Override
    public CommandResult undo() {
        assert model != null && toAdd != null;
        
        // attempt to undo the add by deleting the same task that was added
        try {
            model.deleteTask(toAdd);
        } catch (TaskNotFoundException e) {
            // cannot find the task that was added
            return new CommandResult("Failed to undo last add command: add " + toAdd);
        }
        
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, toAdd));
    }
    
    //@@author A0139655U
    public static String getMessageUsage() {
        return MESSAGE_USAGE;
    }
}
