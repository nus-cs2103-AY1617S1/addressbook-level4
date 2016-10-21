package seedu.flexitrack.model;

import javafx.collections.transformation.FilteredList;
import seedu.flexitrack.commons.core.LogsCenter;
import seedu.flexitrack.commons.core.UnmodifiableObservableList;
import seedu.flexitrack.commons.util.StringUtil;
import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.model.task.UniqueTaskList;
import seedu.flexitrack.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.flexitrack.model.task.UniqueTaskList.IllegalEditException;
import seedu.flexitrack.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.flexitrack.commons.events.model.FlexiTrackChangedEvent;
import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.commons.core.ComponentManager;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the tasktracker data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final FlexiTrack flexiTracker;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given FlexiTracker FlexiTracker and
     * its variables should not be null
     */
    public ModelManager(FlexiTrack src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with tasktracker: " + src + " and user prefs " + userPrefs);

        flexiTracker = new FlexiTrack(src);
        filteredTasks = new FilteredList<>(flexiTracker.getTasks());
    }

    public ModelManager() {
        this(new FlexiTrack(), new UserPrefs());
    }

    public ModelManager(ReadOnlyFlexiTrack initialData, UserPrefs userPrefs) {
        flexiTracker = new FlexiTrack(initialData);
        filteredTasks = new FilteredList<>(flexiTracker.getTasks());
    }

    @Override
    public void resetData(ReadOnlyFlexiTrack newData) {
        flexiTracker.resetData(newData);
        indicateFlexiTrackerChanged();
    }

    @Override
    public ReadOnlyFlexiTrack getFlexiTrack() {
        return flexiTracker;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateFlexiTrackerChanged() {
        raise(new FlexiTrackChangedEvent(flexiTracker));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        flexiTracker.removeTask(target);
        indicateFlexiTrackerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        flexiTracker.addTask(task);
        updateFilteredListToShowAll();
        indicateFlexiTrackerChanged();
    }

    @Override
    public void markTask(int targetIndex) {
        flexiTracker.markTask(targetIndex);
        indicateFlexiTrackerChanged();
    }

    @Override
    public void unmarkTask(int targetIndex) {
        flexiTracker.unmarkTask(targetIndex);
        indicateFlexiTrackerChanged();
    }

    @Override
    public String editTask(int taskToEdit, String[] args)
            throws TaskNotFoundException, IllegalEditException, IllegalValueException {
        String duration = flexiTracker.editTask(taskToEdit, args);
        indicateFlexiTrackerChanged();
        return duration;
    }

    // =========== Filtered Tasks List Accessors
    // ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
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
            if (nameKeyWords.toString().contains("f/")) {
                return nameKeyWords.stream()
                        .filter(keyword -> StringUtil.equalsIgnoreCase(task.getName().fullName, keyword)).findAny()
                        .isPresent();
            }

            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword)).findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
