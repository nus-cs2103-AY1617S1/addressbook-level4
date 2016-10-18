package seedu.agendum.logic.commands;

import seedu.agendum.commons.core.Messages;
import seedu.agendum.commons.core.UnmodifiableObservableList;
import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.model.task.*;
import seedu.agendum.model.task.UniqueTaskList.TaskNotFoundException;


/**
 * Renames a task in the to do list.
 */
public class RenameCommand extends Command {

    public static final String COMMAND_WORD = "rename";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Rename an existing task. "
            + "Parameters: INDEX (must be a positive number) NAME\n"
            + "Example: " + COMMAND_WORD
            + " 2 Watch Star Wars";

    public static final String MESSAGE_SUCCESS = "Task #%1$s renamed: %2$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists";

    public final int targetIndex;
    public final Name newTaskName;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public RenameCommand(int targetIndex, String name)
            throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.newTaskName = new Name(name);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToRename = lastShownList.get(targetIndex - 1);

        try {
            Task renamedTask = new Task(taskToRename);
            renamedTask.setName(newTaskName);
            model.updateTask(taskToRename, renamedTask);
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex, newTaskName));

    }

}
