package seedu.forgetmenot.logic.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.forgetmenot.commons.core.EventsCenter;
import seedu.forgetmenot.commons.core.Messages;
import seedu.forgetmenot.commons.core.UnmodifiableObservableList;
import seedu.forgetmenot.commons.events.ui.JumpToListRequestEvent;
import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.model.task.ReadOnlyTask;
import seedu.forgetmenot.model.task.Time;
import seedu.forgetmenot.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0139671X
/**
 * Edits a task identified using it's last displayed index from the task
 * manager.
 */
public class EditCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger( EditCommand.class.getName() );

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified using it's last displayed index. " + "\tParameters: INDEX NEW_DETAILS\n"
            + "\tExample: " + COMMAND_WORD + " 1 oranges";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "You've successfully edited the task!\n"
            + "Edited Task: %1$s";
    public static final String MESSAGE_EDIT_TASK_NOT_SUCCESSFUL = "Sorry! The edit details are invalid."
            + " Please try again.";

    private int targetIndex;
    private String newName;
    private String newStart;
    private String newEnd;

    public EditCommand(String targetIndex, String name, String start, String end) {
        this.targetIndex = Integer.parseInt(targetIndex);
        this.newName = name;
        this.newStart = start;
        this.newEnd = end;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);

        try {
            if (!validateEditTimings(taskToEdit)) {
                return new CommandResult(Messages.MESSAGE_INVALID_START_AND_END_TIME);
            }

            model.saveToHistory();
            model.editTask(taskToEdit, newName, newStart, newEnd);
            model.updateFilteredTaskListToShowNotDone();
            
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (IllegalValueException e) {
            LOGGER.log(Level.WARNING, "Illegal edit : name[" + newName + "]" 
        + " start[" + newStart + "]" + " end[" + newEnd + "]");
            return new CommandResult(MESSAGE_EDIT_TASK_NOT_SUCCESSFUL);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    public boolean validateEditTimings(ReadOnlyTask taskToEdit) throws IllegalValueException {

        // checks that new start time must be before end
        if (newStart != null && !taskToEdit.getEndTime().isMissing()
                && !Time.checkOrderOfDates(newStart, taskToEdit.getEndTime().appearOnUIFormat())) {
            return false;
        }
        // checks that the new end time must be after start
        if (newEnd != null && !taskToEdit.getStartTime().isMissing()
                && Time.checkOrderOfDates(newEnd, taskToEdit.getStartTime().appearOnUIFormat())) {
            return false;
        }
        // checks that the new start and end time are valid
        if (newEnd != null && newStart != null && !Time.checkOrderOfDates(newStart, newEnd)) {
            return false;
        }
        return true;
    }

}
