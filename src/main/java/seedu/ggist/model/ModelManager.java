package seedu.ggist.model;

import javafx.collections.transformation.FilteredList;
import seedu.ggist.commons.core.ComponentManager;
import seedu.ggist.commons.core.LogsCenter;
import seedu.ggist.commons.core.UnmodifiableObservableList;
import seedu.ggist.commons.events.model.TaskManagerChangedEvent;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.commons.util.StringUtil;
import seedu.ggist.logic.commands.CommandResult;
import seedu.ggist.logic.commands.EditCommand;
import seedu.ggist.model.task.Task;
import seedu.ggist.model.task.ReadOnlyTask;
import seedu.ggist.model.task.UniqueTaskList;
import seedu.ggist.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.ggist.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.ggist.model.task.UniqueTaskList.TaskTypeNotFoundException;

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
    
    public static final String MESSAGE_INVALID_TASK_TYPE = "%1$s is not a valid type";

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
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
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
    public synchronized void doneTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.doneTask(target);
        indicateTaskManagerChanged();
    }

    public synchronized void editTask(ReadOnlyTask target) throws TaskTypeNotFoundException {
    	taskManager.editTask(target);
    	indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredTaskListToShowUndone();
        indicateTaskManagerChanged();
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
    public void updateFilteredListToShowAllDone() {
        updateFilteredListToShowAllDone(new PredicateExpression(new DoneQualifier()));
    }
    
    public void updateFilteredListToShowAllDone(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredTaskListToShowUndone() {
        updateFilteredTaskListToShowUndone(new PredicateExpression(new NotDoneQualifier()));
    }
    
    public void updateFilteredTaskListToShowUndone(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    public void updateFilteredTaskList(Expression expression) {
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
    
    private class NotDoneQualifier implements Qualifier {
        
        NotDoneQualifier() {}
        
        public boolean run(ReadOnlyTask task) {
            return (!task.getDone());
        }
    }
    
    private class DoneQualifier implements Qualifier {
        
        DoneQualifier() {}
        
        public boolean run(ReadOnlyTask task) {
            return task.getDone();
        }
    }

    private class NameQualifier implements Qualifier {
        private Set<String> taskNameKeyWords;

        NameQualifier(Set<String> taskNameKeyWords) {
            this.taskNameKeyWords = taskNameKeyWords;
        }

        @Override

        public boolean run(ReadOnlyTask task) {
            return taskNameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.toString(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", taskNameKeyWords);
        }
    }

}
