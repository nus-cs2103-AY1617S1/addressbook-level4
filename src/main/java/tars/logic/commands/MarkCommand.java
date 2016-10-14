package tars.logic.commands;

import tars.commons.core.Messages;
import tars.commons.core.UnmodifiableObservableList;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.flags.Flag;
import tars.commons.util.MarkTaskTracker;
import tars.model.task.*;
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
            + "Parameters: -do <INDEX>[  <INDEX> <INDEX> ...] -ud <INDEX>[ <INDEX> <INDEX> …]\n" 
            + "Parameters: -do <START_INDEX>..<END_INDEX> -ud <START_INDEX>..<END_INDEX>\n"
            + "Example: " + COMMAND_WORD + " -do 3 5 7 -ud 2 4 6\n"
            + "OR " + COMMAND_WORD + " -do 1..3 -ud 4..6";

    private String markDone;
    private String markUndone;

    private MarkTaskTracker tracker;

    /**
     * Convenience constructor using raw values.
     * 
     * @throws InvalidRangeException
     */
    public MarkCommand(String markDone, String markUndone) {
        this.markDone = markDone.replace(Flag.DONE, " ").trim();
        this.markUndone = markUndone.replace(Flag.UNDONE, " ").trim();
        this.tracker = new MarkTaskTracker();
    }

    /**
     * Get string of indexes separated by spaces given a start and end index
     */
    private String rangeIndexString(String range) throws InvalidRangeException {
        if (!range.contains("..")) {
            return range;
        }

        String rangeToReturn = "";

        int toIndex = range.indexOf("..");
        String start = range.substring(0, toIndex);
        String end = range.substring(toIndex + 2);

        int startInt = Integer.parseInt(start);
        int endInt = Integer.parseInt(end);

        if (startInt > endInt) {
            throw new InvalidRangeException();
        }

        for (int i = startInt; i <= endInt; i++) {
            rangeToReturn += String.valueOf(i) + " ";
        }

        return rangeToReturn.trim();
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        if (!this.markDone.equals("") || !this.markUndone.equals("")) {
            if (!this.markDone.equals("")) {
                try {
                    Status done = new Status(true);
                    String markDone = rangeIndexString(this.markDone);
                    ArrayList<ReadOnlyTask> markDoneTasks = getTasksFromIndexes(markDone.split(" "), done);
                    model.mark(markDoneTasks, Flag.DONE);
                } catch (InvalidTaskDisplayedException e) {
                    return new CommandResult(e.getMessage());
                } catch (DuplicateTaskException dte) {
                    return new CommandResult(dte.getMessage());
                } catch (InvalidRangeException ire) {
                    return new CommandResult(ire.getMessage());
                }
            }
            if (!this.markUndone.equals("")) {
                try {
                    Status undone = new Status(false);
                    String markUndone = rangeIndexString(this.markUndone);
                    ArrayList<ReadOnlyTask> markUndoneTasks = getTasksFromIndexes(markUndone.split(" "), undone);
                    model.mark(markUndoneTasks, Flag.UNDONE);
                } catch (InvalidTaskDisplayedException e) {
                    return new CommandResult(e.getMessage());
                } catch (DuplicateTaskException dte) {
                    return new CommandResult(dte.getMessage());
                } catch (InvalidRangeException ire) {
                    return new CommandResult(ire.getMessage());
                }
            }
            return new CommandResult(getResult());
        }
        return new CommandResult(getResult());
    }

    /**
     * Prepares feedback message to user
     * 
     * @return
     */
    private String getResult() {
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

    /**
     * Signals an index exceed the size of the internal list.
     */
    public class InvalidTaskDisplayedException extends Exception {
        public InvalidTaskDisplayedException(String message) {
            super(message);
        }
    }

    /**
     * Signals invalid range entered
     */
    public class InvalidRangeException extends Exception {
        private static final String MESSAGE_INVALID_RANGE = "Start index must be before end index";
        public InvalidRangeException() {
            super(MESSAGE_INVALID_RANGE);
        }
    }

}
