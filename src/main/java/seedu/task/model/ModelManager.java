package seedu.task.model;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.task.commons.core.ComponentManager;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.Priority;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.Time;
import seedu.task.model.task.UniqueTaskList;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<Task> filteredTasks;
    private final SortedList<Task> sortedTasks;

    /**
     * Initializes a ModelManager with the given TaskManager
     * TaskManager and its variables should not be null
     */
    public ModelManager(TaskManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task manager: " + src + " and user prefs " + userPrefs);

        taskManager = new TaskManager(src);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
        sortedTasks = new SortedList<>(filteredTasks);
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
        sortedTasks = new SortedList<>(filteredTasks);
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

	@Override
	public void insertTask(int index, Task toAdd) throws UniqueTaskList.DuplicateTaskException {
		taskManager.insertTask(index, toAdd);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
	}
	
    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getSortedFilteredTaskList() {
        return new UnmodifiableObservableList<>(sortedTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    //@@ author A0139860X
    @Override
    public void updateFilteredTaskList(String operand, Set<String> keywords) throws IllegalValueException{
        switch (operand) {
        case "pr/":
            updateFilteredTaskList(new PredicateExpression(new PriorityQualifier(keywords)));
            break;
        case "t/":
            updateFilteredTaskList(new PredicateExpression(new TagQualifier(keywords)));
            break;
        case "st/":
            updateFilteredTaskList(new PredicateExpression(new TimeQualifier("start", keywords)));
            break;
        case "ed/":
            updateFilteredTaskList(new PredicateExpression(new TimeQualifier("end", keywords)));
            break;
        default:
            updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
        }
    }

    //@@ author
    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering =================================================

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
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().fullDescription, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    //@@ author A0139860X
    private class TagQualifier implements Qualifier {
        private final Set<Tag> tags = new HashSet<>();

        TagQualifier(Set<String> tags) throws IllegalValueException {
            for(String tag : tags) {
                this.tags.add(new Tag(tag));
            };
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            final Set<Tag> taskTags = new HashSet<>();
            for(Tag tag : task.getTags()) {
                taskTags.add(tag);
            }
            return !Collections.disjoint(taskTags, tags);
        }

        @Override
        public String toString() {
            final StringBuffer buffer = new StringBuffer("tag=");
            final String separator = ", ";
            tags.forEach(tag -> buffer.append(tag).append(separator));
            if (buffer.length() == 0) {
                return "";
            } else {
                return buffer.substring(0, buffer.length() - separator.length());
            }
        }
    }

    private class PriorityQualifier implements Qualifier {
        private Set<Priority> priorities = new HashSet<Priority>();

        PriorityQualifier(Set<String> priorities) throws IllegalValueException {
            for (String priority: priorities) {
                this.priorities.add(new Priority(priority));
            }
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return priorities.contains(task.getPriority());
        }

        @Override
        public String toString() {
            final StringBuffer buffer = new StringBuffer("priority=");
            final String separator = ", ";
            priorities.forEach(tag -> buffer.append(tag).append(separator));
            if (buffer.length() == 0) {
                return "";
            } else {
                return buffer.substring(0, buffer.length() - separator.length());
            }
        }
    }

    private class TimeQualifier implements Qualifier {
        private Time time;
        private String arg;

        TimeQualifier(String arg, Set<String> time) throws IllegalValueException {
            StringBuilder input = new StringBuilder();
            for (String argIn: time) {
                input.append(argIn);
            }
            this.time = new Time(input.toString());
            this.arg = arg;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            // TODO: Implement floating task search
            switch (arg) {
            case "start":
                return time.isBefore(task.getTimeStart());
            case "end":
                return task.getTimeEnd().isBefore(time);
            default:
                return false;
            }
        }

        @Override
        public String toString() {
            return "time=" + time.toString();
        }
    }
    
    //=========== Sorted Task List Accessors ===============================================================
  
    @Override
    public void sortFilteredTaskList(String modifier) {
        switch (modifier) {
        case "-st":
            sortedTasks.setComparator(new StTimeComparator());
            break;
        case "-ed":
            sortedTasks.setComparator(new EdTimeComparator());        
            break;
        case "-pr":
            sortedTasks.setComparator(new PrTimeComparator());
            break;
        default:
            sortedTasks.setComparator(null);
            break;
        }
        
    }
 
    //========== Inner classes/interfaces used for filtering =================================================
    
    private class StTimeComparator implements Comparator<Task> {
        
        @Override
        public int compare(Task o1, Task o2) {
            if (o1.getTimeStart().isBefore(o2.getTimeStart())) {
                return -1;
            }
            else if (o2.getTimeStart().isBefore(o1.getTimeStart())) {
                return 1;
            }
            else if (o1.getTimeStart().equals(o2.getTimeEnd())) {
                return 0;
            }
            else if (o1.getTimeStart() == null && o2.getTimeStart()!= null) {
                return 1;
            }
            else if (o1.getTimeStart() != null && o2.getTimeStart() == null) {
                return -1;
            }
            else {
                return 1;
            }
            
        }
        
    }
    
    private class EdTimeComparator implements Comparator<Task> {
        
        @Override
        public int compare(Task o1, Task o2) {
            if (o1.getTimeEnd().isBefore(o2.getTimeEnd())) {
                return -1;
            }
            else if (o2.getTimeEnd().isBefore(o1.getTimeEnd())) {
                return 1;
            }
            else if (o1.getTimeEnd().equals(o2.getTimeEnd())) {
                return 0;
            }
            else if (o1.getTimeEnd() == null && o2.getTimeEnd()!= null) {
                return 1;
            }
            else if (o1.getTimeEnd() != null && o2.getTimeEnd() == null) {
                return -1;
            }
            else {
                return 1;
            }
            
        }
        
    }

    private class PrTimeComparator implements Comparator<Task> {
    
        @Override
        public int compare(Task o1, Task o2) {
            if (o1.hasHigherPriorityThan(o2)) {
                return -1;
            }
            else if (o1.hasLowerPriorityThan(o2)) {
                return 1;
            }
            else {
                return 0;
            }
            
        }
    
    }
    
}
