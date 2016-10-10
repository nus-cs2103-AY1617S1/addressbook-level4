package taskle.logic.commands;

import taskle.commons.core.Messages;
import taskle.commons.core.ModifiableObservableList;
import taskle.commons.core.UnmodifiableObservableList;
import taskle.commons.exceptions.IllegalValueException;
import taskle.model.person.ModifiableTask;
import taskle.model.person.Name;
import taskle.model.person.ReadOnlyTask;
import taskle.model.person.UniqueTaskList.DuplicateTaskException;
import taskle.model.person.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task identified using it's last displayed index from the task manager.
 */
public class EditCommand extends Command {
    
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the Task identified by the index number used in the last Task listing.\n"
            + "Format: edit task_number new_task\n"
            + "Example: " + COMMAND_WORD + " 1 Buy dinner";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Task Manager";

    public final int targetIndex;
    
    public final Name newName;
    
    public EditCommand(int targetIndex, String newName) throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.newName = new Name(newName);
    }
    
    @Override
    public CommandResult execute() {
        ModifiableObservableList<ModifiableTask> lastShownList = model.getModifiableTaskList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ModifiableTask taskToEdit = lastShownList.get(targetIndex - 1);
        String name = taskToEdit.getName().fullName;
        try {
            try {
                model.editTask(taskToEdit, newName);
            } catch (DuplicateTaskException e) {
                return new CommandResult(MESSAGE_DUPLICATE_TASK);
            }
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, name));
    }

}
