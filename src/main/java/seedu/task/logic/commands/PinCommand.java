package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.model.task.Task;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

public class PinCommand extends Command {
	public static final String COMMAND_WORD = "pin";
	
	public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pin the task identified by the index number used in the last task listing as important.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";
	
	public static final String MESSAGE_PIN_TASK_SUCCESS = "Pinned Task: %1$s";
	
	public final int targetIndex;
	
	public PinCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
	
	@Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask orginialTask = lastShownList.get(targetIndex - 1);
        Task taskToPin=new Task(orginialTask.getName(),orginialTask.getTags(),orginialTask.getImportance());
        
        try {
            taskToPin.setIsImportant(true);
            model.pinTask(orginialTask,taskToPin);
        }catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_PIN_TASK_SUCCESS, orginialTask));
    }

}

