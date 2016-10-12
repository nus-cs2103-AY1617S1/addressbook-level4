package seedu.todo.models;

import java.time.LocalDateTime;

public class Event implements CalendarEvent {
    
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

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
        return getStartDate(); 
    }

    @Override
    public void setCalendarDT(LocalDateTime datetime) {
        setStartDate(datetime);
    }

}
