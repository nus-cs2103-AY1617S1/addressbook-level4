package seedu.task.logic.commands;


import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.ShowHelpRequestEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.LogicManager;

/**
 * Format full help instructions for every command for display.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the last add/delete/addTag/deleteTag/update Command.\n"
            + "Example: " + COMMAND_WORD;

    public UndoCommand() {}

    @Override
    public CommandResult execute() throws IllegalValueException {
    	return LogicManager.undo();
    }
}
