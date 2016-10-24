package seedu.taskscheduler.logic.parser;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.logic.commands.*;

import static seedu.taskscheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskscheduler.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    
    public Parser() {}

    //@@author A0148145E
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        try {
            String className = "seedu.taskscheduler.logic.parser."
                    +   commandWord.substring(0,1).toUpperCase() 
                    +   commandWord.substring(1)
                    +   "CommandParser";
                    
            CommandParser commandParser = (CommandParser)Class.forName(className).newInstance();
            return commandParser.prepareCommand(arguments);
            
        } catch (Exception e) {
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        } 

    }
    //@@author 
    
    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }


}