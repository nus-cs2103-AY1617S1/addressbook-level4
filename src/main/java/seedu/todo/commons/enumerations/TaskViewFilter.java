package seedu.todo.commons.enumerations;

import java.time.LocalDateTime;
import java.util.function.Predicate;
import seedu.todo.commons.util.TimeUtil;

import seedu.todo.model.task.ImmutableTask;

//@@author A0092382A
public enum TaskViewFilter {
    DEFAULT("show all", null, 5), 
    
    COMPLETED("completed", ImmutableTask::isCompleted, 0), 
    
    INCOMPLETE ("incomplete", task -> !task.isCompleted(), 0),
    
    DUE_TODAY ("due today", (task) -> {
        TimeUtil time = new TimeUtil(); 
        return !task.isCompleted() && 
            !task.isEvent() &&
            task.getEndTime().isPresent() && 
            time.isToday(task.getEndTime().get(), LocalDateTime.now());
    }, 4);
    
    private final String name;
    
    private final Predicate<ImmutableTask> filter;
    
    private final int underlineChar;

    TaskViewFilter(String name, Predicate<ImmutableTask> filter, int underlineCharPosition) {
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
        return this.underlineChar;
    }
}
