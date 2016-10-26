package seedu.malitio.logic.commands;

import seedu.malitio.commons.core.Messages;
import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;

/**
 * Marks a specified task or deadline as a priority in Malitio to the user.
 * @@author A0153006W
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks specified task or deadline as priority in Malitio\n" +
            "Parameters: INDEX\n" + "Example: " + COMMAND_WORD + " f1";
    
    public static final String MESSAGE_MARK_SUCCESS = "Task has been marked as priority";
    
    private final int targetIndex;
    private final char taskType;

    public MarkCommand(char taskType, int targetIndex) {
        this.taskType = taskType;
        this.targetIndex = targetIndex;
    }
            
    @Override
    public CommandResult execute() {
        if (!(taskType == 'f' || taskType == 'd')) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX, "\nNote: Events can not be marked as priority"));
        }
        if (taskType == 'f') {
            ReadOnlyFloatingTask taskToMark = model.getFilteredFloatingTaskList().get(targetIndex - 1);
            model.getFuture().clear();
            return executeMarkFloatingTask();
        }
        else {
            ReadOnlyDeadline deadlineToMark = model.getFilteredDeadlineList().get(targetIndex - 1);
            model.getFuture().clear();
            return executeMarkDeadline();
        }
    }

    private CommandResult executeMarkFloatingTask() {
        UnmodifiableObservableList<ReadOnlyFloatingTask> lastShownList = model.getFilteredFloatingTaskList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyFloatingTask taskToMark = lastShownList.get(targetIndex - 1);
        
        try {
            assert model != null;
            model.markFloatingTask(taskToMark);
        } catch (FloatingTaskNotFoundException e) {
            assert false : "The target floating task cannot be missing";
        } catch (UniqueFloatingTaskList.FloatingTaskMarkedException e) {
            return new CommandResult(MESSAGE_MARK_SUCCESS);
        }
        return new CommandResult(MESSAGE_MARK_SUCCESS);
    }
    
    private CommandResult executeMarkDeadline() {
        UnmodifiableObservableList<ReadOnlyDeadline> lastShownList = model.getFilteredDeadlineList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyDeadline deadlineToMark = lastShownList.get(targetIndex - 1);
        
        try {
            assert model != null;
            model.markDeadline(deadlineToMark);
        } catch (DeadlineNotFoundException e) {
            assert false : "The target deadline cannot be missing";
        } catch (UniqueDeadlineList.DeadlineMarkedException e) {
            return new CommandResult(MESSAGE_MARK_SUCCESS);
        }
        return new CommandResult(MESSAGE_MARK_SUCCESS);        
    }
}