package seedu.address.logic.commands;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.TaskNotRecurringException;
import seedu.address.commons.util.ListUtil;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.Task;
import seedu.address.model.item.UniqueTaskList.TaskNotFoundException;

//@@author A0139498J
/**
 * Archives task identified using its last displayed index from the task manager.
 */
public class DoneCommand extends UndoableCommand {
    
    private static final Logger logger = LogsCenter.getLogger(DoneCommand.class);

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    //@@author A0093960X

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Archived Task: %1$s";
    public static final String MESSAGE_DONE_TASKS_SUCCESS = "Archived Tasks: %1$s";
    public static final String MESSAGE_FAILURE = "Failed to archive Task.";

    //@@author
    public static final String TOOL_TIP = "done INDEX [ANOTHER_INDEX ...]";

    public static final String MESSAGE_DONE_UNDO_SUCCESS = "Undid archive tasks! Tasks restored to undone list!";
    public static final String MESSAGE_DONE_UNDO_SOME_FAILURE = "All done tasks have been undone, except the following tasks: %1$s";
    
    private List<Task> readdedRecurringTasks;
    private List<Task> doneTasksUndoFail;
    //@@author A0139498J
    private final List<Integer> targetIndexes;
    private List<Task> targetTasks;
    private boolean isViewingDoneList;

    public DoneCommand(List<Integer> targetIndexes) {
        assert targetIndexes != null;
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        prepareToArchiveTasks();
        if (attemptToExecuteDoneOnDoneList()) {
            return generateCommandResultForDoneListRestriction();
        }
        
        initialiseTargetTasksToArchiveFromTargetIndexes();
        try {
            archiveTargetTasks();
        } catch (TaskNotRecurringException tnre) {
            assert false : "Error in code, tried to update recurrence of non-recurring task";
            return generateCommandResultForFailureToArchiveTask();
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
            return generateCommandResultForFailureToArchiveTask();
        }
        
        updateHistory();
        return generateCommandResultForEndOfExecution();
    }

    /**
     * Prepares for the archiving of tasks by
     * initialising the attributes of this done command class.
     */
    private void prepareToArchiveTasks() {
        logger.fine("In prepareToArchiveTasks(). Setting up.");
        if (!isRedoAction) {
            setCurrentViewingList();
        }
        targetTasks = new ArrayList<Task>();
        readdedRecurringTasks = new ArrayList<Task>();
    }
    
    /**
     * Generates command result for failing to archive task.
     * 
     * @return CommandResult Indicating that a task was unable to be archived.
     */
    private CommandResult generateCommandResultForFailureToArchiveTask() {
        return new CommandResult(String.format(MESSAGE_FAILURE));
    }

    /**
     * Generates command result for an attempt at running the done command on done list.
     * 
     * @return CommandResult Indicating that the user is unable to run done command on done list view.
     */
    private CommandResult generateCommandResultForDoneListRestriction() {
        logger.warning("Invalid command, cannot do done command on done list.");
        indicateAttemptToExecuteIncorrectCommand();
        return new CommandResult(String.format(Messages.MESSAGE_DONE_LIST_RESTRICTION));
    }

    /**
     * Generates the appropriate command result at the end of execution of done command
     * based on the number of tasks inside archived tasks list.
     * 
     * @return CommandResult Indicating the result of the execution of this done command.
     */
    private CommandResult generateCommandResultForEndOfExecution() {
        assert targetTasks != null;
        if (targetTasks.isEmpty()) {
            logger.warning("No tasks archived. None of the given task indexes are valid.");
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        String toDisplay = ListUtil.generateDisplayString(targetTasks);
        return (targetTasks.size() == 1)
                ? new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, toDisplay))
                : new CommandResult(String.format(MESSAGE_DONE_TASKS_SUCCESS, toDisplay));
    }

    /**
     * Returns true if the done command is being executed on the done list.
     */
    private boolean attemptToExecuteDoneOnDoneList() {
        return isViewingDoneList && !isRedoAction;
    }


    /**
     * Adds the tasks referred to by the list of target indexes into a task list.
     * Invalid target indexes in the list will be ignored.
     */
    private void initialiseTargetTasksToArchiveFromTargetIndexes() {
        assert targetIndexes != null;
        assert targetTasks != null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredUndoneTaskList();
        
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
     * Archives the target list of tasks.
     * 
     * @throws TaskNotRecurringException  If there is an attempt to update recurrence of a non-recurring task.
     * @throws TaskNotFoundException      If the target task to be archived is not present in done task list.
     */
    private void archiveTargetTasks() throws TaskNotRecurringException, TaskNotFoundException {
        assert isViewingDoneList == false;
        assert targetTasks != null;
        logger.fine("In archiveTargetTasks(), archiving Tasks");
        for (Task taskToArchive : targetTasks) {                           
            archiveTask(taskToArchive);
        }
    }

    /**
     * Archives the target task by adding it into the done list.
     * A task that is recurring will have its recurrence rates updated in the undone list.
     * A task that is not recurring will be removed from the undone list.
     * 
     * @param taskToArchive The task to archive from undone task list.
     * @throws TaskNotRecurringException  If there is an attempt to update recurrence of a non-recurring task.
     * @throws TaskNotFoundException      If the target task to be archived is not present in done task list.
     */
    private void archiveTask(Task taskToArchive) throws TaskNotFoundException, TaskNotRecurringException {
        assert taskToArchive != null;
        model.deleteUndoneTask(taskToArchive);
        boolean taskToArchiveIsRecurring = (taskToArchive.getRecurrenceRate().isPresent());
        if (taskToArchiveIsRecurring) {
            updateRecurrenceAndReAddTask(taskToArchive);
        }
        model.addDoneTask(taskToArchive);
        logger.fine("Archived Task " + taskToArchive);
    }
    
    /**
     * Adds the recurring task back into the undone task list
     * with their start and end dates updated, if present.
     * 
     * @param taskToArchive A recurring task that is being archived.
     * @throws TaskNotRecurringException  If taskToArchive is not a recurring task.
     */
    private void updateRecurrenceAndReAddTask(Task taskToArchive) throws TaskNotRecurringException {
        assert taskToArchive.getRecurrenceRate().isPresent();
        assert readdedRecurringTasks != null;
        logger.fine("In updateRecurrenceAndReAddTask(). Task is recurring. Updating task details.");
        Task recurringTaskToReAdd = new Task(taskToArchive);
        recurringTaskToReAdd.updateRecurringTask();
        readdedRecurringTasks.add(recurringTaskToReAdd);
        model.addTask(recurringTaskToReAdd);
    }

    /**
     * Sets the boolean attribute isViewingDoneList to reflect if the current
     * list view is done. This attribute is used to ensure that the done operation
     * will not occur while viewing the done list.
     */
    private void setCurrentViewingList() {
        logger.fine("In setCurrentViewingList(), updating boolean isViewingDoneList.");
        isViewingDoneList = model.isCurrentListDoneList();
    }
    

    //@@author A0093960X
    @Override
    public CommandResult undo() {
        doneTasksUndoFail = new ArrayList<Task>();
        
        attemptToDeleteDoneTasksFromDoneList();
        
        attemptToDeleteReaddedRecurTasksFromUndoneList();
        
        readdAllDoneTasksToUndoneList();
        
        if (isSuccessfulInUndoingAllDoneTasks()) {
            return new CommandResult(MESSAGE_DONE_UNDO_SUCCESS);
        }
        else {
            return new CommandResult(String.format(MESSAGE_DONE_UNDO_SOME_FAILURE, doneTasksUndoFail));
        }
    }

    /**
     * Attempt to delete all readded recurring tasks as a result of this done command from the current undone list.
     */
    private void attemptToDeleteReaddedRecurTasksFromUndoneList() {
        for (Task readdedRecurTask : readdedRecurringTasks) { 
            attemptToDeleteReaddedRecurTaskFromUndoneList(readdedRecurTask);
        }
    }

    /**
     * Attempt to delete all done tasks as a result of this done command from the current done list.
     */
    private void attemptToDeleteDoneTasksFromDoneList() {
        for (Task doneTask : targetTasks){
            attemptToDeleteDoneTaskFromDoneList(doneTask);
        }
    }

    /**
     * Readds all the done tasks as a result of this done command back to the current undone list.
     */
    private void readdAllDoneTasksToUndoneList() {
        model.addTasks(targetTasks);

    }

    /**
     * Attempts to delete a readded recurring task from the current undone list.
     * 
     * @param readdedRecurTask The recurring task that was readded to the current undone list as part of this done command
     */
    private void attemptToDeleteReaddedRecurTaskFromUndoneList(Task readdedRecurTask) {
        try {
            model.deleteUndoneTask(readdedRecurTask);
        } catch (TaskNotFoundException e) {
            logger.info("Cannot find task: " + readdedRecurTask + "; adding to list of done task failures to inform user.");
            doneTasksUndoFail.add(readdedRecurTask);
        }
    }

    /**
     * Attempts to delete a done task from the current done list.
     * 
     * @param doneTask The done task that was added to the current done list as part of this done command
     */
    private void attemptToDeleteDoneTaskFromDoneList(Task doneTask) {
        try {
            model.deleteDoneTask(doneTask);
        } catch (TaskNotFoundException e) {
            logger.info("Cannot find task: " + doneTask + "; adding to list of done task failures to inform user.");
            doneTasksUndoFail.add(doneTask);
        }
    }
    
    /**
     * Return whether the undo command was successful in undoing all done tasks.
     * 
     * @return A boolean representing if all done tasks were undone.
     */
    private boolean isSuccessfulInUndoingAllDoneTasks() {
        return doneTasksUndoFail.isEmpty();
    }

}
