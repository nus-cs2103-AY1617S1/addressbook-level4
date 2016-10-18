package seedu.todo.commons.enumerations;
import seedu.todo.commons.util.TimeUtil;
import seedu.todo.model.task.ImmutableTask;

import java.time.LocalDateTime;
import java.util.function.Predicate;


//@@author A0092382A
public enum TaskViewFilters {
    DEFAULT("show all", null, 5), 
    
    COMPLETED("completed", (task) -> task.isCompleted(), 0), 
    
    INCOMPLETE ("incomplete", (task) -> !task.isCompleted(), 0),
    
    DUE_TODAY ("due today", (task) -> new TimeUtil().
                isToday(task.getEndTime().get(), LocalDateTime.now())
                && !task.isEvent()
                && !task.isCompleted(), 4);
    
    private final String name;
    
    private final Predicate<ImmutableTask> filter;
    
    private final int underlineChar;

    TaskViewFilters(String name, Predicate<ImmutableTask> filter, int underlineCharPosition) {
        this.name = name;
        this.filter = filter;
        this.underlineChar = underlineCharPosition;
    }
    
    public String getViewName() {
        return this.name;
    }
    
    public Predicate<ImmutableTask> getFilter() {
        return this.filter;
    }

    public int getUnderlineChar() {
        return underlineChar;
    }
}