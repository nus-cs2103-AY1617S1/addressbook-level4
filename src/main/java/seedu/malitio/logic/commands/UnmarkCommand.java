package seedu.malitio.logic.commands;

import seedu.malitio.commons.core.Messages;
import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineMarkedException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineNotFoundException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineUnmarkedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskMarkedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskUnmarkedException;

/**
 * Unmarks a specified task or deadline as a priority in Malitio to the user.
 * @@author A0153006W
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks specified task or deadline as priority in Malitio\n" +
            "Parameters: INDEX\n" + "Example: " + COMMAND_WORD + " f1";
    
    public static final String MESSAGE_MARK_SUCCESS = "Task has been unmarked as priority";
    
    private final int targetIndex;
    private final char taskType;

    public UnmarkCommand(char taskType, int targetIndex) {
        this.taskType = taskType;
        this.targetIndex = targetIndex;
    }
            
    @Override
    public CommandResult execute() {
        if (!(taskType == 'f' || taskType == 'd')) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        if (taskType == 'f') {
            model.getFuture().clear();
            return executeUnmarkFloatingTask();
        }
        else {
            model.getFuture().clear();
            return executeUnmarkDeadline();
        }
    }

    private CommandResult executeUnmarkFloatingTask() {
        UnmodifiableObservableList<ReadOnlyFloatingTask> lastShownList = model.getFilteredFloatingTaskList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyFloatingTask taskToMark = lastShownList.get(targetIndex - 1);
        
        try {
            assert model != null;
            model.markFloatingTask(taskToMark, false);
        } catch (FloatingTaskNotFoundException e) {
            assert false : "The target floating task cannot be missing";
        } catch (FloatingTaskUnmarkedException e) {
            return new CommandResult(MESSAGE_MARK_SUCCESS);
        } catch (FloatingTaskMarkedException e) {
        }
        return new CommandResult(MESSAGE_MARK_SUCCESS);
    }
    
    private CommandResult executeUnmarkDeadline() {
        UnmodifiableObservableList<ReadOnlyDeadline> lastShownList = model.getFilteredDeadlineList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyDeadline deadlineToMark = lastShownList.get(targetIndex - 1);
        
        try {
            assert model != null;
            model.markDeadline(deadlineToMark, false);
        } catch (DeadlineNotFoundException e) {
            assert false : "The target deadline cannot be missing";
        } catch (DeadlineUnmarkedException e) {
            return new CommandResult(MESSAGE_MARK_SUCCESS);
        } catch (DeadlineMarkedException e) {
        }
        return new CommandResult(MESSAGE_MARK_SUCCESS);        
    }
}