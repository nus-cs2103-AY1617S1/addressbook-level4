package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.commands.ShowDoneCommand;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.core.ComponentManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<Task> filteredTasks;
    private Deque<TaskManager> taskManagerHistory = new ArrayDeque<TaskManager>(); 
    private Deque<TaskManager> undoHistory = new ArrayDeque<TaskManager>();
    
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
    public void clearDone() throws TaskNotFoundException {
    	taskManager.clearDone();
    	indicateTaskManagerChanged();
    }
    
    public void clearHistory() {
        taskManagerHistory.clear();
        undoHistory.clear();
    }
    
    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }
    
    @Override
    public void saveToHistory() {
        taskManagerHistory.push(new TaskManager(taskManager));
        undoHistory.clear();
    }
    
    @Override
    public void loadFromHistory() throws NoSuchElementException {
        TaskManager oldManager = taskManagerHistory.pop();
        undoHistory.push(new TaskManager(taskManager));
        taskManager.setTasks(oldManager.getTasks());
        taskManager.setTags(oldManager.getTagList());
        indicateTaskManagerChanged();
    }
    
    @Override
    public void loadFromUndoHistory() throws NoSuchElementException {
        TaskManager oldManager = undoHistory.pop();
        taskManagerHistory.push(new TaskManager(taskManager));
        taskManager.setTasks(oldManager.getTasks());
        taskManager.setTags(oldManager.getTagList());
        indicateTaskManagerChanged();
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
    	updateFilteredTaskListToShow(ShowCommand.isNotDone());
    	indicateTaskManagerChanged();
    	
    }
    
    @Override
    public synchronized void undoneTask(ReadOnlyTask target) throws TaskNotFoundException {
    	taskManager.undoneTask(target);
    	updateFilteredTaskListToShow(ShowDoneCommand.isDone());
    	indicateTaskManagerChanged();
    	
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    
    @Override
    public synchronized void editTask(ReadOnlyTask task, String newName, String newStart, String newEnd) throws TaskNotFoundException, IllegalValueException {
        if (newName != null)
            taskManager.editTaskName(task, newName);
        
        if (newStart != null)
            taskManager.editTaskStartTime(task, newStart);
        
        if (newEnd != null)
            taskManager.editTaskEndTime(task, newEnd);
        
        updateFilteredListToShowAll();
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
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredTaskListToShow(Predicate<Task> predicate) {
    	filteredTasks.setPredicate(predicate);
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
