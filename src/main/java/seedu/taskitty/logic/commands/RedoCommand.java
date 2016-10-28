package seedu.taskitty.logic.commands;

import seedu.taskitty.commons.exceptions.NoRecentUndoCommandException;

//@@author A0139052L
/**
* Redoes previous command given
*/
public class RedoCommand extends Command {
    
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD;
    public static final String MESSAGE_USAGE = "This command redos your previous undo action, Meow!";

    public static final String MESSAGE_REDO_SUCCESS = "Undoed action restored: ";
    public static final String MESSAGE_NO_RECENT_UNDO_COMMANDS = "There is no recent undoed command in this session.";
    
    @Override
    public CommandResult execute() {
        try {
            String commandRedone = model.redo();
            return new CommandResult(MESSAGE_REDO_SUCCESS + commandRedone);
        } catch (NoRecentUndoCommandException e) {
            return new CommandResult(MESSAGE_NO_RECENT_UNDO_COMMANDS);
        }
    }

}
