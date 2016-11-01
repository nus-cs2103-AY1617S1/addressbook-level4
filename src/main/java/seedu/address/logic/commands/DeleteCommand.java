package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Collections;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.Task;
import seedu.address.model.item.UniqueTaskList.TaskNotFoundException;

//@@author A0139498J
/**
 * Deletes a task identified using its last displayed index from the task manager.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    public static final String MESSAGE_DELETE_ITEM_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_DELETE_ITEMS_SUCCESS = "Deleted Tasks: %1$s";
    
    //@@author
    public static final String TOOL_TIP = "delete INDEX [ANOTHER_INDEX ...]";

    public static final String MESSAGE_UNDO_SUCCESS = "Undid delete on tasks! %1$s Tasks restored!";
    
    //@@author A0139498J
    public final List<Integer> targetIndexes;
    private List<Task> deletedTasks;
    private int adjustmentForRemovedTask;
    private boolean isViewingDoneList;

    public DeleteCommand(List<Integer> targetIndexes) {
        assert targetIndexes != null;
        this.targetIndexes = targetIndexes;
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        
        prepareToDeleteTasks();
        deleteTasksFromGivenTargetIndexes();
        updateHistory();
        
        if (deletedTasks.isEmpty()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        String toDisplay = StringUtil.removeArrayBrackets(deletedTasks.toString());
        return (deletedTasks.size() == 1)? 
                new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, toDisplay)):
                new CommandResult(String.format(MESSAGE_DELETE_ITEMS_SUCCESS, toDisplay));
        
    }


    /**
     * Deletes the tasks denoted by the list of target indexes.
     * Invalid target indexes in the list will be ignored.
     */
    private void deleteTasksFromGivenTargetIndexes() {
        for (int targetIndex: targetIndexes) {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList;
            lastShownList = (isViewingDoneList)? 
                    model.getFilteredDoneTaskList():
                    model.getFilteredUndoneTaskList();
                    
            boolean isTaskTargetIndexOutOfBounds = (lastShownList.size() < targetIndex - adjustmentForRemovedTask);
 
            if (isTaskTargetIndexOutOfBounds) {
                continue;
            }
    
            int adjustedTaskIndex = targetIndex - adjustmentForRemovedTask - 1;
            ReadOnlyTask taskToDelete = lastShownList.get(adjustedTaskIndex);
    
            try {
                if (isViewingDoneList) {
                    model.deleteDoneTask(taskToDelete);
                } else {
                    model.deleteTask(taskToDelete);
                }
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
            deletedTasks.add((Task) taskToDelete);
            adjustmentForRemovedTask++;
        }
    }


    /**
     * Prepares for the deletion of tasks
     * Initialises the attributes of this delete command class
     * Sorts the indexes to ensure the proper offset is used
     */
    private void prepareToDeleteTasks() {
        if (!isRedoAction()) {
            setCurrentViewingList();
        }
        adjustmentForRemovedTask = 0;
        deletedTasks = new ArrayList<Task>();
        Collections.sort(targetIndexes);
    }


    /**
     * Sets the boolean attribute isViewingDoneList to reflect 
     * if the current list view is done.
     */
    private void setCurrentViewingList() {
        isViewingDoneList = model.isCurrentListDoneList();
    }
    
    //@@author A0093960X
    @Override
    public CommandResult undo() {
        assert model != null && deletedTasks != null;    
        
        // attempt to undo the delete by adding back the list of tasks that was deleted
        // add back to the list the user was viewing when clear was executed
        if (isViewingDoneList) {
            model.addDoneTasks(deletedTasks);
        }
        else {
            model.addTasks(deletedTasks);
        }
        
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, deletedTasks));
    }
    //@@author 

}
