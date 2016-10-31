package tars.logic.commands;

import java.util.ArrayList;

import tars.commons.core.Messages;
import tars.commons.core.UnmodifiableObservableList;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.InvalidTaskDisplayedException;
import tars.model.task.ReadOnlyTask;
import tars.model.task.Task;
import tars.model.task.UniqueTaskList.TaskNotFoundException;
import tars.ui.Formatter;

/**
 * Deletes a task identified using it's last displayed index from tars.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "OR " + COMMAND_WORD + " 1..3";
    
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task:\n%1$s";
    
    public static final String MESSAGE_UNDO = "Added Task:\n%1$s";
    public static final String MESSAGE_REDO = "Deleted Task:\n%1$s";

    private final String arguments;
    private ArrayList<ReadOnlyTask> deletedTasks = new ArrayList<ReadOnlyTask>();
    
    public DeleteCommand(String args) {
        this.arguments = args;
    }
    
    @Override
    public CommandResult execute() {
        ArrayList<ReadOnlyTask> tasksToDelete = null;
        try {
            tasksToDelete = getTasksFromIndexes(this.arguments.split(" "));
        } catch (InvalidTaskDisplayedException itde) {
            return new CommandResult(itde.getMessage());
        }
        for (ReadOnlyTask t : tasksToDelete) {
            try {
                model.deleteTask(t);
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be missing";
            }
            deletedTasks.add(t);
        } 
        model.getUndoableCmdHist().push(this);
        String formattedTaskList = new Formatter().formatTaskList(deletedTasks);
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, formattedTaskList));
    }
    
    /**
     * Gets Tasks to delete
     * 
     * @param indexes
     * @return
     * @throws InvalidTaskDisplayedException
     */
    private ArrayList<ReadOnlyTask> getTasksFromIndexes(String[] indexes)
            throws InvalidTaskDisplayedException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        ArrayList<ReadOnlyTask> tasksList = new ArrayList<ReadOnlyTask>();

        for (int i = 0; i < indexes.length; i++) {
            int targetIndex = Integer.parseInt(indexes[i]);
            if (lastShownList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                throw new InvalidTaskDisplayedException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            ReadOnlyTask task = lastShownList.get(targetIndex - 1);
            tasksList.add(task);
        }
        return tasksList;
    }
    
    //@@author A0139924W
    @Override
    public CommandResult undo() {
        try {
            for (ReadOnlyTask t : deletedTasks) {
                Task taskToAdd = new Task(t);
                model.addTask(taskToAdd);
            }
            String formattedTaskList = new Formatter().formatTaskList(deletedTasks);
            return new CommandResult(String.format(UndoCommand.MESSAGE_SUCCESS,
                    String.format(MESSAGE_UNDO, formattedTaskList)));
        } catch (DuplicateTaskException e) {
            return new CommandResult(
                    String.format(UndoCommand.MESSAGE_UNSUCCESS, Messages.MESSAGE_DUPLICATE_TASK));
        }
    }
    
    //@@author A0139924W
    @Override
    public CommandResult redo() {
        try {
            for (ReadOnlyTask t : deletedTasks) {
                Task taskToAdd = new Task(t);
                model.deleteTask(taskToAdd);
            }
            String formattedTaskList = new Formatter().formatTaskList(deletedTasks);
            return new CommandResult(String.format(RedoCommand.MESSAGE_SUCCESS,
                    String.format(MESSAGE_REDO, formattedTaskList)));
        } catch (TaskNotFoundException e) {
            return new CommandResult(String.format(RedoCommand.MESSAGE_UNSUCCESS,
                    Messages.MESSAGE_TASK_CANNOT_BE_FOUND));
        }
    }

}
