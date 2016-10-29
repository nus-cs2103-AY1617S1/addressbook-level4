package tars.logic.commands;

import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.InvalidRangeException;
import tars.commons.exceptions.InvalidTaskDisplayedException;
import tars.model.task.*;

import java.util.ArrayList;

/**
 * Marks a task identified using it's last displayed index from tars as undone.
 * 
 * @@author A0121533W
 */
public class UdCommand extends Command {

    public static final String COMMAND_WORD = "ud";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": mark a task as undone.\n"
            + "Parameters: <INDEX>[  <INDEX> <INDEX> ...]\n" 
            + "Example: " + COMMAND_WORD + " 3 5 7"
            + "OR " + COMMAND_WORD + " 1..3\n";

    private String toUndo;

    private MarkTaskUtil tracker;

    /**
     * Convenience constructor using raw values.
     * 
     * @throws InvalidRangeException
     */
    public UdCommand(String toUndo) {
        this.toUndo = toUndo;
        this.tracker = new MarkTaskUtil();
    }

    @Override
    public CommandResult execute() {
        assert model != null;
                
        try {
            handleMarkUndone();
        } catch (InvalidTaskDisplayedException e) {
            return new CommandResult(e.getMessage());
        } catch (DuplicateTaskException dte) {
            return new CommandResult(dte.getMessage());
        }         
        return new CommandResult(tracker.getResultFromTracker());
    }
    
    private void handleMarkUndone() throws InvalidTaskDisplayedException, DuplicateTaskException {
        Status undone = new Status(false);
        ArrayList<ReadOnlyTask> markUndoneTasks = tracker.getTasksFromIndexes(model, this.toUndo.split(" "), undone);
        model.mark(markUndoneTasks, undone);
    }
    
}
