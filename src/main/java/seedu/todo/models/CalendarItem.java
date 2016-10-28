package seedu.todo.models;

import java.time.LocalDateTime;

/**
 * CalendarItem interface
 * 
 * @@author A0093907W
 */
public interface CalendarItem {
    
    /**
     * Get the display name of the calendar item.
     * @return name
     */
    public String getName();
    
    /**
     * Set the display name of the calendar item.
     * @param name
     */
    public void setName(String name);

    /**
     * Get the calendar date of the calendar item. This is mostly for display
     * and sorting purposes.
     * 
     * @return datetime
     */
    public LocalDateTime getCalendarDT();
    
    /**
     * Set the calendar date of the calendar item. The behavior of this will
     * depend on the implementation, but it is guaranteed that the variable
     * being set by this method is the variable that is returned by
     * <code>getCalendarDT()</code>.<br>
     * 
     * It is unclear why one would ever set a calendar item's datetime using
     * this method, but it is here for completeness.
     * 
     * @param datetime
     */
    public void setCalendarDT(LocalDateTime datetime);
    
    /**
     * Returns true if the calendar item has passed, false otherwise.
     * 
     * @return isOver
     */
    public boolean isOver();

}
