package seedu.forgetmenot.logic.commands;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author A0139671X
/**
 * Undoes a task-modifying action
 */
public class UndoCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger( UndoCommand.class.getName() );

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the most recent command in ForgetMeNot. "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Your recent command was undone!";
    public static final String MESSAGE_UNDO_INVALID = "There were no recent commands to be undone. ";
    
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.loadFromHistory();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (NoSuchElementException e) {
            LOGGER.log(Level.WARNING, "Undo invalid exception : " + e);
            return new CommandResult(MESSAGE_UNDO_INVALID);
        }
    }   

}
