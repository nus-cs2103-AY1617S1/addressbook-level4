package seedu.address.logic.parser;



import seedu.address.logic.commands.*;
import seedu.address.model.task.TaskDate;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;

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

    private static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern FIND_ARGS_WITHOUT_DATE_FORMAT = 
    		Pattern.compile("(?<keywords>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)");
    
    private static final Pattern FIND_ARGS_WITH_DATE_FORMAT = 
    		Pattern.compile("(?<keywords>[^/]+)"
    				+ "((?<startTime>(?: from [^/]+)(?<endTime>(?: to [^/]+)))|"
    				+ "(?<deadline>(?: by [^/]+)))"
    				+ "(?<tagArguments>(?: t/[^/]+)*)");
    
    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern FLOATING_TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    
    private static final Pattern NON_FLOATING_TASK_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<name>[^/]+)"
                    + " (from (?<startdate>[^/ a-zA-Z]+ [^/ 0-9]+ [^/ ]+)"
                    + " to (?<enddate>[^/ a-zA-Z]+ [^/ 0-9]+ [^/ ]+)"
                    + "|by (?<deadline>[^/ a-zA-Z]+ [^/ 0-9]+ [^/ ]+))"
                    + "(?<tagArguments>(?: t/[^ ]+)*)"); // variable number of tags
        
    private static final Pattern BLOCK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("from (?<startdate>[^/ a-zA-Z]+ [^/ 0-9]+ [^/ ]+)"
                    +" to (?<enddate>[^/ a-zA-Z]+ [^/ 0-9]+ [^/ ]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    public Parser() {}

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
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);
            
        case BlockCommand.COMMAND_WORD:
        	return prepareBlock(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);
        
        case ChangeDirectoryCommand.COMMAND_WORD:
        	return new ChangeDirectoryCommand(arguments.trim());

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
            
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();
            
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();          

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    

	/**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        final Matcher matcherNonFloating = NON_FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcherNonFloating.matches()) {
            return prepareAddFloating(args);
        }
        return prepareAddNonFloating(args);
    }
    
    private Command prepareAddFloating(String args) {
        final Matcher matcher = FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFloatingCommand.MESSAGE_USAGE));
        }
        try {
            return new AddFloatingCommand(
                    matcher.group("name"),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    

    private Command prepareAddNonFloating(String args) {
        final Matcher matcher = NON_FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNonFloatingCommand.MESSAGE_USAGE));
        }
        try {
            
            if(matcher.group("deadline") != null) {
                return prepareAddNonFloatingByDate(matcher);
            } else {
                return prepareAddNonFloatingFromDateToDate(matcher);
            }

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private Command prepareAddNonFloatingByDate(Matcher matcher) throws IllegalValueException {
        String endInput = matcher.group("deadline");
        
        return new AddNonFloatingCommand(
                matcher.group("name"),
                getTagsFromArgs(matcher.group("tagArguments")),
                new TaskDate(TaskDate.DATE_NOT_PRESENT),
                new TaskDate(getDateFromString(endInput).getTime())
                );
    }

    private Command prepareAddNonFloatingFromDateToDate(Matcher matcher) throws IllegalValueException {
        String startInput = matcher.group("startdate");
        String endInput = matcher.group("enddate");
        
        return new AddNonFloatingCommand(
                matcher.group("name"),
                getTagsFromArgs(matcher.group("tagArguments")),
                new TaskDate(getDateFromString(startInput).getTime()),
                new TaskDate(getDateFromString(endInput).getTime())
                );
    }
    
    private Command prepareBlock(String args) {
		// TODO Auto-generated method stub
    	Matcher matcher = BLOCK_DATA_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BlockCommand.MESSAGE_USAGE));
        }
        try {
            
            String startInput = matcher.group("startdate");
            String endInput = matcher.group("enddate");
            
            return new BlockCommand(
                    getTagsFromArgs(matcher.group("tagArguments")),
                    new TaskDate(getDateFromString(startInput).getTime()),
                    new TaskDate(getDateFromString(endInput).getTime())
                    );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
	}

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

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }
    
    public static Date getDateFromString(String dateInput) {
        final com.joestelmach.natty.Parser nattyParser = new com.joestelmach.natty.Parser();
        List<DateGroup> dateGroups = nattyParser.parse(dateInput);
        
        return dateGroups.get(0).getDates().get(0);
    }

}