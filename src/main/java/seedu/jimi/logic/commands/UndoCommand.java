package seedu.jimi.logic.commands;

import seedu.jimi.logic.History;

/**
 * 
 * Represents the undo command
 * @@author A0148040R
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String SHORT_COMMAND_WORD = "u";
    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Undoes the previous task.\n" + "To undo a task, type undo\n"
            + "> Tip: Typing 'u' instead of 'undo' works too.\n";
    
    public UndoCommand() {}

    @Override
    public CommandResult execute() {
        return new CommandResult(COMMAND_WORD 
                + ": " 
                + History.getInstance().undo().feedbackToUser);
    }
    
    @Override
    public boolean isValidCommandWord(String commandWord) {
        String lowerStr = commandWord.toLowerCase();
        return lowerStr.equals(COMMAND_WORD.toLowerCase()) 
                || lowerStr.equals(SHORT_COMMAND_WORD.toLowerCase());
    }
}
