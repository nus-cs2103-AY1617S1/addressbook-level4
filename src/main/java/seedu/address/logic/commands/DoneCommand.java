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
 * Archives a task identified using its last displayed index from the task manager.
 */
public class DoneCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the item identified by the index number used in the last item listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    public static final String TOOL_TIP = "done INDEX [ANOTHER_INDEX ...]";

    public static final String MESSAGE_DONE_ITEM_SUCCESS = "Archived Task: %1$s";
    public static final String MESSAGE_DONE_ITEMS_SUCCESS = "Archived Tasks: %1$s";
    
    public static final String MESSAGE_DONE_UNDO_SUCCESS = "Undid archive tasks! Tasks restored to undone list!";
    
    

    
    private List<Task> doneTasks;
    
    private List<Task> readdedRecurringTasks;
    
    private List<Task> doneTasksUndoFail;

    public final List<Integer> targetIndexes;
    
    private boolean viewingDoneList;


    public DoneCommand(List<Integer> targetIndexes) {
        assert targetIndexes != null;
        this.targetIndexes = targetIndexes;
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        if (!checkIfRedoAction()) {
            viewingDoneList = model.isCurrentListDoneList();
        }
        if (viewingDoneList) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(String.format(Messages.MESSAGE_DONE_LIST_RESTRICTION));
        }
        List<String> displayArchivedTasks = new ArrayList<String>();
        Collections.sort(targetIndexes);
        int adjustmentForRemovedTask = 0;
        
        // update history
        doneTasks = new ArrayList<Task>();
        readdedRecurringTasks = new ArrayList<Task>();

        for (int targetIndex: targetIndexes) {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredUndoneTaskList();
    
            if (lastShownList.size() < targetIndex - adjustmentForRemovedTask) {
                continue;      
            }
    
            Task taskToArchive = new Task(lastShownList.get(targetIndex - adjustmentForRemovedTask - 1));
            
            try {
                assert viewingDoneList == false;
                model.deleteTask(taskToArchive);
                doneTasks.add(taskToArchive);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
            
            if (taskToArchive.getRecurrenceRate().isPresent()) {
                Task recurringTaskToReAdd = new Task(taskToArchive);
                recurringTaskToReAdd.updateRecurringTask();
                readdedRecurringTasks.add(recurringTaskToReAdd);
                model.addTask(recurringTaskToReAdd);
            }
            
            model.addDoneTask(taskToArchive);
            
            displayArchivedTasks.add(taskToArchive.toString());
            adjustmentForRemovedTask++;
        }
        
        updateHistory();
        if (displayArchivedTasks.isEmpty()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }
        String toDisplay = displayArchivedTasks.toString().replace("[", "").replace("]", "");
        return (displayArchivedTasks.size() == 1)? 
                new CommandResult(String.format(MESSAGE_DONE_ITEM_SUCCESS, toDisplay)):
                new CommandResult(String.format(MESSAGE_DONE_ITEMS_SUCCESS, toDisplay));
    }

    //@@author A0093960X
    @Override
    public CommandResult undo() {
        doneTasksUndoFail = new ArrayList<Task>();
        
        for (Task doneTask : doneTasks){
            try {
                model.deleteDoneTask(doneTask);
            } catch (TaskNotFoundException e) {
                doneTasksUndoFail.add(doneTask);
            }
        }
        
        for (Task readdedRecurTask : readdedRecurringTasks) { 
            try {
                model.deleteTask(readdedRecurTask);
            } catch (TaskNotFoundException e) {
                doneTasksUndoFail.add(readdedRecurTask);
            }
        }
        
        model.addTasks(doneTasks);
        return new CommandResult(MESSAGE_DONE_UNDO_SUCCESS);
    }
    //@@author 
}
