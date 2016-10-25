//@@author A0147971U
package teamfour.tasc.logic.commands;

/**
 * Redo the last (n) commands.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redo the last command(s). "
            + "Parameters: [Number of steps]\n"
            + "Example: " + COMMAND_WORD + " 5";

    public static final String MESSAGE_SUCCESS = "Last %1$s redone.";
    public static final String MESSAGE_NO_PAST_COMMAND_TO_REDO = 
            "There is no past command to redo.";

    private final int numCommandsToBeRedone;

    /**
     * Add Command for floating tasks
     * Convenience constructor using raw values.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public RedoCommand(int numCommandsToBeUndone) {
        this.numCommandsToBeRedone = numCommandsToBeUndone;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        int numRedone = model.redoTaskListHistory(numCommandsToBeRedone);
        
        if (numRedone == 0) {
            return new CommandResult(MESSAGE_NO_PAST_COMMAND_TO_REDO);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, 
                numRedone == 1 ? 
                "command" : numRedone + " commands"));
    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
