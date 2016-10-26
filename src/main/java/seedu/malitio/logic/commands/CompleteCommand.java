package seedu.malitio.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.malitio.commons.core.Messages;
import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.DateTime;
import seedu.malitio.model.task.Deadline;
import seedu.malitio.model.task.Event;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.model.task.Name;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineNotFoundException;
import seedu.malitio.model.task.UniqueEventList.DuplicateEventException;
import seedu.malitio.model.task.UniqueEventList.EventNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;

/**
 * Complete a floating task/ deadline identified using it's last displayed index from Malitio.
 * strikeout the completed floating task/ deadline
 * 
 */
public class CompleteCommand extends Command{

    public static final String COMMAND_WORD = "complete";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": complete the task or deadline identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be either 'f'/'d' and a positive integer) "
            + "Example: " + COMMAND_WORD + " f1";
    
    public static final String MESSAGE_COMPLETED_TASK = "The floating task is completed in Malitio";
 
    public static final String MESSAGE_COMPLETED_DEADLINE = "The deadline is completed in Malitio";
    
    public static final String MESSAGE_COMPLETED_TASK_SUCCESS = "Successfully completed floating task.";

    public static final String MESSAGE_COMPLETED_DEADLINE_SUCCESS = "Successfully completed deadline.";
    
    private final char taskType;
    
    private final int targetIndex;
      
    public CompleteCommand(char taskType, int targetIndex) throws IllegalValueException {
        assert taskType == 'd' || taskType == 'f';
        this.taskType = taskType;
        this.targetIndex = targetIndex;
    }   
    
    @Override
    public CommandResult execute() {
        CommandResult result;
        if (taskType=='f') {
            result = executeCompleteFloatingTask();
            model.getFuture().clear();
            return result;
        }
        else {
            result = executeCompleteDeadline();
            model.getFuture().clear();
            return result;
        }
    }
    
    private CommandResult executeCompleteFloatingTask() {
        UnmodifiableObservableList<ReadOnlyFloatingTask> lastShownList = model.getFilteredFloatingTaskList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyFloatingTask taskToComplete = lastShownList.get(targetIndex - 1);
                
        try {
            assert model != null;
            model.completeFloatingTask(taskToComplete);
        } catch (FloatingTaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueFloatingTaskList.FloatingTaskCompletedException e) {
            return new CommandResult(MESSAGE_COMPLETED_TASK);
        }
        return new CommandResult(String.format(MESSAGE_COMPLETED_TASK_SUCCESS, taskToComplete));
    }
    
    private CommandResult executeCompleteDeadline() {
        UnmodifiableObservableList<ReadOnlyDeadline> lastShownList = model.getFilteredDeadlineList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_DEADLINE_DISPLAYED_INDEX);
        }

        ReadOnlyDeadline deadlineToComplete = lastShownList.get(targetIndex - 1);
                
        try {
            assert model != null;
            model.completeDeadline(deadlineToComplete);
        } catch (DeadlineNotFoundException pnfe) {
            assert false : "The target deadline cannot be missing";
        } catch (UniqueDeadlineList.DeadlineCompletedException e) {
            return new CommandResult(MESSAGE_COMPLETED_DEADLINE);
        }
        return new CommandResult(String.format(MESSAGE_COMPLETED_DEADLINE_SUCCESS, deadlineToComplete));
    }
    
}
