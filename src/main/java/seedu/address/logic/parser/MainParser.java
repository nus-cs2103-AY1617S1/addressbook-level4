package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Priority;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.exceptions.IllegalValueException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.joestelmach.natty.*;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses user input.
 */
public class MainParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

	private static final String MESSAGE_INVALID_PRIORITY = "Priority is either high, medium or low. Please try again.";
	
	public static final String NO_DATE_DEFAULT = LocalDate.MIN.toString();	// All floating tasks are giving this date.
	public static final String NO_TIME_DEFAULT = LocalTime.MAX.toString();	// All timeless tasks are given this time.
	
	// Singleton
	private static MainParser mainParser;
	private ArrayList<LocalDateTime> datesAndTimes;
	private String reducedArgs;
	
    /**
     * Private constructor
     */
	private MainParser() {
    	init();
    }
    
    public static MainParser getInstance() {
    	if (mainParser == null) {
    		mainParser = new MainParser();
    	} return mainParser;
    }

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
        String arguments = matcher.group("arguments");
        arguments = getCleanString(arguments);
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);
            
        case DoneCommand.COMMAND_WORD:
            return prepareDone(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:        	
        case ListCommand.COMMAND_WORD_SHORT_ALL:
        	return prepareList(arguments);
        case ListCommand.COMMAND_WORD_SHORT_DONE:
            return prepareList("--done");
            
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

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
        String[] splittedArgs = getCleanString(args).split(" ");
        reducedArgs = extractDueByDateAndTime(args);
        LocalDateTime dt;
        if (datesAndTimes.size() != 0)
        	dt = datesAndTimes.get(0);
        else
        	dt = LocalDateTime.of(LocalDate.parse(NO_DATE_DEFAULT, DateTimeFormatter.ISO_LOCAL_DATE), 
        			LocalTime.MAX);
        // For testing purposes
        datesAndTimes.clear();
    	
    	try {
    		return new AddCommand(
    				extractDetail(reducedArgs),
    				dt.toLocalDate(),
    				dt.toLocalTime(),
    				extractPriority(splittedArgs),
    				getTagsFromArgs(splittedArgs));
    	} catch (IllegalValueException ive) {
    		return new IncorrectCommand(ive.getMessage());
    	}
    }
    
    /**
     * Extracts the details out of the reduced args string (without date and time)
     * 
     * @param reducedArgs
     * @return a clean string without redundant words, tags, priority
     * 
     * @author A0139661Y
     */
    private String extractDetail(String reducedArgs) {
		return getCleanString(reducedArgs.replaceAll("\\sby\\s$", " ")
											.replaceAll("\\son\\s$", " ")
											.replaceAll("\\sat\\s$", " ")
											.replaceAll("/[^ ]+", "")
											.replaceAll("-[^ ]+", ""));
    }
    
	/**
     * Extracts the priority out of the args.
     * 
     * @param splittedArgs an array of split user input
     * @return priority level string.
     * 
     * @author A0139661Y
     */ 
    private String extractPriority(String[] splittedArgs) {
    	List<String> rawArgs = Arrays.asList(splittedArgs);
    	for (String rawArg : rawArgs) {
    		if (rawArg.toLowerCase().startsWith("/")) {
    			switch(rawArg.replace("/", "")) {
    			case Priority.HIGH:
    				return Priority.HIGH;
    			case Priority.MEDIUM:
    				return Priority.MEDIUM;
    			case Priority.LOW:
    				return Priority.LOW;
    			}
    		}
    	}
    	return Priority.LOW;
    }
    
    /**
     * Extracts the dueByDate and dueByTime out of the args.
     * 
     * This snippet of code uses natty by Joel Ostenmach and its implementation was inspired by
     * https://github.com/cs2103aug2015-t16-1j/fini
     * 
     * @author A0139661Y
     */
    public String extractDueByDateAndTime(String dirtyArgs) {
    	Parser parser = new Parser();
    	List<DateGroup> groups = parser.parse(dirtyArgs);
    	String cleanArgs = dirtyArgs;
    	
    	try {
    		DateGroup group = groups.get(0);
    		List<Date> dateList = group.getDates(); 	// Extract date
    		Map<String, List<ParseLocation>> parseMap = group.getParseLocations();
    		if ((!parseMap.containsKey("explicit_time") && parseMap.containsKey("relative_date")) || 
    				(!parseMap.containsKey("explicit_time") && parseMap.containsKey("formal_date"))) {
    			for (Date date : dateList) {
    				LocalDateTime temp = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    				datesAndTimes.add(LocalDateTime.of(temp.toLocalDate(), LocalTime.MAX));
    			}
    		} else {
    			for (Date date : dateList) {
    				datesAndTimes.add(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    			}
    		}
    		for (ParseLocation parsedWord : parseMap.get("parse")) {
    			cleanArgs = cleanArgs.substring(0, parsedWord.getStart() - 1) + cleanArgs.substring(parsedWord.getEnd() -1);
    		}
    		return cleanArgs;	// Return a cleaned up string
    	} catch (IndexOutOfBoundsException e) {
    		return dirtyArgs;
    	}
    }
    
    /**
     * Checks for non-essential groups.
     * 
     * @author A0139661Y
     */
    private static String checkEmpty(String argument) {
    	if (argument.isEmpty()) {
    		return "";
    	} return argument;
    }

    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String[] splittedArgs) throws IllegalValueException {
    	List<String> rawArgs = Arrays.asList(splittedArgs);
    	Collection<String> tagStrings = new ArrayList<String>();
    	boolean isEmpty = true;
    	for (String rawArg : rawArgs) {
    		isEmpty = false;
    		if (rawArg.startsWith("-")) {
    			tagStrings.add(rawArg.replace("-", ""));
    		}
    	}
    	if (isEmpty) {
    		return Collections.emptySet();
    	} 
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
     * Parses arguments in the context of the done task command.
     *
     * @param args full command args string
     * @return the prepared command
     * 
     * @author A0141128R
     */
    private Command prepareDone(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(index.get());
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
        boolean taskStatus = false; // we assume the user is searching for undone tasks
    	final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        if (keywordSet.contains("--done")) {
        	taskStatus = true;
        	keywordSet.remove("--done");
        }
        return new FindCommand(keywordSet, taskStatus);
    }
    
    /**
     * Parses arguments in the context of the list task command.
     *
     * @param args full command args string
     * @return the prepared command
     * 
     * @author A0139661Y
     */
    private Command prepareList(String args) {
        boolean taskStatus = false; // we assume the user is searching for undone tasks
        if (args.contains("--done")) {
        	taskStatus = true;
        }
        return new ListCommand(taskStatus);
    }
    
    /**
     * Utility method which replaces all redundant spaces
     * @param args an uncleaan string
     * @return a cleaned up string
     * 
     * @author A0139661Y
     */
    private String getCleanString(String args) {
    	return args.trim().replaceAll("\\s+", " ");
    }
    
    /**
     * Initialize main parser.
     * 
     * Natty is a natural language parser for dates by Joe Stelmach
     * 
     * @author A0139661Y
     */
    private void init() {
    	Parser parser = new Parser();
    	datesAndTimes = new ArrayList<LocalDateTime>();
    }
}