package harmony.mastermind.logic.commands;

// @@author A0138862W
public interface Undoable {

    /**
     * Specify the undo operation according to the nature of the corresponding class.
     * For example, to implement an undo operation on AddCommand, a delete operation should be implemented.
     * Similarly, a DeleteCommand should implement a add operation to restore the record.
     * 
     * @return CommandResult, the Object contains details & feedback about the undo operation.
     */
    //@@author A0138862W
    public CommandResult undo();
    
}