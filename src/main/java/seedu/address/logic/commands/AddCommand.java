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

            + "Example: " + COMMAND_WORD + " feed cat by today 11:30am repeat every day -high";

    //@author
    public static final String MESSAGE_SUCCESS = "New item added: %1$s";
    public static final String MESSAGE_UNDO_SUCCESS = "Undid add item: %1$s";
    private static final String MESSAGE_UNDO_FAILURE = "Failed to undo last add command: add %1$s";
    public static final String TOOL_TIP = "[add] NAME [start DATE_TIME] [end DATE_TIME] "
            + "[repeat every RECURRING_INTERVAL] [-PRIORITY]";

    // @@author
    private Task toAdd;

    // @@author A0139655U
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException If any of the raw values are invalid.
     */
    public AddCommand(HashMap<String, Optional<String>> mapOfStrings) throws IllegalValueException {
        HashMap<String, Object> mapOfTaskParameters = AddCommandHelper.convertStringToObjects(mapOfStrings);
        assert mapOfTaskParameters.get(Name.getMapNameKey()) != null;

        Name taskName = (Name) mapOfTaskParameters.get(Name.getMapNameKey());
        Date startDate = (Date) mapOfTaskParameters.get(DateTime.getMapStartDateKey());
        Date endDate = (Date) mapOfTaskParameters.get(DateTime.getMapEndDateKey());
        RecurrenceRate recurrenceRate = (RecurrenceRate) mapOfTaskParameters
                .get(RecurrenceRate.getMapRecurrenceRateKey());
        Priority priority = (Priority) mapOfTaskParameters.get(Priority.getMapPriorityKey());

        logger.log(Level.FINEST, "taskName is " + taskName + "\nstartDate is " + startDate + "\nendDate is "
                + endDate + "\n recurrenceRate is " + recurrenceRate + "\npriority is " + priority);
        this.toAdd = new Task(taskName, startDate, endDate, recurrenceRate, priority);
    }
    // @@author

    public AddCommand(String taskName) {
        this.toAdd = new Task(new Name(taskName));
    }

    // @@author A0093960X
    @Override
    public CommandResult execute() {
        assert model != null && toAdd != null;

        if (attemptToExecuteAddOnDoneList()) {
            logger.info("Attempting to execute the add command on the done list. Aborting this action.");
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_DONE_LIST_RESTRICTION);
        }

        logger.info("Adding task in execute() of AddCommand");
        model.addTask(toAdd);
        updateHistory();

        String formattedResult = String.format(MESSAGE_SUCCESS, toAdd);
        return new CommandResult(formattedResult);
    }

    @Override
    public CommandResult undo() {
        assert model != null && toAdd != null;

        String formattedResult;
        try {
            logger.info("Attempting to undo the add by deleting the added task from the undone list.");
            model.deleteUndoneTask(toAdd);
            formattedResult = String.format(MESSAGE_UNDO_SUCCESS, toAdd);
            return new CommandResult(formattedResult);

        } catch (TaskNotFoundException e) {
            logger.info("Unable to undo the add as the task was not found in the undone list.");
            formattedResult = String.format(MESSAGE_UNDO_FAILURE, toAdd);
            return new CommandResult(formattedResult);
        }

    }

    /**
     * Returns true if the add command is being executed on the done list.
     * 
     * @return A boolean representing if the add is executed on the done list.
     */
    private boolean attemptToExecuteAddOnDoneList() {
        return model.isCurrentListDoneList() && (!isRedoAction);
    }

    // @@author A0139655U
    public static String getMessageUsage() {
        return MESSAGE_USAGE;
    }
}
