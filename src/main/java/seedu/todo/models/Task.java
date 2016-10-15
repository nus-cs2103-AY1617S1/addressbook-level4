package seedu.todo.models;

import java.time.LocalDateTime;

public class Task implements CalendarItem {
    
    private String name;
    private LocalDateTime dueDate;
    private boolean isCompleted = false;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    
    @Override
    public LocalDateTime getCalendarDT() {
        return getDueDate();
    }
    
    @Override
    public void setCalendarDT(LocalDateTime date) {
        setDueDate(date);
    }

    public boolean isCompleted() {
        return isCompleted;
    }
    
    public void setCompleted() {
        this.isCompleted = true;
    }

    public void setIncomplete() {
        this.isCompleted = false;
    }

}
