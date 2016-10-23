package seedu.taskitty.logic.commands;

import seedu.taskitty.commons.exceptions.NoPreviousCommandException;

//@@author A0139052L
/**
 * Undoes previous command given
 */
public class UndoCommand extends Command {
    
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undos the previous action\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_SUCCESS = "Previous action undone: ";
    public static final String MESSAGE_NO_PREVIOUS_COMMANDS = "There is no more previous command in this session.";
    
    public UndoCommand() {}
    
    @Override
    public CommandResult execute() {
        try {
            String commandUndone = model.undo();
            return new CommandResult(MESSAGE_UNDO_SUCCESS + commandUndone);
        } catch (NoPreviousCommandException e) {
            return new CommandResult(MESSAGE_NO_PREVIOUS_COMMANDS);
        }       
    }

    @Override
    public void saveStateIfNeeded(String commandText) {}

}
