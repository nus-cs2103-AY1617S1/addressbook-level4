package seedu.forgetmenot.logic.commands;

import java.util.function.Predicate;

import org.apache.commons.lang.ObjectUtils.Null;

import seedu.forgetmenot.commons.core.Messages;
import seedu.forgetmenot.commons.core.UnmodifiableObservableList;
import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.model.task.ReadOnlyTask;
import seedu.forgetmenot.model.task.Task;
import seedu.forgetmenot.model.task.Time;
import seedu.forgetmenot.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task identified using it's last displayed index from the task manager.
 * @@author A0139671X
 */
public class EditCommand extends Command {
    
    public static final String COMMAND_WORD = "edit";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the task identified using it's last displayed index. "
            + "Parameters: INDEX PROPERTY NEW_INPUT\n"
            + "Example: " + COMMAND_WORD 
            + " 1 name oranges";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "You've successfully editted the task!\n"
            + "Editted Task: %1$s";
    public static final String MESSAGE_EDIT_TASK_NOT_SUCCESSFUL = "Sorry! The edit details are invalid. Please try again.";
    
    private int targetIndex;
    private String newName;
    private String newStart;
    private String newEnd;
    private String newRecur;
    
    public EditCommand(String targetIndex, String name, String start, String end, String recur) {
        this.targetIndex = Integer.parseInt(targetIndex);
        this.newName = name;
        this.newStart = start;
        this.newEnd = end;
        this.newRecur = recur;
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
            
            // checks that new start time must be before end
            if (newStart != null && !taskToEdit.getEndTime().isMissing() && !Time.checkOrderOfDates(newStart, taskToEdit.getEndTime().appearOnUIFormat()))
                return new CommandResult(Messages.MESSAGE_INVALID_START_AND_END_TIME);
            
            // checks that the new end time must be after start
            if (newEnd != null && !taskToEdit.getStartTime().isMissing() && Time.checkOrderOfDates(newEnd, taskToEdit.getStartTime().appearOnUIFormat()))
                return new CommandResult(Messages.MESSAGE_INVALID_START_AND_END_TIME);
            
            // checks that the new start and end time are valid
            if (newEnd != null && newStart != null && !Time.checkOrderOfDates(newStart, newEnd))
                return new CommandResult(Messages.MESSAGE_INVALID_START_AND_END_TIME);
            
            model.saveToHistory();
            model.editTask(taskToEdit, newName, newStart, newEnd, newRecur);
            model.updateFilteredTaskListToShowNotDone();
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (IllegalValueException e) {
            return new CommandResult(MESSAGE_EDIT_TASK_NOT_SUCCESSFUL);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }
    
}
