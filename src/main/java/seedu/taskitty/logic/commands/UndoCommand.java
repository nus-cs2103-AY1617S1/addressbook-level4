package seedu.taskitty.logic.commands;

import seedu.taskitty.commons.exceptions.NoPreviousValidCommandException;

//@@author A0139052L
/**
 * Undoes previous command given
 */
public class UndoCommand extends Command {
    
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD;
    public static final String MESSAGE_USAGE = "This command undos your previous action, Meow!";

    public static final String MESSAGE_UNDO_SUCCESS = "Previous action undone: ";
    public static final String MESSAGE_NO_PREVIOUS_VALID_COMMANDS = "There is no more previous command in this session.";
    
    public UndoCommand() {}
    
    @Override
    public CommandResult execute() {
        try {
            String commandUndone = model.undo();
            return new CommandResult(MESSAGE_UNDO_SUCCESS + commandUndone);
        } catch (NoPreviousValidCommandException e) {
            return new CommandResult(MESSAGE_NO_PREVIOUS_VALID_COMMANDS);
        }       
    }

    @Override
    public void saveStateIfNeeded(String commandText) {}

}
