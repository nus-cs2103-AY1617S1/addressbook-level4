package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.events.model.ToDoListChangedEvent;
import seedu.address.commons.core.ComponentManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the todo list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ToDoList toDoList;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given ToDoList
     * ToDoList and its variables should not be null
     */
    public ModelManager(ToDoList src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with todo list: " + src + " and user prefs " + userPrefs);

        toDoList = new ToDoList(src);
        filteredTasks = new FilteredList<>(toDoList.getTasks());
    }

    public ModelManager() {
        this(new ToDoList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyToDoList initialData, UserPrefs userPrefs) {
        toDoList = new ToDoList(initialData);
        filteredTasks = new FilteredList<>(toDoList.getTasks());
    }

    @Override
    public void resetData(ReadOnlyToDoList newData) {
        toDoList.resetData(newData);
        indicateToDoListChanged();
    }

    @Override
    public ReadOnlyToDoList getToDoList() {
        return toDoList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateToDoListChanged() {
        raise(new ToDoListChangedEvent(toDoList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        toDoList.removeTask(target);
        indicateToDoListChanged();
    }
    
    public synchronized void doneTask(Task target) throws TaskNotFoundException {
        target.checkDone().setDone();
        indicateToDoListChanged();
        updateFilteredListToShowAll();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        toDoList.addTask(task);
        updateFilteredListToShowAll();
        indicateToDoListChanged();
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
        updateFilteredTaskList(new PredicateExpression(new DetailQualifier(keywords)));
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

    private class DetailQualifier implements Qualifier {
        private Set<String> detailKeyWords;

        DetailQualifier(Set<String> detailKeyWords) {
            this.detailKeyWords = detailKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return detailKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getDetail().details, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "detail=" + String.join(", ", detailKeyWords);
        }
    }

}
