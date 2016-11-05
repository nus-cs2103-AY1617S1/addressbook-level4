package seedu.todolist.logic.parser;

import com.google.common.base.Strings;
import com.joestelmach.natty.*;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.commons.util.StringUtil;
import seedu.todolist.logic.commands.*;
import seedu.todolist.model.task.TaskDate;
import seedu.todolist.model.task.TaskTime;

import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todolist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user input.
 */
public class CommandParser {

	/**
	 * Used for initial separation of command word and args.
	 */
	private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

	private static final Pattern KEYWORDS_ARGS_FORMAT =
			Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

	private static final Pattern TASK_DATA_ARGS_FORMAT =
			Pattern.compile("(?<name>(.)+?)?"
					+ "((\\bfrom\\b|\\bby\\b)(?<interval>(.)+?))?"
					+ "((\\bat\\b)(?<location>(.)+?))?" 
					+ "((\\bremarks\\b)(?<remarks>(.)+?))?");

	private static final Pattern EDIT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
			Pattern.compile("(?<name>(.)+?)?"
					+ "((\\bfrom\\b|\\bby\\b)(?<interval>(.)+?))?"
					+ "((\\bat\\b)(?<location>(.)+?))?" 
					+ "((\\bremarks\\b)(?<remarks>(.)+?))?");

	public static final int INTERVAL_COMPONENT_COUNT = 2;
	public static final int INDEX_FROM = 0;
	public static final int INDEX_TO = 1;
	
	public static final int DATETIME_COMPONENT_COUNT = 2; 
    public static final int INDEX_DATE = 0;
    public static final int INDEX_TIME = 1;

	public static final int DETAILED_INTERVAL_COMPONENT_COUNT = 4;
	public static final int INDEX_STARTDATE = 0;
	public static final int INDEX_STARTTIME = 1;
	public static final int INDEX_ENDDATE = 2;
	public static final int INDEX_ENDTIME = 3;

	public static final String INTERVAL_SEPARATOR = " to ";
	
	public static final int NATTY_INDEX_FIRST = 0;
	
	public static final String INDEX_DELIMITER = ",";
	public static final int DEFAULT_INDEX_SIZE = 1;
	public static final int DEFAULT_INDEX_FIRST = 0;

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

		case EditCommand.COMMAND_WORD:
			return prepareEdit(arguments);

		case DoneCommand.COMMAND_WORD:
			return prepareDone(arguments);

		case DeleteCommand.COMMAND_WORD:
			return prepareDelete(arguments);

		case UndoCommand.COMMAND_WORD:
			return prepareUndo(arguments);
			
		case RedoCommand.COMMAND_WORD:
			return prepareRedo(arguments);

		case ClearCommand.COMMAND_WORD:
			return prepareClear(arguments);

		case FindCommand.COMMAND_WORD:
			return prepareFind(arguments);

		case ListCommand.COMMAND_WORD:
			return prepareList(arguments);

		case SetstorageCommand.COMMAND_WORD:
			return prepareSetstorage(arguments);

		case ExitCommand.COMMAND_WORD:
			return prepareExit(arguments);

		case HelpCommand.COMMAND_WORD:
			return prepareHelp(arguments);

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
		final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
		// Validate arg string format
		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}
		try {
			return parseAddCommand(
					matcher.group("name"),
					parseInterval(matcher.group("interval")),
					matcher.group("location"),
					matcher.group("remarks")
					);
		} catch (IllegalValueException ive) {
			return new IncorrectCommand(ive.getMessage());
		}
	}

	private AddCommand parseAddCommand(String name, String[] interval, String location, String remarks) throws IllegalValueException {
	    return new AddCommand(
				name, 
				interval[INDEX_STARTDATE], 
				interval[INDEX_STARTTIME], 
				interval[INDEX_ENDDATE], 
				interval[INDEX_ENDTIME],
				location,
				remarks);
	}

	//@@author A0138601M
	/**
	 * Extracts the new task's start date and time, and end date and time from the add command's interval arguments string.
	 * @return a string array that contains start date, start time, end date and end time.
	 */
	private String[] parseInterval(String interval) {
	    String[] detailedIntervalComponents = new String[DETAILED_INTERVAL_COMPONENT_COUNT];
		if (!Strings.isNullOrEmpty(interval)) {
		    detailedIntervalComponents = parseTimedInterval(interval);
		}
		return detailedIntervalComponents;
	}
	
	/**
	 * Parses the interval that has date and time
	 * @return a string array that contains start date, start time, end date and end time.
	 */
	private String[] parseTimedInterval(String interval) {
	    System.out.println(interval);
        if (interval.contains(INTERVAL_SEPARATOR)) {
            return parseEventInterval(interval);
        } else {
            return parseDeadlineInterval(interval);
        }
	}
	
	/**
     * Parses the interval for event type task
     * @return a string array that contains start date, start time, end date and end time.
     */
	private String[] parseEventInterval(String interval) {
	    String[] intervalComponents = parseDatetime(interval);
        return new String[] {
                intervalComponents[INDEX_STARTDATE], 
                intervalComponents[INDEX_STARTTIME], 
                intervalComponents[INDEX_ENDDATE], 
                intervalComponents[INDEX_ENDTIME]};
    }
	
	/**
     * Parses the interval for deadline type task
     * @return a string array that contains start date, start time, end date and end time.
     */
	private String[] parseDeadlineInterval(String interval) {
	    String[] endDateTime = parseDatetime(interval);
	    return new String[] {
                null, 
                null, 
                endDateTime[INDEX_DATE], 
                endDateTime[INDEX_TIME]};
    }

	/**
	 * Extracts the new task's date and time from the add command's datetime arguments string using Natty.
	 * @return a string array that contains date and time
	 */
	private String[] parseDatetime(String datetime) {
	    ArrayList<String> intervalComponents = new ArrayList<String>();
    
	    Parser nattyParser = new Parser();
        DateGroup group = nattyParser.parse(datetime).get(NATTY_INDEX_FIRST); 
        Calendar currentDateTime = Calendar.getInstance(); //Get current datetime to compare with natty datetime
        
        for (Date date : group.getDates()) {
            intervalComponents.add(parseDate(date));
            intervalComponents.add(parseTime(date, currentDateTime));
            
        }       
        return intervalComponents.toArray(new String[intervalComponents.size()]);
	}
	
	/**
     * Extracts the date from Natty's dategroup
     */
	private String parseDate(Date date) {
	    DateFormat dateFormat = new SimpleDateFormat(TaskDate.DATE_DISPLAY_FORMAT);
	    return dateFormat.format(date);
	}
	
	/**
     * Extracts the time from Natty's dategroup
     */
	private String parseTime(Date date, Calendar currentDateTime) {
	    DateFormat timeFormat = new SimpleDateFormat(TaskTime.TIME_DISPLAY_FORMAT);
	    String parsedTime = timeFormat.format(date);
	    //ignore if it is a time generated by natty
        if (timeFormat.format(currentDateTime.getTime()).equals(parsedTime)) {
            parsedTime = null;
        }
        return parsedTime;
    }

	/**
	 * Parses arguments in the context of the done task command.
	 *
	 * @param args full command args string
	 * @return the prepared command
	 */
	private Command prepareDone(String args) {
		int[] indexes;
		try {
			indexes = parseIndex(args);
		}
		catch (IllegalValueException ive) {
			return new IncorrectCommand(
					String.format(MESSAGE_INVALID_TASK_DISPLAYED_INDEX, DoneCommand.MESSAGE_USAGE));
		}
		return new DoneCommand(indexes);
	}

	//@@author A0146682X
	/**
	 * Parses arguments in the context of the edit task command.
	 *
	 * @param args full command args string
	 * @return the prepared command
	 */
	private Command prepareEdit(String args) {

		Matcher index_matcher = Pattern.compile("\\d+").matcher(args);
		index_matcher.find();

		int index;

		try {
			index = Integer.valueOf(index_matcher.group());
		} catch (IllegalStateException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}

		//split command into its index (content[0]) and the string that follows (content[1])
		String[] content = args.split("\\d+", 2);

		//verify the format of the string command is correct
		final Matcher matcher = EDIT_DATA_ARGS_FORMAT.matcher(content[1]);

		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}

		String name = matcher.group("name");
		String[] interval = parseInterval(matcher.group("interval"));
		String location = matcher.group("location");
		String remarks = matcher.group("remarks");

		try {
			return new EditCommand(index, name, interval[0], interval[1], interval[2], interval[3], location, remarks);
		} catch (IllegalValueException ive) {
			return new IncorrectCommand(ive.getMessage());
		}
	}

	//@@author A0138601M
	/**
	 * Parses arguments in the context of the delete task command.
	 *
	 * @param args full command args string
	 * @return the prepared command
	 */
	private Command prepareDelete(String args) {
		int[] indexes;
		try {
			indexes = parseIndex(args);
		}
		catch (IllegalValueException ive) {
			return new IncorrectCommand(
					String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
		}
		return new DeleteCommand(indexes);
	}
	
	/**
	 * Extract the indexes from a string of command
	 * 
	 * @throws IllegalValueException if indexes are invalid
	 * @return an int array if valid indexes are provided.
	 */
	private int[] parseIndex(String command) throws IllegalValueException {
	    assert command != null;
		int[] indexes = new int[DEFAULT_INDEX_SIZE];
		
		if (command.trim().contains(INDEX_DELIMITER)) {
			indexes =  parseIndexSeparatedByComma(command);
		} else {
			if (!StringUtil.isUnsignedInteger(command.trim())) {
				throw new IllegalValueException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
			}
			indexes[DEFAULT_INDEX_FIRST] = Integer.parseInt(command.trim());
		}
		Arrays.sort(indexes);
		return indexes;
	}

	/**
     * Extract the indexes from a string of command
     * 
     * @param command is guaranteed to contain commas
     * @throws IllegalValueException if indexes are invalid
     * @return an int array if valid indexes are provided.
     */
	private int[] parseIndexSeparatedByComma(String command) throws IllegalValueException {
		command = command.trim();

		String[] indexesString = command.split(INDEX_DELIMITER);
		int[] indexes = new int[indexesString.length];
		for (int i = 0; i < indexesString.length; i++) {
			if (!StringUtil.isUnsignedInteger(indexesString[i].trim())) {
				throw new IllegalValueException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
			}
			indexes[i] = Integer.parseInt(indexesString[i].trim());
		}
		return indexes;
	}

	//@@author A0153736B
	/**
	 * Parses arguments in the context of the undo command.
	 *
	 * @param args full command args string
	 * @return the prepared command
	 */
	private Command prepareUndo(String args) {
		if (!args.trim().isEmpty())
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
					UndoCommand.MESSAGE_USAGE));
		return new UndoCommand();
	}
	
	/**
	 * Parses arguments in the context of the redo command.
	 *
	 * @param args full command args string
	 * @return the prepared command
	 */
	private Command prepareRedo(String args) {
		if (!args.trim().isEmpty())
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
					RedoCommand.MESSAGE_USAGE));
		return new RedoCommand();
	}
	
	/**
	 * Parses arguments in the context of the clear command.
	 *
	 * @param args full command args string
	 * @return the prepared command
	 */
	private Command prepareClear(String args) {
		if (!args.trim().isEmpty())
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
					ClearCommand.MESSAGE_USAGE));
		return new ClearCommand();
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
		String findType = "null";
		if (keywords[0].equals("all") || keywords[0].equals("exactly")) {
			findType = keywords[0];
			keywordSet.remove(keywords[0]);
		}
		return new FindCommand(keywordSet, findType);
	}

	/**
	 * Parses arguments in the context of the list command.
	 *
	 * @param args full command args string
	 * @return the prepared command
	 */
	private Command prepareList(String args) {
	    final String dateFilter = args.trim();	
		return new ListCommand(dateFilter);
	}
	//@@author

	//@@author A0158963M 
	/**
	 * Parses arguments in the context of the setstorage command.
	 *
	 * @param args full command args string
	 * @return the prepared command
	 */
	private Command prepareSetstorage(String args){
		File file = new File(args.trim());    
		if (!file.exists() && !file.isDirectory()){    
			file.mkdirs();
		}  

		return new SetstorageCommand(args.trim());
	}
	//@@author
	
	//@@author A0153736B	
	/**
	 * Parses arguments in the context of the exit command.
	 *
	 * @param args full command args string
	 * @return the prepared command
	 */
	private Command prepareExit(String args) {
		if (!args.trim().isEmpty())
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
					ExitCommand.MESSAGE_USAGE));
		return new ExitCommand();
	}

	/**
	 * Parses arguments in the context of the help command.
	 *
	 * @param args full command args string
	 * @return the prepared command
	 */
	private Command prepareHelp(String args) {
		if (!args.trim().isEmpty())
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
					HelpCommand.MESSAGE_USAGE));
		return new HelpCommand();
	}
}