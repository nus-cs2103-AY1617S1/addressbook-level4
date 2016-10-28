package seedu.malitio.logic.commands;

import seedu.malitio.commons.core.Messages;
import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.commons.exceptions.IllegalValueException;

import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;

//@@author A0122460W
/**
 * Uncomplete a floating task/ deadline identified using it's last displayed index from Malitio.
 * unstrikeout the completed floating task/ deadline
 * 
 */
public class UncompleteCommand extends Command{

    public static final String COMMAND_WORD = "uncomplete";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": uncomplete the task or deadline identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be either 'f'/'d' and a positive integer) "
            + "Example: " + COMMAND_WORD + " f1";
    
    public static final String MESSAGE_UNCOMPLETED_TASK = "The floating task is uncompleted in Malitio";
 
    public static final String MESSAGE_UNCOMPLETED_DEADLINE = "The deadline is uncompleted in Malitio";
    
    public static final String MESSAGE_UNCOMPLETED_TASK_SUCCESS = "Successfully uncomplete floating task.";

    public static final String MESSAGE_UNCOMPLETED_DEADLINE_SUCCESS = "Successfully uncomplete deadline.";
    
    private final char taskType;
    
    private final int targetIndex;
      
    public UncompleteCommand(char taskType, int targetIndex) throws IllegalValueException {
        assert taskType == 'd' || taskType == 'f';
        this.taskType = taskType;
        this.targetIndex = targetIndex;
    }   
    
    @Override
    public CommandResult execute() {
        CommandResult result;
        if (taskType=='f') {
            result = executeUncompleteFloatingTask();
            model.getFuture().clear();
            return result;
        }
        else {
            result = executeUncompleteDeadline();
            model.getFuture().clear();
            return result;
        }
    }
    
    private CommandResult executeUncompleteFloatingTask() {
        UnmodifiableObservableList<ReadOnlyFloatingTask> lastShownList = model.getFilteredFloatingTaskList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyFloatingTask taskToUncomplete = lastShownList.get(targetIndex - 1);
                
        try {
            assert model != null;
            model.uncompleteFloatingTask(taskToUncomplete);
        } catch (FloatingTaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueFloatingTaskList.FloatingTaskUncompletedException e) {
            return new CommandResult(MESSAGE_UNCOMPLETED_TASK);
        }
        return new CommandResult(String.format(MESSAGE_UNCOMPLETED_TASK_SUCCESS, taskToUncomplete));
    }
    
    private CommandResult executeUncompleteDeadline() {
        UnmodifiableObservableList<ReadOnlyDeadline> lastShownList = model.getFilteredDeadlineList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_DEADLINE_DISPLAYED_INDEX);
        }

        ReadOnlyDeadline deadlineToUncomplete = lastShownList.get(targetIndex - 1);
                
        try {
            assert model != null;
            model.uncompleteDeadline(deadlineToUncomplete);
        } catch (DeadlineNotFoundException pnfe) {
            assert false : "The target deadline cannot be missing";
        } catch (UniqueDeadlineList.DeadlineUncompletedException e) {
            return new CommandResult(MESSAGE_UNCOMPLETED_DEADLINE);
        }
        return new CommandResult(String.format(MESSAGE_UNCOMPLETED_DEADLINE_SUCCESS, deadlineToUncomplete));
    }
    
}
