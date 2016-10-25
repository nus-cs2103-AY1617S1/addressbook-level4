package harmony.mastermind.logic.commands;

import harmony.mastermind.commons.exceptions.CommandCancelledException;

//@@author A0139194X
//Currently not in used yet. Will be use for enhancement to clear command
public interface Confirmable {
    /**
     * Specify the confirmWithUser operation according to the nature of the corresponding class.
     * 
     * @return CommandResult, the Object contains details & feedback about the confirming operation.
     * @throws CommandCancelledException 
     */
    public void confirmWithUser() throws CommandCancelledException;
}
