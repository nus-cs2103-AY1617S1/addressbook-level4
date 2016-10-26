package seedu.jimi.logic.commands;

import seedu.jimi.logic.History;

/**
 * 
 * @@author A0148040R
 * Represents the redo command
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String SHORT_COMMAND_WORD = "r";
    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Redoes the previous task.\n" + "To redo a task, type redo\n"
            + "> Tip: Typing 'r' instead of 'redo' works too.\n";

    public RedoCommand() {}

    @Override
    public CommandResult execute() {
        return new CommandResult(COMMAND_WORD.substring(0, 1).toUpperCase() + COMMAND_WORD.substring(1)  
                + ": " 
                + History.getInstance().redo().feedbackToUser);
    }

    @Override
    public boolean isValidCommandWord(String commandWord) {
        String lowerStr = commandWord.toLowerCase();
        return lowerStr.equals(COMMAND_WORD.toLowerCase()) 
                || lowerStr.equals(SHORT_COMMAND_WORD.toLowerCase());
    }
}
