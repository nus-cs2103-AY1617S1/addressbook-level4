package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;
import seedu.address.commons.core.LogsCenter;
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
import seedu.address.model.task.Name;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;


public class Parser {
	// @@author A0141019U	
	private static final Logger logger = LogsCenter.getLogger(Parser.class);
	
	private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

	private static final Prefix namePrefix = new Prefix("'");
	private static final Prefix startDateTimePrefix = new Prefix("from ");
	private static final Prefix endDateTimePrefix = new Prefix("to ");
	private static final Prefix dlEndDateTimePrefix = new Prefix("by ");
	private static final Prefix datePrefix = new Prefix("on ");
	private static final Prefix tagsPrefix = new Prefix("#");

	private static final Pattern[] EDIT_ARGS_FORMAT = new Pattern[] { 
			Pattern.compile("(?<index>\\d+)(\\s+'(?<taskName>.+)')?(\\s+(?<dateTime1>(from|by|to\\b)\\s+[^']+?))?(\\s+(?<dateTime2>(from|by|to\\b)\\s+[^']+))?"),
			Pattern.compile("(?<index>\\d+)(\\s+(?<dateTime1>(from|by|to\\b)\\s+([^'](?!(from | by | to\\b)))+))?(\\s+'(?<taskName>.+)')?(\\s+(?<dateTime2>(from|by|to\\b)\\s+[^']+))?"),
			Pattern.compile("(?<index>\\d+)(\\s+(?<dateTime1>(from|by|to\\b)\\s+[^']+?))?(\\s+(?<dateTime2>(from|by|to\\b)\\s+[^']+?))?(\\s+'(?<taskName>.+)')?")
	};
 	
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
	
	
	// @@author A0141019U
	private Command prepareAdd(String arguments) {
		if (StringUtil.countOccurrences('\'', arguments) != 2) {
			// TODO better error msg?
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}
		
		Pair<String, String> nameAndArgs = separateNameAndArgs(arguments);
		String taskName = nameAndArgs.getKey();
		String args = nameAndArgs.getValue();

		System.out.println("name: " + taskName);
		System.out.println("args: " + args);
		
		String argsLowerCase = args.toLowerCase();	
		
		if (argsLowerCase.contains(" on ")
				&& argsLowerCase.contains(" from ") 
				&& argsLowerCase.contains(" to ")) {
			logger.log(Level.FINEST, "Calling prepareAddEventSameDay");
			return prepareAddEventSameDay(taskName, "event", args);
		}
		else if (argsLowerCase.contains(" from ")
				&& argsLowerCase.contains(" to ")) {
			logger.log(Level.FINEST, "Calling prepareAddEventDifferentDays");
			return prepareAddEventDifferentDays(taskName, "event", args);
		}
		else if (argsLowerCase.contains(" by ")) {
			logger.log(Level.FINEST, "Calling prepareAddDeadline");
			return prepareAddDeadline(taskName, "deadline", args);
		}
		else if (args.matches("\\s*(#.+)*\\s*")) {
			logger.log(Level.FINEST, "Calling prepareAddSomeday");
			return prepareAddSomeday(taskName, "someday", args);
		}
		else {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}
	}
	
	
	private Pair<String, String> separateNameAndArgs(String arguments) {
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(namePrefix);
		argsTokenizer.tokenize(arguments);
		
		String preamble = argsTokenizer.getPreamble().orElse("");
		
		List<String> stringsAfterQuotes = argsTokenizer.getAllValues(namePrefix).get();
		String taskName = stringsAfterQuotes.get(0);
		String argsAfterName = stringsAfterQuotes.get(1);
		
		return new Pair<>(taskName, " " + preamble + " " + argsAfterName + " ");	
	}
	
	
	private Command prepareAddEventDifferentDays(String taskName, String taskType, String arguments) {
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(startDateTimePrefix, endDateTimePrefix, tagsPrefix);
		argsTokenizer.tokenize(arguments);
		
		String startDateTimeString = argsTokenizer.getValue(startDateTimePrefix).get();
		String endDateTimeString = argsTokenizer.getValue(endDateTimePrefix).get();
		Set<String> tagSet = toSet(argsTokenizer.getAllValues(tagsPrefix));

		return getAddCommand(taskName, taskType, startDateTimeString, endDateTimeString, tagSet);
	}
	
	
	private Command prepareAddEventSameDay(String taskName, String taskType, String arguments) {
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(startDateTimePrefix, endDateTimePrefix, datePrefix, tagsPrefix);
		argsTokenizer.tokenize(arguments);
		
		String dateString = argsTokenizer.getValue(datePrefix).get();
		String startDateTimeString = argsTokenizer.getValue(startDateTimePrefix).get() + " " + dateString;
		String endDateTimeString = argsTokenizer.getValue(endDateTimePrefix).get() + " " + dateString;
		Set<String> tagSet = toSet(argsTokenizer.getAllValues(tagsPrefix));

		return getAddCommand(taskName, taskType, startDateTimeString, endDateTimeString, tagSet);
	}

	
	private Command prepareAddDeadline(String taskName, String taskType, String arguments) {
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(dlEndDateTimePrefix, tagsPrefix);
		argsTokenizer.tokenize(arguments);
		
		String endDateTimeString = argsTokenizer.getValue(dlEndDateTimePrefix).get();
		Set<String> tagSet = toSet(argsTokenizer.getAllValues(tagsPrefix));
		
		return getAddCommand(taskName, taskType, null, endDateTimeString, tagSet);
	}
	
	private Command prepareAddSomeday(String taskName, String taskType, String arguments) {
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(dlEndDateTimePrefix, tagsPrefix);
		argsTokenizer.tokenize(arguments);
		
		Set<String> tagSet = toSet(argsTokenizer.getAllValues(tagsPrefix));
		
		// TODO better approach than using nulls as flag values
		return getAddCommand(taskName, taskType, null, null, tagSet);
	}
	
	
	private Command getAddCommand(String taskName, String taskType, String startDateTimeString, String endDateTimeString, Set<String> tagSet) {
		Optional<LocalDateTime> startDateTimeOpt, endDateTimeOpt;

		try {
			if (startDateTimeString == null) {
				startDateTimeOpt = Optional.empty();
			}
			else {
				startDateTimeOpt = Optional.of(DateParser.parse(startDateTimeString));
			}
			
			if (endDateTimeString == null) {
				endDateTimeOpt = Optional.empty();
			}
			else {
				endDateTimeOpt = Optional.of(DateParser.parse(endDateTimeString));
			}
			
			return new AddCommand(taskName, taskType, startDateTimeOpt, endDateTimeOpt, tagSet);
		} catch (ParseException e) {
			return new IncorrectCommand(e.getMessage());
		} catch (IllegalValueException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
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
	// Only supports task type and status type options.
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
			case "pending":
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
	
	private Set<String> toSet(Optional<List<String>> tagsOptional) {
        List<String> tags = tagsOptional.orElse(Collections.emptyList());
        return new HashSet<>(tags);
    }

	public static void main(String[] args) {
		Parser p = new Parser();
		p.parseCommand("add from 5pm 'dd' to 6pm");
	}
	
}
