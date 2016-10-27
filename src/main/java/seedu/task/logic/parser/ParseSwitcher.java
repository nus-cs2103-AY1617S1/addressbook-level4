//@@author A0141052Y
package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.task.commons.core.Messages.MESSAGE_INTERNAL_ERROR;

import java.util.Optional;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;

/**
 * ParseSwitcher -- parses the raw command from user and delegates the
 * parsing to the specific command parsers for further parsing.
 * 
 * @author Syed Abdullah
 *
 */
public class ParseSwitcher {
    
    private final ParserMapping parserMappings = new ParserMapping();
    
    public ParseSwitcher() { }

    /**
     * Parses the user's input and determines the appropriate command
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        String[] commandSegments = userInput.split(" ", 2);
        final String commandWord = (commandSegments.length > 0) ? commandSegments[0] : "";
        final String commandArgs = (commandSegments.length > 1) ? commandSegments[1] : "";
        
        Optional<Class<? extends BaseParser>> selectedParser = parserMappings.getParserForCommand(commandSegments[0]);
        
        if (!selectedParser.isPresent()) {
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        } else {
            BaseParser parser;
            try {
                parser = selectedParser.get().newInstance();
                return parser.parse(commandWord, commandArgs);
            } catch (InstantiationException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return new IncorrectCommand(MESSAGE_INTERNAL_ERROR);
            }
        }
    }
}