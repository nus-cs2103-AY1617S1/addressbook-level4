package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task identified using it's last displayed index from the task manager.
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
    
    private int targetIndex;
    private String targetProperty;
    private String newInfo;
    
    public EditCommand(String targetIndex, String targetProperty, String newInfo) {
        this.targetIndex = Integer.parseInt(targetIndex);
        this.targetProperty = targetProperty;
        this.newInfo = newInfo;
    }

    @Override
    public CommandResult execute() {
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
       try{ 
           model.editTask(taskToEdit, targetProperty, newInfo);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }
    
}
