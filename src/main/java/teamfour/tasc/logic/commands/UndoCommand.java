package teamfour.tasc.logic.commands;

/**
 * Undo the last (n) commands.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the last command(s). "
            + "Parameters: [Number of steps]\n"
            + "Example: " + COMMAND_WORD + " 5";

    public static final String MESSAGE_SUCCESS = "Last %1$s undone.";
    public static final String MESSAGE_NUM_COMMANDS_NOT_ENOUGH = 
            "There is no past command to undo.";

    private final int numCommandsToBeUndone;

    /**
     * Add Command for floating tasks
     * Convenience constructor using raw values.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public UndoCommand(int numCommandsToBeUndone) {
        this.numCommandsToBeUndone = numCommandsToBeUndone;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        int numCommandsUndone = 0;
        for (int i = 0; i < numCommandsToBeUndone; i++) {
            if (model.undoTaskListHistory()) {
                numCommandsUndone++;
            }
        }
        
        if (numCommandsUndone == 0) {
            return new CommandResult(MESSAGE_NUM_COMMANDS_NOT_ENOUGH);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, 
                numCommandsUndone == 1 ? 
                "command" : numCommandsUndone + " commands"));
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public CommandResult executeUndo() {
        return null;
    }

}
