package seedu.jimi.logic.commands;

import seedu.jimi.model.TaskBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command implements TaskBookEditor{
    
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Jimi has been cleared!";
    
    public ClearCommand() {}
    
    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(TaskBook.getEmptyTaskBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    // @@author A0140133B
    @Override
    public boolean isValidCommandWord(String commandWord) {
        return commandWord.equals(COMMAND_WORD);
    }
    // @@author
}
