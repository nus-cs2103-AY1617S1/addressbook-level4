package harmony.mastermind.logic.commands;

public interface Redoable {
    /**
     * Specify the redo operation according to the nature of the corresponding class.
     * 
     * @return CommandResult, the Object contains details & feedback about the redo operation.
     */
    //@@author A0138862W
    public CommandResult redo();
}
