package seedu.todo.models;

import java.time.LocalDateTime;

public interface CalendarEvent {
    
    public String getName();
    public void setName(String name);

    public LocalDateTime getCalendarDT();
    public void setCalendarDT(LocalDateTime datetime);

}
