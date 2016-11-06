package seedu.task.commons.events.ui;

import java.time.LocalDateTime;

import seedu.task.commons.events.BaseEvent;
import seedu.task.commons.util.StringUtil;
import seedu.taskcommons.core.CalendarView;

//@@author A0144702N
public class UpdateCalendarEvent extends BaseEvent {
	private LocalDateTime displayedDateTime;
	private CalendarView calendarViewMode;
	
	public UpdateCalendarEvent(LocalDateTime displayedDateTime, CalendarView calendarViewMode) {
		this.displayedDateTime = displayedDateTime;
		this.calendarViewMode = calendarViewMode;
	}	

	@Override
	public String toString() {
		return "Setting displayed time " + this.displayedDateTime.format(StringUtil.DATE_FORMATTER)
		+" With mode: " + calendarViewMode; 
	}

	public LocalDateTime getDisplayedDateTime() {
		return displayedDateTime;
	}

	public CalendarView getCalendarViewMode() {
		return calendarViewMode;
	}
}
