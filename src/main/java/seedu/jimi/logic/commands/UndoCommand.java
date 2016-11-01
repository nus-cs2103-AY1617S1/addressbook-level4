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
        return new CommandResult(COMMAND_WORD.substring(0, 1).toUpperCase() + COMMAND_WORD.substring(1) 
                + ": " 
                + History.getInstance().undo().feedbackToUser);
    }
    
    @Override
    public boolean isValidCommandWord(String commandWord) {
        for (int i = 1; i <= COMMAND_WORD.length(); i++) {
            if (commandWord.toLowerCase().equals(COMMAND_WORD.substring(0, i))) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getMessageUsage() {
        return MESSAGE_USAGE;
    }
    
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
