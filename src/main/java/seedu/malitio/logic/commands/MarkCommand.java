package seedu.malitio.logic.commands;

import seedu.malitio.commons.core.Messages;
import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineMarkedException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineNotFoundException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineUnmarkedException;
import seedu.malitio.model.task.UniqueEventList.EventMarkedException;
import seedu.malitio.model.task.UniqueEventList.EventNotFoundException;
import seedu.malitio.model.task.UniqueEventList.EventUnmarkedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskMarkedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskUnmarkedException;

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
        if (!(taskType == 'f' || taskType == 'd' || taskType == 'e')) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        if (taskType == 'f') {
            model.getFuture().clear();
            return executeMarkFloatingTask();
        } else if (taskType == 'd') {
            model.getFuture().clear();
            return executeMarkDeadline();
        } else {
            model.getFuture().clear();
            return executeMarkEvent();
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
            model.markFloatingTask(taskToMark, true);
        } catch (FloatingTaskNotFoundException e) {
            assert false : "The target floating task cannot be missing";
        } catch (FloatingTaskMarkedException e) {
            return new CommandResult(MESSAGE_MARK_SUCCESS);
        } catch (FloatingTaskUnmarkedException e) {
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
            model.markDeadline(deadlineToMark, true);
        } catch (DeadlineNotFoundException e) {
            assert false : "The target deadline cannot be missing";
        } catch (DeadlineMarkedException e) {
            return new CommandResult(MESSAGE_MARK_SUCCESS);
        } catch (DeadlineUnmarkedException e) {
        }
        return new CommandResult(MESSAGE_MARK_SUCCESS);        
    }
    
    private CommandResult executeMarkEvent() {
        UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredEventList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyEvent eventToMark = lastShownList.get(targetIndex - 1);
        
        try {
            assert model != null;
            model.markEvent(eventToMark, true);
        } catch (EventNotFoundException e) {
            assert false : "The target deadline cannot be missing";
        } catch (EventMarkedException e) {
            return new CommandResult(MESSAGE_MARK_SUCCESS);
        } catch (EventUnmarkedException e) {
        }
        return new CommandResult(MESSAGE_MARK_SUCCESS);        
    }
}