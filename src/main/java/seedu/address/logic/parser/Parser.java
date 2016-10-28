package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.task.Name;


public class Parser {
	// @@author A0141019U
	private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

	// Different regex for different permutations of arguments
	private static final Pattern ADD_COMMAND_FORMAT_1 = Pattern
			.compile("(?i)(?<taskType>event|ev|deadline|dl|someday|sd)(?<addTaskArgs>.*)");
	private static final Pattern ADD_COMMAND_FORMAT_2 = Pattern
			.compile("(?i)(?<addTaskArgs>.*)(?<taskType>event|deadline|someday)");
	
	// Start and end on same day
	private static final Pattern EVENT_ARGS_FORMAT_1 = Pattern.compile(
			"(?i)'(?<taskName>.*\\S+.*)'\\s+(on\\s+)*(?<date>\\S+)\\s+from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)");
	private static final Pattern EVENT_ARGS_FORMAT_2 = Pattern.compile(
			"(?i)'(?<taskName>.*\\S+.*)'\\s+from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)\\s+(on\\s+)*(?<date>\\S+)");
	private static final Pattern EVENT_ARGS_FORMAT_3 = Pattern.compile(
			"(?i)(on\\s+)*(?<date>\\S+)\\s+from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)\\s+'(?<taskName>.*\\S+.*)'");
	private static final Pattern EVENT_ARGS_FORMAT_4 = Pattern.compile(
			"(?i)(on\\s+)*(?<date>\\S+)\\s+'(?<taskName>.*\\S+.*\\S+.+)'\\s+from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)");
	private static final Pattern EVENT_ARGS_FORMAT_5 = Pattern.compile(
			"(?i)from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)\\s+(on\\s+)*(?<date>\\S+)\\s+'(?<taskName>.*\\S+.*)'");
	private static final Pattern EVENT_ARGS_FORMAT_6 = Pattern.compile(
			"(?i)from\\s+(?<startTime>\\S+)\\s+to\\s+(?<endTime>\\S+)\\s+'(?<taskName>.*\\S+.*)'\\s+(on\\s+)*(?<date>\\S+)");
	// Start and end on different days
	private static final Pattern EVENT_ARGS_FORMAT_7 = Pattern.compile(
			"(?i)'(?<taskName>.*\\S+.*)'\\s+from\\s+(?<startDateTime>.+)\\s+to\\s+(?<endDateTime>.+)");
	private static final Pattern EVENT_ARGS_FORMAT_8 = Pattern.compile(
			"(?i)from\\s+(?<startDateTime>.+)\\s+to\\s+(?<endDateTime>.+)\\s+'(?<taskName>.*\\S+.*)'");


	private static final Pattern DEADLINE_ARGS_FORMAT_1 = Pattern
			.compile("(?i)'(?<taskName>.*\\S+.*)'\\s+by\\s+(?<dateTime>.+)");
	private static final Pattern DEADLINE_ARGS_FORMAT_2 = Pattern
			.compile("(?i)by\\s+(?<dateTime>.+)\\s+'(?<taskName>.*\\S+.*)'");


	private static final Pattern SOMEDAY_ARGS_FORMAT = Pattern.compile("'(?<taskName>.*\\S+.*)'");
	
	private static final Pattern[] EDIT_ARGS_FORMAT = new Pattern[] { 
			Pattern.compile("(?<index>\\d+)(\\s+'(?<taskName>.+)')?(\\s+(?<dateTime1>(from|by)\\s+[^']+?))?(\\s+(?<dateTime2>(from|by)\\s+[^']+))?"),
			Pattern.compile("(?<index>\\d+)(\\s+(?<dateTime1>(from|by)\\s+([^'](?!(from | by)))+))?(\\s+'(?<taskName>.+)')?(\\s+(?<dateTime2>(from|by)\\s+[^']+))?"),
			Pattern.compile("(?<index>\\d+)(\\s+(?<dateTime1>(from|by)\\s+[^']+?))?(\\s+(?<dateTime2>(from|by)\\s+[^']+?))?(\\s+'(?<taskName>.+)')?")
	};
	
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

		case FindCommand.COMMAND_WORD:
			return prepareFind(arguments);

		case HelpCommand.COMMAND_WORD:
			return new HelpCommand();

		case ExitCommand.COMMAND_WORD:
			return new ExitCommand();

		case UndoCommand.COMMAND_WORD:
			return new UndoCommand();

		case RedoCommand.COMMAND_WORD:
			return new RedoCommand();

		default:
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}
	}
	
	//@@author A0139339W
	/**
	 * parse the argument based on first occurrence of keyword "not"
	 * indices before not are for tasks to be marked done
	 * indices after not are for tasks to be marked not done
	 * missing keyword "not" means all indices are for tasks to be marked done
	 */
	private Command prepareDone(String arguments) {
		String[] args = arguments.split("not");
		int[] doneIndices = new int[0];
		int[] notDoneIndices = new int[0];
		try {
			if(!args[0].equals("")) {
			    doneIndices = prepareIndexList(args[0]);
			}
			if(args.length > 1) {
				notDoneIndices = prepareIndexList(args[1].trim());
			}
		} catch (IncorrectCommandException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
		}

		return new DoneCommand(doneIndices, notDoneIndices);
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
		case "ev":
			return prepareAddEvent(addTaskArgs);

		case "deadline":
		case "dl":
			return prepareAddDeadline(addTaskArgs);

		case "someday":
		case "sd":
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
		
		int i = -1;
		for (Matcher matcher : matchers) {
			i++;
			if (matcher.matches()) {
				System.out.println("i: " + i);
				
				
				isAnyMatch = true;

				taskName = matcher.group("taskName").trim();
				String date = matcher.group("date").trim();
				String startTime = matcher.group("startTime").trim();
				String endTime = matcher.group("endTime").trim();
				
				try {
					
					System.out.println("start: " + date + " " + startTime);
					System.out.println("end: " + date + " " + endTime);
					
					startDateTime = DateParser.parse(date + " " + startTime);
					endDateTime = DateParser.parse(date + " " + endTime);
					
					System.out.println("startDateTime: " + startDateTime.toString());
				} catch (ParseException e) {
					// TODO better command
					return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
				}				

				try {
					return new AddCommand(taskName, startDateTime, endDateTime);
				} catch (IllegalValueException e) {
					// TODO Auto-generated catch block
					return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
				}
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
					startDateTime = DateParser.parse(startDayAndTime);
					endDateTime = DateParser.parse(endDayAndTime);
				} catch (ParseException e) {
					// TODO better command
					return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
				}				

				break;
			}
		}

		//System.out.println("task name: " + taskName);
		//System.out.println("start date: " + startDateTime.toString());
		//System.out.println("end date: " + endDateTime.toString());
		
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
	
	//@@author A0141019U
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
				System.out.println("dateTimeString: " + dateTimeString);
				try {
					dateTime = DateParser.parse(dateTimeString);
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
		System.out.println("date: " + dateTime.toString());

		// TODO format date properly
		try {
			return new AddCommand(taskName, dateTime);
		} catch (IllegalValueException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}
	}
	
	//@@author A0141019U
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
	/**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        if (args.equals("")) {
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    	
    	// keyphrases delimited by commas
        final String[] keyphrases = args.split(",");
        final Set<String> keyphraseSet = new HashSet<>(Arrays.asList(keyphrases));
        
        System.out.println("keyphrase set: " + keyphraseSet.toString());
        
        return new FindCommand(keyphraseSet);
    }

	//@@author A0141019U
	// Only supports task type and done|not-done options.
	private Command prepareList(String arguments) {
		if (arguments.equals("")) {
			return new ListCommand();
		}

		String[] args = arguments.split(" ");

		String taskType = null;
		String done = null;
		for (int i = 0; i < args.length; i++) {
			switch (args[i].trim()) {
			case "event":
			case "ev":
			case "deadline":
			case "dl":
			case "someday":
			case "sd":
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

		return new ListCommand(taskType, done);
	}
	
	//@@author
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

	//@@author A0139339W
	/**
	 * Parses arguments in the context of the edit task command.
	 * Supports editing of task name, start date and time, end date and time.
	 *
	 * @param args
	 *            full command args string
	 *            at least one of the three values are to be edited
	 * @return the prepared EditCommand
	 */
	private Command prepareEdit(String arguments) {
		String indexString = null;
		Optional<String> newNameString = Optional.empty();
		ArrayList<Optional<String>> newDateTime = new ArrayList<Optional<String>>();
		ArrayList<Matcher> matchers = new ArrayList<Matcher>();
		for(int i = 0; i < EDIT_ARGS_FORMAT.length; i++) {
			matchers.add(EDIT_ARGS_FORMAT[i].matcher(arguments.trim()));
		}
		for(Matcher matcher : matchers) {
			if(matcher.matches()) {
				indexString = matcher.group("index");
				newNameString = Optional.ofNullable(matcher.group("taskName"));
				newDateTime.add(Optional.ofNullable(matcher.group("dateTime1")));
				newDateTime.add(Optional.ofNullable(matcher.group("dateTime2")));
				System.out.println("indexString: " + indexString);
				System.out.println("newName: " + newNameString);
				System.out.println("dateTime1: " + Optional.ofNullable(matcher.group("dateTime1")));
				System.out.println("dateTime2: " + Optional.ofNullable(matcher.group("dateTime2")));
				break;
			}
			
		}
		if(!StringUtil.isUnsignedInteger(indexString)) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}
		int index = Integer.parseInt(indexString);
		Optional<Name> name = Optional.empty();
		if(newNameString.isPresent()) {
			try {
				name = Optional.ofNullable(new Name(newNameString.get()));
			} catch (IllegalValueException e1) {
				return new IncorrectCommand(e1.getMessage());
			}
		}
		
		Optional<LocalDateTime> newStartDate = Optional.empty();
		Optional<LocalDateTime> newEndDate = Optional.empty();
		
		for(int i = 0; i < newDateTime.size(); i++) {
			if(newDateTime.get(i).isPresent()) {
				try{
					String[] newDate = newDateTime.get(i).get().split(" ", 2);
					System.out.println("newDate[0]: " + newDate[0]);
					assert newDate[0].equals("from") || newDate[0].equals("to") 
						|| newDate[0].equals("by");
					switch(newDate[0]) {
					case "from":
						newStartDate = Optional.ofNullable(DateParser.parse(newDate[1]));
						break;
					case "to":
					case "by":
						newEndDate = Optional.ofNullable(DateParser.parse(newDate[1]));
						break;
					}
				} catch(ParseException e) {
					return new IncorrectCommand(e.getMessage());
				}
			}
		}
				
		// No values are to be edited
		if(!name.isPresent() && !newStartDate.isPresent() && !newEndDate.isPresent()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}

		try {
			return new EditCommand(index, name, newStartDate, newEndDate);
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
			System.out.println("parseIndices: " + indexStrings[i].trim());
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
		p.parseCommand("find bob, oh my darling, clementine");
	}
	
}
