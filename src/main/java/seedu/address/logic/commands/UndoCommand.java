package seedu.address.logic.commands;


import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.LogicManager;

/**
 * Format full help instructions for every command for display.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the last add/delete/addTag/deleteTag/update Command.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_UNDO_MESSAGE = "Undo complete";

    public UndoCommand() {}

    @Override
    public CommandResult execute() throws IllegalValueException {
        LogicManager.undo();
        return new CommandResult(SHOWING_UNDO_MESSAGE);
    }
}
