package teamfour.tasc.logic.commands;

import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.events.ui.ChangeCalendarViewRequestEvent;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.model.keyword.CalendarCommandKeyword;
import teamfour.tasc.model.keyword.DayKeyword;
import teamfour.tasc.model.keyword.WeekKeyword;
import teamfour.tasc.ui.CalendarPanel;

/**
 * Changes the calendar view.
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = CalendarCommandKeyword.keyword;

    public static final String KEYWORD_CALENDAR_VIEW_DAY = DayKeyword.keyword;
    public static final String KEYWORD_CALENDAR_VIEW_WEEK = WeekKeyword.keyword;
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the calendar view. "
            + "Parameters: day (or) week\n"
            + "Example: " + COMMAND_WORD + " day";

    public static final String MESSAGE_SUCCESS = 
            "Calendar changed to %1$s view.";
    public static final String MESSAGE_FAILURE_ALREADY_IN_VIEW = 
            "Calendar is already in %1$s view.";

    private final String calendarView;
    
    /**
     * Calendar Command
     * Convenience constructor using raw values.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public CalendarCommand(String calendarView) throws IllegalValueException {
        if (!isCalendarViewValid(calendarView)) {
            throw new IllegalValueException("Invalid calendar view type.");
        }
        this.calendarView = calendarView;
    }
    
    /**
     * Validates the calendar view String in the argument.
     */
    private boolean isCalendarViewValid(String calendarView) {
        if (calendarView != null &&
                (calendarView.equals(KEYWORD_CALENDAR_VIEW_DAY) ||
                calendarView.equals(KEYWORD_CALENDAR_VIEW_WEEK))) {
            return true;
        }
        return false;
    }

    @Override
    public CommandResult execute() {
        if (calendarView.equals(CalendarPanel.getCalendarView())) {
            return new CommandResult(String.format(
                    MESSAGE_FAILURE_ALREADY_IN_VIEW, calendarView));
        }
        
        EventsCenter.getInstance().post(new ChangeCalendarViewRequestEvent(calendarView));
        return new CommandResult(String.format(MESSAGE_SUCCESS, calendarView));
    }

    @Override
    public boolean canUndo() {
        return false;
    }
}
