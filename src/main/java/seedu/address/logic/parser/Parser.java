package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.IncorrectCommandException;
import seedu.address.commons.util.StringUtil;
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
import seedu.address.logic.commands.UndoCommand;


public class Parser {
	// @@author A0141019U
	private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

	// Different regex for different permutations of arguments
	private static final Pattern ADD_COMMAND_FORMAT_1 = Pattern
			.compile("(?i)(?<taskType>event|deadline|someday)(?<addTaskArgs>.*)");
	private static final Pattern ADD_COMMAND_FORMAT_2 = Pattern
			.compile("(?i)(?<addTaskArgs>.*)(?<taskType>event|deadline|someday)");
	
	// Start and end on same day
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
	
	// Start and end on different days
	private static final Pattern EVENT_ARGS_FORMAT_7 = Pattern.compile(
			"(?i)'(?<taskName>(\\s*[^\\s+])+)'\\s+from\\s+(?<startDateTime>.+)\\s+to\\s+(?<endDateTime>.+)");
	private static final Pattern EVENT_ARGS_FORMAT_8 = Pattern.compile(
			"(?i)from\\s+(?<startDateTime>.+)\\s+to\\s+(?<endDateTime>.+)\\s+'(?<taskName>(\\s*[^\\s+])+)'");

	
	private static final Pattern DEADLINE_ARGS_FORMAT_1 = Pattern
			.compile("(?i)'(?<taskName>(\\s*[^\\s+])+)'\\s+by\\s+(?<dateTime>.+)");
	private static final Pattern DEADLINE_ARGS_FORMAT_2 = Pattern
			.compile("(?i)by\\s+(?<dateTime>.+)\\s+'(?<taskName>(\\s*[^\\s+])+)'");
	
	
	private static final Pattern SOMEDAY_ARGS_FORMAT = Pattern.compile("'(?<taskName>(\\s*[^\\s+])+)'");

	private static final Pattern EDIT_ARGS_FORMAT_1 = Pattern.compile("(?<index>\\d)\\s+'(?<newName>(\\s*[^\\s+])+)'");
	
	
	//@@author A0141019U
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

	//@@author A0141019U
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
				
				break;
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
	
	//@@author A0141019U
	private Command prepareAddEvent(String arguments) {
		arguments = arguments.trim();
		
		ArrayList<Matcher> matchers = new ArrayList<>();
		matchers.add(EVENT_ARGS_FORMAT_1.matcher(arguments));
		matchers.add(EVENT_ARGS_FORMAT_2.matcher(arguments));
		matchers.add(EVENT_ARGS_FORMAT_3.matcher(arguments));
		matchers.add(EVENT_ARGS_FORMAT_4.matcher(arguments));
		matchers.add(EVENT_ARGS_FORMAT_5.matcher(arguments));
		matchers.add(EVENT_ARGS_FORMAT_6.matcher(arguments));

		// Null values will always be overwritten if the matcher matches.
		String taskName = null;
		LocalDateTime startDateTime = null;
		LocalDateTime endDateTime = null;
		
		boolean isAnyMatch = false;

		for (Matcher matcher : matchers) {
			if (matcher.matches()) {
				isAnyMatch = true;

				taskName = matcher.group("taskName").trim();
				String date = matcher.group("date").trim();
				String startTime = matcher.group("startTime").trim();
				String endTime = matcher.group("endTime").trim();
				
				try {
					startDateTime = DateUtil.parseDate(date + " " + startTime);
					endDateTime = DateUtil.parseDate(date + " " + endTime);
				} catch (ParseException e) {
					// TODO better command
					return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
				}				

				break;
			}
		}
		
		ArrayList<Matcher> diffDayMatchers = new ArrayList<>();
		diffDayMatchers.add(EVENT_ARGS_FORMAT_7.matcher(arguments));
		diffDayMatchers.add(EVENT_ARGS_FORMAT_8.matcher(arguments));
		
		for (Matcher matcher : diffDayMatchers) {
			if (matcher.matches()) {
				isAnyMatch = true;

				taskName = matcher.group("taskName").trim();
				String startDayAndTime = matcher.group("startDateTime").trim();
				String endDayAndTime = matcher.group("endDateTime").trim();
				
				try {
					startDateTime = DateUtil.parseDate(startDayAndTime);
					endDateTime = DateUtil.parseDate(endDayAndTime);
				} catch (ParseException e) {
					// TODO better command
					return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
				}				

				break;
			}
		}

		System.out.println("task name: " + taskName);
		System.out.println("start date: " + startDateTime.toString());
		System.out.println("end date: " + endDateTime.toString());
		
		if (!isAnyMatch) {
			System.out.println("no match");
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}

		// TODO format date properly
		try {
			return new AddCommand(taskName, startDateTime, endDateTime);
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
					dateTime = DateUtil.parseDate(dateTimeString);
				} catch (ParseException e) {
					// TODO better command
					return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
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
			return new AddCommand(taskName, dateTime);
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

	//@@author A0141019U
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
	
	//@@author A0141019U
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
		Parser p = new Parser();
		p.parseCommand("add event 'eat' from 2012-12-25 00:00 to 2012-12-26 01:00");
	}
	
}
