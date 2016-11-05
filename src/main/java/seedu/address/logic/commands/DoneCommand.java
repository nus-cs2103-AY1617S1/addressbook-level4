package seedu.address.logic.commands;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.TaskNotRecurringException;
import seedu.address.commons.util.StringUtil;
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
    
    public static final String MESSAGE_DONE_TASK_SUCCESS = "Archived Task: %1$s";
    public static final String MESSAGE_DONE_TASKS_SUCCESS = "Archived Tasks: %1$s";
    public static final String MESSAGE_FAILURE = "Failed to archive Task.";

    //@author
    public static final String TOOL_TIP = "done INDEX [ANOTHER_INDEX ...]";

    public static final String MESSAGE_DONE_UNDO_SUCCESS = "Undid archive tasks! Tasks restored to undone list!";
    
    private List<Task> readdedRecurringTasks;
    private List<Task> doneTasksUndoFail;
    //@@author A0139498J
    private final List<Integer> targetIndexes;
    private List<Task> archivedTasks;
    private boolean isViewingDoneList;

    public DoneCommand(List<Integer> targetIndexes) {
        assert targetIndexes != null;
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        prepareToArchiveTasks();
        if (isViewingDoneList) {
            return generateCommandResultForDoneListRestriction();
        }
        
        List<Task> tasksToArchive = getTasksFromTargetIndexes(targetIndexes);
        try {
            archiveTasks(tasksToArchive);
        } catch (TaskNotRecurringException tnre) {
            assert false : "Tried to update recurrence of non-recurring task";
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
        archivedTasks = new ArrayList<Task>();
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
        if (archivedTasks.isEmpty()) {
            logger.warning("No tasks archived. None of the given task indexes are valid.");
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        String toDisplay = StringUtil.removeArrayBrackets(archivedTasks.toString());
        return (archivedTasks.size() == 1)
                ? new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, toDisplay))
                : new CommandResult(String.format(MESSAGE_DONE_TASKS_SUCCESS, toDisplay));
    }

    /**
     * Adds the tasks denoted by the list of target indexes into a task list.
     * Invalid target indexes in the list will be ignored.
     * 
     * @param targetIndexes List of Integers denoting indexes of tasks to be archived.
     * @return              TaskList containing tasks referred to by target indexes. 
     */
    private List<Task> getTasksFromTargetIndexes(List<Integer> targetIndexes) {
        List<Task> tasks = new ArrayList<Task>();
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredUndoneTaskList();
        for (int targetIndex : targetIndexes) {            
            boolean isTaskTargetIndexOutOfBounds = (lastShownList.size() < targetIndex);
            if (isTaskTargetIndexOutOfBounds) {
                continue;
            }
            int adjustedTaskIndex = targetIndex - 1;
            Task task = new Task(lastShownList.get(adjustedTaskIndex));
            tasks.add(task);
        }
        return tasks;
    }
    
    /**
     * Takes in a list of tasks and archives them by adding them into the done list.
     * Tasks that are recurring will have their recurrence rates updated in the undone list.
     * Tasks that are not recurring will be removed from the undone list.
     * 
     * @param tasksToArchive The list of tasks to archive.
     * @throws TaskNotRecurringException  If there is an attempt to update recurrence of a non-recurring task.
     * @throws TaskNotFoundException      If the target task to be archived is not present in done task list.
     */
    private void archiveTasks(List<Task> tasksToArchive) 
            throws TaskNotRecurringException, TaskNotFoundException {
        assert isViewingDoneList == false;
        assert tasksToArchive != null;
        logger.fine("In archiveTasks(), archiving Tasks");
        for (Task taskToArchive : tasksToArchive) {                           
            model.deleteTask(taskToArchive);
            archivedTasks.add(taskToArchive);
            boolean taskToArchiveIsRecurring = (taskToArchive.getRecurrenceRate().isPresent());
            if (taskToArchiveIsRecurring) {
                updateRecurrenceAndReAddTask(taskToArchive);
            }
            model.addDoneTask(taskToArchive);
            logger.fine("Archived Task " + taskToArchive);
        }
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
        
        for (Task doneTask : archivedTasks){
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
        
        model.addTasks(archivedTasks);
        return new CommandResult(MESSAGE_DONE_UNDO_SUCCESS);
    }

    //@@author 
}
