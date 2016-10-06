package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;

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

	public static Command parseUserInput(String userInput) {
		String[] splitSpace = userInput.split(" ");
		String commandWord = splitSpace[0].trim();
		System.out.println("command: " + commandWord);

		switch (commandWord) {
		case AddCommand.COMMAND_WORD:
			return prepareAdd(userInput);

			//		case SelectCommand.COMMAND_WORD:
			//			return prepareSelect(userInput);
			//
			//		case DeleteCommand.COMMAND_WORD:
			//			return prepareDelete(userInput);
			//
			//		case ClearCommand.COMMAND_WORD:
			//			return new ClearCommand();
			//
			//		case FindCommand.COMMAND_WORD:
			//			return prepareFind(userInput);
			//
			//		case ListCommand.COMMAND_WORD:
			//			return new ListCommand();
			//
			//		case ExitCommand.COMMAND_WORD:
			//			return new ExitCommand();
			//
			//		case HelpCommand.COMMAND_WORD:
			//			return new HelpCommand();

		default:
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}
	}


	private static Command prepareAdd(String userInput) {
		// Second word in command string must be task type
		String[] splitSpace = userInput.split(" ");
		String taskType = splitSpace[1].trim();
		System.out.println("task type: " + taskType);

		String[] userInputWithoutCommandsArr = Arrays.copyOfRange(splitSpace, 2, splitSpace.length);
		String userInputWithoutCommands = String.join(" ", userInputWithoutCommandsArr);

		switch (taskType) {
		// TODO change hardcoded strings to references to strings in command classes
		//		case "event":
		//			prepareAddEvent(userInput);
		//		case "deadline":
		//			return prepareAddDeadline(userInput);
		case "someday":
			return prepareAddSomeday(userInputWithoutCommands);
		default: 
			// TODO better error message
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}
	}


	private static Command prepareAddSomeday(String userInputWithoutCommands) {
		// Short alias
		String input = userInputWithoutCommands;

		if (input.indexOf("'") == -1) {
			// TODO better error message
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}

		String taskName = input.substring(input.indexOf("'") + 1, input.lastIndexOf("'")).trim();
		System.out.println("task name: " + taskName);

		String args = input.replace("'" + taskName + "'", "").trim();
		
		if (!args.equals("")) {
			// TODO better error message
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}
		
		// return new AddSomedayCommand(taskName);
		return null;
	}


	private Command prepareAddDeadline(String userInputWithoutCommands) {
		//TODO
		// Short alias
		String input = userInputWithoutCommands;

		if (input.indexOf("'") == -1) {
			// TODO better error message
			return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
		}

		String taskName = input.substring(input.indexOf("'") + 1, input.lastIndexOf("'")).trim();
		System.out.println("task name: " + taskName);

		String args = input.replace("'" + taskName + "'", "").trim();
		String[] argsArr = args.split(" ");

		HashSet<String> validArgs = new HashSet<>();
		validArgs.add("");

		for (int i=0; i<argsArr.length; i++) {

		}

		return null;
	}


	public static void main(String[] args) {
		String userInput = "add someday 'Read 50 Shades of Grey'";
		parseUserInput(userInput);
	}
}
