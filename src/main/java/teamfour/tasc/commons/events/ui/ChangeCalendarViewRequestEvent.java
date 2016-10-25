//@@author A0148096W

package teamfour.tasc.commons.events.ui;

import teamfour.tasc.commons.events.BaseEvent;

/**
 * An event requesting to change the calendar view.
 */
public class ChangeCalendarViewRequestEvent extends BaseEvent {

    private final String calendarViewType;
    
    public ChangeCalendarViewRequestEvent(String calendarViewType) {
        this.calendarViewType = calendarViewType;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getCalendarViewType() {
        return calendarViewType;
    }
}
