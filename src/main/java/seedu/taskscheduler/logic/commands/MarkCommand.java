package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.commons.core.UnmodifiableObservableList;
import seedu.taskscheduler.model.tag.UniqueTagList.DuplicateTagException;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.Task;
import seedu.taskscheduler.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0148145E

/**
 * Marks a task in task scheduler as completed.
 */
public class MarkCommand extends Command {
    
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last tasks listing as completed.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_MARK_TASK_FAIL = "This task is already completed.";

    private final int targetIndex;
    private Task taskToMark;
    

    public MarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        taskToMark = (Task)lastShownList.get(targetIndex - 1);

        try {
        	model.markTask(taskToMark);
        	CommandHistory.addExecutedCommand(this);
        } catch (DuplicateTagException e) {
            return new CommandResult(MESSAGE_MARK_TASK_FAIL);
        } catch (TaskNotFoundException pnfe) {
            assert false : Messages.MESSAGE_TASK_CANNOT_BE_MISSING;
        } 
        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }

    @Override
    public CommandResult revert() {
        try {
            model.unMarkTask(taskToMark);
            CommandHistory.addRevertedCommand(this);
        } catch (NullPointerException npe) {
            return new CommandResult(UnmarkCommand.MESSAGE_UNMARK_TASK_FAIL);
        } catch (TaskNotFoundException e) {
            assert false : Messages.MESSAGE_TASK_CANNOT_BE_MISSING;
        }
        return new CommandResult(String.format(MESSAGE_REVERT_COMMAND, COMMAND_WORD, "\n" + taskToMark));       
    }
}
