package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ChangeStatusCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

public class TokParser {

	// @@author A0141019U
	private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

	private static final Pattern ADD_COMMAND_FORMAT = Pattern
			.compile("'(?<taskName>.*\\S*.*)'(?<addTaskArgs>.+)");
	
	private static final Prefix startDateTimePrefix = new Prefix("from ");
	private static final Prefix endDateTimePrefix = new Prefix("to ");
	private static final Prefix datePrefix = new Prefix("on ");

	
	// @@author A0141019U-reused
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

		// case FindCommand.COMMAND_WORD:
		// return prepareFind(arguments);
		//
		// case ListCommand.COMMAND_WORD:
		// return prepareList(arguments);
		//
		// case DeleteCommand.COMMAND_WORD:
		// return prepareDelete(arguments);
		//
		// case EditCommand.COMMAND_WORD:
		// return prepareEdit(arguments);
		//
		// case ChangeStatusCommand.COMMAND_WORD_DONE:
		// return prepareChangeStatus(arguments, "done");
		//
		// case ChangeStatusCommand.COMMAND_WORD_PENDING:
		// return prepareChangeStatus(arguments, "pending");

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
		final Matcher matcher = ADD_COMMAND_FORMAT.matcher(arguments.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        final String name = matcher.group("taskName");
        final String args = matcher.group("addTaskArgs");
		
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(startDateTimePrefix, endDateTimePrefix, datePrefix);
		argsTokenizer.tokenize(args);		
		
		System.out.println("pre: " + argsTokenizer.getPreamble());
		System.out.println("name: " + name);
		System.out.println("st date: " + argsTokenizer.getValue(startDateTimePrefix));
		System.out.println("end date: " + argsTokenizer.getValue(endDateTimePrefix));
		System.out.println("date: " + argsTokenizer.getValue(datePrefix));
		
		Optional<String> startDateTimeStringOpt = argsTokenizer.getValue(startDateTimePrefix);
		Optional<String> endDateTimeStringOpt = argsTokenizer.getValue(endDateTimePrefix);	
		Optional<String> dateStringOpt = argsTokenizer.getValue(datePrefix);
		
		// TODO extract method
		if (dateStringOpt.isPresent()) {
			String date = dateStringOpt.get();
			
			if (startDateTimeStringOpt.isPresent()) {
				startDateTimeStringOpt = Optional.of(startDateTimeStringOpt.get() + date);
			}
			
			if (endDateTimeStringOpt.isPresent()) {
				endDateTimeStringOpt = Optional.of(endDateTimeStringOpt.get() + date);
			}
		}
		
		try {
			Optional<LocalDateTime> startDateTimeOpt = DateParser.parse(startDateTimeStringOpt);
			Optional<LocalDateTime> endDateTimeOpt = DateParser.parse(endDateTimeStringOpt);
			
			System.out.println("st opt: " + startDateTimeOpt);
			System.out.println("end opt: " + endDateTimeOpt);
			
			return new AddCommand(name, startDateTimeOpt, endDateTimeOpt);
		} 
		catch (ParseException e) {
			return new IncorrectCommand(e.getMessage());
		} 
		catch (IllegalValueException e) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		}
	}

	public static void main(String[] args) {
		TokParser t = new TokParser();
		t.parseCommand("add 'dance from atelier' to 4pm on tomorrow");
	}
}
