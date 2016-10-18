package harmony.mastermind.logic.commands;

import harmony.mastermind.commons.exceptions.CommandCancelledException;

public interface Confirmable {
    /**
     * Specify the confirmWithUser operation according to the nature of the corresponding class.
     * 
     * @return CommandResult, the Object contains details & feedback about the confirming operation.
     * @throws CommandCancelledException 
     */
    //@@author A0139194X
    public void confirmWithUser() throws CommandCancelledException;
}
