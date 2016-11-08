package seedu.dailyplanner.logic.parser;

import static seedu.dailyplanner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.dailyplanner.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.commons.util.ArgumentFormatUtil;
import seedu.dailyplanner.commons.util.DateUtil;
import seedu.dailyplanner.commons.util.StringUtil;
import seedu.dailyplanner.logic.commands.*;
import seedu.dailyplanner.model.task.Date;
import seedu.dailyplanner.model.task.DateTime;
import seedu.dailyplanner.model.task.Time;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    /*
     * One or more keywords separated by whitespace
     */
    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)");

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
	final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput);
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

	case CompleteCommand.COMMAND_WORD:
	    return prepareComplete(arguments);

	case UncompleteCommand.COMMAND_WORD:
	    return prepareUncomplete(arguments);

	case UndoCommand.COMMAND_WORD:
	    return new UndoCommand();

	case PinCommand.COMMAND_WORD:
	    return preparePin(arguments);

	case UnpinCommand.COMMAND_WORD:
	    return prepareUnpin(arguments);

	case ShowCommand.COMMAND_WORD:
	    if (arguments.equals(""))
		return new ShowCommand();
	    else
		return prepareShow(arguments);

	default:
	    return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
	}
    }

    // @@author A0146749N

    /**
     * Parses arguments in the context of the pin task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command preparePin(String arguments) {
	String trimmedArg = arguments.trim();
	Optional<Integer> index = parseIndex(trimmedArg);
	if (!index.isPresent()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE));
	}
	return new PinCommand(index.get());
    }

    /**
     * Parses arguments in the context of the unpin task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareUnpin(String arguments) {
	String trimmedArg = arguments.trim();
	Optional<Integer> index = parseIndex(trimmedArg);
	if (!index.isPresent()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinCommand.MESSAGE_USAGE));
	}
	return new UnpinCommand(index.get());
    }

    /**
     * Parses arguments in the context of the complete task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareComplete(String arguments) {
	String trimmedArg = arguments.trim();
	Optional<Integer> index = parseIndex(trimmedArg);
	if (!index.isPresent()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
	}
	return new CompleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the uncomplete task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareUncomplete(String arguments) {
	String trimmedArg = arguments.trim();
	Optional<Integer> index = parseIndex(trimmedArg);
	if (!index.isPresent()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UncompleteCommand.MESSAGE_USAGE));
	}
	return new UncompleteCommand(index.get());
    }

    // @@author A0139102U
    /**
     * Parses arguments in the context of the edit task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String arguments) {

	int index = 0;
	String taskName = null;
	DateTime formattedStart = null, formattedEnd = null;
	Set<String> categories = new HashSet<String>();

	String trimmedArgs = arguments.trim();

	if (!(ArgumentFormatUtil.isValidEditArgumentFormat(arguments))) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
	}

	HashMap<String, String> mapArgs = parseEdit(trimmedArgs);

	// If arguments are in hashmap, pass them to editCommand, if not pass
	// them as empty string
	// Change date to "dd/mm/yy/", time to "hh:mm"

	nattyParser natty = new nattyParser();

	if (mapArgs.containsKey("index")) {
	    index = Integer.parseInt(mapArgs.get("index"));
	}
	if (mapArgs.containsKey("taskName")) {
	    taskName = mapArgs.get("taskName");
	}
	if (mapArgs.containsKey("start")) {
	    formattedStart = extractStart(mapArgs, natty);
	}
	if (mapArgs.containsKey("end")) {
	    formattedEnd = extractEnd(formattedStart, mapArgs, natty);
	}
	if (mapArgs.containsKey("cats")) {
	    categories = extractCategories(mapArgs);
	}

	try {
	    return new EditCommand(index, taskName, formattedStart, formattedEnd, categories);
	} catch (IllegalValueException ive) {
	    return new IncorrectCommand(ive.getMessage());
	}
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */

    // @@author A0140124B
    private Command prepareAdd(String args) {
	String taskName = "";
	DateTime formattedStart = DateUtil.getEmptyDateTime();
	DateTime formattedEnd = DateUtil.getEmptyDateTime();
	Set<String> cats = new HashSet<String>();

	String trimmedArgs = args.trim();

	if (!(ArgumentFormatUtil.isValidAddArgumentFormat(trimmedArgs))) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
	}

	HashMap<String, String> mapArgs = parseAdd(trimmedArgs);
	nattyParser natty = new nattyParser();

	if (mapArgs.containsKey("taskName")) {
	    taskName = mapArgs.get("taskName");
	}
	if (mapArgs.containsKey("start")) {
	    formattedStart = extractStart(mapArgs, natty);
	}
	if (mapArgs.containsKey("end")) {
	    formattedEnd = extractEnd(formattedStart, mapArgs, natty);
	}
	if (mapArgs.containsKey("cats")) {
	    cats = extractCategories(mapArgs);
	}
	try {
	    return new AddCommand(taskName, formattedStart, formattedEnd, cats);
	} catch (IllegalValueException ive) {
	    return new IncorrectCommand(ive.getMessage());
	}

    }

    private boolean hasEndDate(String endString) {
	return endString.length() >= 7 && !Character.isDigit(endString.charAt(0));
    }

    private DateTime extractStart(HashMap<String, String> mapArgs, nattyParser natty) {
	DateTime formattedStart;
	String startString = mapArgs.get("start");
	// if field is empty, return empty DateTime
	if (startString.equals("")) {
	    formattedStart = DateUtil.getEmptyDateTime();
	}
	// if start time is given
	if (startString.contains("am") || startString.contains("pm")) {
	    String start = natty.parse(startString);
	    formattedStart = DateUtil.getDateTimeFromString(start);
	} else {
	    String start = natty.parseDate(startString);
	    Date startDate = new Date(start);
	    formattedStart = new DateTime(startDate, new Time(""));
	}
	return formattedStart;
    }

    private DateTime extractEnd(DateTime formattedStart, HashMap<String, String> mapArgs, nattyParser natty) {
	DateTime formattedEnd;
	String endString = mapArgs.get("end");
	// if field is empty, return empty DateTime
	if (endString.equals("")) {
	    formattedEnd = DateUtil.getEmptyDateTime();
	}
	// if end time is given
	if (endString.contains("am") || endString.contains("pm")) {
	    // if end date is given
	    if (hasEndDate(endString)) {
		String end = natty.parse(endString);
		formattedEnd = DateUtil.getDateTimeFromString(end);
	    } else {
		Date endDate;
		// if no start date, infer end date as today
		if (!mapArgs.containsKey("start")) {
		    endDate = DateUtil.todayAsDate();
		}
		// if start date present, infer end date as start date
		else {
		    endDate = formattedStart.getDate();
		}
		Time endTime = new Time(natty.parseTime(endString));
		formattedEnd = new DateTime(endDate, endTime);
	    }
	} else {
	    String end = natty.parseDate(endString);
	    Date endDate = new Date(end);
	    formattedEnd = new DateTime(endDate, new Time(""));
	}
	return formattedEnd;
    }

    private Set<String> extractCategories(HashMap<String, String> mapArgs) {
	Set<String> cats;
	if (mapArgs.get("cats").equals("")) {
	    cats = new HashSet<String>();
	}
	String[] catArray = mapArgs.get("cats").split(" ");
	cats = new HashSet<String>(Arrays.asList(catArray));
	return cats;
    }

    /**
     * Parses the arguments given by the user in the add command and returns it
     * to prepareAdd in a HashMap with keys taskName, date, startTime, endTime,
     */

    private HashMap<String, String> parseAdd(String arguments) {
	HashMap<String, String> mapArgs = new HashMap<String, String>();
	String taskName = getTaskNameFromArguments(arguments);
	mapArgs.put("taskName", taskName);
	if (arguments.contains("/")) {
	    String[] splitArgs = arguments.substring(taskName.length() + 1).split(" ");
	    // loop through rest of arguments, add them to hashmap if valid

	    argumentArrayToHashMap(mapArgs, splitArgs);
	}

	return mapArgs;
    }

    /**
     * Parses the arguments given by the user in the add command and returns it
     * to prepareEdit in a HashMap with keys taskName, date, startTime, endTime,
     */
    private HashMap<String, String> parseEdit(String arguments) {

	HashMap<String, String> mapArgs = new HashMap<String, String>();

	// Extract index
	String[] splitArgs1 = arguments.split(" ", 2);
	int indexStringLength = splitArgs1[0].length();
	String index = arguments.substring(0, indexStringLength);
	mapArgs.put("index", index);

	arguments = arguments.substring(indexStringLength + 1);
	if (hasTaskName(arguments)) {
	    String taskName = getTaskNameFromArguments(arguments);
	    mapArgs.put("taskName", taskName);
	    if (arguments.contains("/")) {
		String[] splitArgs = arguments.substring(taskName.length() + 1).split(" ");
		argumentArrayToHashMap(mapArgs, splitArgs);
	    }
	} else if (arguments.contains("/")) {
	    String[] splitArgs = arguments.split(" ");
	    argumentArrayToHashMap(mapArgs, splitArgs);
	}

	return mapArgs;
    }

    /*
     * Loops through arguments, adds them to hashmap if valid
     */

    private void argumentArrayToHashMap(HashMap<String, String> mapArgs, String[] splitArgs) {
	for (int i = 0; i < splitArgs.length; i++) {
	    if (splitArgs[i].substring(0, 2).equals("s/")) {
		extractArgument(mapArgs, splitArgs, i, "start");
	    }

	    if (splitArgs[i].substring(0, 2).equals("e/")) {
		extractArgument(mapArgs, splitArgs, i, "end");
	    }

	    if (splitArgs[i].substring(0, 2).equals("c/")) {
		extractArgument(mapArgs, splitArgs, i, "cats");

	    }
	}
    }

    private void extractArgument(HashMap<String, String> mapArgs, String[] splitArgs, int i, String type) {
	int j = i + 1;
	String arg = splitArgs[i].substring(2);
	while (j < splitArgs.length && !splitArgs[j].contains("/")) {
	    arg += " " + splitArgs[j];
	    j++;
	}
	i = j;
	mapArgs.put(type, arg);
    }

    // @@author A0146749N
    /**
     * Checks if argument given contains a task name
     */
    private boolean hasTaskName(String arguments) {
	String trimmedArgs = arguments.trim();
	// if first parameter is a start, end or category field
	if (trimmedArgs.length() >= 2 && trimmedArgs.charAt(1) == '/') {
	    return false;
	}
	return true;
    }

    /**
     * Extracts the task name from the rest of the arguments
     */
    private String getTaskNameFromArguments(String arguments) {
	if (arguments.contains("/")) {
	    String[] splitArgs = arguments.split("/");
	    return splitArgs[0].substring(0, splitArgs[0].length() - 2);
	} else {
	    return arguments;
	}
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
	String trimmedArgs = args.trim();

	if (trimmedArgs.contains("complete")) {
	    return new DeleteCompletedCommand();
	} else {
	    Optional<Integer> index = parseIndex(trimmedArgs);
	    if (!index.isPresent()) {
		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
	    }
	    return new DeleteCommand(index.get());
	}
    }

    /**
     * Parses arguments in the context of the show tasks command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareShow(String args) {
	final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
	if (!matcher.matches()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
	}

	// keywords delimited by whitespace
	final String keyword = matcher.group("keywords");

	String[] keywords = new String[1];

	// if command is a show by completion status command
	if (keyword.contains("complete")) {
	    if (keyword.contains(ShowCommand.KEYWORD_SHOW_NOT_COMPLETED)) {
		keywords[0] = "not complete";
	    } else {
		keywords[0] = "complete";
	    }
	}
	// command is a show by date command
	else {
	    nattyParser natty = new nattyParser();
	    keywords[0] = natty.parseDate(keyword);
	}
	final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
	return new ShowCommand(keywordSet);
    }

    // @@ author
    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
	String trimmedArg = args.trim();
	Optional<Integer> index = parseIndex(trimmedArg);
	if (!index.isPresent()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
	}
	return new SelectCommand(index.get());
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
     * Parses arguments in the context of the find task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
	String trimmedArg = args.trim();
	final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(trimmedArg);
	if (!matcher.matches()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
	}

	// keywords delimited by whitespace
	final String[] keywords = matcher.group("keywords").split("\\s+");
	final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
	return new FindCommand(keywordSet);
    }

}