package seedu.tasklist.model;

import javafx.collections.transformation.FilteredList;
import seedu.tasklist.commons.core.ComponentManager;
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.commons.events.model.TaskListChangedEvent;
import seedu.tasklist.commons.util.StringUtil;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;
import seedu.tasklist.model.task.UniqueTaskList.TaskCompletionException;
import seedu.tasklist.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskList taskList;
    private final FilteredList<Task> filteredTask;

    /**
     * Initializes a ModelManager with the given TaskList
     * TaskList and its variables should not be null
     */
    public ModelManager(TaskList src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task list: " + src + " and user prefs " + userPrefs);

        taskList = new TaskList(src);
        filteredTask = new FilteredList<>(taskList.getTask());
    }

    public ModelManager() {
        this(new TaskList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskList initialData, UserPrefs userPrefs) {
        taskList = new TaskList(initialData);
        filteredTask = new FilteredList<>(taskList.getTask());
    }

    @Override
    public void resetData(ReadOnlyTaskList newData) {
        taskList.resetData(newData);
        indicateTaskListChanged();
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskListChanged() {
        raise(new TaskListChangedEvent(taskList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskList.removeTask(target);
        indicateTaskListChanged();
    }
    
    @Override
    public void markTask(ReadOnlyTask target) throws TaskNotFoundException, TaskCompletionException {
        taskList.markTask(target);
        indicateTaskListChanged();
    }
    
    @Override
    public void unmarkTask(ReadOnlyTask target) throws TaskNotFoundException, TaskCompletionException {
        taskList.unmarkTask(target);
        indicateTaskListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskList.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskListChanged();
    }
    
    @Override
    public void editTask(Task taskToEdit, ReadOnlyTask target) throws TaskNotFoundException {
        taskList.editTask(taskToEdit, target);
        updateFilteredListToShowAll();
        indicateTaskListChanged();
    }
    
    @Override
    public void updateFilePathChange() {
    	indicateTaskListChanged();
    }
    
    //@@author A0153837X
    @Override
    public String timeTask (Task target) {
    	return target.timeTask();
    }
    
    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTask);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTask.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new TitleQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTask.setPredicate(expression::satisfies);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getListCommandFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTask);
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

    private class TitleQualifier implements Qualifier {
        private Set<String> titleKeyWords;

        TitleQualifier(Set<String> titleKeyWords) {
            this.titleKeyWords = titleKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return titleKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getAsText(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "title=" + String.join(", ", titleKeyWords);
        }
    }

}
