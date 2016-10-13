package seedu.todo.models;

import java.time.LocalDateTime;

public interface CalendarItem {
    
    public String getName();
    public void setName(String name);

    public LocalDateTime getCalendarDT();
    public void setCalendarDT(LocalDateTime datetime);

}
