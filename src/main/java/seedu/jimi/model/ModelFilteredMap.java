package seedu.jimi.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import javafx.collections.transformation.FilteredList;
import seedu.jimi.commons.core.UnmodifiableObservableList;
import seedu.jimi.commons.util.StringUtil;
import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.event.Event;
import seedu.jimi.model.task.DeadlineTask;
import seedu.jimi.model.task.FloatingTask;
import seedu.jimi.model.task.ReadOnlyTask;

public class ModelFilteredMap {
    
    public enum ListIdentifier {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY,
        FLOATING_TASKS,
        COMPLETED,
        INCOMPLETE,
        TASKS_AGENDA,
        EVENTS_AGENDA
    }
    
    private final HashMap<ListIdentifier, FilteredList<ReadOnlyTask>> filteredMap =
            new HashMap<ListIdentifier, FilteredList<ReadOnlyTask>>();
    
    public ModelFilteredMap(TaskBook taskBook) {
        for (ListIdentifier li : ListIdentifier.values()) {
            filteredMap.put(li, new FilteredList<ReadOnlyTask>(taskBook.getTasks()));
        }
    }
    
    public void updateFilteredListToShowAll() {
        filteredMap.get(ListIdentifier.FLOATING_TASKS)
                .setPredicate(new PredicateExpression(new FloatingTaskQualifier())::satisfies);

        filteredMap.get(ListIdentifier.COMPLETED)
                .setPredicate(new PredicateExpression(new CompletedQualifier(true))::satisfies);
        filteredMap.get(ListIdentifier.INCOMPLETE)
                .setPredicate(new PredicateExpression(new CompletedQualifier(false))::satisfies);

        filteredMap.get(ListIdentifier.MONDAY)
                .setPredicate(new PredicateExpression(new DateQualifier(ListIdentifier.MONDAY))::satisfies);
        filteredMap.get(ListIdentifier.TUESDAY)
                .setPredicate(new PredicateExpression(new DateQualifier(ListIdentifier.TUESDAY))::satisfies);
        filteredMap.get(ListIdentifier.WEDNESDAY)
                .setPredicate(new PredicateExpression(new DateQualifier(ListIdentifier.WEDNESDAY))::satisfies);
        filteredMap.get(ListIdentifier.THURSDAY)
                .setPredicate(new PredicateExpression(new DateQualifier(ListIdentifier.THURSDAY))::satisfies);
        filteredMap.get(ListIdentifier.FRIDAY)
                .setPredicate(new PredicateExpression(new DateQualifier(ListIdentifier.FRIDAY))::satisfies);
        filteredMap.get(ListIdentifier.SATURDAY)
                .setPredicate(new PredicateExpression(new DateQualifier(ListIdentifier.SATURDAY))::satisfies);
        filteredMap.get(ListIdentifier.SUNDAY)
                .setPredicate(new PredicateExpression(new DateQualifier(ListIdentifier.SUNDAY))::satisfies);

        filteredMap.get(ListIdentifier.TASKS_AGENDA)
                .setPredicate(new PredicateExpression(new FloatingTaskQualifier())::satisfies);
        filteredMap.get(ListIdentifier.EVENTS_AGENDA)
                .setPredicate(new PredicateExpression(new EventQualifier())::satisfies);
    }
    
    public void updateAllDefault() {
        
    }
    
    public UnmodifiableObservableList<ReadOnlyTask> getRequiredFilteredTaskList(ListIdentifier li) {
        return new UnmodifiableObservableList<>(filteredMap.get(li));
    }
    
    public void updateRequiredFilteredTaskList(ListIdentifier li, Set<String> keywords){
        updateFilteredTaskList(li, new PredicateExpression(new NameQualifier(keywords)));
    }
    
    private void updateFilteredTaskList(ListIdentifier li, Expression expression) {
        filteredMap.get(li).setPredicate(expression::satisfies);
    }
    
    //========== Inner classes/interfaces used for filtering ==================================================
    
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
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword)).findAny()
                    .isPresent();
        }
        
        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
    private class DateQualifier implements Qualifier {
        private ListIdentifier identifier;
        private DayOfWeek dayOfWeek;
        
        DateQualifier(ListIdentifier i) {
            identifier = i;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            if (task.isCompleted()) {
                return false;
            }

            switch (identifier) {
            case MONDAY:
                dayOfWeek = DayOfWeek.MONDAY;
            case TUESDAY:
                dayOfWeek = DayOfWeek.TUESDAY;
            case WEDNESDAY:
                dayOfWeek = DayOfWeek.WEDNESDAY;
            case THURSDAY:
                dayOfWeek = DayOfWeek.THURSDAY;
            case FRIDAY:
                dayOfWeek = DayOfWeek.FRIDAY;
            case SATURDAY:
                dayOfWeek = DayOfWeek.SATURDAY;
            case SUNDAY:
                dayOfWeek = DayOfWeek.SUNDAY;
            default:
                break;
            }

            if (task instanceof DeadlineTask) {
                return isTaskSameWeekDate((DeadlineTask) task, dayOfWeek);
            } else if (task instanceof Event) {
                return isEventSameDate((Event) task, dayOfWeek);
            } else {
                return false;
            }
        }
        
        /**
         * Checks if the task is at most 1 week ahead of current time.
         * @param task
         * @param day
         * @return True if task is at most 1 week ahead of current time.
         */
        private boolean isTaskSameWeekDate(DeadlineTask task, DayOfWeek day) {
            long daysDifference = new DateTime().getDifferenceInDays(task.getDeadline());

            if (daysDifference >= 0 && daysDifference <= 7) {
                return task.getDeadline().getLocalDateTime().getDayOfWeek().getValue() == day.getValue();
            }

            return false;
        }
        
        /**
         * Checks if the event is at most 1 week ahead of current time or is occuring now.
         * @param event
         * @param day
         * @return True if event is at most 1 week ahead of current time or is occuring now.
         */
        private boolean isEventSameDate(Event event, DayOfWeek day) {
            long daysDifference = new DateTime().getDifferenceInDays(event.getStart());
            int eventStartDay = event.getStart().getLocalDateTime().getDayOfWeek().getValue();
            int eventEndDay = 0; //set to be smaller than DayOfWeek values
            
            if(event.getEnd() != null) {
                eventEndDay = event.getEnd().getLocalDateTime().getDayOfWeek().getValue();
            }
            
            if (daysDifference >= 0 && daysDifference <= 7) {
                return day.getValue() >= eventStartDay 
                        && day.getValue() <= eventEndDay;
            }
            
            return false;
        }
    }
    
    private class CompletedQualifier implements Qualifier {
        
        boolean isCompleteState;
        
        public CompletedQualifier(boolean isCompleteState) {
            this.isCompleteState = isCompleteState;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            return task.isCompleted() && isCompleteState;
        }
    }
    
    private class EventQualifier implements Qualifier {

        @Override
        public boolean run(ReadOnlyTask task) {
            return task instanceof Event;
        }
    }
    
    private class FloatingTaskQualifier implements Qualifier {

        @Override
        public boolean run(ReadOnlyTask task) {
            return task instanceof FloatingTask;
        }
    }
}
