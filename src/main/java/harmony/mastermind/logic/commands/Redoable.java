package harmony.mastermind.logic.commands;
// @@author A0138862W
public interface Redoable {
    /**
     * Specify the redo operation according to the nature of the corresponding class.
     * 
     * @return CommandResult, the Object contains details & feedback about the redo operation.
     */
    public CommandResult redo();
}
