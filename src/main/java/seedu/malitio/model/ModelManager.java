package seedu.malitio.model;

import javafx.collections.transformation.FilteredList;
import seedu.malitio.commons.core.ComponentManager;
import seedu.malitio.commons.core.LogsCenter;
import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.commons.events.model.MalitioChangedEvent;
import seedu.malitio.commons.util.StringUtil;
import seedu.malitio.model.task.ReadOnlyTask;
import seedu.malitio.model.task.Task;
import seedu.malitio.model.task.UniqueTaskList;
import seedu.malitio.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the malitio data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Malitio malitio;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given Malitio
     * Malitio and its variables should not be null
     */
    public ModelManager(Malitio src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with malitio: " + src + " and user prefs " + userPrefs);

        malitio = new Malitio(src);
        filteredTasks = new FilteredList<>(malitio.getTasks());
    }

    public ModelManager() {
        this(new Malitio(), new UserPrefs());
    }

    public ModelManager(ReadOnlyMalitio initialData, UserPrefs userPrefs) {
        malitio = new Malitio(initialData);
        filteredTasks = new FilteredList<>(malitio.getTasks());
    }

    @Override
    public void resetData(ReadOnlyMalitio newData) {
        malitio.resetData(newData);
        indicatemalitioChanged();
    }

    @Override
    public ReadOnlyMalitio getMalitio() {
        return malitio;
    }

    /** Raises an event to indicate the model has changed */
    private void indicatemalitioChanged() {
        raise(new MalitioChangedEvent(malitio));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        malitio.removeTask(target);
        indicatemalitioChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        malitio.addTask(task);
        updateFilteredListToShowAll();
        indicatemalitioChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
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
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
