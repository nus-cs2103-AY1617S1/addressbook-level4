//@@author A0148096W

package teamfour.tasc.logic.commands;

import teamfour.tasc.model.keyword.UndoCommandKeyword;
import teamfour.tasc.commons.exceptions.IllegalValueException;

/**
 * Undo the last (n) commands.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = UndoCommandKeyword.keyword;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the last command(s). "
            + "Parameters: [Number of steps]\n"
            + "Example: " + COMMAND_WORD + " 5";

    public static final String MESSAGE_SUCCESS = "Last %1$s undone.";
    public static final String MESSAGE_NO_PAST_COMMAND_TO_UNDO = 
            "There is no past command to undo.";

    private final int numCommandsToBeUndone;
    
    /**
     * Default behavior of UndoCommand, undoes 1 command
     */
    public UndoCommand() throws IllegalValueException {
        this(1);
    }

    /**
     * Add Command for floating tasks
     * Convenience constructor using raw values.
     * @throws IllegalValueException if numCommandsToBeUndone is < 1
     */
    public UndoCommand(int numCommandsToBeUndone) throws IllegalValueException {
        if (numCommandsToBeUndone < 1) {
            throw new IllegalValueException("Number of undo must be positive.");
        }
        this.numCommandsToBeUndone = numCommandsToBeUndone;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        int numUndone = model.undoTaskListHistory(numCommandsToBeUndone);
        
        if (numUndone == 0) {
            return new CommandResult(MESSAGE_NO_PAST_COMMAND_TO_UNDO);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, 
                numUndone == 1 ? 
                "command" : numUndone + " commands"));
    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
