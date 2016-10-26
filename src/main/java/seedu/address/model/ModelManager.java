package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.core.ComponentManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskFilter;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.ReadOnlyTaskFilter;
import seedu.address.model.task.Status;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<Task> filteredTasks;
    private Stack<TaskManager> stateHistory;
    private Stack<TaskManager> undoHistory;

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
        filteredTasks = new FilteredList<>(taskManager.getFilteredTasks());
        stateHistory = new Stack<>();
        undoHistory = new Stack<>();
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        filteredTasks = new FilteredList<>(taskManager.getFilteredTasks());
        stateHistory = new Stack<>();
        undoHistory = new Stack<>();
    }
    
  //@@author A0141019U
    public void saveState() {
    	stateHistory.push(new TaskManager(taskManager));
    	// Allow redos only if the previous action is an undo
    	undoHistory.clear();
    }
    
    public void loadPreviousState() throws EmptyStackException {
    	TaskManager oldTaskManager = stateHistory.pop();
    	
    	undoHistory.push(new TaskManager(taskManager));
    	
    	taskManager.setTasks(oldTaskManager.getFilteredTasks());
    	taskManager.setTags(oldTaskManager.getTagList());
    	
    	indicateTaskManagerChanged();
    }
    
    public void loadNextState() throws EmptyStackException {
    	TaskManager oldTaskManager = undoHistory.pop();

    	stateHistory.push(new TaskManager(taskManager));
    	
    	taskManager.setTasks(oldTaskManager.getFilteredTasks());
    	taskManager.setTags(oldTaskManager.getTagList());
    	
    	indicateTaskManagerChanged();
    }
    
    //@@author
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
    public synchronized void deleteTasks(ArrayList<ReadOnlyTask> targets) throws TaskNotFoundException {
        for(ReadOnlyTask target : targets) {
        	taskManager.removeTask(target);
        	indicateTaskManagerChanged();
        }
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredTaskList(ReadOnlyTaskFilter.isDone().negate());
        indicateTaskManagerChanged();
    }
    
    @Override
    public synchronized void editTask(int index, Task task) throws TaskNotFoundException {
        taskManager.editTask(index, task);
        indicateTaskManagerChanged();
	}
    
    //@@author A0141019U
    @Override
    public synchronized void checkForOverdueTasks() {
    	LocalDateTime now = LocalDateTime.now();
    	
    	for (Task task : taskManager.getUniqueTaskList().getInternalList()) {
    		if (!task.getStatus().isDone() && task.getEndDate().orElse(LocalDateTime.MAX).isBefore(now)) {
    			task.setStatus(new Status("overdue"));
    		}
    	}
    }
    //@@author

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getUnfilteredTaskList() {
        return new UnmodifiableObservableList<>(taskManager.getFilteredTasks());
    }
    
    //@@author A0142184L
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getNonDoneTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks.filtered(TaskFilter.isDone().negate()));
    }

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getTodayTaskList() {
        return new UnmodifiableObservableList<>(taskManager.getFilteredTasks().filtered(TaskFilter.isDone().negate().and(TaskFilter.isTodayTask())));
	}

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getTomorrowTaskList() {
        return new UnmodifiableObservableList<>(taskManager.getFilteredTasks().filtered(TaskFilter.isDone().negate().and(TaskFilter.isTomorrowTask())));
	}

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getIn7DaysTaskList() {
        return new UnmodifiableObservableList<>(taskManager.getFilteredTasks().filtered(TaskFilter.isDone().negate().and(TaskFilter.isIn7DaysTask())));
	}

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getIn30DaysTaskList() {
        return new UnmodifiableObservableList<>(taskManager.getFilteredTasks().filtered(TaskFilter.isDone().negate().and(TaskFilter.isIn30DaysTask())));
	}

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getSomedayTaskList() {
        return new UnmodifiableObservableList<>(taskManager.getFilteredTasks().filtered(TaskFilter.isDone().negate().and(TaskFilter.isSomedayTask())));
	}
	
	//@@author
    @Override
    public void updateFilteredListToShowAll() {
       filteredTasks.setPredicate(null);
    }
    
    //@@author A0139339W
    @Override
    public void updateFilteredTaskList(Predicate<ReadOnlyTask> taskFilter) {
    	filteredTasks.setPredicate(taskFilter);
    }
    //@@author

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
