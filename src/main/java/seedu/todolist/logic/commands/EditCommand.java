package seedu.todolist.logic.commands;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.task.Interval;
import seedu.todolist.model.task.LocationParameter;
import seedu.todolist.model.task.Name;
import seedu.todolist.model.task.ReadOnlyTask;
import seedu.todolist.model.task.RemarksParameter;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits the information of an existing task.
 */

public class EditCommand extends Command {
	
	public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task in the list displayed. "
            + "Parameters: [index] NAME [from DATETIME] [to DATETIME] [at LOCATION] [remarks REMARKS] \n"
            + "Example: " + COMMAND_WORD
            + " 1 dinner with mom from 13 oct 2016 7pm to 13 oct 2016 8pm at home remarks buy fruits";

    public static final String MESSAGE_SUCCESS = "Task edited: %1$s";

    private final int targetIndex;
    
    private final Task replacement;

    public EditCommand(int targetIndex, String name, String startDate, String startTime, String endDate, String endTime,
            String location, String remarks) throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.replacement = new Task(
                new Name(name),
                new Interval(startDate, startTime, endDate, endTime),
                new LocationParameter(location),
                new RemarksParameter(remarks)
        );
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
            model.editTask(taskToEdit, replacement);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, replacement));
    }
}
