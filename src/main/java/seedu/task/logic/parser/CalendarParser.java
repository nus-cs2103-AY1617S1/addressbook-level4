package seedu.task.logic.parser;

import seedu.task.commons.exceptions.EmptyValueException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.CalendarCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;;

public class CalendarParser implements Parser{

	@Override
	public Command prepare(String args) {
		if(args.isEmpty()) {
			//TODO: show calendar view?
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
			return new IncorrectCommand(e.getMessage()); //TODO: better handling of messages
		} catch (IllegalValueException ive) {
			return new IncorrectCommand(ive.getMessage());
		}
	}
}
