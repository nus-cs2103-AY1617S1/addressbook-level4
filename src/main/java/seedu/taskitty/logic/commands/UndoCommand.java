package seedu.taskitty.logic.commands;

import seedu.taskitty.commons.exceptions.NoPreviousCommandException;

/**
 * Undoes previous command given
 * @author tan
 *
 */
public class UndoCommand extends Command {
    
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undos the previous action\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_SUCCESS = "Previous action undone: ";
    
    public UndoCommand() {}
    
    @Override
    public CommandResult execute() {
        try {
            String commandUndone = model.undo();
            return new CommandResult(MESSAGE_UNDO_SUCCESS + commandUndone);
        } catch (NoPreviousCommandException e) {
            return new CommandResult(e.getMessage());
        }       
    }

    @Override
    public void saveStateIfNeeded(String commandText) {}

}
