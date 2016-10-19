package seedu.emeraldo.logic.commands;

import seedu.emeraldo.commons.core.Messages;
import seedu.emeraldo.commons.core.UnmodifiableObservableList;
import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.model.task.Description;
import seedu.emeraldo.model.task.DateTime;
import seedu.emeraldo.model.task.ReadOnlyTask;
import seedu.emeraldo.model.task.Task;
import seedu.emeraldo.model.task.UniqueTaskList.TaskNotFoundException;

public class EditCommand extends Command{
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD            
            + ": Edits the task identified by the index number used in the last tasks listing.\n"
            + "Parameters: INDEX (must be a positive integer) and \"TASK_DESCRIPTION\"\n"
            + "Example: " + COMMAND_WORD + " 1" + " \"CS2103T Week 8 Tutorial\"";
   
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";
    
    public final int targetIndex;
    public final Description description;
    public final DateTime dateTime;
    
    public EditCommand(String targetIndex, String description, String completeDT) throws IllegalValueException {
        this.targetIndex = Integer.parseInt(targetIndex);
        this.description = new Description(description);
        this.dateTime = new DateTime(completeDT);
    }
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = (Task) lastShownList.get(targetIndex - 1);

        try {
            model.editTask(taskToEdit, targetIndex - 1, description, dateTime);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));

    }

}
