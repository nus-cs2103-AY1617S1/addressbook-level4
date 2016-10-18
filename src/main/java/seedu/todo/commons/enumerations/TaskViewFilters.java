package seedu.todo.commons.enumerations;
import java.time.LocalDateTime;
import java.util.function.Predicate;
import seedu.todo.commons.util.TimeUtil;

import seedu.todo.model.task.ImmutableTask;


//@@author A0092382A
public enum TaskViewFilters {
    DEFAULT("default", null), 
    COMPLETE("complete", (task) -> task.isCompleted()), 
    INCOMPLETE ("incomplete", (task) -> !task.isCompleted()),
    DUE_TODAY ("due today", (task) -> new TimeUtil().
                isToday(task.getEndTime().get(), LocalDateTime.now())
                && !task.isEvent()
                && !task.isCompleted()),
    DUE_TOMORROW ("due tomorrow", (task) -> new TimeUtil().
                  isTomorrow(task.getEndTime().get(),LocalDateTime.now())
                  && !task.isEvent()
                  && !task.isCompleted());
    
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
