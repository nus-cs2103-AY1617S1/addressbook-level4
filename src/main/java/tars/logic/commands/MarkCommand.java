package tars.logic.commands;

import tars.commons.core.Messages;
import tars.commons.core.UnmodifiableObservableList;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.flags.Flag;
import tars.model.task.*;
import java.util.ArrayList;


/**
 * Marks a task identified using it's last displayed index from tars as done or undone.
 * 
 * @@author A0121533W
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": mark a task as done or undone. "
            + "Parameters: -do <INDEX>[  <INDEX> <INDEX> ...] -ud <INDEX>[ <INDEX> <INDEX> …]"
            + "Example: " + COMMAND_WORD
            + " -do 3 5 7 -ud 2 4 6";

    public static final String MESSAGE_MARK_SUCCESS = "Marked Tasks";
    public static final String MESSAGE_MARK_FAILURE = "No Tasks Marked";

    private String markDone;
    private String markUndone;

    /**
     * Convenience constructor using raw values.
     */
    public MarkCommand(String markDone, String markUndone) {
        this.markDone = markDone.replace(Flag.DONE, " ").trim();
        this.markUndone = markUndone.replace(Flag.UNDONE, " ").trim();
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        if (!this.markDone.equals("") || !this.markUndone.equals("")) {
            if (!this.markDone.equals("")) {
                try {
                    ArrayList<ReadOnlyTask> markDoneTasks = getTasksFromIndexes(this.markDone.split(" "));
                    model.mark(markDoneTasks, Flag.DONE);
                } catch (InvalidTaskDisplayedException e) {
                    return new CommandResult(e.getMessage());
                } catch (DuplicateTaskException dte) {
                    return new CommandResult(dte.getMessage());
                }
            } 
            if (!this.markUndone.equals("")) {
                try {
                    ArrayList<ReadOnlyTask> markUndoneTasks = getTasksFromIndexes(this.markUndone.split(" "));
                    model.mark(markUndoneTasks, Flag.UNDONE);
                } catch (InvalidTaskDisplayedException e) {
                    return new CommandResult(e.getMessage());
                } catch (DuplicateTaskException dte) {
                    return new CommandResult(dte.getMessage());
                }
            }
            return new CommandResult(MESSAGE_MARK_SUCCESS);
        }
        return new CommandResult(MESSAGE_MARK_FAILURE);
    }

    /**
     * Gets Tasks to mark done or undone from indexes
     * @param indexes
     * @return
     * @throws InvalidTaskDisplayedException
     */
    private ArrayList<ReadOnlyTask> getTasksFromIndexes(String[] indexes) throws InvalidTaskDisplayedException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        ArrayList<ReadOnlyTask> tasksList = new ArrayList<ReadOnlyTask>();

        for (int i = 0; i < indexes.length; i++) {
            int targetIndex = Integer.valueOf(indexes[i]);
            if (lastShownList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                throw new InvalidTaskDisplayedException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask task = lastShownList.get(targetIndex - 1);
            tasksList.add(task);
        }
        return tasksList;
    }

    /**
     * Signals an index exceed the size of the internal list.
     */
    public class InvalidTaskDisplayedException extends Exception {
        public InvalidTaskDisplayedException(String message) {
            super(message);
        }
    }

}
