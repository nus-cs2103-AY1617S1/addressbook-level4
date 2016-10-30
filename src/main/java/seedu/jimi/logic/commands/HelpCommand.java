package seedu.jimi.logic.commands;

import java.util.Optional;

import seedu.jimi.commons.core.EventsCenter;
import seedu.jimi.commons.core.Messages;
import seedu.jimi.commons.events.ui.ShowHelpRequestEvent;
import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.logic.parser.JimiParser;

// @@author A0140133B
/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {
    
    public static final String COMMAND_WORD = "help";
    
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Shows program usage instructions.\n" + "Example: " + COMMAND_WORD;
    
    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    
    private Command toHelp;
    
    public HelpCommand() {}
    
    public HelpCommand(String cmdToShow) throws IllegalValueException {
        // Tries to find a match with all command words.
        Optional<Command> match =
                JimiParser.getCommandStubList().stream().filter(c -> c.isValidCommandWord(cmdToShow)).findFirst();
        
        if (match.isPresent()) {
            toHelp = match.get();
        } else {
            throw new IllegalValueException(COMMAND_WORD.substring(0, 1).toUpperCase() + COMMAND_WORD.substring(1)
                    + ": " + Messages.MESSAGE_UNKNOWN_COMMAND + ", " + cmdToShow);
        }
    }
    
    @Override
    public CommandResult execute() {
        if (toHelp == null) {
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);
        } else { // user specified command for help.
            return new CommandResult(toHelp.getMessageUsage());
        }
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
}
