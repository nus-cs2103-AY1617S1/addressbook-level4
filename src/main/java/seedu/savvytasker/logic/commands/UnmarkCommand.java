package seedu.savvytasker.logic.commands;

import java.util.LinkedList;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.commons.core.UnmodifiableObservableList;
import seedu.savvytasker.logic.commands.models.UnmarkCommandModel;
import seedu.savvytasker.model.ReadOnlySavvyTasker;
import seedu.savvytasker.model.SavvyTasker;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.model.task.TaskList.DuplicateTaskException;
import seedu.savvytasker.model.task.TaskList.TaskNotFoundException;

public class UnmarkCommand extends ModelRequiringCommand {

    public static final String COMMAND_WORD = "unmark";
    public static final String COMMAND_FORMAT = "unmark INDEX [MORE_INDEX]";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks the marked tasks identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Unmarked Task: %1$s\n";
    public static final String MESSAGE_UNMARK_TASK_FAIL = "Task is already unmarked!\n";
    
    public final UnmarkCommandModel commandModel;
    private ReadOnlySavvyTasker original;
    
    public UnmarkCommand(UnmarkCommandModel commandModel) {
        assert (commandModel != null);
        this.commandModel = commandModel;
    }
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        LinkedList<Task> tasksToUnmark = new LinkedList<Task>();
        for(int targetIndex : commandModel.getTargetIndex()) {
            if (lastShownList.size() < targetIndex || targetIndex <= 0) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            tasksToUnmark.add((Task) lastShownList.get(targetIndex - 1));
        }
        
        original = new SavvyTasker(model.getSavvyTasker());

        StringBuilder resultSb = new StringBuilder();
        try {
            for(Task taskToUnmark : tasksToUnmark) {
                if (taskToUnmark.isArchived()){
                    taskToUnmark.setArchived(false);
                    model.deleteTask(taskToUnmark);
                    model.addTask(taskToUnmark);
                    model.updateFilteredListToShowArchived();
                    resultSb.append(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
                } else {
                    resultSb.append(String.format(MESSAGE_UNMARK_TASK_FAIL, taskToUnmark));
                }
            }
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (DuplicateTaskException e) {
            e.printStackTrace();
        }
        return new CommandResult(resultSb.toString());
    }

    /**
     * Checks if a command can perform undo operations
     * @return true if the command supports undo, false otherwise
     */
    @Override
    public boolean canUndo() {
        return true;
    }

    /**
     * Redo the unmark command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        execute();
        return true;
    }

    /**
     * Undo the unmark command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        assert model != null;
        model.resetData(original);
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
