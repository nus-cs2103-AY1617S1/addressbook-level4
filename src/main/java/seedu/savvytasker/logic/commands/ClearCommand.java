package seedu.savvytasker.logic.commands;

import seedu.savvytasker.model.SavvyTasker;

/**
 * Clears the address book.
 */
public class ClearCommand extends ModelRequiringCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Savvy Tasker has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(SavvyTasker.getEmptySavvyTasker());
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    @Override
    public boolean canUndo() {
        return false;
    }

    /**
     * Redo the clear command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        // nothing required to be done
        return true;
    }

    /**
     * Undo the clear command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        // nothing required to be done
        return true;
    }
}
