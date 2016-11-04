package jym.manager.model;

import javafx.collections.transformation.FilteredList;

import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import jym.manager.model.TaskManager;
import jym.manager.model.ReadOnlyTaskManager;
import jym.manager.commons.core.ComponentManager;
import jym.manager.commons.core.LogsCenter;
import jym.manager.commons.core.UnmodifiableObservableList;
import jym.manager.commons.events.model.TaskManagerChangedEvent;
import jym.manager.commons.util.StringUtil;
import jym.manager.model.task.ReadOnlyTask;
import jym.manager.model.task.Task;
import jym.manager.model.task.UniqueTaskList;
import jym.manager.model.task.UniqueTaskList.DuplicateTaskException;
import jym.manager.model.task.UniqueTaskList.TaskNotFoundException;
import jym.manager.ui.MainWindow;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
//@@author a0153617e

public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Task> filteredCompleteTasks;
    private final FilteredList<Task> filteredIncompleteTasks;
    
    private final Stack<ReadOnlyTaskManager> taskManagerHistory;
    private String currentTab;

    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(TaskManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        taskManager = new TaskManager(src);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
        filteredCompleteTasks = new FilteredList<>(taskManager.getCompletedTasks());
        filteredIncompleteTasks = new FilteredList<>(taskManager.getIncompleteTasks());
        taskManagerHistory = new Stack<ReadOnlyTaskManager>();
        currentTab = MainWindow.TAB_TASK_INCOMPLETE;
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
        filteredCompleteTasks = new FilteredList<>(taskManager.getCompletedTasks());
        filteredIncompleteTasks = new FilteredList<>(taskManager.getIncompleteTasks());
        taskManagerHistory = new Stack<ReadOnlyTaskManager>();
        currentTab = MainWindow.TAB_TASK_INCOMPLETE;
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
    public void setCurrentTab(String tab) {
        currentTab = tab;
    }
    
    @Override
    public String getCurrentTab() {
        return currentTab;
    }
    
    @Override
    public synchronized void markTask(ReadOnlyTask... tasks) throws TaskNotFoundException {
    	TaskManager previousTaskManager = new TaskManager(this.taskManager);
 //   	taskManager.completeTask(tasks);
    	taskManagerHistory.push(previousTaskManager);
        indicateTaskManagerChanged();
    }
    //@@author a0153617e

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
    	TaskManager previousToDoList = new TaskManager(this.taskManager);
        taskManagerHistory.push(previousToDoList);
    	taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }
    
    @Override
    public synchronized void updateTask(ReadOnlyTask target, Task updatedTask) throws TaskNotFoundException {
    	TaskManager previousToDoList = new TaskManager(this.taskManager);
        taskManagerHistory.push(previousToDoList);
    	taskManager.updateTask(target, updatedTask);
    	updateFilteredListToShowAll();
    	indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
    	TaskManager previousToDoList = new TaskManager(this.taskManager);
        taskManagerHistory.push(previousToDoList);
    	taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    
    @Override
    public synchronized void completeTask(ReadOnlyTask target) throws TaskNotFoundException {
    	TaskManager previousToDoList = new TaskManager(this.taskManager);
        taskManagerHistory.push(previousToDoList);
        taskManager.completeTask(target);
        indicateTaskManagerChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }
  //@@author a0153617e

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredCompleteTaskList() {
        return new UnmodifiableObservableList<>(filteredCompleteTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredIncompleteTaskList() {
        return new UnmodifiableObservableList<>(filteredIncompleteTasks);
    }
  

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        filteredCompleteTasks.setPredicate(null);
        filteredIncompleteTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
        filteredCompleteTasks.setPredicate(expression::satisfies);
        filteredIncompleteTasks.setPredicate(expression::satisfies);
    }
    
	@Override
	public void updateFilteredListToShowAll(String sortType) {
		try {
			taskManager.sortTask(sortType);
			updateFilteredListToShowAll();
			indicateTaskManagerChanged();
		} catch (DuplicateTaskException e) {
			e.printStackTrace();
		}
	}
    
  //@@author
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
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getDescription().toString(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

	@Override
    public synchronized void undoToDoList() throws EmptyStackException {
    	taskManager.resetData(taskManagerHistory.pop());
    	updateFilteredListToShowAll();
    	indicateTaskManagerChanged();
    }

}
