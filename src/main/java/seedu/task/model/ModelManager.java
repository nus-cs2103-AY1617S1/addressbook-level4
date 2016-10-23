package seedu.task.model;

import javafx.collections.transformation.FilteredList;
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
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<Task> filteredPersons;

    /**
     * Initializes a ModelManager with the given TaskManager
     * TaskManager and its variables should not be null
     */
    public ModelManager(TaskManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        taskManager = new TaskManager(src);
        filteredPersons = new FilteredList<>(taskManager.getPersons());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        filteredPersons = new FilteredList<>(taskManager.getPersons());
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
        taskManager.removePerson(target);
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

    //=========== Filtered Person List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredPersons);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredPersons.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(String operand, Set<String> keywords) throws IllegalValueException{
        switch (operand) {
        case "pr/":
            updateFilteredPersonList(new PredicateExpression(new PriorityQualifier(keywords)));    
            break;
        case "t/":
            updateFilteredPersonList(new PredicateExpression(new TagQualifier(keywords)));    
            break;
        case "start/":
            updateFilteredPersonList(new PredicateExpression(new TimeQualifier("start", keywords)));    
            break;
        case "end/":
            updateFilteredPersonList(new PredicateExpression(new TimeQualifier("end", keywords)));    
            break;
        default:
            updateFilteredPersonList(new PredicateExpression(new NameQualifier(keywords)));
        }
    }

    private void updateFilteredPersonList(Expression expression) {
        filteredPersons.setPredicate(expression::satisfies);
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
                return time.isEndBeforeStart(task.getTimeStart());
            case "end":
                return task.getTimeEnd().isEndBeforeStart(time);
            default:
                return false;
            }
        }

        @Override
        public String toString() {
            return "time=" + time.toString();
        }
    }

}
