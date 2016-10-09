package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;

public class MasterParser {
    private static final Pattern BASIC_COMMAND_FORMAT = 
            Pattern.compile("(?<header>\\S+).*");
    
    private final Map<String, CommandParser<? extends Command>> commandParsers;
    
    public MasterParser() {
        this.commandParsers = new HashMap<String, CommandParser<? extends Command>>();
    }

    public Command parse(String userInput) {
        final String trimmedUserInput = userInput.trim();
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(trimmedUserInput);
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        
        final String header = matcher.group("header");
        CommandParser<? extends Command> parser = commandParsers.get(header);
        if (parser == null) {
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
        
        try {
            return parser.parse(trimmedUserInput);
        } catch (ParseException pe) {
            /* TODO: Modify this to use ParseException's detail. */
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, parser.getRequiredFormat()));
        }
    }
    
    /**
     * Registers a command parser that will be used by the master parser.
     * The header of this command parser must not be used by any other command parsers
     * that are currently registered into the master parser. Use {@link #isCommandParserRegistered(String)
     * isCommandParserRegistered } method to check if such a command parser is already registered.
     * 
     * Parameter commandParser should not be null.
     * 
     * @param commandParser the command parser
     */
    public void registerCommandParser(CommandParser<? extends Command> commandParser) {
        assert commandParser != null;
        assert commandParsers.get(commandParser.getHeader()) != null;
        
        commandParsers.put(commandParser.getHeader(), commandParser);        
    }
    
    /**
     * Checks if a command parser with the specified header is already 
     * registered into the master parser.
     * 
     * @param header the header to check against
     * @return true if such a command parser is registered, false otherwise
     */
    public boolean isCommandParserRegistered(String header) {
        return (commandParsers.get(header) != null);
    }
    
    /**
     * Unregisters and returns the command parser that uses the specified header.
     * If such a parser is not registered, null is returned.
     * 
     * @param header the header to check against
     * @return the CommandParser object that uses the specified header.
     */
    public CommandParser<? extends Command> unregisterCommandParser(String header) {
        return commandParsers.remove(header);
    }
}
