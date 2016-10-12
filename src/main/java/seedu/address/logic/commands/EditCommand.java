package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateFormatter;
import seedu.address.model.Undo;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Location;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task in the task scheduler.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task in the scheduler. "
            + "Parameters: INDEX TASK s/START_DATE e/END_DATE a/LOCATION \n"
            + "Example: " + COMMAND_WORD
            + " 1 MUST do CS2103 Pretut s/07102016 e/10102016 a/NUS COM1-B103";

    public static final String MESSAGE_SUCCESS = "Task edited: %1$s";
    
    public final int targetIndex;
    private final Task toCopy;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(int targetIndex, String name, String startDate, String endDate, String address)
            throws IllegalValueException {
 
        this.targetIndex = targetIndex;
        final Set<Tag> tagSet = new HashSet<>();
        this.toCopy = new Task(
                new Name(name),
                DateFormatter.convertStringToDate(startDate),
                DateFormatter.convertStringToDate(endDate),
                new Location(address),
                new UniqueTagList(tagSet)
        );
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
            toCopy.setTags(personToEdit.getTags());
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
