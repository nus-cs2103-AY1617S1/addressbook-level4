package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.FinishStateException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.ModelManager.Qualifier;
import seedu.address.model.deadline.DateManager;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.state.StateManager;
import seedu.address.model.state.TaskCommandState;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task manager data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
	private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

	private final TaskManager taskManager;
	private final FilteredList<Task> filteredTasks;
	private final StateManager stateManager;

	/**
	 * Initializes a ModelManager with the given TaskManager and its
	 * variables should not be null
	 */
	public ModelManager(TaskManager src, UserPrefs userPrefs) {
		super();
		assert src != null;
		assert userPrefs != null;

		logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

		taskManager = new TaskManager(src);
		filteredTasks = new FilteredList<>(taskManager.getTasks());
		stateManager = new StateManager(new TaskCommandState(taskManager, ""));
	}

	public ModelManager() {
		this(new TaskManager(), new UserPrefs());
	}

	public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
		taskManager = new TaskManager(initialData);
		filteredTasks = new FilteredList<>(taskManager.getTasks());
		stateManager = new StateManager(new TaskCommandState(taskManager, ""));
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
	public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
		taskManager.addTask(task);
		updateFilteredListToShowAll();
		indicateTaskManagerChanged();
	}
	
	@Override
	public void currentState(String command) {
		stateManager.currentState(new TaskCommandState(taskManager, command));	
	}

	@Override
	public String getPreviousState() throws FinishStateException {
		TaskCommandState previousState = stateManager.getPreviousState();
		return getState(previousState);
	}

	@Override
	public String getInitialState() throws FinishStateException {
		TaskCommandState initialState = stateManager.getInitialState();
		return getState(initialState);
	}
	
	private String getState(TaskCommandState state) {
		resetData(state.getTaskCommand());
		return state.getCommand();
	}

	// =========== Filtered Task List Accessors
	// ===============================================================

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
		return new UnmodifiableObservableList<>(filteredTasks);
	}

	@Override
	public void updateFilteredListToShowAll() {
		filteredTasks.setPredicate(null);
	}

	@Override
	public void updateFilteredTaskList(Set<String> keywords) {
		updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
	}
	
	@Override
	public void updateFilteredTaskGroup(String keywords) {
		updateFilteredTaskList(new PredicateExpression(new TagQualifier(keywords)));
	}

	private void updateFilteredTaskList(Expression expression) {
		filteredTasks.setPredicate(expression::satisfies);
	}

	public void updateFilteredListToShowClashing() throws DuplicateTaskException {

		TaskManager taskmanager = new TaskManager();
		for (int i = 0; i < filteredTasks.size() - 1; i++) {
			boolean isClashing = false;
			Task task = filteredTasks.get(i);
			Deadline deadline = task.getDeadline();
			for (int j = i + 1; j < filteredTasks.size(); j++) {
				Task task2 = filteredTasks.get(j);
				Deadline deadline2 = task2.getDeadline();
				if (deadline.calendar != null && deadline2.calendar != null && deadline.equals(deadline2)) {
					if (!taskmanager.contains(task2))
						taskmanager.addTask(task2);
					isClashing = true;
				}
			}
			if (isClashing) {
				if (!taskmanager.contains(task))
					taskmanager.addTask(task);
			}
		}
		FilteredList<Task> clashingTasks = new FilteredList<Task>(taskmanager.getTasks());
		updateFilteredTaskList(new PredicateExpression(new ClashQualifier(clashingTasks)));
	}
	
	/**@@author A0144202Y
	 * Filters the list to show all incomplete tasks.
	 * @param keyword
	 * @throws DuplicateTaskException
	 */
	public void updateFilteredListToShowIncompleteTask() throws DuplicateTaskException {
		TaskManager taskmanager = new TaskManager();
		
		for (int i = 0; i <= filteredTasks.size() - 1; i++){
			boolean isIncompleted = false;
			Task task = filteredTasks.get(i);
			if (!task.getName().toString().contains(" is completed")) isIncompleted = true;
			if (isIncompleted) {
				if (!taskmanager.contains(task))
					taskmanager.addTask(task);
			}
		}
		
		FilteredList<Task> incompleteTasks = new FilteredList<Task>(taskmanager.getTasks());
		updateFilteredTaskList(new PredicateExpression(new ClashQualifier(incompleteTasks)));
	}
	
	/**@@author A0144202Y
	 * Filters the list in the context of the List "keyword" commmand.
	 * @param keyword
	 * @throws DuplicateTaskException
	 */
	public void updateFilteredListToShowUncompleteAndKeywordTasks(String keyword) throws DuplicateTaskException {
		TaskManager uncompletedTaskManager = getUncompletedTaskManager();
		TaskManager uncompletedAndKeywordTaskManager = getUncompletedAndKeywordTaskManager(keyword, uncompletedTaskManager);
		FilteredList<Task> uncompletedandKeywordTasks = new FilteredList<Task>(uncompletedAndKeywordTaskManager.getTasks());
		updateFilteredTaskList(new PredicateExpression(new ClashQualifier(uncompletedandKeywordTasks)));
	}
	
	/**@@author A0144202Y
	 * Gets a TaskManager from the current FilteredList which shows uncompleted tasks.
	 * @return a TaskManager with only uncompleted tasks.
	 * @throws DuplicateTaskException
	 */
	private TaskManager getUncompletedTaskManager() throws DuplicateTaskException {
		TaskManager taskmanager = new TaskManager();
		for(Task t: filteredTasks) {
			boolean isIncompleted = false;
			if (!t.getName().toString().contains(" is completed")) isIncompleted = true;
			if (isIncompleted) {
				if (!taskmanager.contains(t))
					taskmanager.addTask(t);
			}
		}
		return taskmanager;
	}
	
	/**
	 * Gets a TaskManager with uncompleted tasks relevant to keyword
	 * @param keyword
	 * @param uncompletedTaskManager
	 * @return
	 * @throws DuplicateTaskException
	 */
	private TaskManager getUncompletedAndKeywordTaskManager(String keyword, TaskManager uncompletedTaskManager) throws DuplicateTaskException {
		TaskManager taskmanager = new TaskManager();
		for(Task t: uncompletedTaskManager.getTasks()){
			if(getKeywordQualifier(keyword, t)){
				taskmanager.addTask(t);
			}
		}
		return taskmanager;
	}
	
	/**
	 * Calculates the number of days to task deadline and returns true if relevant to keyword.
	 * @param keyword
	 * @param Task
	 * @return true if relevant to the keyword.
	 */
	private boolean getKeywordQualifier(String keyword, Task t) {
		int i;
		switch(keyword.toUpperCase()) {
			case "TODAY": i = 0; break;
			case "TOMORROW": i = 1; break;
			default: i = 0;
		}
		if(t.getDeadline().calendar != null) {
			DateManager datemanager = new DateManager(t.getDeadline().calendar);
			if(datemanager.calculateDaysRemaining() == i){
				return true;
			}
		}
		return false;
	}

	// ========== Inner classes/interfaces used for filtering
	// ==================================================

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
	
	// @@author A0144202Y
		/**
		 * Allows user to find Name case insensitively, with partial keyword 
		 *
		 */
	private class NameQualifier implements Qualifier {
		private Set<String> nameKeyWords;

		NameQualifier(Set<String> nameKeyWords) {
			this.nameKeyWords = nameKeyWords;
		}

		@Override
		public boolean run(ReadOnlyTask person) {
			boolean isContained = false;
			
			for (String temp : nameKeyWords){
				if (person.getName().toString().toLowerCase().contains(temp.toLowerCase()))
				    isContained = true;
				
			}
			return isContained;
		}

		@Override
		public String toString() {
			return "name=" + String.join(", ", nameKeyWords);
		}
	}

	private class ClashQualifier implements Qualifier {
		private FilteredList<Task> tasks;

		ClashQualifier(FilteredList<Task> tasks) {
			this.tasks = tasks;
		}

		@Override
		public boolean run(ReadOnlyTask task) {
			for (Task temp : tasks) {
				if (temp.equals(task)) {
					return true;
				}
			}
			return false;
		}
	}

	/**@@author A0144202Y
	 * Allows user to find tagName case insensitively 
	 *
	 */
	
	private class TagQualifier implements Qualifier {
		private String keyWords;

		TagQualifier(String keyWords) {
			this.keyWords = keyWords;
		}

		@Override // for loop embedded
		public boolean run(ReadOnlyTask task) {
			
			boolean isContains = false;
			for (Tag temp:task.getTags()){
			    if (temp.getTagName().equalsIgnoreCase(keyWords)) 	
				    isContains = true;
			}
			
			return isContains;
	
		}

		@Override
		public String toString() {
			return "name=" + String.join(", ", keyWords);
		}
	}

	
}
