package seedu.todo.commons.enumerations;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import seedu.todo.model.task.ImmutableTask;

public enum TaskViewFilters {
    DEFAULT("default", null), 
    COMPLETE("complete", (task) -> task.isCompleted()), 
    INCOMPLETE ("incomplete", (task) -> !task.isCompleted()),
    DUE_TODAY ("due today", (task) -> task.getEndTime().get().toLocalDate().
               equals(LocalDateTime.now().toLocalDate()) 
               && !task.isEvent());
    
    
    private final String name;
    
    private final Predicate<ImmutableTask> filter;

    TaskViewFilters(String name, Predicate<ImmutableTask> filter) {
        this.name = name;
        this.filter = filter;
    }
    
    public String getViewName() {
        return this.name;
    }
    
    public Predicate<ImmutableTask> getFilter() {
        return this.filter;
    }
    
    
    
    
}
