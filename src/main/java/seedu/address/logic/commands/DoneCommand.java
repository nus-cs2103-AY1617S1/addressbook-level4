package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Mark a completed task as done and hides it from the main display window.
 */

//@@author A0135722L Zhiyuan
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
    
    public ArrayList<String> targetIndexes = new ArrayList<String>();
    
    public DoneCommand(ArrayList<String> targetIndexes) throws IllegalValueException {
        this.targetIndexes = targetIndexes;
    }
    
    
    @Override
    public CommandResult execute() {
        
        ArrayList<String> pass = new ArrayList<String>(targetIndexes);
//        Collections.sort(targetIndexes);
//        Collections.reverse(targetIndexes);
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownDeadlineList = model.getFilteredDeadlineList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownTodoList = model.getFilteredTodoList();
        
        
        ArrayList<Integer> intIndex = new ArrayList<Integer>();
        for(int i = 0; i < targetIndexes.size(); i++) {
            Integer index = Integer.valueOf(targetIndexes.get(i).substring(1));
            intIndex.add(index);
        }
        
        Collections.sort(intIndex);
        Collections.reverse(intIndex);
        
        for(int i = 0; i < targetIndexes.size(); i++) {
            if(Character.toUpperCase(targetIndexes.get(i).charAt(0)) == 'E') {
                if(lastShownEventList.size() < intIndex.get(i)) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                
                ReadOnlyTask taskDone = lastShownEventList.get(intIndex.get(i) - 1);
                
                try {
                    model.addToUndoStack();
                    model.markDone(taskDone);
                    model.getCommandHistory().add("done");
                } catch (TaskNotFoundException e) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
            }

            else if (Character.toUpperCase(targetIndexes.get(i).charAt(0)) == 'D') {
                if (lastShownDeadlineList.size() < intIndex.get(i)) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                
                ReadOnlyTask taskDone = lastShownDeadlineList.get(intIndex.get(i) - 1);
                
                try {
                    model.addToUndoStack();
                    model.markDone(taskDone);
                    model.getCommandHistory().add("done");
                } catch (TaskNotFoundException e) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
            }
        
            else if(Character.toUpperCase(targetIndexes.get(i).charAt(0)) == 'T') {
                if (lastShownTodoList.size() < intIndex.get(i)) {
                   indicateAttemptToExecuteIncorrectCommand();
                   return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
                
                ReadOnlyTask taskDone = lastShownTodoList.get(intIndex.get(i) - 1);

                try {
                    model.addToUndoStack();
                    model.markDone(taskDone);
                    model.getCommandHistory().add("done");
                } catch (TaskNotFoundException e) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
            }
        }
        
        return new CommandResult(String.format(MESSAGE_MARK_DONE_SUCCESS, pass));
    }

}
