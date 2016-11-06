package seedu.task.logic.parser;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.task.commons.exceptions.EmptyValueException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.CalendarCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.taskcommons.core.CalendarView;;

//@@author A0144702N
/**
 * Parses which parses command argument for show calendar command
 * 
 * @author xuchen
 */
public class CalendarParser implements Parser {

	@Override
	public Command prepare(String args) {
		if (args.isEmpty()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarCommand.MESSAGE_USAGE));
		}

		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(calendarViewDayPrefix);
		argsTokenizer.tokenize(args.trim());
		Optional<String> displayedDateTime = argsTokenizer.getPreambleAllowEmpty();
		boolean toggleToDayView = argsTokenizer.hasPrefix(calendarViewDayPrefix);
		
		CalendarView showView = (toggleToDayView)? CalendarView.DAY : CalendarView.WEEK; 
		
		try {
			return new CalendarCommand(displayedDateTime.orElse(""), showView);
		} catch (IllegalValueException ive) {
			return new IncorrectCommand(ive.getMessage());
		}
	}
}
