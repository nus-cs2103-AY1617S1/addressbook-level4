package seedu.address.logic.commands.taskcommands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.HideHelpRequestEvent;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.task.Task;

//@@author A0138978E
/**
 * Pins a task identified using it's last displayed index from TaskManager.
 */
public class PinTaskCommand extends TaskCommand {

	public static final String[] COMMAND_WORD = {"pin"};

    public static final String HELP_MESSAGE_USAGE = "Pin a task: \t" + COMMAND_WORD[0] + " <index>";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD[0]
            + ": Pins the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD[0] + " 1";

    public static final String MESSAGE_pin_TASK_SUCCESS = "Pinned task: %1$s";
    public static final String MESSAGE_TASK_ALR_PINNED = "Task has already been pinned";

    public final int targetIndex;

    public PinTaskCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

	    ObservableList<Task> lastShownList = model.getCurrentFilteredTasks();

        if (lastShownList.size() < targetIndex || targetIndex <= 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToPin = lastShownList.get(targetIndex - 1);

        if(!taskToPin.isPinned()){
            EventsCenter.getInstance().post(new HideHelpRequestEvent());
        	model.pinTask(taskToPin);
            return new CommandResult(String.format(MESSAGE_pin_TASK_SUCCESS, taskToPin));
        }
        else{
        	return new CommandResult(MESSAGE_TASK_ALR_PINNED);
        }
    }

}
