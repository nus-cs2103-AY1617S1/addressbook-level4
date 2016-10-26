package seedu.savvytasker.logic.commands;

/**
 * Terminates the program.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_REDO_ACKNOWLEDGEMENT = "Last command redone";

    public RedoCommand() {}

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_REDO_ACKNOWLEDGEMENT);
    }
    
    /**
     * Checks if a command can perform undo operations
     * @return true if the command supports undo, false otherwise
     */
    @Override
    public boolean canUndo() {
        return false;
    }

    /**
     * Redo the redo command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        // nothing required to be done
        return false;
    }

    /**
     * Undo the redo command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        // nothing required to be done
        return false;
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
        return true;
    }
}
