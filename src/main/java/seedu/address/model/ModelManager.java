package seedu.address.model;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.core.ComponentManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<Task> filteredTasks;
	private final FilteredList<Task> filteredTodayTasks;
	private final FilteredList<Task> filteredTomorrowTasks;
	private final FilteredList<Task> filteredIn7DaysTasks;
	private final FilteredList<Task> filteredIn30DaysTasks;
	private final FilteredList<Task> filteredSomedayTasks;

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
        filteredTasks = new FilteredList<>(taskManager.getTasks());
    	filteredTodayTasks = new FilteredList<>(taskManager.getTodayTasks());
    	filteredTomorrowTasks = new FilteredList<>(taskManager.getTomorrowTasks());
    	filteredIn7DaysTasks = new FilteredList<>(taskManager.getIn7DaysTasks());
    	filteredIn30DaysTasks = new FilteredList<>(taskManager.getIn30DaysTasks());
    	filteredSomedayTasks = new FilteredList<>(taskManager.getSomedayTasks());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
    	filteredTodayTasks = new FilteredList<>(taskManager.getTodayTasks());
    	filteredTomorrowTasks = new FilteredList<>(taskManager.getTomorrowTasks());
    	filteredIn7DaysTasks = new FilteredList<>(taskManager.getIn7DaysTasks());
    	filteredIn30DaysTasks = new FilteredList<>(taskManager.getIn30DaysTasks());
    	filteredSomedayTasks = new FilteredList<>(taskManager.getSomedayTasks());
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
    public synchronized void deleteTasks(ArrayList<ReadOnlyTask> targets) throws TaskNotFoundException {
        for(ReadOnlyTask target : targets) {
        	taskManager.removeTask(target);
        	indicateTaskManagerChanged();
        }
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    
    @Override
	public synchronized void editTask(int index, Task task) throws TaskNotFoundException {
    	taskManager.editTask(index, task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
	}

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

	@Override
	public ObservableList<ReadOnlyTask> getFilteredTodayTaskList() {
		return new UnmodifiableObservableList<>(filteredTodayTasks);
	}

	@Override
	public ObservableList<ReadOnlyTask> getFilteredTomorrowTaskList() {
		return new UnmodifiableObservableList<>(filteredTomorrowTasks);
	}

	@Override
	public ObservableList<ReadOnlyTask> getFilteredIn7DaysTaskList() {
		return new UnmodifiableObservableList<>(filteredIn7DaysTasks);
	}

	@Override
	public ObservableList<ReadOnlyTask> getFilteredIn30DaysTaskList() {
		return new UnmodifiableObservableList<>(filteredIn30DaysTasks);
	}

	@Override
	public ObservableList<ReadOnlyTask> getFilteredSomedayTaskList() {
		return new UnmodifiableObservableList<>(filteredSomedayTasks);
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
