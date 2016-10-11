package seedu.todo.models;

import java.time.LocalDateTime;

public class Event implements CalendarEvent {
    
    private String name;
    private LocalDateTime calendarDT;
    
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public LocalDateTime getCalendarDT() {
        return this.calendarDT; 
    }

    @Override
    public void setCalendarDT(LocalDateTime datetime) {
        this.calendarDT = datetime;
    }

}
