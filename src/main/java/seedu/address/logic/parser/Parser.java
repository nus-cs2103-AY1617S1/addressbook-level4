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
import seedu.address.logic.commands.AddAliasCommand;
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
import seedu.address.logic.commands.ListAliasCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SetStorageCommand;
import seedu.address.logic.commands.TabCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.alias.ReadOnlyAlias;

public class Parser {
	// @@author A0141019U
	private Model model;
	
	private static final Logger logger = LogsCenter.getLogger(Parser.class);
	
	private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
	//@@author A0139339W
	private static final Pattern EDIT_ARGS_FORMAT = Pattern.compile(
			"(?<index>\\d+)\\s+(?<editTaskArgs>.+)"); 
	private static final Pattern ADD_ALIAS_COMMAND_FORMAT = Pattern
			.compile("\\s*'(?<alias>(\\s*\\S+)+)\\s*'\\s*=\\s*'(?<originalPhrase>(\\s*\\S+)+)\\s*'\\s*");
	//@@author A0143756Y
	private static final Pattern SET_STORAGE_ARGS_FORMAT = Pattern.compile
			("(?<folderFilePath>(\\s*[^\\s+])+)\\s+save-as\\s+(?<fileName>(\\s*[^\\s+])+)");
	
	//@@author A0141019U
	private static final Prefix namePrefix = new Prefix("'");
	private static final Prefix startDateTimePrefix = new Prefix("from ");
	private static final Prefix endDateTimePrefix = new Prefix("to ");
	private static final Prefix dlEndDateTimePrefix = new Prefix("by ");
	private static final Prefix datePrefix = new Prefix("on ");
	private static final Prefix tagsPrefix = new Prefix("#");
	
	public Parser(Model model) {
		this.model = model;
	}
	
	//@@author A0141019U-reused
	public Command parseCommand(String userInput) {
		String replacedInput = replaceAliases(userInput);
		
		final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(replacedInput.trim());
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
			
		case SetStorageCommand.COMMAND_WORD:
			return prepareSetStorage(arguments);	
			
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
		
		case AddAliasCommand.COMMAND_WORD:
			return prepareAddAlias(arguments);
			
		case ListAliasCommand.COMMAND_WORD:
			return new ListAliasCommand();
			
		case TabCommand.COMMAND_WORD:
			return prepareTabCommand(arguments);

		default:
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}
	}

	//@@author A0141019U	
	private String replaceAliases(String userInput) {
		String inputWithNameRemoved = separateNameAndArgs(userInput).getValue();
		System.out.println("userInput before replacing: " + userInput);
		System.out.println("input with name removed: " + inputWithNameRemoved);
		
		List<ReadOnlyAlias> aliasList = this.model.getFilteredAliasList();
		List<String> aliases = new ArrayList<>(); 
		List<String> originals = new ArrayList<>(); 
		
		for (ReadOnlyAlias aliasObj : aliasList) {
			aliases.add(aliasObj.getAlias());
			originals.add(aliasObj.getOriginalPhrase());
		}
		aliases.add("a");
		originals.add("add");
		
		for (int i=0; i<aliases.size(); i++) {
			String alias = aliases.get(i);
			String original = originals.get(i);
			
			System.out.println("alias: " + alias);
			
			// Does not replace arguments in find command or within quotes			
			if (inputWithNameRemoved.contains(alias) 
					&& !inputWithNameRemoved.matches(".*'.*(" + alias + ").*'.*") 
					&& !inputWithNameRemoved.contains("find")) {
				System.out.println("match");
				userInput = userInput.replace(alias, original);
			}
		}
		
		System.out.println("userInput after replacing: " + userInput);
		
		return userInput;
	}
	
	
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
	
	
	/**
	 * 
	 * @param an input command string that may contain 0 or 2 single quotes
	 * @return a Pair of strings. If there are 2 quotes, the first value in the pair is the 
	 * text enclosed by the quotes and the second is a concatenation of the text outside them.
	 * If there are no quotes, the first argument is an empty string and the second is the argument
	 * string that was supplied to the method.
	 */
	private Pair<String, String> separateNameAndArgs(String arguments) {
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(namePrefix);
		argsTokenizer.tokenize(arguments);
		
		String preamble = argsTokenizer.getPreamble().orElse("");
		
		List<String> listEmptyString = new ArrayList<>();
		listEmptyString.add("");
		listEmptyString.add("");
		
		List<String> stringsAfterQuotes = argsTokenizer.getAllValues(namePrefix).orElse(listEmptyString);
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
		Matcher matcher = EDIT_ARGS_FORMAT.matcher(arguments);
		if(!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}
		
		int index = Integer.parseInt(matcher.group("index"));
		String editTaskArgs = matcher.group("editTaskArgs");
		
		Optional<String> taskName;
		String args;
		if(editTaskArgs.contains("\'")) {
			Pair<String,String> nameAndArgs = separateNameAndArgs(editTaskArgs);
			taskName = Optional.of(nameAndArgs.getKey());
			args = nameAndArgs.getValue();
		} else {
			taskName = Optional.empty();
			args = editTaskArgs;
		}
		String argsLowerCase = args.toLowerCase();
		
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(
				startDateTimePrefix, endDateTimePrefix, dlEndDateTimePrefix, tagsPrefix);
		
		argsTokenizer.tokenize(argsLowerCase);
		Optional<String> startDateTimeString = argsTokenizer.getValue(startDateTimePrefix);
		Optional<String> endDateTimeString = argsTokenizer.getValue(endDateTimePrefix);
		if(!endDateTimeString.isPresent()) {
			endDateTimeString = argsTokenizer.getValue(dlEndDateTimePrefix);
		}
		//TODO
		Optional<List<String>> tagSet = argsTokenizer.getAllValues(tagsPrefix);
		
		boolean isRemoveStartDateTime = isToRemoveDateTime(startDateTimeString);
		boolean isRemoveEndDateTime = isToRemoveDateTime(endDateTimeString);
		
		Optional<LocalDateTime> startDateTime;
		Optional<LocalDateTime> endDateTime;
		
		try {
			startDateTime = isRemoveStartDateTime ? Optional.empty() : 
				convertToLocalDateTime(startDateTimeString);
			endDateTime = isRemoveEndDateTime ? Optional.empty() : 
				convertToLocalDateTime(endDateTimeString);
		} catch (ParseException e) {
			return new IncorrectCommand(e.getMessage());
		}
		
		try {
			return new EditCommand(index, taskName, startDateTime, endDateTime,
					isRemoveStartDateTime, isRemoveEndDateTime);
		} catch (IllegalValueException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}
	}
	
	//@@author A0143756Y
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

	private Optional<LocalDateTime> convertToLocalDateTime(Optional<String> dateTimeString) 
		throws ParseException{
		Optional<LocalDateTime> dateTime = Optional.empty();
		if(dateTimeString.isPresent()) {
			dateTime = Optional.of(DateParser.parse(dateTimeString.get()));
		} 
		return dateTime;
	}
	
	private boolean isToRemoveDateTime (Optional<String> dateTimeString) {
		if(dateTimeString.isPresent()) {
			if(dateTimeString.get().equals("-")) {
				return true;
			}
		}
		return false;
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
	
	//@@author A0143756Y
	/**
     * Parses arguments in the context of the set alias task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAddAlias(String arguments) {
        final Matcher matcher = ADD_ALIAS_COMMAND_FORMAT.matcher(arguments.trim());
    	
    	if(!matcher.matches()){	
        	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAliasCommand.MESSAGE_USAGE));
        }
    	
    	final String alias = matcher.group("alias").trim();
    	final String originalPhrase = matcher.group("originalPhrase").trim();
        
        return new AddAliasCommand(alias, originalPhrase);
    }
    
    
	//@@author A0141019U
    /**
     * @return a TabCommand with argument corresponding to the name 
     * of the tab to switch to.
     * @throws IllegalArgumentException for inputs other than
     * today, tomorrow, week, month, someday (case-insensitive)
     */
    private Command prepareTabCommand(String arguments) {
    	switch (arguments.trim().toLowerCase()) {
    	case "today":
    		return new TabCommand(TabCommand.TabName.TODAY);
    	case "tomorrow":
    		return new TabCommand(TabCommand.TabName.TOMORROW);
    	case "week":
    		return new TabCommand(TabCommand.TabName.WEEK);
    	case "month":
    		return new TabCommand(TabCommand.TabName.MONTH);
    	case "someday":
    		return new TabCommand(TabCommand.TabName.SOMEDAY);
    	default: 
    		return new IncorrectCommand("Invalid tab name input.");
    	}
    }
    
    
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
		Parser p = new Parser(new ModelManager());
//		p.parseCommand("add 'dd' by 5pm today");
		p.replaceAliases("bloh 'a' k");
	}
}
