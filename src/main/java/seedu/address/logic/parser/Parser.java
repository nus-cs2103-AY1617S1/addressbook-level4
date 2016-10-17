package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Date;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.StartTime;

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
    
    private static final Pattern EDIT_ARGS_FORMAT = 
            Pattern.compile("(?<index>\\d+)"
                    + " (?<property>\\w+)"
                    + " (?<newInfo>.*)");
    
    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern EVENT_TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " (?<isDatePrivate>p?)d/(?<date>[^@]+)"
                    + " (?<isStartTimePrivate>p?)s/(?<start>[^/]+)"
                    + " (?<isEndTimePrivate>p?)e/(?<end>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    
    private static final Pattern DEADLINE_TASK_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<name>[^/]+)"
                    + " (?<isDatePrivate>p?)d/(?<date>[^@]+)"
                    + " (?<isEndTimePrivate>p?)e/(?<end>[^/]+)"
            		);
    
    private static final Pattern FLOATING_TASK_DATA_ARGS_FORMAT = 
            Pattern.compile("(?<name>[^/]+)");



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
        	System.out.println(arguments);
            return prepareAdd(arguments);

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ShowAllCommand.COMMAND_WORD:
            return new ShowAllCommand();
            
        case ShowDoneCommand.COMMAND_WORD:
            return new ShowDoneCommand();
            
        case ShowCommand.COMMAND_WORD:
        	return new ShowCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        
        case DoneCommand.COMMAND_WORD:
        	return prepareDone(arguments);
            
        case UndoneCommand.COMMAND_WORD:
        	return prepareUndone(arguments);
        	
        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
    
//    private Command prepareShow(String args){
//    	if(args.equals("done")) {
//    		return new ShowDoneCommand();
//    	}
//    	
//    	else if(args.equals("all")) {
//    		return new ShowAllCommand();
//    	}
//    	else if (args.equals(null)) {
//    		return new ShowCommand();
//    	}
//    	else
//    		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
//    }
    
    private Command prepareEdit(String args) {
        final Matcher matcher = EDIT_ARGS_FORMAT.matcher(args.trim());
        //final String index = matcher.group("index");
        //final String property = matcher.group("property");
        //final String newInfo = matcher.group("newInfo"); 
        //I dont understand why i cant do this. It an error for parser when i try it
        
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        if (matcher.group("property").toLowerCase().equals("date")) {
            System.out.println("is it valid date?");
            System.out.println(matcher.group("newInfo"));
            if(!Date.isValidDate(matcher.group("newInfo"))) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Date.MESSAGE_DATE_CONSTRAINTS));
            }
        }
        if (matcher.group("property").toLowerCase().equals("starttime")) {
            System.out.println("is it a valid start time?");
            System.out.println(matcher.group("newInfo"));
            if(!StartTime.isValidStartTime(matcher.group("newInfo"))) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartTime.MESSAGE_TIME_CONSTRAINTS));
            }
        }
        if (matcher.group("property").toLowerCase().equals("endtime")) {
            if(!EndTime.isValidEndTime(matcher.group("newInfo"))) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartTime.MESSAGE_TIME_CONSTRAINTS));
            }
        }
        
        return new EditCommand(
                matcher.group("index"),
                matcher.group("property"),
                matcher.group("newInfo")
        );
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        final Matcher event_matcher = EVENT_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher deadline_matcher = DEADLINE_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        final Matcher floating_matcher = FLOATING_TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!event_matcher.matches() && !deadline_matcher.matches() && !floating_matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
        	if(event_matcher.matches()) {
        		System.out.println("Event");
	            return new AddCommand(
	                    event_matcher.group("name"),
	                    event_matcher.group("date"),
	                    event_matcher.group("start"), // start and end are swapped to match ui
	                    event_matcher.group("end"),
	                    getTagsFromArgs(event_matcher.group("tagArguments"))
	            );
        	}else if(deadline_matcher.matches()) {
        		System.out.println("Deadline");
        		return new AddCommand(
	                    deadline_matcher.group("name"),
	                    deadline_matcher.group("date"),
	                    "",
	                    deadline_matcher.group("end"),
	                    new HashSet<>()
	            );
        	}else {
        		System.out.println("Floating");
        		System.out.println(floating_matcher.group("name"));
        		return new AddCommand(
	                    floating_matcher.group("name"),
	                    "", "", "", new HashSet<>()
	            );
        	}
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
     * Parses arguments in the context of the done command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    
    private Command prepareDone(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(index.get());
    }
    
    private Command prepareUndone(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoneCommand.MESSAGE_USAGE));
        }

        return new UndoneCommand(index.get());
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
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
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

}