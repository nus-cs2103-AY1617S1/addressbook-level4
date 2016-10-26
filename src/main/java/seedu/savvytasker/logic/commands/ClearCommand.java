package seedu.savvytasker.logic.commands;

import seedu.savvytasker.model.ReadOnlySavvyTasker;
import seedu.savvytasker.model.SavvyTasker;

/**
 * Clears the address book.
 */
public class ClearCommand extends ModelRequiringCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Savvy Tasker has been cleared!";
    
    private ReadOnlySavvyTasker original;
    
    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        original = new SavvyTasker(model.getSavvyTasker());
        model.resetData(SavvyTasker.getEmptySavvyTasker());
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    /**
     * Checks if a command can perform undo operations
     * @return true if the command supports undo, false otherwise
     */
    @Override
    public boolean canUndo() {
        return true;
    }

    /**
     * Redo the clear command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        execute();
        return true;
    }

    /**
     * Undo the clear command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        assert model != null;
        model.resetData(original);
        return true;
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
        return false;
    }
}
