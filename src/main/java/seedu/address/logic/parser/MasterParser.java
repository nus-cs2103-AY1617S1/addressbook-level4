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
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, parser.getRequiredFormat()));
        }
    }
}
