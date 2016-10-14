package teamfour.tasc.logic.commands;

import teamfour.tasc.logic.LogicManager;

/**
 * Undo the last (n) commands.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the last command(s). "
            + "Parameters: [Number of steps]\n"
            + "Example: " + COMMAND_WORD + " 5";

    public static final String MESSAGE_SUCCESS = "Last %1$s undone.";
    public static final String MESSAGE_NO_COMMAND_ENTERED = "There is no command entered.";
    public static final String MESSAGE_NUM_COMMANDS_NOT_ENOUGH = 
            "You can onlt undo the last %1$s.";

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
        try {
            LogicManager.executeUndo(numCommandsToBeUndone);
            return new CommandResult(String.format(MESSAGE_SUCCESS, numCommandsToBeUndone == 1 ? 
                    "command" : numCommandsToBeUndone + " commands"));
        } catch (LogicManager.UndoableTaskNotEnoughException e) {
            indicateAttemptToExecuteIncorrectCommand();
            int numUndoableCommands = LogicManager.numUndoableCommands();
            if (numUndoableCommands == 0) {
                return new CommandResult(MESSAGE_NO_COMMAND_ENTERED);
            } else {
                return new CommandResult(String.format(MESSAGE_NUM_COMMANDS_NOT_ENOUGH, numUndoableCommands));
            }
        }
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
