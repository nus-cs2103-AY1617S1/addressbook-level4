package seedu.savvytasker.logic.commands;

/**
 * Terminates the program.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_REDO_ACKNOWLEDGEMENT = "Redo last command as requested ...";

    public RedoCommand() {}

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_REDO_ACKNOWLEDGEMENT);
    }
    
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
        return true;
    }

    /**
     * Undo the redo command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        // nothing required to be done
        return true;
    }

}
