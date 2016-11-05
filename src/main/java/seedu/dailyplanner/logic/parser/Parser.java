package seedu.dailyplanner.logic.parser;

import static seedu.dailyplanner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.dailyplanner.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.commons.util.StringUtil;
import seedu.dailyplanner.logic.commands.*;
import seedu.dailyplanner.model.task.DateTime;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one
    // or
    // more
    // keywords
    // separated
    // by
    // whitespace

    private static final Pattern PERSON_DATA_ARGS_FORMAT = // '/' forward
	    // slashes are
	    // reserved for
	    // delimiter prefixes
	    Pattern.compile("(?<name>[^/]+)" + " (?<isPhonePrivate>p?)d/(?<date>[^/]+)"
		    + " (?<isEmailPrivate>p?)st/(?<starttime>[^/]+)" + " (?<isAddressPrivate>p?)et/(?<endtime>[^/]+)"
		    + "(?<tagArguments>(?: c/[^/]+)*)"); // variable
							 // number
							 // of
    // tags

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

    private Command prepareUnpin(String arguments) {
	Optional<Integer> index = parseIndex(arguments);
	if (!index.isPresent()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
	}
	return new UnpinCommand(index.get());
    }

    private Command preparePin(String arguments) {
	Optional<Integer> index = parseIndex(arguments);
	if (!index.isPresent()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
	}
	return new PinCommand(index.get());
    }

    private Command prepareComplete(String arguments) {
	Optional<Integer> index = parseIndex(arguments);
	if (!index.isPresent()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
	}
	return new CompleteCommand(index.get());
    }

    // @@author A0139102U

    private Command prepareEdit(String arguments) {

	int index = 0;
	String taskName = null;
	String start = null, end = null;
	DateTime formattedStart = null, formattedEnd = null;
	Set<String> tags = null;

	HashMap<String, String> mapArgs = parseEdit(arguments.trim());

	// If arguments are in hashmap, pass them to addCommand, if not pass
	// them as empty string
	// Change date to "dd/mm/yy/", time to "hh:mm"

	nattyParser natty = new nattyParser();
	DateTimeParser dt = new DateTimeParser();

	if (mapArgs.containsKey("index")) {
	    index = Integer.parseInt(mapArgs.get("index"));
	}
	if (mapArgs.containsKey("taskName")) {
	    taskName = mapArgs.get("taskName");
	}
	if (mapArgs.containsKey("start")) {
	    String startString = mapArgs.get("start");
	    // if start time is given
	    if (startString.contains("am") || startString.contains("pm")) {
		start = natty.parse(startString);
		formattedStart = dt.parseDateAndTime(start);
	    } else {
		start = natty.parseDate(startString);
		formattedStart = dt.parseDate(start);
	    }
	}
	if (mapArgs.containsKey("end")) {
	    String endString = mapArgs.get("end");
	    // if end time is given
	    if (endString.contains("am") || endString.contains("pm")) {
		if (dt.containsDate(endString)) {
		    end = natty.parse(endString);
		} else {
		    if (!mapArgs.containsKey("start")) {
			end = natty.parse("today") + " ";
		    } else {
			end = start.split(" ")[0] + " ";
		    }
		    end += natty.parseTime(endString);
		    formattedStart = dt.parseDateAndTime(end);
		}
	    } else {
		end = natty.parseDate(endString);
		formattedEnd = dt.parseDate(end);
	    }
	}
	if (mapArgs.containsKey("tags")) {
	    String[] tagArray = mapArgs.get("tags").split(" ");
	    tags = new HashSet<String>(Arrays.asList(tagArray));
	}

	try {
	    return new EditCommand(index, taskName, formattedStart, formattedEnd, tags);
	} catch (IllegalValueException ive) {
	    return new IncorrectCommand(ive.getMessage());
	}
    }

    /**
     * Parses arguments in the context of the add person command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */

    // @@author A0140124B
    private Command prepareAdd(String args) {
	String taskName = null;
	String start = null, end = null;
	DateTime formattedStart = null, formattedEnd = null;
	Set<String> tags = null;

	String trimmedArgs = args.trim();

	if (!(isValidAddArgumentFormat(trimmedArgs))) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
	}

	HashMap<String, String> mapArgs = parseAdd(trimmedArgs);

	// If arguments are in hashmap, pass them to addCommand, if not pass
	// them as empty string

	// Change date to "dd/mm/yy/", time to "hh:mm"
	nattyParser natty = new nattyParser();
	DateTimeParser dt = new DateTimeParser();

	if (mapArgs.containsKey("taskName")) {
	    taskName = mapArgs.get("taskName");
	}
	if (mapArgs.containsKey("start")) {
	    String startString = mapArgs.get("start");
	    // if start time is given
	    if (startString.contains("am") || startString.contains("pm")) {
		start = natty.parse(startString);
		formattedStart = dt.parseDateAndTime(start);
	    } else {
		start = natty.parseDate(startString);
		formattedStart = dt.parseDate(start);
	    }
	}
	if (mapArgs.containsKey("end")) {
	    String endString = mapArgs.get("end");
	    // if end time is given
	    if (endString.contains("am") || endString.contains("pm")) {
		if (dt.containsDate(endString)) {
		    end = natty.parse(endString);
		} else {
		    if (!mapArgs.containsKey("start")) {
			end = natty.parse("today") + " ";
		    } else {
			end = start.split(" ")[0] + " ";
		    }
		    end += natty.parseTime(endString);
		    formattedStart = dt.parseDateAndTime(end);
		}
	    } else {
		end = natty.parseDate(endString);
		formattedEnd = dt.parseDate(end);
	    }
	}
	if (mapArgs.containsKey("tags")) {
	    String[] tagArray = mapArgs.get("tags").split(" ");
	    tags = new HashSet<String>(Arrays.asList(tagArray));
	}

	try {

	    return new AddCommand(taskName, formattedStart, formattedEnd, tags);
	} catch (IllegalValueException ive) {
	    return new IncorrectCommand(ive.getMessage());
	}

    }

    private boolean isValidAddArgumentFormat(String trimmedArgs) {
	if (trimmedArgs.charAt(1) == '/') {
	    return false;
	}
	for (int k = 0; k < trimmedArgs.length(); k++) {
	    if (trimmedArgs.charAt(k) == '/') {
		if (!(k + 1 == trimmedArgs.length())) {
		    if (trimmedArgs.charAt(k + 1) == ' ') {
			return false;
		    }
		} else {
		    if (trimmedArgs.charAt(k) == '/')
			return false;
		}

	    }
	}
	return true;
    }

    /**
     * Parses the arguments given by the user in the add command and returns it
     * to prepareAdd in a HashMap with keys taskName, date, startTime, endTime,
     * isRecurring
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

    private HashMap<String, String> parseEdit(String arguments) {

	HashMap<String, String> mapArgs = new HashMap<String, String>();

	// Extract index
	String[] splitArgs1 = arguments.split(" ", 2);
	int indexStringLength = splitArgs1[0].length();
	String index = arguments.substring(0, indexStringLength);
	mapArgs.put("index", index);

	arguments = arguments.substring(indexStringLength + 1);
	String taskName = "";
	if (hasTaskName(arguments)) {
	    taskName = getTaskNameFromArguments(arguments);
	}
	mapArgs.put("taskName", taskName);
	if (hasTaskName(arguments) && arguments.contains("/")) {
	    String[] splitArgs = arguments.substring(taskName.length() + 1).split(" ");
	    argumentArrayToHashMap(mapArgs, splitArgs);
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
		int j = i + 1;
		String arg = splitArgs[i].substring(2);
		while (j < splitArgs.length && !splitArgs[j].contains("/")) {
		    arg += " " + splitArgs[j];
		    j++;
		}
		mapArgs.put("start", arg);
	    }

	    if (splitArgs[i].substring(0, 2).equals("e/")) {
		int j = i + 1;
		String arg = splitArgs[i].substring(2);
		while (j < splitArgs.length && !splitArgs[j].contains("/")) {
		    arg += " " + splitArgs[j];
		    j++;
		}
		mapArgs.put("end", arg);
	    }

	    if (splitArgs[i].substring(0, 2).equals("c/")) {
		int j = i + 1;
		String arg = splitArgs[i].substring(2);
		while (j < splitArgs.length) {
		    arg += " " + splitArgs[j].substring(2);
		    j++;
		}
		i = j;
		mapArgs.put("tags", arg);

	    }
	}
    }

    // @@author A0146749N
    private boolean hasTaskName(String arguments) {
	if (arguments.substring(0, 3).contains("/")) {
	    return false;
	} else {
	    return true;
	}
    }

    private String getTaskNameFromArguments(String arguments) {
	if (arguments.contains("/")) {
	    String[] firstPart = arguments.split("/");
	    return firstPart[0].substring(0, firstPart[0].length() - 2);
	} else {
	    return arguments;
	}
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

	if (args.contains("complete")) {
	    return new DeleteCompletedCommand();
	} else {

	    Optional<Integer> index = parseIndex(args);
	    if (!index.isPresent()) {
		return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
	    }

	    return new DeleteCommand(index.get());
	}
    }

    private Command prepareShow(String args) {
	final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
	if (!matcher.matches()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
	}

	// keywords delimited by whitespace
	final String keyword = matcher.group("keywords");

	String[] keywords = new String[1];

	if (keyword.equals("complete") || keyword.equals("not complete")) {
	    keywords[0] = keyword;
	} else {
	    nattyParser natty = new nattyParser();
	    keywords[0] = natty.parseDate(keyword);
	}
	final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
	return new ShowCommand(keywordSet);
    }

    /**
     * Parses arguments in the context of the select person command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
	Optional<Integer> index = parseIndex(args);
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
	final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(command.trim());
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
    private Command prepareFind(String args) {
	final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
	if (!matcher.matches()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
	}

	// keywords delimited by whitespace
	final String[] keywords = matcher.group("keywords").split("\\s+");
	final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
	return new FindCommand(keywordSet);
    }

}