package seedu.tasklist.logic.commands;

import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.tasklist.model.task.Task;
import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0153837X
public class TimeCommand extends Command{
	
	public static final String COMMAND_WORD = "time";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Gives the time remaining before a deadline/ an event.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: \n" + COMMAND_WORD + " 1\n" + COMMAND_WORD + " 2";

    private int targetIndex;
    public TimeCommand() {};
    public TimeCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

	@Override
	public Command prepare(String args) {
		Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        return new TimeCommand(index.get());
	}

	@Override
	public CommandResult execute() {
		String result = null;
		
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
		
		// TargetIndex has to be smaller than the index of the last task shown on the screen
		if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
		
		Task taskTime = new Task(lastShownList.get(targetIndex - 1));

		try {
            result = model.timeTask(taskTime);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
		
		// Display result to user
		return new CommandResult(result);
	}

}
