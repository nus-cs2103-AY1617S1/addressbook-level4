package seedu.unburden.logic.commands;
import seedu.unburden.commons.core.EventsCenter;
import seedu.unburden.commons.core.Messages;
import seedu.unburden.commons.core.UnmodifiableObservableList;
import seedu.unburden.commons.events.ui.JumpToListRequestEvent;
import seedu.unburden.model.task.ReadOnlyTask;

//@@author A0147986H-unused
/**
 * Selects a task identified using it's last displayed index from the address book.
 * It will also show the details of the task selected, especially
 * task descriptions that may not be able to show on the taskList panel
 * This is unused because I did not discuss with my teammates in advanced so they decided 
 * not to include this method
 */
public class SelectCommand extends Command {

    public final int taskIndex;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer that does not exceed the maximum number of tasks)\n"
            + "Format: select INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    public SelectCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < taskIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(taskIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, lastShownList.get(taskIndex-1)));
    }
}
