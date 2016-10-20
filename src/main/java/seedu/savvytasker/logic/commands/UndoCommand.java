package seedu.savvytasker.logic.commands;

/**
 * Command to undo the last action performed
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_ACKNOWLEDGEMENT = "Undo last command as requested ...";

    public UndoCommand() {}

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_UNDO_ACKNOWLEDGEMENT);
    }
    
    @Override
    public boolean canUndo() {
        return false;
    }

    /**
     * Redo the undo command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        // nothing required to be done
        return true;
    }

    /**
     * Undo the undo command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        // nothing required to be done
        return true;
    }

}
