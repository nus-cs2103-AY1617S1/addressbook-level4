package seedu.jimi.logic.commands;

import seedu.jimi.logic.History;

/**
 * 
 * @@author A0148040R
 * Represents the redo command
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Redoes the previously undone task.\n" 
            + "> Shortcuts: r, re, red";

    public RedoCommand() {}

    @Override
    public CommandResult execute() {
        return new CommandResult(COMMAND_WORD.substring(0, 1).toUpperCase() + COMMAND_WORD.substring(1)  
                + ": " 
                + History.getInstance().redo().feedbackToUser);
    }

    @Override
    public boolean isValidCommandWord(String commandWord) {
        for (int i = 1; i <= COMMAND_WORD.length(); i++) {
            if (commandWord.equalsIgnoreCase(COMMAND_WORD.substring(0, i))) {
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
