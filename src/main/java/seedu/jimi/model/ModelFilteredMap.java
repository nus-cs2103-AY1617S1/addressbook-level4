package seedu.jimi.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.jimi.commons.core.LogsCenter;
import seedu.jimi.commons.core.UnmodifiableObservableList;
import seedu.jimi.commons.util.StringUtil;
import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.event.Event;
import seedu.jimi.model.task.DeadlineTask;
import seedu.jimi.model.task.FloatingTask;
import seedu.jimi.model.task.ReadOnlyTask;

public class ModelFilteredMap {
    private static final Logger logger = LogsCenter.getLogger(ModelFilteredMap.class);

    public enum ListIdentifier {
        DAY_AHEAD_0, DAY_AHEAD_1, DAY_AHEAD_2, DAY_AHEAD_3, DAY_AHEAD_4, DAY_AHEAD_5, DAY_AHEAD_6, FLOATING_TASKS, COMPLETED, INCOMPLETE, TASKS_AGENDA, EVENTS_AGENDA
    }

    private final HashMap<ListIdentifier, FilteredList<ReadOnlyTask>> filteredMap = new HashMap<ListIdentifier, FilteredList<ReadOnlyTask>>();

    public ModelFilteredMap(TaskBook taskBook) {
        for (ListIdentifier li : ListIdentifier.values()) {
            filteredMap.put(li, new FilteredList<ReadOnlyTask>(taskBook.getTasks()));
        }

        updateFilteredListToShowAll();
    }

    public void updateFilteredListToShowAll() {
        filteredMap.get(ListIdentifier.FLOATING_TASKS)
                .setPredicate(new PredicateExpression(new FloatingTaskQualifier(false))::satisfies);

        filteredMap.get(ListIdentifier.COMPLETED)
                .setPredicate(new PredicateExpression(new CompletedTaskQualifier(true))::satisfies);
        filteredMap.get(ListIdentifier.INCOMPLETE)
                .setPredicate(new PredicateExpression(new CompletedTaskQualifier(false))::satisfies);

        filteredMap.get(ListIdentifier.DAY_AHEAD_0)
                .setPredicate(new PredicateExpression(new DateQualifier(ListIdentifier.DAY_AHEAD_0))::satisfies);
        filteredMap.get(ListIdentifier.DAY_AHEAD_1)
                .setPredicate(new PredicateExpression(new DateQualifier(ListIdentifier.DAY_AHEAD_1))::satisfies);
        filteredMap.get(ListIdentifier.DAY_AHEAD_2)
                .setPredicate(new PredicateExpression(new DateQualifier(ListIdentifier.DAY_AHEAD_2))::satisfies);
        filteredMap.get(ListIdentifier.DAY_AHEAD_3)
                .setPredicate(new PredicateExpression(new DateQualifier(ListIdentifier.DAY_AHEAD_3))::satisfies);
        filteredMap.get(ListIdentifier.DAY_AHEAD_4)
                .setPredicate(new PredicateExpression(new DateQualifier(ListIdentifier.DAY_AHEAD_4))::satisfies);
        filteredMap.get(ListIdentifier.DAY_AHEAD_5)
                .setPredicate(new PredicateExpression(new DateQualifier(ListIdentifier.DAY_AHEAD_5))::satisfies);
        filteredMap.get(ListIdentifier.DAY_AHEAD_6)
                .setPredicate(new PredicateExpression(new DateQualifier(ListIdentifier.DAY_AHEAD_6))::satisfies);

        filteredMap.get(ListIdentifier.TASKS_AGENDA)
                .setPredicate(new PredicateExpression(new TaskQualifier(true))::satisfies);
        filteredMap.get(ListIdentifier.EVENTS_AGENDA)
                .setPredicate(new PredicateExpression(new EventQualifier(true))::satisfies);
    }

    public void updateAllDefault() {

    }

    public UnmodifiableObservableList<ReadOnlyTask> getRequiredFilteredTaskList(ListIdentifier li) {
        return new UnmodifiableObservableList<>(filteredMap.get(li));
    }

    public void updateRequiredFilteredTaskList(ListIdentifier li, Set<String> keywords) {
        updateFilteredTaskList(li, new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(ListIdentifier li, Expression expression) {
        filteredMap.get(li).setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering
    // ==================================================

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
        private DayOfWeek currentDay;

        DateQualifier(ListIdentifier i) {
            identifier = i;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (task.isCompleted()) {
                return false;
            }

            currentDay = new DateTime().getLocalDateTime().getDayOfWeek();

            // dynamically set the day that each list corresponds to
            switch (identifier) {
            case DAY_AHEAD_0:
                dayOfWeek = currentDay;
                break;
            case DAY_AHEAD_1:
                dayOfWeek = currentDay.plus(1);
                break;
            case DAY_AHEAD_2:
                dayOfWeek = currentDay.plus(2);
                break;
            case DAY_AHEAD_3:
                dayOfWeek = currentDay.plus(3);
                break;
            case DAY_AHEAD_4:
                dayOfWeek = currentDay.plus(4);
                break;
            case DAY_AHEAD_5:
                dayOfWeek = currentDay.plus(5);
                break;
            case DAY_AHEAD_6:
                dayOfWeek = currentDay.plus(6);
                break;
            default:
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
         * 
         * @param task
         * @param day
         * @return True if task is at most 1 week ahead of current time.
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
         * 
         * @param event
         * @param day
         * @return True if event is at most 1 week ahead of current time or is
         *         occuring now.
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

        boolean isCompleteState;

        public CompletedTaskQualifier(boolean isCompleteState) {
            this.isCompleteState = isCompleteState;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (task instanceof Event) {
                return false;
            }

            if (isCompleteState == true) { // if want to check completed task
                return task.isCompleted();
            } else {
                return !task.isCompleted();
            }
        }
    }
    
    /**
     * Predicate for filtering events from the internal list.
     * @author zexuan
     * @param checkCompleted If true, checks for event completion as well.
     */
    private class EventQualifier implements Qualifier {

        boolean checkCompleted;
        
        public EventQualifier(boolean checkCompleted) {
            this.checkCompleted = checkCompleted;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            if (checkCompleted) {
                if (!task.isCompleted()) {
                    return false;
                }
            }
            return task instanceof Event;
        }
    }

    /**
     * Predicate for filtering floatingTasks from the internal list.
     * @author zexuan
     * @param checkCompleted If true, checks for task completion as well.
     */
    private class FloatingTaskQualifier implements Qualifier {
        
        boolean checkCompleted;
        
        public FloatingTaskQualifier(boolean checkCompleted) {
            this.checkCompleted = checkCompleted;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            if (checkCompleted) {
                if (!task.isCompleted()) {
                    return false;
                }
            }
            return !(task instanceof DeadlineTask) && !(task instanceof Event) && task instanceof FloatingTask;
        }
    }
    
    
    /**
     * Predicate for filtering tasks from the internal list.
     * @author zexuan
     * @param checkCompleted If true, checks for task completion as well.
     */
    private class TaskQualifier implements Qualifier {

        boolean checkCompleted;
        
        public TaskQualifier(boolean checkCompleted) {
            this.checkCompleted = checkCompleted;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) {
            if (checkCompleted) {
                if (!task.isCompleted()) {
                    return false;
                }
            }
            return task instanceof DeadlineTask || task instanceof FloatingTask;
        }
    }
}
