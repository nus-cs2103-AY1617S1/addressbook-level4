//@@author A0140156R

package seedu.oneline.logic.commands;

import seedu.oneline.commons.core.EventsCenter;
import seedu.oneline.commons.events.ui.ExitAppRequestEvent;
import seedu.oneline.commons.exceptions.StateNonExistentException;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "Action undone.";
    public static final String MESSAGE_NO_PREVIOUS_STATE = "No previous state to undo.";
    
    public UndoCommand() {}

    public static UndoCommand createFromArgs(String args) {
        return new UndoCommand();
    }
    
    @Override
    public CommandResult execute() {
        try {
            model.undo();
        } catch (StateNonExistentException ex) {
            return new CommandResult(MESSAGE_NO_PREVIOUS_STATE);
        }
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }

}