package seedu.savvytasker.logic.commands;

import java.util.LinkedList;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.commons.core.UnmodifiableObservableList;
import seedu.savvytasker.logic.commands.models.DeleteCommandModel;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.model.task.TaskList.DuplicateTaskException;
import seedu.savvytasker.model.task.TaskList.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends ModelRequiringCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_FORMAT = "delete INDEX [MORE_INDEX]";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tasks identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final DeleteCommandModel commandModel;
    private Task deleted;

    public DeleteCommand(DeleteCommandModel commandModel) {
        assert (commandModel != null);
        this.commandModel = commandModel;
        this.deleted = null;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        LinkedList<ReadOnlyTask> tasksToDelete = new LinkedList<ReadOnlyTask>();
        for(int targetIndex : commandModel.getTargetIndex()) {
            if (lastShownList.size() < targetIndex || targetIndex <= 0) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            tasksToDelete.add(lastShownList.get(targetIndex - 1));
        }

        StringBuilder resultSb = new StringBuilder();
        try {
            for(ReadOnlyTask taskToDelete : tasksToDelete) {
                deleted = (Task)taskToDelete;
                model.deleteTask(taskToDelete);
                resultSb.append(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
            }
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(resultSb.toString());
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }

    /**
     * Redo the delete command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        UnmodifiableObservableList<Task> lastShownList = model.getFilteredTaskListTask();
        
        for (int i = 0; i < lastShownList.size(); i++) {
            if (lastShownList.get(i) == deleted){
                ReadOnlyTask taskToDelete = lastShownList.get(i);
                try {
                    model.deleteTask(taskToDelete);
                } catch (TaskNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } 
        return true;
    }

    /**
     * Undo the delete command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {      
        assert model != null;
        try {
            model.addTask(deleted);
        } catch (DuplicateTaskException e) {
            e.printStackTrace();
        }
        
        return true;
    }
    
    /**
     * Check if command is an undo command
     * @return true if the command is an undo operation, false otherwise
     */
    @Override
    public boolean isUndo() {
        return false;
    }
    
    /**
     * Check if command is a redo command
     * @return true if the command is a redo operation, false otherwise
     */
    @Override
    public boolean isRedo(){
        return false;
    }
}
