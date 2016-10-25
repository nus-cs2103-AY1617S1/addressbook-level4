package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBException;

import com.joestelmach.natty.DateGroup;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.IncorrectCommandException;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SetStorageCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.TaskManager;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskType;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.storage.XmlSerializableTaskManager;

public class Parser {

	private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

	// Different regexes for different permutations of arguments
	private static final Pattern ADD_COMMAND_FORMAT_1 = Pattern
			.compile("(?i)(?<taskType>event|deadline|someday)(?<addTaskArgs>.*)");
	private static final Pattern ADD_COMMAND_FORMAT_2 = Pattern
			.compile("(?i)(?<addTaskArgs>.*)(?<taskType>event|deadline|someday)");

	private static final Pattern EVENT_ARGS_FORMAT_1 = Pattern.compile(
			"(?i)'(?<taskName>(\\s*[^\\s+])+)'\\s+on\\s+(?<date>\\S+)\\s+from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)");
	private static final Pattern EVENT_ARGS_FORMAT_2 = Pattern.compile(
			"(?i)'(?<taskName>(\\s*[^\\s+])+)'\\s+from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)\\s+on\\s+(?<date>\\S+)");
	private static final Pattern EVENT_ARGS_FORMAT_3 = Pattern.compile(
			"(?i)on\\s+(?<date>\\S+)\\s+from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)\\s+'(?<taskName>(\\s*[^\\s+])+)'");
	private static final Pattern EVENT_ARGS_FORMAT_4 = Pattern.compile(
			"(?i)on\\s+(?<date>\\S+)\\s+'(?<taskName>(\\s*[^\\s+])+)'\\s+from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)");
	private static final Pattern EVENT_ARGS_FORMAT_5 = Pattern.compile(
			"(?i)from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)\\s+on\\s+(?<date>\\S+)\\s+'(?<taskName>(\\s*[^\\s+])+)'");
	private static final Pattern EVENT_ARGS_FORMAT_6 = Pattern.compile(
			"(?i)from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)\\s+'(?<taskName>(\\s*[^\\s+])+)'\\s+on\\s+(?<date>\\S+)");

	private static final Pattern DEADLINE_ARGS_FORMAT_1 = Pattern
			.compile("(?i)'(?<taskName>(\\s*[^\\s+])+)'\\s+by\\s+(?<dateTime>.+)");
	private static final Pattern DEADLINE_ARGS_FORMAT_2 = Pattern
			.compile("(?i)by\\s+(?<dateTime>.+)\\s+'(?<taskName>(\\s*[^\\s+])+)'");

	private static final Pattern SOMEDAY_ARGS_FORMAT = Pattern.compile("'(?<taskName>(\\s*[^\\s+])+)'");

	private static final Pattern EDIT_ARGS_FORMAT_1 = Pattern.compile("(?<index>\\d)\\s+'(?<newName>(\\s*[^\\s+])+)'");
	
	private static final Pattern SET_STORAGE_ARGS_FORMAT = Pattern.compile
			("(?<folderFilePath>(\\s*[^\\s+])+)\\s+save-as\\s+(?<fileName>(\\s*[^\\s+])+)");

	private com.joestelmach.natty.Parser nattyParser;

	public Parser() {
		nattyParser = new com.joestelmach.natty.Parser();
	}

	public Command parseCommand(String userInput) {
		final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
		}

		final String commandWord = matcher.group("commandWord").trim();
		final String arguments = matcher.group("arguments").trim();

		System.out.println("command: " + commandWord);
		System.out.println("arguments: " + arguments);

		switch (commandWord) {
		case AddCommand.COMMAND_WORD:
			return prepareAdd(arguments);

		case ListCommand.COMMAND_WORD:
			return prepareList(arguments);

		case DeleteCommand.COMMAND_WORD:
			return prepareDelete(arguments);

		case EditCommand.COMMAND_WORD:
			return prepareEdit(arguments);
			
		case SetStorageCommand.COMMAND_WORD:
			return prepareSetStorage(arguments);	
			
		case DoneCommand.COMMAND_WORD:
			return prepareDone(arguments);
		
		case ClearCommand.COMMAND_WORD:
			return new ClearCommand();

		// case FindCommand.COMMAND_WORD:
		// return prepareFind(arguments);

		case HelpCommand.COMMAND_WORD:
			return new HelpCommand();

		case ExitCommand.COMMAND_WORD:
			return new ExitCommand();

		// case HelpCommand.COMMAND_WORD:
		// return new HelpCommand();

		case UndoCommand.COMMAND_WORD:
			return new UndoCommand();

		case RedoCommand.COMMAND_WORD:
			return new RedoCommand();

		default:
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}
	}

	private Command prepareDone(String arguments) {
		int[] indices;
		try {
			indices = prepareIndexList(arguments);
		} catch (IncorrectCommandException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
		}

		return new DoneCommand(indices);
	}


	private int[] prepareIndexList(String arguments) throws IncorrectCommandException{
		ArrayList<Optional<Integer>> indexOptionals = parseIndices(arguments);

		int[] indices = new int[indexOptionals.size()];
		int i = 0;
		for (Optional<Integer> index : indexOptionals) {
			if (!index.isPresent()) {
				throw new IncorrectCommandException("Incorrect Command");
			}
			indices[i] = index.get();
			i++;
		}

		System.out.println("indices: " + Arrays.toString(indices));
		return indices;
	}


	private Command prepareAdd(String arguments) {
		ArrayList<Matcher> matchers = new ArrayList<>();
		matchers.add(ADD_COMMAND_FORMAT_1.matcher(arguments.trim()));
		matchers.add(ADD_COMMAND_FORMAT_2.matcher(arguments.trim()));

		// Null values will always be overwritten if the matcher matches.
		String taskType = null;
		String addTaskArgs = null;

		boolean isAnyMatch = false;

		for (Matcher matcher : matchers) {
			if (matcher.matches()) {
				isAnyMatch = true;
				taskType = matcher.group("taskType").trim();
				addTaskArgs = matcher.group("addTaskArgs").trim();
			}
		}

		if (!isAnyMatch) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}

		System.out.println("task type: " + taskType);
		System.out.println("add args: " + addTaskArgs);

		switch (taskType.toLowerCase()) {
		// TODO change hardcoded strings to references to strings in command
		// classes
		case "event":
			return prepareAddEvent(addTaskArgs);

		case "deadline":
			return prepareAddDeadline(addTaskArgs);

		case "someday":
			return prepareAddSomeday(addTaskArgs);

		default:
			// TODO better error message
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}
	}

	private Command prepareAddEvent(String arguments) {
		ArrayList<Matcher> matchers = new ArrayList<>();
		matchers.add(EVENT_ARGS_FORMAT_1.matcher(arguments.trim()));
		matchers.add(EVENT_ARGS_FORMAT_2.matcher(arguments.trim()));
		matchers.add(EVENT_ARGS_FORMAT_3.matcher(arguments.trim()));
		matchers.add(EVENT_ARGS_FORMAT_4.matcher(arguments.trim()));
		matchers.add(EVENT_ARGS_FORMAT_5.matcher(arguments.trim()));
		matchers.add(EVENT_ARGS_FORMAT_6.matcher(arguments.trim()));

		// Null values will always be overwritten if the matcher matches.
		String taskName = null;
		String date = null;
		String startTime = null;
		String endTime = null;

		boolean isAnyMatch = false;

		for (Matcher matcher : matchers) {
			if (matcher.matches()) {
				isAnyMatch = true;

				taskName = matcher.group("taskName").trim();
				date = matcher.group("date").trim();
				startTime = matcher.group("startTime").trim();
				endTime = matcher.group("endTime").trim();

				break;
			}
		}

		if (!isAnyMatch) {
			System.out.println("no match");
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}

		System.out.println("task name: " + taskName);
		System.out.println("date: " + date);
		System.out.println("start time: " + startTime);
		System.out.println("end time: " + endTime);

		// TODO format date properly
		try {
			return new AddCommand(taskName, date + startTime, date + endTime);
		} catch (IllegalValueException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}
	}

	private Command prepareAddDeadline(String arguments) {
		ArrayList<Matcher> matchers = new ArrayList<>();
		matchers.add(DEADLINE_ARGS_FORMAT_1.matcher(arguments.trim()));
		matchers.add(DEADLINE_ARGS_FORMAT_2.matcher(arguments.trim()));

		// Null values will always be overwritten if the matcher matches.
		String taskName = null;
		LocalDateTime dateTime = null;

		boolean isAnyMatch = false;

		for (Matcher matcher : matchers) {
			if (matcher.matches()) {
				isAnyMatch = true;

				taskName = matcher.group("taskName").trim();
				String dateTimeString = matcher.group("dateTime").trim();
				try {
					dateTime = parseDate(dateTimeString);
				} catch (ParseException e) {
					// TODO better command
					return new IncorrectCommand(
							String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
				}

				break;
			}
		}

		if (!isAnyMatch) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}

		System.out.println("task name: " + taskName);
		System.out.println("date: " + dateTime);

		// TODO format date properly
		try {
			return new AddCommand(taskName, dateTime.toString());
		} catch (IllegalValueException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}
	}

	private Command prepareAddSomeday(String arguments) {
		final Matcher matcher = SOMEDAY_ARGS_FORMAT.matcher(arguments.trim());
		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}

		final String taskName = matcher.group("taskName").trim();
		System.out.println("task name: " + taskName);

		// TODO format date properly
		try {
			return new AddCommand(taskName);
		} catch (IllegalValueException e) {
			return new IncorrectCommand(e.getMessage());
		}
	}

	/**
	 * Uses Natty to parse the date/time contained in the input string. If no
	 * time is specified, time at instant of parsing is taken.
	 * 
	 * @param string
	 *            that contains date/time to be parsed
	 * @return LocalDateTime
	 * @throws ParseException
	 *             if 0, or more than one date is found
	 */
	private LocalDateTime parseDate(String string) throws ParseException {
		List<DateGroup> groups = nattyParser.parse(string);
		List<Date> dates = groups.get(0).getDates();

		if (groups.size() > 1 || groups.size() == 0 || dates.size() > 1 || dates.size() == 0) {
			System.out.println("parsing error");
			// TODO better err msg
			throw new ParseException(
					"Error while parsing date and time. Trying entering the date in yyyy-mm-dd instead", 0);
		}

		Date input = dates.get(0);
		Instant instant = input.toInstant();
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

	// Only supports task type and done|not-done options.
	private Command prepareList(String arguments) {
		if (arguments.equals("")) {
			return new ListCommand();
		}

		String[] args = arguments.split(" ");

		System.out.println(Arrays.toString(args));

		String taskType = null;
		String done = null;
		for (int i = 0; i < args.length; i++) {
			switch (args[i].trim()) {
			case "event":
			case "deadline":
			case "someday":
				taskType = args[i];
				break;
			case "done":
			case "not-done":
				done = args[i];
				break;
			default:
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
			}
		}

		System.out.println("task type: " + taskType);
		System.out.println("done: " + done);

		// TODO return new listcommand(taskType, done)
		// Since both taskType and done may be supplied as the only parameter to
		// the listcommand constructor,
		// the listcommand constructor must make null checks. Alternatively, the
		// parameters can be encapsulated in an object
		// and the constructor overloaded.
		return new ListCommand();
	}

	/**
	 * Parses arguments in the context of the delete task command.
	 *
	 * @param args
	 *            full command args string
	 * @return the prepared command
	 */
	private Command prepareDelete(String arguments) {
		int[] indices;
		try {
			indices = prepareIndexList(arguments);
		} catch (IncorrectCommandException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
		}
		return new DeleteCommand(indices);
	}

	private Command prepareEdit(String arguments) {
		final Matcher matcher = EDIT_ARGS_FORMAT_1.matcher(arguments.trim());
		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}

		final String index = matcher.group("index").trim();
		final String newName = matcher.group("newName").trim();

		System.out.println("index: " + index);
		System.out.println("new name: " + newName);

		try {
			return new EditCommand(Integer.parseInt(index), newName);
		} catch (NumberFormatException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		} catch (IllegalValueException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}
	}
	
	private Command prepareSetStorage(String arguments){
		final Matcher matcher = SET_STORAGE_ARGS_FORMAT.matcher(arguments.trim());
		
		if(!matcher.matches()){
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetStorageCommand.MESSAGE_USAGE));
		}
		
		final String folderFilePath = matcher.group("folderFilePath").trim();
		final String fileName = matcher.group("fileName").trim();
		
		System.out.println("Folder File Path: " + folderFilePath);
		System.out.println("File Name: " + fileName);
		
		return new SetStorageCommand(folderFilePath, fileName);
	}

	/**
	 * Returns an ArrayList of the specified indices in the {@code command} IF
	 * positive unsigned integers are given. Returns an ArrayList with a single
	 * element {@code Optional.empty()} otherwise.
	 */
	private ArrayList<Optional<Integer>> parseIndices(String args) {
		String[] indexStrings = args.split(" ");
		ArrayList<Optional<Integer>> optionals = new ArrayList<>();

		for (int i = 0; i < indexStrings.length; i++) {
			if (!StringUtil.isUnsignedInteger(indexStrings[i].trim())) {
				optionals = new ArrayList<>();
				optionals.add(Optional.empty());
				return optionals;
			}

			optionals.add(Optional.of(Integer.parseInt(indexStrings[i])));
		}

		return optionals;
	}

	public static void main(String[] args) {
//		Parser p = new Parser();
//		p.parseCommand("add deadline 'eat' by 2012-12-25");
	}
	
}
