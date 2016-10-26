package seedu.savvytasker.logic.commands;

import java.util.LinkedList;

import seedu.savvytasker.commons.core.Messages;
import seedu.savvytasker.commons.core.UnmodifiableObservableList;
import seedu.savvytasker.model.ReadOnlySavvyTasker;
import seedu.savvytasker.model.SavvyTasker;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.model.task.TaskList.DuplicateTaskException;
import seedu.savvytasker.model.task.TaskList.InvalidDateException;
import seedu.savvytasker.model.task.TaskList.TaskNotFoundException;

public class MarkCommand extends ModelRequiringCommand {

    //@@author A0097627N
    public static final String COMMAND_WORD = "mark";
    public static final String COMMAND_FORMAT = "mark INDEX [MORE_INDEX]";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the tasks identified by the index number used in the last task listing as done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s\n";
    public static final String MESSAGE_MARK_TASK_FAIL = "Task is already marked!\n";
    
    public final int[] targetIndices;
    private ReadOnlySavvyTasker original;
    
    public MarkCommand(int[] targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        LinkedList<Task> tasksToMark = new LinkedList<Task>();
        for(int targetIndex : targetIndices) {
            if (lastShownList.size() < targetIndex || targetIndex <= 0) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            tasksToMark.add((Task) lastShownList.get(targetIndex - 1));
        }
        
        original = new SavvyTasker(model.getSavvyTasker());

        StringBuilder resultSb = new StringBuilder();
        try {
            for(Task taskToMark : tasksToMark) {
                if (!taskToMark.isArchived()){
                    taskToMark.setArchived(true);
                    model.deleteTask(taskToMark);
                    model.addTask(taskToMark);
                    resultSb.append(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
                } else {
                    resultSb.append(String.format(MESSAGE_MARK_TASK_FAIL, taskToMark));
                }
            }
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (DuplicateTaskException e) {
            e.printStackTrace();
        } catch (InvalidDateException e) {
            assert false : "The target task should be valid, only the archived flag is set";
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
     * Redo the mark command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        execute();
        return true;
    }

    /**
     * Undo the mark command
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
    //@@author
}
