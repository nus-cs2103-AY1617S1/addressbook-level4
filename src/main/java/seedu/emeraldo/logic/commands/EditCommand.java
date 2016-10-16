package seedu.emeraldo.logic.commands;

import seedu.emeraldo.commons.core.Messages;
import seedu.emeraldo.commons.core.UnmodifiableObservableList;
import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.model.task.ReadOnlyTask;
import seedu.emeraldo.model.task.UniqueTaskList.TaskNotFoundException;

public class EditCommand extends Command{
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD            
            + ": Edits the task identified by the index number used in the last tasks listing.\n"
            + "Parameters: INDEX (must be a positive integer) and \"TASK_DESCRIPTION\"\n"
            + "Example: " + COMMAND_WORD + " 1" + "CS2103T Week 8 Tutorial";
   
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";
    
    public final int targetIndex;
    
    public final String field;
    
    public EditCommand(String targetIndex, String field) throws IllegalValueException {
        this.targetIndex = Integer.parseInt(targetIndex);
        this.field = field;
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
            model.editTask(taskToEdit);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));

    }

}
