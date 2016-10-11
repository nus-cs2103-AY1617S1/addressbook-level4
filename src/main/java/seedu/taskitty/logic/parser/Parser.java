package seedu.taskitty.logic.parser;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.commons.util.StringUtil;
import seedu.taskitty.logic.commands.*;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.model.task.TaskDate;
import seedu.taskitty.model.task.TaskTime;

import static seedu.taskitty.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskitty.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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

    private static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern PERSON_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<data>[\\p{Alnum}: ]+)"
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

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

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
            
        case DoneCommand.COMMAND_WORD:
        	return prepareDone(arguments);

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
        final Matcher matcher = PERSON_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            String data = matcher.group("data");
            
            return new AddCommand(
                    extractTaskDetails(data),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Extracts the new person's tags from the add command's tag arguments string.
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
     * Parses arguments in the context of the delete person command.
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
     * Parses arguments in the context of the mark as done command.
     * 
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDone(String args) {
    	
    	Optional<Integer> index = parseIndex(args);
    	if (!index.isPresent()){
    		return new IncorrectCommand(
    				String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    	}
    	
    	return new DoneCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select person command.
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
     * Parses arguments in the context of the find person command.
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
    
    /**
     * Returns an array of String representing 
     * 
     * @param args full command args String
     */
    //Should return an object instead of String[].. but how?
    private String[] extractTaskDetails(String args) {
        ArrayList<String> details = new ArrayList<String>();
        Pattern datePattern = Pattern.compile(TaskDate.DATE_VALIDATION_REGEX_FORMAT);
        Pattern timePattern = Pattern.compile(TaskTime.TIME_VALIDATION_REGEX_FORMAT);
        
        //default name is the entire argument
        int nameLastIndex = args.length();
        
        Matcher dateMatcher = datePattern.matcher(args);
        //Max only have 1 date
        if (dateMatcher.find()) {
            nameLastIndex = Math.min(nameLastIndex, dateMatcher.start());
            details.add(dateMatcher.group());
        }
        
        Matcher timeMatcher = timePattern.matcher(args);
        //Max only have 2 times
        while (timeMatcher.find()) {
            nameLastIndex = Math.min(nameLastIndex, timeMatcher.start());
            details.add(timeMatcher.group());
        }
        
        //Do I need to declare the 0 as START_OF_ALL_STRINGS_IN_THE_WORLD?
        details.add(Task.TASK_COMPONENT_INDEX_NAME, args.substring(0, nameLastIndex));
        
        String[] returnDetails = new String[details.size()];
        details.toArray(returnDetails);
        return returnDetails;
    }
    
    /**
     * Returns the date found from the arguments as a String or null if no date is found
     * 
     * @param args full command args string
     */
    private String findDate(String args) {
        Pattern datePattern = Pattern.compile(TaskDate.DATE_VALIDATION_REGEX_FORMAT);
        Matcher dateMatcher = datePattern.matcher(args);
        
        //Max of 2 dates
        String date = null;
        if (dateMatcher.find()) {
            date = dateMatcher.group();
        }

        return date;
    }
    
    /**
     * Returns the times found from the arguments as a String array
     * The length of the array represents the number of times found
     * 
     * @param args full command args string
     */
    private String[] findTimes(String args) {
        Pattern timePattern = Pattern.compile(TaskTime.TIME_VALIDATION_REGEX_FORMAT);
        Matcher timeMatcher = timePattern.matcher(args);
        
        //Max of 2 dates
        int timesArraySize = 0;
        String firstTime = null;
        if (timeMatcher.find()) {
            timesArraySize++;
            firstTime = timeMatcher.group();
        }
        
        String secondTime = null;
        if (timeMatcher.find()) {
            timesArraySize++;
            secondTime = timeMatcher.group();
        }
        
        String[] times = new String[timesArraySize];
        if (timesArraySize == 1) {
            times[0] = firstTime;
        } else if (timesArraySize == 2){
            times[0] = firstTime;
            times[1] = secondTime;
        }
        
        return times;
    }

}