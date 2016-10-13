package harmony.mastermind.logic.commands;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.UniqueTaskList.TaskNotFoundException;
import javafx.collections.ObservableList;

//@@author A0124797R
public class MarkCommand extends Command{

    public static final String COMMAND_WORD = "mark";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": mark a task as done "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    public static final String COMMAND_SUMMARY = "Marking a task as done:"
            + "\n" + COMMAND_WORD + " INDEX";

    public static final String MESSAGE_SUCCESS = "%1$s has been archived";
    public static final String MESSAGE_MARKED_TASK = "%1$s is already marked";

    public final int targetIndex;

    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    @Override
    public CommandResult execute() {
        ObservableList<Task> lastShownList = model.getListToMark();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToMark = lastShownList.get(targetIndex - 1);
        
        if (taskToMark.isMarked()) {
            return new CommandResult(String.format(MESSAGE_MARKED_TASK, taskToMark));
        }

        try {
            model.markTask(taskToMark);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToMark));
    }
    
    

}
