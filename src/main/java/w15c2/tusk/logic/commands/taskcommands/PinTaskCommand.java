package w15c2.tusk.logic.commands.taskcommands;

import javafx.collections.ObservableList;
import w15c2.tusk.commons.core.EventsCenter;
import w15c2.tusk.commons.core.Messages;
import w15c2.tusk.commons.events.ui.HideHelpRequestEvent;
import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.model.task.Task;

//@@author A0138978E
/**
 * Pins a task identified using it's last displayed index from TaskManager.
 */
public class PinTaskCommand extends TaskCommand {

	public static final String COMMAND_WORD = "pin";
    public static final String ALTERNATE_COMMAND_WORD = null;

    public static final String HELP_MESSAGE_USAGE = "Pin a task: \t" + COMMAND_WORD + " <index>";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pins the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PIN_TASK_SUCCESS = "Pinned task: %1$s";
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
            return new CommandResult(String.format(MESSAGE_PIN_TASK_SUCCESS, taskToPin));
        }
        else{
        	return new CommandResult(MESSAGE_TASK_ALR_PINNED);
        }
    }

}
