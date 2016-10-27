package tars.logic.commands;

import tars.commons.core.Messages;
import tars.commons.core.UnmodifiableObservableList;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.InvalidRangeException;
import tars.commons.exceptions.InvalidTaskDisplayedException;
import tars.commons.util.MarkTaskTracker;
import tars.model.task.*;

import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

/**
 * Marks a task identified using it's last displayed index from tars as done or
 * undone.
 * 
 * @@author A0121533W
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": mark a task as done or undone."
            + "Parameters: /do <INDEX>[  <INDEX> <INDEX> ...] /ud <INDEX>[ <INDEX> <INDEX> …]\n" 
            + "Parameters: /do <START_INDEX>..<END_INDEX> /ud <START_INDEX>..<END_INDEX>\n"
            + "Example: " + COMMAND_WORD + " /do 3 5 7 /ud 2 4 6\n"
            + "OR " + COMMAND_WORD + " /do 1..3 /ud 4..6";

    private String markDone;
    private String markUndone;

    private MarkTaskTracker tracker;

    /**
     * Convenience constructor using raw values.
     * 
     * @throws InvalidRangeException
     */
    public MarkCommand(String markDone, String markUndone) {
        this.markDone = markDone;
        this.markUndone = markUndone;
        this.tracker = new MarkTaskTracker();
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        if (this.markDone.equals("") && this.markUndone.equals("")) {
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }
        
        try {
            handleMarkDone();
            handleMarkUndone();
        } catch (InvalidTaskDisplayedException e) {
            return new CommandResult(e.getMessage());
        } catch (DuplicateTaskException dte) {
            return new CommandResult(dte.getMessage());
        }         
        return new CommandResult(getResultFromTracker());
    }
    
    private void handleMarkDone() throws InvalidTaskDisplayedException, DuplicateTaskException {
        if (!this.markDone.equals("")) {
            Status done = new Status(true);
            ArrayList<ReadOnlyTask> markDoneTasks = getTasksFromIndexes(this.markDone.split(" "), done);
            model.mark(markDoneTasks, "/do");
        }
    }
    
    private void handleMarkUndone() throws InvalidTaskDisplayedException, DuplicateTaskException {
        if (!this.markUndone.equals("")) {
            Status undone = new Status(false);
            ArrayList<ReadOnlyTask> markUndoneTasks = getTasksFromIndexes(this.markUndone.split(" "), undone);
            model.mark(markUndoneTasks, "/ud");
        }
    }
        
    /**
     * Returns feedback message of mark command to user
     * 
     * @return
     */
    private String getResultFromTracker() {
        String commandResult = tracker.getResult();
        return commandResult;
    }

    /**
     * Gets Tasks to mark done or undone from indexes
     * 
     * @param indexes
     * @return
     * @throws InvalidTaskDisplayedException
     */
    private ArrayList<ReadOnlyTask> getTasksFromIndexes(String[] indexes, Status status)
            throws InvalidTaskDisplayedException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        ArrayList<ReadOnlyTask> tasksList = new ArrayList<ReadOnlyTask>();

        for (int i = 0; i < indexes.length; i++) {
            int targetIndex = Integer.valueOf(indexes[i]);
            if (lastShownList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                throw new InvalidTaskDisplayedException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            ReadOnlyTask task = lastShownList.get(targetIndex - 1);
            if (!task.getStatus().equals(status)) {
                tasksList.add(task);
                tracker.addToMark(targetIndex, status);
            } else {
                tracker.addAlreadyMarked(targetIndex, status);
            }
        }
        return tasksList;
    }
}
