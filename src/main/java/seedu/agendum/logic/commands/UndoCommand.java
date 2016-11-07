package seedu.agendum.logic.commands;

import seedu.agendum.model.ModelManager.NoPreviousListFoundException;

//@@author A0133367E
/**
 * Undo the last change to the to-do list
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_FORMAT = "undo";
    public static final String COMMAND_DESCRIPTION = "undo the last change to your to-do list";

    public static final String MESSAGE_SUCCESS = "Previous change undone!";
    public static final String MESSAGE_FAILURE = "Nothing to undo!";
	
    @Override
    public CommandResult execute() {
        assert model != null;
        
        try {
            model.restorePreviousToDoList();
        } catch (NoPreviousListFoundException nplfe) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    public static String getName() {
        return COMMAND_WORD;
    }
	
    public static String getFormat() {
        return COMMAND_FORMAT;
    }
	
    public static String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}