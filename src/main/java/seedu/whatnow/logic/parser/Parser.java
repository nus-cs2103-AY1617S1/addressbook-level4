package seedu.whatnow.logic.parser;

import static seedu.whatnow.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.whatnow.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.whatnow.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.commons.util.StringUtil;
import seedu.whatnow.logic.commands.*;

/**
 * Parses user input.
 */
public class Parser {

	/**
	 * Used for initial separation of command word and args.
	 */
	private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

	private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

	private static final Pattern KEYWORDS_ARGS_FORMAT =
			Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

	private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
			Pattern.compile("(?<name>[^/]+)"
					+ "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags


	private static final Pattern TASK_MODIFIED_WITH_DATE_ARGS_FORMAT =				//This arguments is for e.g. add task on today, add task on 18/10/2016
			Pattern.compile("(?<name>[^/]+)\\s" + "(.*?\\bon|by\\b.*?\\s)??" +
					"(?<dateArguments>([0-3]??[0-9][//][0-1]??[0-9][//][0-9]{4})??)" + "(?<tagArguments>(?: t/[^/]+)*)");	

	
//	private static final Pattern TEMP2 = 
//			Pattern.compile("(?<name>[^/]+)\\s" +".*?\\bon|by\\b.*?" +
//					" (?<date>[^;]+");
	private static final Pattern TEMP3 = 
			Pattern.compile("(?<name>[^/]+)\\s" +"(.*?\\bon|by\\b.*?\\s)??" +
					"(?<dateArguments>([0-3]??[0-9][//][0-1]??[0-9][//][0-9]{4})??)" + "(?<tagArguments>(?: t/[^/]+)*)");
				
	private static final int TASK_TYPE = 0;
	private static final int INDEX = 1;
	private static final int ARG_TYPE = 2;
	private static final int ARG = 3;
	private static final int LIST_ARG = 0;


	public Parser() {}

	/**
	 * Parses user input into command for execution.
	 *
	 * @param userInput full user input string
	 * @return the command based on the user input
	 * @throws ParseException 
	 */
	public Command parseCommand(String userInput) throws ParseException {
		//	System.out.println("User input is :" + userInput);
		final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
		}
		final String commandWord = matcher.group("commandWord");
		final String arguments = matcher.group("arguments");

		switch (commandWord) {
		case AddCommand.COMMAND_WORD:
			return prepareAdd(arguments);
		
		case SelectCommand.COMMAND_WORD:
			return prepareSelect(arguments);

		case DeleteCommand.COMMAND_WORD:
			return prepareDelete(arguments);

		case ClearCommand.COMMAND_WORD:
			return new ClearCommand();

		case FindCommand.COMMAND_WORD:
			return prepareFind(arguments);

		case ListCommand.COMMAND_WORD:
			  return prepareList(arguments);

		case ExitCommand.COMMAND_WORD:
			return new ExitCommand();

		case HelpCommand.COMMAND_WORD:
			return new HelpCommand();

		case UpdateCommand.COMMAND_WORD:
			return prepareUpdate(arguments);
		
		case ChangeCommand.COMMAND_WORD:
			return prepareChange(arguments);
		
		case MarkDoneCommand.COMMAND_WORD:
			return prepareMarkDone(arguments);
		
		case UndoCommand.COMMAND_WORD:
			return new UndoCommand();
			
		case RedoCommand.COMMAND_WORD:
			return new RedoCommand();
			
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
		System.out.println("Entered prepareAdd");
//		if(TEMP3.matcher(args).find()) {
//			System.out.println("matches");
//		}
		final Matcher matcher = TASK_MODIFIED_WITH_DATE_ARGS_FORMAT.matcher(args.trim());
		// Validate arg string format
		if (!TASK_DATA_ARGS_FORMAT.matcher(args).find() && !TASK_MODIFIED_WITH_DATE_ARGS_FORMAT.matcher(args).find()){
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}
		
		String[] arguments = args.split("\"");
		
		for (int i = 0; i < arguments.length; i++) {
			System.out.print(arguments[i] + " " + i + " ");
		}
		
		// E.g. add "Buy Milk"
		if (arguments.length == 2) {
			try {
				return new AddCommand(arguments[1], Collections.emptySet());
			} catch (IllegalValueException ive) {
				return new IncorrectCommand(ive.getMessage());
			}
		}
		
		String[] additionalArgs = null;
		
		if (arguments.length > 2) {
			additionalArgs = arguments[arguments.length - 1].split(" ");
		}
		
		System.out.println();
		
		for (int i = 0; i < additionalArgs.length; i++) {
			System.out.print(additionalArgs[i] + " " + i + " ");
		}
		
		if (additionalArgs[1].equals("on") || additionalArgs[1].equals("by")) {
			try {
				return new AddCommand(arguments[1], additionalArgs[2], Collections.emptySet());
			} catch (IllegalValueException ive) {
				return new IncorrectCommand(ive.getMessage());
			} catch (ParseException ive) {
				return new IncorrectCommand(ive.getMessage());
			}
		}
		
		try {
			return new AddCommand(
					matcher.group("name"),
					getTagsFromArgs(matcher.group("tagArguments"))
					);
		} catch (IllegalValueException ive) {
			return new IncorrectCommand(ive.getMessage());
		}
	}
	
	/**
	 * Extracts the new task's tags from the add command's tag arguments string.
	 * Merges duplicate tag strings.
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
     * Parses arguments in the context of the change data file location command.
    /**
     * Parses arguments in the context of the list command.
     * *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareChange(String args) {
        String[] argComponents= args.trim().split(" ");
        if(argComponents[0].equals("location") && argComponents[1].equals("to")){
            return new ChangeCommand(argComponents[2]);
        }
        else{
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE));
        }
    }
    
    private Command prepareList(String args) {
        String[] argComponents= args.trim().split(" ");
        String listArg = argComponents[LIST_ARG];
        System.out.println(listArg);
        if (!isListCommandValid(listArg)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        return new ListCommand(listArg);
    }
    
    private boolean isListCommandValid(String listArg) {
        return listArg.equals("done") || listArg.equals("") || listArg.equals("all");
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
     * Parses arguments in the context of the update task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareUpdate(String args) {
        String[] argComponents= args.trim().split(" ");
        String type = argComponents[TASK_TYPE];
        String argType = argComponents[ARG_TYPE];
        String arg = "";
        Optional<Integer> index = parseIndex(argComponents[INDEX]);
        for (int i = ARG; i < argComponents.length; i++) {
            arg += argComponents[i] + " ";
        }
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }
        
        if (!isValidUpdateCommandFormat(type, index.get(), argType)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }
        
        try {
            return new UpdateCommand(
                type,
                index.get(),
                argType,
                arg);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /**
     * Parses arguments in the context of the markDone task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareMarkDone(String args) {
        String[] argComponents = args.trim().split(" ");
        Optional<Integer> index = parseIndex(argComponents[INDEX]);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
        }
        return new MarkDoneCommand(argComponents[TASK_TYPE], index.get());
    }

	/**
	 * Checks that the command format is valid
	 * @param type is todo/schedule, index is the index of item on the list, argType is description/tag/date/time
	 */
	private boolean isValidUpdateCommandFormat(String type, int index, String argType) {
		if (!(type.compareToIgnoreCase("todo") == 0 || type.compareToIgnoreCase("schedule") == 0)) {
			return false;
		}
		if (index < 0) {
			return false;
		}
		if (!(argType.compareToIgnoreCase("description") == 0 || argType.compareToIgnoreCase("tag") == 0 
				|| argType.compareToIgnoreCase("date") == 0 || argType.compareToIgnoreCase("time") == 0)) {
			return false;
		}
		return true;
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
		final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
					FindCommand.MESSAGE_USAGE));
		}

		// keywords delimited by whitespace
		final String[] keywords = matcher.group("keywords").split("\\s+");
		final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
		return new FindCommand(keywordSet);
	}
}