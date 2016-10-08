package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    
    //one or more keywords separated by whitespace
    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); 

    private static final Pattern TASK_DATA_ARGS_FORMAT_FT = Pattern.compile(
            "(?<name>[^/]+)" + " from (?<fromDateTime>.+)" + " till (?<tillDateTime>[^;]+)" + "(?: ?; ?(?<detail>.+))?");

    private static final Pattern TASK_DATA_ARGS_FORMAT_ON = Pattern
            .compile("(?<name>[^/]+) on (?<fromDateTime>[^;]+)(?: ?; ?(?<detail>.+))?");

    private static final Pattern TASK_DATA_ARGS_FORMAT_BY = Pattern
            .compile("(?<name>[^/]+) by (?<fromDateTime>[^;]+)(?: ?; ?(?<detail>.+))?");

    private static final Pattern TASK_DATA_ARGS_FORMAT_FLOAT = Pattern
            .compile("(?<name>[a-zA-Z_0-9 ]+)(?: ?; ?(?<detail>.+))?");
    
    public Parser() {
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput
     *            full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case MarkCommand.COMMAND_WORD:
            return prepareMark(arguments);

        case SearchCommand.COMMAND_WORD:
            return prepareSearch(arguments);

        case SeeCommand.COMMAND_WORD:
            return prepareSee(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
    	Pattern[] dataPatterns = {TASK_DATA_ARGS_FORMAT_FT, TASK_DATA_ARGS_FORMAT_BY, TASK_DATA_ARGS_FORMAT_ON, 
    	                          TASK_DATA_ARGS_FORMAT_FLOAT};
    	
    	Matcher matcher;
    	try {
        	for (Pattern p : dataPatterns) {
        		matcher = p.matcher(args.trim());
        		if (matcher.matches()) {
        		    if (p.equals(TASK_DATA_ARGS_FORMAT_FT)) { 
        		        return new AddCommand(
        		                matcher.group("name"),
        		                matcher.group("detail"),
                	            matcher.group("fromDateTime"),
                	            matcher.group("tillDateTime")
                	    );
            		} else if (p.equals(TASK_DATA_ARGS_FORMAT_ON)) {
                	    return new AddCommand(
                	            matcher.group("name"),
                	            matcher.group("detail"),
                	            matcher.group("fromDateTime"),
                	            null
                	    );
            	    } else if (p.equals(TASK_DATA_ARGS_FORMAT_BY)) {
            		    return new AddCommand(
            		            matcher.group("name"),
            		            matcher.group("detail"),
            		            null,
            		            matcher.group("tillDateTime")
                	    );
            	    } else {
            	        return new AddCommand(
                                matcher.group("name"),
                                matcher.group("detail"),
                                null,
                                null
                            );
            	    }
                }
        	}
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    	
    	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    /**
     * Extracts the new person's tags from the add command's tag arguments
     * string. Merges duplicate tag strings.
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

    /**
     * Parses arguments in the context of the delete person command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the mark task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareMark(String args) {

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }

        return new MarkCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select person command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareSee(String args) {
        try {
            return new SeeCommand(args);
        } catch (Exception e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SeeCommand.MESSAGE_USAGE));
        }

    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned
     * integer is given as the index. Returns an {@code Optional.empty()}
     * otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find person command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareSearch(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new SearchCommand(keywordSet);
    }

}