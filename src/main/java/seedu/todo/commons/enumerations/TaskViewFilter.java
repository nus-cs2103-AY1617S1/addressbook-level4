package seedu.todo.commons.enumerations;

import seedu.todo.commons.util.TimeUtil;
import seedu.todo.model.task.ImmutableTask;

import java.time.LocalDateTime;
import java.util.function.Predicate;

//@@author A0092382A
public enum TaskViewFilter {
    DEFAULT("show all", null, false, 5), 
    
    COMPLETED("completed", ImmutableTask::isCompleted, false, 0), 
    
    INCOMPLETE ("incomplete", task -> !task.isCompleted(), true, 0),
    
    DUE_SOON ("due soon", (task) -> {
        TimeUtil time = new TimeUtil(); 
        return !task.isCompleted() && 
            !task.isEvent() &&
            task.getEndTime().isPresent() && 
            time.isToday(task.getEndTime().get(), LocalDateTime.now());
    }, true, 0);
    
    private final String name;
    
    private final Predicate<ImmutableTask> filter;
    
    private final int shortcutCharPosition;
    
    private boolean chronological; 

    TaskViewFilter(String name, Predicate<ImmutableTask> filter, boolean chronological, int underlineCharPosition) {
        this.name = name;
        this.filter = filter;
        this.chronological = chronological;
        this.shortcutCharPosition = underlineCharPosition;
    }
    
    public String getViewName() {
        return this.name;
    }
    
    public Predicate<ImmutableTask> getFilter() {
        return this.filter;
    }
    
    public int getShortcutCharPosition() {
        return this.shortcutCharPosition;
    }

    public boolean isChronological() {
        return chronological;
    }
}
