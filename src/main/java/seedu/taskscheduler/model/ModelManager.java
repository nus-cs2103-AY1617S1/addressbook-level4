package seedu.taskscheduler.model;

import javafx.collections.transformation.FilteredList;
import seedu.taskscheduler.commons.core.ComponentManager;
import seedu.taskscheduler.commons.core.LogsCenter;
import seedu.taskscheduler.commons.core.UnmodifiableObservableList;
import seedu.taskscheduler.commons.events.model.TaskSchedulerChangedEvent;
import seedu.taskscheduler.commons.events.storage.FilePathChangedEvent;
import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.commons.util.StringUtil;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.Task;
import seedu.taskscheduler.model.task.UniqueTaskList;
import seedu.taskscheduler.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

/**
 * Represents the in-memory model of the Task Scheduler data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskScheduler taskScheduler;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given Task Scheduler
     * Task Scheduler and its variables should not be null
     */
    public ModelManager(TaskScheduler src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task scheduler: " + src + " and user prefs " + userPrefs);

        taskScheduler = new TaskScheduler(src);
        filteredTasks = new FilteredList<>(taskScheduler.getTasks());
    }

    public ModelManager() {
        this(new TaskScheduler(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskScheduler initialData, UserPrefs userPrefs) {
        taskScheduler = new TaskScheduler(initialData);
        filteredTasks = new FilteredList<>(taskScheduler.getTasks());
    }

    @Override
    public void resetData(ReadOnlyTaskScheduler newData) {
        taskScheduler.resetData(newData);
        indicateTaskSchedulerChanged();
    }

    @Override
    public ReadOnlyTaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskSchedulerChanged() {
        raise(new TaskSchedulerChangedEvent(taskScheduler));
    }

    //@@author A0148145E
    @Override
    public synchronized void deleteTask(ReadOnlyTask... target) throws TaskNotFoundException {
        for (ReadOnlyTask task : target) {
            taskScheduler.removeTask(task);
        }
        indicateTaskSchedulerChanged();
    }

    @Override
    public synchronized void addTask(Task... tasks) throws UniqueTaskList.DuplicateTaskException {
        for (Task task : tasks) {
            taskScheduler.addTask(task);
        }
        updateFilteredListToShowAll();
        indicateTaskSchedulerChanged();
    }

    @Override
    public void replaceTask(Task oldTask, Task newTask) 
            throws TaskNotFoundException, UniqueTaskList.DuplicateTaskException {
        taskScheduler.replaceTask(oldTask, newTask);
        updateFilteredListToShowAll();
        indicateTaskSchedulerChanged();
        
    }   

    @Override
    public void markTask(Task task) 
            throws IllegalValueException, TaskNotFoundException {
        taskScheduler.markTask(task);
        updateFilteredListToShowAll();
        indicateTaskSchedulerChanged();
    }

    @Override
    public void unMarkTask(Task task) 
            throws IllegalValueException, TaskNotFoundException {
        taskScheduler.unMarkTask(task);
        updateFilteredListToShowAll();
        indicateTaskSchedulerChanged();
    }
    
    //@@author A0140007B
    @Override
    public void insertTask(int index, Task newTask) 
            throws TaskNotFoundException {
        taskScheduler.insertTask(index, newTask);
        updateFilteredListToShowAll();
        indicateTaskSchedulerChanged();
    }
    //@@author
    
    @Subscribe
    public void changeFilePathRequestEvent(FilePathChangedEvent event) {
        indicateTaskSchedulerChanged();
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

        //@@author A0138696L
        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getAllFieldAsText(), keyword))
                    .findAny()
                    .isPresent();
        }
        //@@author
        
        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }


}
