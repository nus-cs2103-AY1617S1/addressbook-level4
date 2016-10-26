package seedu.jimi.logic.commands;

import java.util.Optional;

import seedu.jimi.commons.core.Messages;
import seedu.jimi.commons.core.UnmodifiableObservableList;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Marks an existing task as complete.
 * @@author A0138915X
 *
 */
public class CompleteCommand extends Command implements TaskBookEditor{
    
    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks an existing task in Jimi as completed.\n"
            + "You can specify the task/event by entering its index number given in the last listing. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " t1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "You have completed this task: %1$s";

    public final String targetIndex;
    
    public CompleteCommand() {
        this(null);
    }
    
    public CompleteCommand(String targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    @Override
    public CommandResult execute() {
        
        Optional<UnmodifiableObservableList<ReadOnlyTask>> optionalList = 
                determineListFromIndexPrefix(targetIndex);
        
        // actual integer index is everything after the first character prefix.
        int actualIdx = Integer.parseInt(targetIndex.substring(1).trim());
        if (!optionalList.isPresent() || optionalList.get().size() < actualIdx) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = optionalList.get();
        
        ReadOnlyTask taskToComplete = lastShownList.get(actualIdx - 1);        

        try {
            model.completeTask(taskToComplete, true);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }
        
        return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete));
    }
    
    // @@author A0140133B
    @Override
    public boolean isValidCommandWord(String commandWord) {
        for (int i = 1; i <= COMMAND_WORD.length(); i++) {
            if (commandWord.toLowerCase().equals(COMMAND_WORD.substring(0, i))) {
                return true;
            }
        }
        return false;
    }
    // @@author
    
}
