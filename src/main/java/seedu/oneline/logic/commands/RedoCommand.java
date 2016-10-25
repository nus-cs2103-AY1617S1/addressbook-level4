package seedu.oneline.logic.commands;

import seedu.oneline.commons.exceptions.StateNonExistentException;

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_REDO_SUCCESS = "Action redone.";
    public static final String MESSAGE_NO_NEXT_STATE = "No undone actions to redo.";
    
    public RedoCommand() {}

    public RedoCommand(String args) {}
    
    @Override
    public CommandResult execute() {
        try {
            model.redo();
        } catch (StateNonExistentException ex) {
            return new CommandResult(MESSAGE_NO_NEXT_STATE);
        }
        return new CommandResult(MESSAGE_REDO_SUCCESS);
    }

}