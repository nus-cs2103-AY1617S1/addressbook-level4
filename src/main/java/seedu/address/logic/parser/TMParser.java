package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SelectCommand;


public class TMParser {
	
	private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
	// Allow multiple orderings
	private static final Pattern ADD_ARGS_FORMAT = 
			Pattern.compile("(?<taskType>event|deadline|someday)(?<addCommandArgs>.*)"
			+ "|(?<addCommandArgs2>.*)(?<taskType2>event|deadline|someday)");
	private static final Pattern SOMEDAY_ARGS_FORMAT = Pattern.compile("(?<taskName>'.+')");
	
	
	public static Command parseUserInput(String userInput) {
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

//		case SelectCommand.COMMAND_WORD:
//            return prepareSelect(arguments);
//
//        case DeleteCommand.COMMAND_WORD:
//            return prepareDelete(arguments);
//
//        case ClearCommand.COMMAND_WORD:
//            return new ClearCommand();
//
//        case FindCommand.COMMAND_WORD:
//            return prepareFind(arguments);
//
//        case ListCommand.COMMAND_WORD:
//            return new ListCommand();
//
//        case ExitCommand.COMMAND_WORD:
//            return new ExitCommand();
//
//        case HelpCommand.COMMAND_WORD:
//            return new HelpCommand();

		default:
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}
	}


	private static Command prepareAdd(String arguments) {
		final Matcher matcher = ADD_ARGS_FORMAT.matcher(arguments.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        
        String taskType;
        String addCommandArgs;
        try {
        	taskType = matcher.group("taskType").trim();
            addCommandArgs = matcher.group("addCommandArgs").trim();
        } catch (NullPointerException e) {
        	taskType = matcher.group("taskType2").trim();
            addCommandArgs = matcher.group("addCommandArgs2").trim();
        }
        
        System.out.println("task type: " + taskType);
        System.out.println("add args: " + addCommandArgs);
        
        
		switch (taskType) {
		// TODO change hardcoded strings to references to strings in command classes
		//case "event":
		//	return prepareAddEvent(userInput);
		case "deadline":
			return prepareAddDeadline(addCommandArgs);
		case "someday":
			return prepareAddSomeday(addCommandArgs);
		default: 
			// TODO better error message
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}
	}


	private static Command prepareAddSomeday(String arguments) {
		final Matcher matcher = SOMEDAY_ARGS_FORMAT.matcher(arguments.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        
        final String taskName = matcher.group("taskName").trim();
        System.out.println("task name: " + taskName);
        
        // TODO return new addsomedaycommand
		return null;
	}


	private static Command prepareAddDeadline(String userInputWithoutCommands) {
//		// Short alias
//		String input = userInputWithoutCommands;
//
//		if (input.indexOf("'") == -1) {
//			// TODO better error message
//			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
//		}
//
////		String taskName = input.substring(input.indexOf("'") + 1, input.lastIndexOf("'")).trim();
////		System.out.println("task name: " + taskName);
////
////		String args = input.replace("'" + taskName + "'", "").trim();
////		
////		System.out.println("args: " + args);
//		
//		Pattern MY_PATTERN = Pattern.compile("'(.+)'");
//		Matcher m = MY_PATTERN.matcher(userInputWithoutCommands);
//		
//		while (m.find()) {
//		    String s = m.group(1);
//		    System.out.println("s: " + s);
//		}
		return null;
	}
	
	
	public static void main(String[] args) {
		String userInput = "add 'Read 50 Shades of Grey' someday";
		parseUserInput(userInput);
	}
}
