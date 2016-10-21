package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.core.ComponentManager;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBook taskBook;
    private final FilteredList<Task> filteredDatedTasks;
    private final FilteredList<Task> filteredUndatedTasks;

    /**
     * Initializes a ModelManager with the given TaskBook
     * TaskBook and its variables should not be null
     */
    public ModelManager(TaskBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task book: " + src + " and user prefs " + userPrefs);

        taskBook = new TaskBook(src);
        filteredDatedTasks = new FilteredList<>(taskBook.getDatedTasks());
        filteredUndatedTasks = new FilteredList<>(taskBook.getUndatedTasks());
    }

    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskBook initialData, UserPrefs userPrefs) {
        taskBook = new TaskBook(initialData);
        filteredDatedTasks = new FilteredList<>(taskBook.getDatedTasks());
        filteredUndatedTasks = new FilteredList<>(taskBook.getUndatedTasks());
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        taskBook.resetData(newData);
        indicateTaskBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getTaskBook() {
        return taskBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskBookChanged() {
        raise(new TaskBookChangedEvent(taskBook));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException {
        taskBook.removeTask(target);
        indicateTaskBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskBookChanged();
    }
    
    @Override
    public void completeTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException {
        taskBook.completeTask(target);
        indicateTaskBookChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDatedTaskList() {
        return new UnmodifiableObservableList<>(filteredDatedTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredUndatedTaskList() {
        return new UnmodifiableObservableList<>(filteredUndatedTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredDatedTasks.setPredicate(null);
        filteredUndatedTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new TaskQualifier(keywords)));
    }

    public void updateFilteredTaskList(String keyword){
        updateFilteredTaskList(new PredicateExpression(new StatusQualifier(keyword)));
    }
    
    private void updateFilteredTaskList(Expression expression) {
        filteredDatedTasks.setPredicate(expression::satisfies);
        filteredUndatedTasks.setPredicate(expression::satisfies);
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

    private class TaskQualifier implements Qualifier {
        private Set<String> taskKeyWords;

        TaskQualifier(Set<String> taskKeyWords) {
            this.taskKeyWords = taskKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return (taskKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent()
                    || taskKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getDatetime().toString(), keyword))
                    .findAny()
                    .isPresent()
                    || taskKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getDescription().value, keyword))
                    .findAny()
                    .isPresent()
                    || taskKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getTags().toString(), keyword))
                    .findAny()
                    .isPresent());
        }

        @Override
        public String toString() {
            return "task=" + String.join(", ", taskKeyWords);
        }
    }
    
    private class StatusQualifier implements Qualifier {
        private Status stateKeyWord;

        StatusQualifier(String stateKeyWord) {
            this.stateKeyWord = new Status(stateKeyWord);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.getStatus().equals(stateKeyWord);
        }

        @Override
        public String toString() {
            return "status=" + stateKeyWord;
        }
    }
}
