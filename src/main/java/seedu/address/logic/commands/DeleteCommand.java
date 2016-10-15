package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the  Task identified by the index number or specific name used in the last Task listing.\n"
            + "Parameters: INDEX (must be a positive integer) or NAME\n"
            + "Example: " + COMMAND_WORD + " 1 or horror night";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";

    public final int targetIndex;
    public final String name;
    
    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.name = null;
    }

    public DeleteCommand(String name){
    	this.name = name;
    	this.targetIndex = -1;
    }
    
    @Override
    public CommandResult execute() {
        ReadOnlyTask TaskToDelete = null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (targetIndex != -1) {
            if (lastShownList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            TaskToDelete = lastShownList.get(targetIndex - 1);
        } else {
            assert this.name != null;
            for (ReadOnlyTask e : lastShownList) {
                if (name.trim().equals(e.getName().toString())) {
                    TaskToDelete = e;
                    break;
                }
            }
            if (TaskToDelete == null)
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_NAME);
        }
        try {
            model.deleteTask(TaskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        String successMessage = getDeleteSuccessMessage(TaskToDelete);
        return new CommandResult(String.format(successMessage, TaskToDelete));
    }

    public static String getDeleteSuccessMessage(ReadOnlyTask TaskToDelete) {
        if (TaskToDelete.isEvent()) {
            return MESSAGE_DELETE_EVENT_SUCCESS;
        } else {
            return MESSAGE_DELETE_TASK_SUCCESS;
        }
    }

}
