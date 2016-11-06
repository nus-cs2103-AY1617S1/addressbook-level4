package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.ListUtil;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.Task;
import seedu.address.model.item.UniqueTaskList.TaskNotFoundException;

//@@author A0139498J
/**
 * Deletes task identified using its last displayed index from the task manager.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_DELETE_TASKS_SUCCESS = "Deleted Tasks: %1$s";
    public static final String MESSAGE_FAILURE = "Failed to delete Task.";

    //@@author A0093960X
    public static final String TOOL_TIP = "delete INDEX [ANOTHER_INDEX ...]";

    public static final String MESSAGE_UNDO_SUCCESS = "Undid delete on tasks! %1$s Tasks restored!";
    
    //@@author A0139498J
    public final List<Integer> targetIndexes;
    private List<Task> targetTasks;
    private boolean isViewingDoneList;

    public DeleteCommand(List<Integer> targetIndexes) {
        assert targetIndexes != null;
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        prepareToDeleteTasks();
        try {
            deleteTargetTasks();
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
            return generateCommandResultForFailureToDeleteTask();
        }
        updateHistory();
        return generateCommandResultForEndOfExecution();
    }

    /**
     * Deletes the target list of tasks.
     * 
     * @throws TaskNotFoundException  If unable to find Task to delete.
     */
    private void deleteTargetTasks() throws TaskNotFoundException { 
        assert targetTasks != null;
        for (Task taskToDelete : targetTasks) {         
            deleteTask(taskToDelete);
        }
    }

    /**
     * Deletes the task from the current list view.
     * 
     * @param taskToDelete The target task to be deleted.
     * @throws TaskNotFoundException  If unable to find Task to delete in current list view.
     */
    private void deleteTask(Task taskToDelete) throws TaskNotFoundException {
        assert taskToDelete != null;
        if (isViewingDoneList) {
            model.deleteDoneTask(taskToDelete);
        } else {
            model.deleteUndoneTask(taskToDelete);
        }     
    }

    /**
     * Adds the tasks referred to by the list of target indexes into a task list.
     * Invalid target indexes in the list will be ignored.
     */
    private void initialiseTargetTasksToDeleteFromTargetIndexes() {
        assert targetIndexes != null;
        assert targetTasks != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = (isViewingDoneList)
                ? model.getFilteredDoneTaskList()
                : model.getFilteredUndoneTaskList();
                
        for (int targetIndex : targetIndexes) {            
            boolean isTaskTargetIndexOutOfBounds = (lastShownList.size() < targetIndex);
            if (isTaskTargetIndexOutOfBounds) {
                continue;
            }

            int adjustedTaskIndex = targetIndex - 1;
            Task task = new Task(lastShownList.get(adjustedTaskIndex));
            targetTasks.add(task);
        }
    }
    
    /**
     * Prepares for the deletion of tasks.
     * Initialises the attributes of this delete command class.
     */
    private void prepareToDeleteTasks() {
        if (!isRedoAction) {
            setCurrentViewingList();
        }
        targetTasks = new ArrayList<Task>();
        initialiseTargetTasksToDeleteFromTargetIndexes();
    }

    /**
     * Sets the boolean attribute isViewingDoneList to reflect 
     * if the current list view is done.
     */
    private void setCurrentViewingList() {
        isViewingDoneList = model.isCurrentListDoneList();
    }
    
    /**
     * Generates command result for failing to delete task.
     * 
     * @return CommandResult Indicating that a task was unable to be deleted.
     */
    private CommandResult generateCommandResultForFailureToDeleteTask() {
        return new CommandResult(String.format(MESSAGE_FAILURE));
    }
    
    /**
     * Generates the appropriate command result at the end of execution of delete command
     * based on the number of tasks inside deleted tasks list.
     * 
     * @return CommandResult Indicating the result of the execution of this delete command.
     */
    private CommandResult generateCommandResultForEndOfExecution() {
        assert targetTasks != null;
        if (targetTasks.isEmpty()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        String toDisplay = ListUtil.generateDisplayString(targetTasks);
        return (targetTasks.size() == 1)
                ? new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, toDisplay))
                : new CommandResult(String.format(MESSAGE_DELETE_TASKS_SUCCESS, toDisplay));
    }
    
    //@@author A0093960X
    @Override
    public CommandResult undo() {
        assert model != null && targetTasks != null;    
        
        if (isViewingDoneList) {
            undoDeletedDoneTasks();
        }
        else {
            undoDeletedUndoneTasks();

        }
        
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, targetTasks));
    }
    //@@author 


    private void undoDeletedUndoneTasks() {
        model.addTasks(targetTasks);
    }


    private void undoDeletedDoneTasks() {
        model.addDoneTasks(targetTasks);
    }

}