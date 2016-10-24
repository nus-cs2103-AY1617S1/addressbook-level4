package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.commons.core.UnmodifiableObservableList;
import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.Task;
import seedu.taskscheduler.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.taskscheduler.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0148145E

/**
 * Edits a task in the task scheduler.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task in the scheduler. "
            + "Parameters: INDEX TASK s/START_DATE e/END_DATE at LOCATION \n"
            + "Example: " + COMMAND_WORD
            + " 1 Must Do CS2103 Pretut\n"
            + "Example: " + COMMAND_WORD
            + " 2 new task name s/10-Oct-2016 8am e/10-Oct-2016 9am at NUS\n"
            + "Example: " + COMMAND_WORD
            + " 1 another new task name s/11-Oct-2016 8am e/11-Oct-2016 9am at there\n";

    public static final String MESSAGE_SUCCESS = "Task edited: %1$s";
    
    public final int targetIndex;
    private final Task toCopy;
    private Task toEdit;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(int targetIndex, Task toCopy)
            throws IllegalValueException {
        
        this.targetIndex = targetIndex;
        this.toCopy = toCopy;
    }

    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        toEdit = (Task)lastShownList.get(targetIndex - 1);

        try {
            model.editTask(toEdit, toCopy);
            CommandHistory.addExecutedCommand(this);
        } catch (DuplicateTaskException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            assert false : Messages.MESSAGE_TASK_CANNOT_BE_MISSING;
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toEdit));
    }

    @Override
    public CommandResult revert() {
        try {
            model.editTask(toCopy, toEdit);
            CommandHistory.addRevertedCommand(this);
        } catch (DuplicateTaskException e) {
            assert false : Messages.MESSAGE_TASK_CANNOT_BE_DUPLICATED;
        } catch (TaskNotFoundException e) {
            assert false : Messages.MESSAGE_TASK_CANNOT_BE_MISSING;
        }
        return new CommandResult(String.format(MESSAGE_REVERT_COMMAND, COMMAND_WORD, "\n" + toCopy));
    }
}
