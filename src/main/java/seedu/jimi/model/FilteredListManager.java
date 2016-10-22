package seedu.jimi.model;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javafx.collections.transformation.FilteredList;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.commons.core.UnmodifiableObservableList;
import seedu.jimi.commons.util.StringUtil;
import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.event.Event;
import seedu.jimi.model.task.DeadlineTask;
import seedu.jimi.model.task.FloatingTask;
import seedu.jimi.model.task.ReadOnlyTask;

/**
 * Represents a manager for filtered lists used in the UI component.
 * Respective UI components should already be listeners to each of the lists in {@code listMap}.
 */
public class FilteredListManager {
    private static final Logger logger = LogsCenter.getLogger(FilteredListManager.class);
    
    public enum ListId {
        DAY_AHEAD_0, 
        DAY_AHEAD_1, 
        DAY_AHEAD_2, 
        DAY_AHEAD_3, 
        DAY_AHEAD_4, 
        DAY_AHEAD_5, 
        DAY_AHEAD_6, 
        FLOATING_TASKS, 
        COMPLETED, 
        INCOMPLETE, 
        TASKS_AGENDA, 
        EVENTS_AGENDA
    }
    
    private final HashMap<ListId, FilteredList<ReadOnlyTask>> listMap =
            new HashMap<ListId, FilteredList<ReadOnlyTask>>();
    
    public FilteredListManager(TaskBook taskBook) {
        // Initializing each list with taskBook's own internal list
        for (ListId id : ListId.values()) {
            listMap.put(id, new FilteredList<ReadOnlyTask>(taskBook.getTasks()));
        }
        
        initFilters();
    }
    
    public void initFilters() {
        listMap.get(ListId.FLOATING_TASKS)
                .setPredicate(new PredicateExpression(new FloatingTaskQualifier(false))::satisfies);
        
        listMap.get(ListId.COMPLETED)
                .setPredicate(new PredicateExpression(new CompletedTaskQualifier(true))::satisfies);
        listMap.get(ListId.INCOMPLETE)
                .setPredicate(new PredicateExpression(new CompletedTaskQualifier(false))::satisfies);
        
        listMap.get(ListId.DAY_AHEAD_0)
                .setPredicate(new PredicateExpression(new DateQualifier(ListId.DAY_AHEAD_0))::satisfies);
        listMap.get(ListId.DAY_AHEAD_1)
                .setPredicate(new PredicateExpression(new DateQualifier(ListId.DAY_AHEAD_1))::satisfies);
        listMap.get(ListId.DAY_AHEAD_2)
                .setPredicate(new PredicateExpression(new DateQualifier(ListId.DAY_AHEAD_2))::satisfies);
        listMap.get(ListId.DAY_AHEAD_3)
                .setPredicate(new PredicateExpression(new DateQualifier(ListId.DAY_AHEAD_3))::satisfies);
        listMap.get(ListId.DAY_AHEAD_4)
                .setPredicate(new PredicateExpression(new DateQualifier(ListId.DAY_AHEAD_4))::satisfies);
        listMap.get(ListId.DAY_AHEAD_5)
                .setPredicate(new PredicateExpression(new DateQualifier(ListId.DAY_AHEAD_5))::satisfies);
        listMap.get(ListId.DAY_AHEAD_6)
                .setPredicate(new PredicateExpression(new DateQualifier(ListId.DAY_AHEAD_6))::satisfies);
        
        listMap.get(ListId.TASKS_AGENDA)
                .setPredicate(new PredicateExpression(new TaskQualifier(true))::satisfies);
        listMap.get(ListId.EVENTS_AGENDA)
                .setPredicate(new PredicateExpression(new EventQualifier(true))::satisfies);

    }
    
    public void updateFilteredListToDefault() {
        listMap.get(ListId.TASKS_AGENDA)
                .setPredicate(new PredicateExpression(new TaskQualifier(true))::satisfies);
        listMap.get(ListId.EVENTS_AGENDA)
                .setPredicate(new PredicateExpression(new EventQualifier(true))::satisfies);
    }
    
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredList(ListId id) {
        return new UnmodifiableObservableList<>(listMap.get(id));
    }
    
    public void updateFilteredList(ListId id, Set<String> keywords) {
        updateFilteredList(id, new PredicateExpression(new NameQualifier(keywords)));
    }
    
    private void updateFilteredList(ListId id, Expression... expressions) {
        updateFilteredListToDefault(); // first reset to default
        Predicate<? super ReadOnlyTask> defaultPredicate = listMap.get(id).getPredicate();
        Predicate<? super ReadOnlyTask> updatedPredicate = 
                t -> defaultPredicate.test(t) && Stream.of(expressions).allMatch(ex -> ex.satisfies(t));
        listMap.get(id).setPredicate(updatedPredicate);
    }
    
    /*
     *  ============================================
     *  Private qualifier classes used for filtering
     *  ============================================
     */
    
    interface Expression {
        
        boolean satisfies(ReadOnlyTask task);
        
        String toString();
        
    }
    
    private class PredicateExpression implements Expression {
        
        private final Qualifier qualifier;
        
        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }
        
        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }
        
        @Override
        public String toString() {
            return qualifier.toString();
        }
    }
    
    interface Qualifier {
        boolean run(ReadOnlyTask task);
        
        String toString();
    }
    
    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;
        
        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }
        
        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
    private class DateQualifier implements Qualifier {
        private ListId id;
        private DayOfWeek dayOfWeek;
        private DayOfWeek currentDay;
        
        DateQualifier(ListId i) {
            id = i;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            if (task.isCompleted()) {
                return false;
            }
            
            currentDay = new DateTime().getLocalDateTime().getDayOfWeek();
            
            // dynamically set the day that each list corresponds to
            switch (id) {
            case DAY_AHEAD_0 :
                dayOfWeek = currentDay;
                break;
            case DAY_AHEAD_1 :
                dayOfWeek = currentDay.plus(1);
                break;
            case DAY_AHEAD_2 :
                dayOfWeek = currentDay.plus(2);
                break;
            case DAY_AHEAD_3 :
                dayOfWeek = currentDay.plus(3);
                break;
            case DAY_AHEAD_4 :
                dayOfWeek = currentDay.plus(4);
                break;
            case DAY_AHEAD_5 :
                dayOfWeek = currentDay.plus(5);
                break;
            case DAY_AHEAD_6 :
                dayOfWeek = currentDay.plus(6);
                break;
            default :
                break;
            }
            
            if (task instanceof DeadlineTask) {
                return isTaskSameWeekDate((DeadlineTask) task, dayOfWeek);
            } else if (task instanceof Event) {
                return isEventSameWeekDate((Event) task, dayOfWeek);
            } else {
                return false;
            }
        }
        
        /**
         * Checks if the task is at most 1 week ahead of current time.
         */
        private boolean isTaskSameWeekDate(DeadlineTask task, DayOfWeek day) {
            long daysDifference = new DateTime().getDifferenceInDays(task.getDeadline());
            
            if (daysDifference > 0) {
                return task.getDeadline().getLocalDateTime().getDayOfWeek().getValue() == day.getValue(); // check if day of the week
            }
            
            return false;
        }
        
        /**
         * Checks if the event is at most 1 week ahead of current time or is
         * occuring now.
         */
        private boolean isEventSameWeekDate(Event event, DayOfWeek day) {
            long daysDifference = new DateTime().getDifferenceInDays(event.getStart());
            
            // checks if event is not within only at most a week ahead
            if (!(daysDifference >= 0 && daysDifference <= 7)) {
                return false;
            }
            
            int eventStartDay = event.getStart().getLocalDateTime().getDayOfWeek().getValue();
            DateTime eventEndDate = event.getEnd();
            Optional<DateTime> ed = Optional.ofNullable(eventEndDate);
            
            logger.info("Checking event: " + day + " " + daysDifference);
            
            if (ed.isPresent()) {
                Integer eventEndDay = ed.get().getLocalDateTime().getDayOfWeek().getValue();
                
                Optional<Integer> endDay = Optional.ofNullable(eventEndDay);
                
                return day.getValue() >= eventStartDay && day.getValue() <= endDay.orElse(0);
            } else {
                return day.getValue() == eventStartDay;
            }
        }
    }
    
    private class CompletedTaskQualifier implements Qualifier {
        
        boolean isCheckCompleted;
        
        public CompletedTaskQualifier(boolean isCheckCompleted) {
            this.isCheckCompleted = isCheckCompleted;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            if (task instanceof Event) {
                return false;
            }
            
            return isCheckCompleted == task.isCompleted();
        }
    }
    
    /**
     * Predicate for filtering events from the internal list.
     * @author zexuan
     * @param isCheckCompleted If true, checks for event completion as well.
     */
    private class EventQualifier implements Qualifier {
        
        boolean isCheckCompleted;
        
        public EventQualifier(boolean isCheckCompleted) {
            this.isCheckCompleted = isCheckCompleted;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            if (isCheckCompleted && task.isCompleted()) {
                return false;
            }
            return task instanceof Event;
        }
    }
    
    /**
     * Predicate for filtering floatingTasks from the internal list.
     * @author zexuan
     * @param isCheckCompleted If true, checks for task completion as well.
     */
    private class FloatingTaskQualifier implements Qualifier {
        
        boolean isCheckCompleted;
        
        public FloatingTaskQualifier(boolean isCheckCompleted) {
            this.isCheckCompleted = isCheckCompleted;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            if (isCheckCompleted && task.isCompleted()) {
                return false;
            }
            
            return !(task instanceof DeadlineTask) 
                    && !(task instanceof Event) 
                    && task instanceof FloatingTask;
        }
    }
    
    /**
     * Predicate for filtering tasks from the internal list.
     * @author zexuan
     * @param isCheckCompleted If true, checks for task completion as well.
     */
    private class TaskQualifier implements Qualifier {
        
        boolean isCheckCompleted;
        
        public TaskQualifier(boolean isCheckCompleted) {
            this.isCheckCompleted = isCheckCompleted;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            if (isCheckCompleted && task.isCompleted()) {
                return false;
            }
            return task instanceof FloatingTask;
        }
    }
}
