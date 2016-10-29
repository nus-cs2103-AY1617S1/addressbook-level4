package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

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
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ChangeStatusCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;


public class Parser {
	// @@author A0141019U	
	private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

	// Different regexps for different permutations of arguments
	private static final Pattern ADD_COMMAND_FORMAT_1 = Pattern
			.compile("(?i)(?<taskType>event|ev|deadline|dl|someday|sd)(?<addTaskArgs>.*)");
	private static final Pattern ADD_COMMAND_FORMAT_2 = Pattern
			.compile("(?i)(?<addTaskArgs>.*)(?<taskType>event|ev|deadline|dl|someday|sd)");
	
	private static final Pattern EVENT_ARGS_FORMAT_1 = Pattern.compile(
			"(?i)'(?<taskName>.*\\S+.*)'(\\s+on\\s+)?(?<date>\\S+)?\\s+from\\s+(?<startTime>\\S+\\s?\\S+)\\s+to\\s+(?<endTime>\\S+\\s?\\S+)");
	private static final Pattern EVENT_ARGS_FORMAT_2 = Pattern.compile(
			"(?i)'(?<taskName>.*\\S+.*)'\\s+from\\s+(?<startTime>\\S+\\s?\\S+)\\s+to\\s+(?<endTime>\\S+\\s?\\S+)(\\s+on\\s+)?(?<date>\\S+)?");
	private static final Pattern EVENT_ARGS_FORMAT_3 = Pattern.compile(
			"(?i)from\\s+(?<startTime>\\S+\\s?\\S+)\\s+to\\s+(?<endTime>\\S+\\s?\\S+)(\\s+on\\s+)?(?<date>\\S+)?\\s+'(?<taskName>.*\\S+.*)'");
	
	private static final Pattern DEADLINE_ARGS_FORMAT_1 = Pattern
			.compile("(?i)'(?<taskName>.*\\S+.*)'\\s+by\\s+(?<dateTime>.+)");
	private static final Pattern DEADLINE_ARGS_FORMAT_2 = Pattern
			.compile("(?i)by\\s+(?<dateTime>.+)\\s+'(?<taskName>.*\\S+.*)'");

	private static final Pattern SOMEDAY_ARGS_FORMAT = Pattern.compile("'(?<taskName>.*\\S+.*)'");

	
	//@@author A0141019U-reused
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
			
		case FindCommand.COMMAND_WORD:
			return prepareFind(arguments);

		case ListCommand.COMMAND_WORD:
			return prepareList(arguments);

		case DeleteCommand.COMMAND_WORD:
			return prepareDelete(arguments);

		case EditCommand.COMMAND_WORD:
			return prepareEdit(arguments);
			
		case ChangeStatusCommand.COMMAND_WORD_DONE:
			return prepareChangeStatus(arguments, "done");
			
		case ChangeStatusCommand.COMMAND_WORD_PENDING:
			return prepareChangeStatus(arguments, "pending");
		
		case ClearCommand.COMMAND_WORD:
			return new ClearCommand();

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


		// Null values will always be overwritten if the matcher matches.
		String taskName = null;
		LocalDateTime startDateTime = null;
		LocalDateTime endDateTime = null;
		
		boolean isAnyMatch = false;
		
		for (Matcher matcher : matchers) {
			if (matcher.matches()) {
				isAnyMatch = true;

				taskName = matcher.group("taskName").trim();
			
				String startTime = matcher.group("startTime").trim();
				String endTime = matcher.group("endTime").trim();
				
				Optional<String> dateOpt = Optional.ofNullable(matcher.group("date"));
				String date = dateOpt.orElse("");
				
				try {
					System.out.println("start: " + date + " " + startTime);
					System.out.println("end: " + date + " " + endTime);
					
					startDateTime = DateParser.parse(date + " " + startTime);
					endDateTime = DateParser.parse(date + " " + endTime);
				} catch (ParseException e) {
					return new IncorrectCommand(e.getMessage());
				}				

				try {
					return new AddCommand(taskName, startDateTime, endDateTime);
				} catch (IllegalValueException e) {
					return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
				}
			}
		}

		if (!isAnyMatch) {
			System.out.println("no match");
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}

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
					return new IncorrectCommand(e.getMessage());
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
        final String[] keyphrases = args.trim().split("\\s*,\\s*");
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
		String status = null;
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
			case "overdue":
				status = args[i];
				break;
			default:
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
			}
		}

		return new ListCommand(taskType, status);
	}
	
	//@@author A0141019U
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
			indices = parseIndices(arguments);
		} catch (IllegalArgumentException e) {
			return new IncorrectCommand(e.getMessage());
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
		String index = "";
		String newName = "";
		String newStartDate = "";
		String newEndDate = "";
		
		String[] args = arguments.split(" ");
		System.out.println(arguments);
				
		//if the args[0] is not the index, return IncorrectCommand
		if(!StringUtil.isUnsignedInteger(args[0])) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}
		
		index = args[0];

		for(int i=1; i<args.length; i++) {
			System.out.println("args[i]: " + args[i]);
			if(args[i].startsWith("'") && newName.equals("")) {			//only takes the first pair of ' '
				do {
					newName += (args[i] + " ");
				} while(i+1<args.length && !args[i++].endsWith("'"));	//continue adding until the next '
				i--;													//to undo i++ in while loop when while condition fails
				System.out.println("newName: " + newName);
				
			} else if(args[i].equals("from") && newStartDate.equals("")) {	//only takes the first from
				while(++i<args.length &&
						!(args[i].equals("to") ||
						args[i].equals("by") ||
						args[i].equals("from") ||
						args[i].startsWith("'"))) {
					
					newStartDate += (args[i] + " ");
					System.out.println("i is: " + args[i]);
				}
				i--;
				System.out.println("newStartDate: " + newStartDate);
			
			} else if((args[i].equals("to") || args[i].equals("by")) && newEndDate.equals("")) {
				while(++i<args.length &&
						!(args[i].equals("from") ||
						args[i].equals("by") ||
						args[i].equals("to") ||
						args[i].startsWith("'"))) {
					
					newEndDate += (args[i] + " ");
					System.out.println("i is: " + args[i]);
				}
				i--;
				System.out.println("newEndDate: " + newEndDate);
			}
					
		}
		
		// newName while loop might have end at end of array and not '
		newName = newName.trim();
		if(!newName.endsWith("'")) {
			newName = "";
		} else {
			newName = newName.substring(1, newName.length()-1);		//remove the ' '
			newName = newName.trim();
		}
		
		// No values are to be edited
		if(newName.equals("") && newStartDate.equals("") && newEndDate.equals("")) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}
		
		
		System.out.println("newName post trim: " + newName);
		LocalDateTime startDateTime = null;
		LocalDateTime endDateTime = null;
		
		
		if(!newStartDate.equals("")) {
			try {
				startDateTime = DateParser.parse(newStartDate);
			} catch (ParseException e) {
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
			}
		}
		
		if(!newEndDate.equals("")) {
			try {
				endDateTime = DateParser.parse(newEndDate);
			} catch (ParseException e) {
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
			}
		}
		

		try {
			return new EditCommand(Integer.parseInt(index), newName, startDateTime, endDateTime);
		} catch (NumberFormatException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		} catch (IllegalValueException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}
	}

	//@@author A0139339W
	/**
	 * parse the argument based on first occurrence of keyword "not" indices
	 * before not are for tasks to be marked done indices after not are for
	 * tasks to be marked not done missing keyword "not" means all indices are
	 * for tasks to be marked done
	 */
	private Command prepareChangeStatus(String arguments, String newStatus) {
		int[] doneIndices;

		try {
			doneIndices = parseIndices(arguments);
		} catch (IllegalArgumentException e) {
			return new IncorrectCommand(e.getMessage());
		}

		return new ChangeStatusCommand(doneIndices, newStatus);
	}

	//@@author A0141019U
	/**
	 * @return an array of the specified indices in the {@code command} if
	 * positive unsigned integers are given. 
	 * @throws IllegalArgumentException otherwise
	 */
	private int[] parseIndices(String args) throws IllegalArgumentException {
		String[] indexStrings = args.split(" ");
		int[] indices = new int[indexStrings.length];

		for (int i = 0; i < indexStrings.length; i++) {
			String index = indexStrings[i].trim();
			
			if (!StringUtil.isUnsignedInteger(index)) {
				throw new IllegalArgumentException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
			}
			else {
				indices[i] = Integer.parseInt(index);
			}
		}

		return indices;
	}

	public static void main(String[] args) {
		Parser p = new Parser();
		p.parseCommand("find bob, oh my darling, clementine");
	}
	
}
