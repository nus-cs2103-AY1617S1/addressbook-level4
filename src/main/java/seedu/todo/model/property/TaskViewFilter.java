package seedu.todo.model.property;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import seedu.todo.model.task.ImmutableTask;

import java.util.Comparator;
import java.util.function.Predicate;

//@@author A0092382A
public class TaskViewFilter {
    /* Constants */
    private static final String EMPTY_MESSAGE_DEFAULT
            = "No tasks in view. Type 'add' to add a new tasks.";
    private static final String EMPTY_MESSAGE_INCOMPLETE
            = "You have no incomplete tasks or upcoming events.";
    private static final String EMPTY_MESSAGE_COMPLETED
            = "You have no completed tasks. Type 'view incomplete to view what tasks you have due.";
    private static final String EMPTY_MESSAGE_DUE_SOON
            = "You have completed all your tasks. Look ahead to upcoming events under 'view events'.";
    private static final String EMPTY_MESSAGE_EVENTS
            = "You have no upcoming events. Type 'add' to add new events.";
    private static final String EMPTY_MESSAGE_TODAY
            = "You have no tasks/events for today. Type 'add' to add a new task.";

    private static final Comparator<ImmutableTask> CHRONOLOGICAL = (a, b) -> ComparisonChain.start()
        .compare(a.getEndTime().orElse(null), b.getEndTime().orElse(null), Ordering.natural().nullsLast())
        .result();
    
    private static final Comparator<ImmutableTask> LAST_UPDATED = (a, b) -> 
        b.getCreatedAt().compareTo(a.getCreatedAt());
    
    public static final TaskViewFilter DEFAULT = new TaskViewFilter("all",
        null, LAST_UPDATED, EMPTY_MESSAGE_DEFAULT);
    
    public static final TaskViewFilter INCOMPLETE = new TaskViewFilter("incomplete",
        task -> !task.isCompleted(), CHRONOLOGICAL, EMPTY_MESSAGE_INCOMPLETE);
    
    public static final TaskViewFilter DUE_SOON = new TaskViewFilter("due soon", 
        task -> !task.isCompleted() && !task.isEvent() && task.getEndTime().isPresent(), CHRONOLOGICAL,
            EMPTY_MESSAGE_DUE_SOON);
    
    public static final TaskViewFilter EVENTS = new TaskViewFilter("events",
        ImmutableTask::isEvent, CHRONOLOGICAL, EMPTY_MESSAGE_EVENTS);
    
    public static final TaskViewFilter COMPLETED = new TaskViewFilter("completed",
        ImmutableTask::isCompleted, LAST_UPDATED, EMPTY_MESSAGE_COMPLETED);

    public final String name;
    
    public final Predicate<ImmutableTask> filter;
    
    public final Comparator<ImmutableTask> sort;
    
    public final int shortcutCharPosition;

    public final String emptyListMessage;

    public TaskViewFilter(String name, Predicate<ImmutableTask> filter, Comparator<ImmutableTask> sort,
                          String emptyListMessage) {
        this(name, filter, sort, emptyListMessage, 0);
    }

    public TaskViewFilter(String name, Predicate<ImmutableTask> filter, Comparator<ImmutableTask> sort,
                          String emptyListMessage, int underlineCharPosition) {
        this.name = name;
        this.filter = filter;
        this.sort = sort;
        this.shortcutCharPosition = underlineCharPosition;
        this.emptyListMessage = emptyListMessage;
    }
    
    public static TaskViewFilter[] all() {
        return new TaskViewFilter[]{
            DEFAULT, COMPLETED, INCOMPLETE, EVENTS, DUE_SOON,
        };
    }

    @Override
    public String toString() {
        return name;
    }
}
