package seedu.jimi.logic.commands;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

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

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    
    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Shows program usage instructions.\n"
            + "You can also get specific help for commands.\n"
            + "For example, getting help for add command: " + COMMAND_WORD + " add"
            + "\n";
    
    private static final String UNKNOWN_HELP_COMMAND = 
            Messages.MESSAGE_UNKNOWN_COMMAND + " - \"%1$s\" \n"
            + "\n"
            + "All available commands: %2$s \n"
            + "\n"
            + MESSAGE_USAGE;
    
    
    private Command toHelp;
    
    public HelpCommand() {}
    
    public HelpCommand(String cmdToShow) throws IllegalValueException {
        List<Command> cmdStubList = JimiParser.getCommandStubList();
        
        // Tries to find a match with all command words.
        Optional<Command> match =
                cmdStubList.stream().filter(c -> c.isValidCommandWord(cmdToShow)).findFirst();
        
        if (match.isPresent()) {
            toHelp = match.get();
        } else {
            // Creating string of all command words in Jimi.
            StringJoiner sj = new StringJoiner(", ");
            cmdStubList.stream()
                .map(c -> c.getCommandWord())
                .filter(s -> s != null && !s.isEmpty())
                .forEach(s -> sj.add(s));
            
            throw new IllegalValueException(String.format(UNKNOWN_HELP_COMMAND, cmdToShow, sj.toString()));
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
    
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
