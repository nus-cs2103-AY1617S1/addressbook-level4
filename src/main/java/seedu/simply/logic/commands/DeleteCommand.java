
package seedu.simply.logic.commands;

import java.util.ArrayList;
import java.util.Collections;

import seedu.simply.commons.core.Messages;
import seedu.simply.commons.core.UnmodifiableObservableList;
import seedu.simply.commons.exceptions.IllegalValueException;
import seedu.simply.model.task.ReadOnlyTask;
import seedu.simply.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " T1"
            + "      "
            + "Example: " + COMMAND_WORD + " T1, E2, D3"
            + "      "
            + "Example: " + COMMAND_WORD + " T1-T10";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    private static final String MESSAGE_CATEGORY_CONSTRAINTS = "The Task Category provided is invalid!\n"
            + "Valid Categories: [E, D, T]\n"
            + "Example: " + COMMAND_WORD + " T1, E2, D3\n"
            + "Example: " + COMMAND_WORD + " T1-10";

    public final ArrayList<Integer> targetIndexesE = new ArrayList<Integer>();
    public final ArrayList<Integer> targetIndexesD = new ArrayList<Integer>();
    public final ArrayList<Integer> targetIndexesT = new ArrayList<Integer>();
    public final ArrayList<String> pass; 

    //@@author A0139430L
    public DeleteCommand(ArrayList<String> targetIndexes) throws IllegalValueException {
        pass = targetIndexes;
        extractToEachList(targetIndexes);
        sortAndReverse(targetIndexesE);
        sortAndReverse(targetIndexesD);
        sortAndReverse(targetIndexesT);
    }

    private void extractToEachList(ArrayList<String> targetIndexes) throws IllegalValueException {
        for(int i= 0; i < targetIndexes.size(); i++){
            String temp = targetIndexes.get(i);
            String stringIdx = temp.substring(1);
            Integer intIdx = Integer.valueOf(stringIdx);
            if(temp.charAt(0)=='E'){
                targetIndexesE.add(intIdx);
            }
            else if(temp.charAt(0)=='D'){
                targetIndexesD.add(intIdx);
            }
            else if(temp.charAt(0)=='T'){
                targetIndexesT.add(intIdx);
            }
            else{
                throw new IllegalValueException(MESSAGE_CATEGORY_CONSTRAINTS);
            }
        }
    }

    //@@author A0139430L
    @Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownEventList = model.getFilteredEventList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownDeadlineList = model.getFilteredDeadlineList();
        UnmodifiableObservableList<ReadOnlyTask> lastShownTodoList = model.getFilteredTodoList();
        ArrayList<ReadOnlyTask> tasksToDeleteList = new ArrayList<ReadOnlyTask>();
        
        if(targetIndexesE.size()>0){
            for(int i=0; i<targetIndexesE.size();i++){    
                Integer idx = targetIndexesE.get(i); 
                if (lastShownEventList.size() < idx) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }              
                ReadOnlyTask taskToDelete = lastShownEventList.get(idx-1);  
                if (model.checkTask(taskToDelete)) {
                    tasksToDeleteList.add(taskToDelete);
                } else {
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
            }
        }
        
        if(targetIndexesD.size()>0){
            for(int i=0; i<targetIndexesD.size();i++){    
                Integer idx = targetIndexesD.get(i); 
                if (lastShownDeadlineList.size() < idx) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }              
                ReadOnlyTask taskToDelete = lastShownDeadlineList.get(idx-1);  
                if (model.checkTask(taskToDelete)) {
                    tasksToDeleteList.add(taskToDelete);
                } else {
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
            }
        }
        
        if(targetIndexesT.size()>0){
            for(int i=0; i<targetIndexesT.size();i++){    
                Integer idx = targetIndexesT.get(i); 
                if (lastShownTodoList.size() < idx) {
                    indicateAttemptToExecuteIncorrectCommand();
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }              
                ReadOnlyTask taskToDelete = lastShownTodoList.get(idx-1);  
                if (model.checkTask(taskToDelete)) {
                    tasksToDeleteList.add(taskToDelete);
                } else {
                    return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
                }
            }
        }
        
        model.addToUndoStack();
        for (int i=0; i<tasksToDeleteList.size(); i++) {   
            model.deleteTask(tasksToDeleteList.get(i));
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, pass));
    }

    private void sortAndReverse(ArrayList<Integer> list) {
        Collections.sort(list);
        Collections.reverse(list);
    }

}
