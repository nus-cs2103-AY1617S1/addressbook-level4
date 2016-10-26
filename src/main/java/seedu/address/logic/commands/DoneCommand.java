package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Mark a completed task as done and hides it from the main display window.
 */

public class DoneCommand extends Command {
    
    public static final String COMMAND_WORD = "done";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark the task identified by the index number used in the last task listing as done. \n"
            + "Parameters: INDEX (must be positive integer) \n"
            + "Example: " + COMMAND_WORD + " T1"
            + "      "
            + "Example: " + COMMAND_WORD + " T1, E2, D3"
            + "      "
            + "Example: " + COMMAND_WORD + " T1-T10";
    
    public static final String MESSAGE_MARK_DONE_SUCCESS = "Marked task as done: %1$s";
    
    public final ArrayList<String> targetIndexes;
    
    public DoneCommand(ArrayList<String> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }
    
    
    @Override
    public CommandResult execute() {
        
        ArrayList<String> pass = new ArrayList<String>(targetIndexes);
        Collections.sort(targetIndexes);
        Collections.reverse(targetIndexes);
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownDeadlineList = model.getFilteredDeadlineList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownTodoList = model.getFilteredTodoList();
        
        for(int i = 0; i < targetIndexes.size(); i++) {
            if(Character.toUpperCase(targetIndexes.get(i).charAt(0)) == 'E') {
                if(lastShownEventList.size() < Character.getNumericValue(targetIndexes.get(i).charAt(1))) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                
                ReadOnlyTask taskDone = lastShownEventList.get(Character.getNumericValue(targetIndexes.get(i).charAt(1)) - 1);
                
                try {
                    model.addToUndoStack();
                    model.markDone(taskDone);
                } catch (TaskNotFoundException e) {
                    assert false : "The target task cannot be missing";
                }
            }
            else if (Character.toUpperCase(targetIndexes.get(i).charAt(0)) == 'D') {
                if (lastShownDeadlineList.size() < Character.getNumericValue(targetIndexes.get(i).charAt(1))) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                
                ReadOnlyTask taskDone = lastShownDeadlineList.get(Character.getNumericValue(targetIndexes.get(i).charAt(1)) - 1);
                
                try {
                    model.addToUndoStack();
                    model.markDone(taskDone);
                } catch (TaskNotFoundException e) {
                    assert false : "The target Deadline cannot be missing";
                }
            }
            else if(Character.toUpperCase(targetIndexes.get(i).charAt(0)) == 'T') {
                if (lastShownTodoList.size() < Character.getNumericValue(targetIndexes.get(i).charAt(1))) {
                   indicateAttemptToExecuteIncorrectCommand();
                   return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                
                ReadOnlyTask taskDone = lastShownTodoList.get(Character.getNumericValue(targetIndexes.get(i).charAt(1)) - 1);

                try {
                    model.addToUndoStack();
                    model.markDone(taskDone);
                } catch (TaskNotFoundException e) {
                    assert false : "The target Deadline cannot be missing";
                }
            }
        }
        
        return new CommandResult(String.format(MESSAGE_MARK_DONE_SUCCESS, pass));
    }

}
