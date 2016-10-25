package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Collections;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.Task;
import seedu.address.model.item.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using its last displayed index from the task manager.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number used in the last item listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    public static final String TOOL_TIP = "delete INDEX [ANOTHER_INDEX ...]";

    public static final String MESSAGE_DELETE_ITEM_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_DELETE_ITEMS_SUCCESS = "Deleted Tasks: %1$s";
    
    public static final String MESSAGE_UNDO_SUCCESS = "Undid delete on tasks! %1$s Tasks restored!";
    
    private List<Task> deletedTasks;
    private List<String> displayDeletedTasks;
    public final List<Integer> targetIndexes;
    private boolean viewingDoneList;

    public DeleteCommand(List<Integer> targetIndexes) {
        assert targetIndexes != null;
        this.targetIndexes = targetIndexes;
    }


    @Override
    public CommandResult execute() {
        assert model != null && targetIndexes != null;
        
        displayDeletedTasks = new ArrayList<String>();
        if (!checkIfRedoAction()) {
            viewingDoneList = model.isCurrentListDoneList();
        }
        Collections.sort(targetIndexes);
        int adjustmentForRemovedTask = 0;
        deletedTasks = new ArrayList<Task>();
        
        if (viewingDoneList) {
            return deleteFromDoneList(adjustmentForRemovedTask);
        }
        else {
            return deleteFromUndoneList(adjustmentForRemovedTask);
        }
    }
    
    private CommandResult deleteFromDoneList(int adjustmentForRemovedTask) {
        for (int targetIndex: targetIndexes) {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList;
            if (viewingDoneList) {
                lastShownList = model.getFilteredDoneTaskList();
            } else {
                lastShownList = model.getFilteredUndoneTaskList();
            }
    
            if (lastShownList.size() < targetIndex - adjustmentForRemovedTask) {
                continue;
            }
    
            ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - adjustmentForRemovedTask - 1);
    
            try {
                model.deleteDoneTask(taskToDelete);
                deletedTasks.add((Task) taskToDelete);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
            displayDeletedTasks.add(taskToDelete.toString());
            adjustmentForRemovedTask++;
        }
        
       
        updateHistory();
        if (displayDeletedTasks.isEmpty()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }
        String toDisplay = displayDeletedTasks.toString().replace("[", "").replace("]", "");
        return (displayDeletedTasks.size() == 1)? 
                new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, toDisplay)):
                new CommandResult(String.format(MESSAGE_DELETE_ITEMS_SUCCESS, toDisplay));
    }


    private CommandResult deleteFromUndoneList(int adjustmentForRemovedTask) {
        for (int targetIndex: targetIndexes) {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList;
            if (viewingDoneList) {
                lastShownList = model.getFilteredDoneTaskList();
            } else {
                lastShownList = model.getFilteredUndoneTaskList();
            }
    
            if (lastShownList.size() < targetIndex - adjustmentForRemovedTask) {
                continue;
            }
    
            ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - adjustmentForRemovedTask - 1);
    
            try {
                model.deleteTask(taskToDelete);
                deletedTasks.add((Task) taskToDelete);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
            displayDeletedTasks.add(taskToDelete.toString());
            adjustmentForRemovedTask++;
        }
        
       
        updateHistory();
        if (displayDeletedTasks.isEmpty()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }
        String toDisplay = displayDeletedTasks.toString().replace("[", "").replace("]", "");
        return (displayDeletedTasks.size() == 1)? 
                new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, toDisplay)):
                new CommandResult(String.format(MESSAGE_DELETE_ITEMS_SUCCESS, toDisplay));
    }

    //@@author A0093960X
    @Override
    public CommandResult undo() {
        assert model != null && deletedTasks != null;    
        
        // attempt to undo the delete by adding back the list of tasks that was deleted
        // add back to the list the user was viewing when clear was executed
        if (viewingDoneList) {
            model.addDoneTasks(deletedTasks);
        }
        else {
            model.addTasks(deletedTasks);
        }
        
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, displayDeletedTasks));
    }
    //@@author 

}
