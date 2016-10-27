package seedu.task.logic.parser;

import seedu.task.commons.exceptions.EmptyValueException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.CalendarCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;;

//@@author A0144702N
/**
 * Parses which parses command argument for show calendar command
 * @author xuchen
 */
public class CalendarParser implements Parser{

	@Override
	public Command prepare(String args) {
		if(args.isEmpty()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarCommand.MESSAGE_USAGE));
		}
		
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(calendarViewDayPrefix, calendarViewWeekPrefix);
		
		argsTokenizer.tokenize(args.trim());
		
		try {
			Optional<String> displayedDateTime = argsTokenizer.getPreamble();
			boolean toggleToDayView = argsTokenizer.hasPrefix(calendarViewDayPrefix);
			boolean toggleToWeekView = argsTokenizer.hasPrefix(calendarViewWeekPrefix);
			
			return new CalendarCommand(displayedDateTime.orElse(""), toggleToWeekView, toggleToDayView);
		} catch (EmptyValueException e) {
			return new IncorrectCommand(e.getMessage());
		} catch (IllegalValueException ive) {
			return new IncorrectCommand(ive.getMessage());
		}
	}
}
