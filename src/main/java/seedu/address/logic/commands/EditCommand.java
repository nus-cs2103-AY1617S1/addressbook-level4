package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Undo;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Location;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDateTime;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

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

        ReadOnlyTask personToEdit = lastShownList.get(targetIndex - 1);

        try {
//            toCopy.setTags(personToEdit.getTags());
            model.editTask(personToEdit, toCopy);
            CommandHistory.addMutateCmd(new Undo(COMMAND_WORD, targetIndex, (Task)personToEdit));
        } catch (DuplicateTaskException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing"; 
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, personToEdit));
    }
}
