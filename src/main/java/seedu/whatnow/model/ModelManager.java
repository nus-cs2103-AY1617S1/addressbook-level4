package seedu.whatnow.model;

import javafx.collections.transformation.FilteredList;
import seedu.whatnow.commons.core.ComponentManager;
import seedu.whatnow.commons.core.Config;
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.commons.events.model.ConfigChangedEvent;
import seedu.whatnow.commons.events.model.WhatNowChangedEvent;
import seedu.whatnow.commons.exceptions.DataConversionException;
import seedu.whatnow.commons.util.StringUtil;
import seedu.whatnow.logic.commands.Command;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.UniqueTaskList;
import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the WhatNow data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {

	private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
	private static final String TASK_TYPE_FLOATING = "floating";
	private static final String TASK_TYPE_NOT_FLOATING = "not_floating";
	private static final String TASK_STATUS_COMPLETED = "completed";
	private static final String TASK_STATUS_INCOMPLETE = "incomplete";

	private final WhatNow whatNow;
	private final FilteredList<Task> filteredTasks; //
	private final FilteredList<Task> filteredSchedules; //
	private final Stack<Command> stackOfUndo; //
	private final Stack<Command> stackOfRedo; //
	private final Stack<ReadOnlyTask> stackOfOldTask; //
	private final Stack<ReadOnlyTask> stackOfNewTask; //
	private final Stack<ReadOnlyWhatNow> stackOfWhatNow; //
	private final Stack<ReadOnlyTask> stackOfDeletedTasks; //
	private final Stack<ReadOnlyTask> stackOfDeletedTasksRedo; //
	private final Stack<ReadOnlyTask> stackOfDeletedTasksAdd; //
	private final Stack<ReadOnlyTask> stackOfDeletedTasksAddRedo; //
	private final Stack<ReadOnlyTask> stackOfMarkDone; //
	private final Stack<ReadOnlyTask> stackOfMarkUndone;
	private final Stack<String> stackOfMarkDoneTaskTypes; //
	private final Stack<String> stackOfMarkUndoneTaskTypes;
	private final Stack<String> stackOfListTypes;	//
	private final Stack<String> stackOfListTypesRedo;	//
	/**
	 * Initializes a ModelManager with the given WhatNow
	 * WhatNow and its variables should not be null
	 */
	public ModelManager(WhatNow src, UserPrefs userPrefs) {
		super();
		assert src != null;
		assert userPrefs != null;

		logger.fine("Initializing with WhatNow: " + src + " and user prefs " + userPrefs);

		whatNow = new WhatNow(src);
		new Config();
		filteredTasks = new FilteredList<>(whatNow.getTasks());
		filteredSchedules = new FilteredList<>(whatNow.getTasks());
		stackOfUndo = new Stack<>();
		stackOfRedo = new Stack<>();
		stackOfOldTask = new Stack<>();
		stackOfNewTask = new Stack<>();
		stackOfWhatNow = new Stack<>();
		stackOfDeletedTasks = new Stack<>();
		stackOfDeletedTasksRedo = new Stack<>();
		stackOfDeletedTasksAdd = new Stack<>();
		stackOfDeletedTasksAddRedo = new Stack<>();
		stackOfMarkDone= new Stack<>();
		stackOfMarkUndone = new Stack<>();
		stackOfMarkDoneTaskTypes = new Stack<>();
		stackOfMarkUndoneTaskTypes = new Stack<>();
		stackOfListTypes = new Stack<>();
		stackOfListTypesRedo = new Stack<>();
	}

	public ModelManager() {
		this(new WhatNow(), new UserPrefs());
	}

	public ModelManager(ReadOnlyWhatNow initialData, UserPrefs userPrefs) {
		whatNow = new WhatNow(initialData);
		new Config();
		filteredTasks = new FilteredList<>(whatNow.getTasks());
		filteredSchedules = new FilteredList<>(whatNow.getTasks());
		stackOfUndo =  new Stack<>();
		stackOfRedo = new Stack<>();
		stackOfOldTask = new Stack<>();
		stackOfNewTask = new Stack<>();
		stackOfWhatNow = new Stack<>();
		stackOfDeletedTasks = new Stack<>();
		stackOfDeletedTasksRedo = new Stack<>();
		stackOfDeletedTasksAdd = new Stack<>();
		stackOfDeletedTasksAddRedo = new Stack<>();
		stackOfMarkDone = new Stack<>();
		stackOfMarkUndone = new Stack<>();
		stackOfMarkDoneTaskTypes = new Stack<>();
		stackOfMarkUndoneTaskTypes = new Stack<>();
		stackOfListTypes = new Stack<>();
		stackOfListTypesRedo = new Stack<>();
	}

	@Override
	public void resetData(ReadOnlyWhatNow newData) {
		stackOfWhatNow.push(new WhatNow(whatNow));
		whatNow.resetData(newData);
		indicateWhatNowChanged();
	}

	@Override
	public synchronized void revertData() {
		whatNow.revertEmptyWhatNow(stackOfWhatNow.pop());
		indicateWhatNowChanged();
	}

	@Override
	public ReadOnlyWhatNow getWhatNow() {
		return whatNow;
	}

	/** Raises an event to indicate the model has changed */
	private void indicateWhatNowChanged() {
		raise(new WhatNowChangedEvent(whatNow));
	}

	@Override
	public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
		whatNow.removeTask(target);
		indicateWhatNowChanged();
	}
	@Override
	public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
		whatNow.addTask(task);
		updateFilteredListToShowAll();
		indicateWhatNowChanged();
	}

	@Override
	public synchronized void updateTask(ReadOnlyTask old, Task toUpdate) throws TaskNotFoundException, DuplicateTaskException {

		whatNow.updateTask(old, toUpdate);
		indicateWhatNowChanged();
	}

	@Override
	public synchronized void markTask(ReadOnlyTask target) throws TaskNotFoundException {
		whatNow.markTask(target);
		indicateWhatNowChanged();
	}

	@Override
	public synchronized void unMarkTask(ReadOnlyTask target) throws TaskNotFoundException {
		whatNow.unMarkTask(target);
		indicateWhatNowChanged();
	}
	@Override
	public Stack<Command> getUndoStack() {
		return stackOfUndo;
	}
	@Override
	public Stack<Command> getRedoStack() {
		return stackOfRedo;
	}
	@Override
	public Stack<ReadOnlyTask> getOldTask() {
		return stackOfOldTask;
	}
	@Override
	public Stack<ReadOnlyTask> getNewTask() {
		return stackOfNewTask;
	}
	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getAllTaskTypeList() {
		filteredTasks.setPredicate(null);
		return new UnmodifiableObservableList<>(filteredTasks);
	}
	@Override
	public Stack<ReadOnlyTask> getDeletedStackOfTasks() {
		return stackOfDeletedTasks;
	}
	@Override
	public Stack<ReadOnlyTask> getDeletedStackOfTasksRedo() {
		return stackOfDeletedTasksRedo;
	}
	@Override
	public Stack<ReadOnlyTask> getDeletedStackOfTasksAdd() {
		return stackOfDeletedTasksAdd;
	}
	@Override
	public Stack<ReadOnlyTask> getDeletedStackOfTasksAddRedo() {
		return stackOfDeletedTasksAddRedo;
	}
	@Override
	public Stack<ReadOnlyTask> getStackOfMarkDoneTask() {
		return stackOfMarkDone;
	}
	@Override
	public Stack<ReadOnlyTask> getStackOfMarkUndoneTask() {
		return stackOfMarkUndone;
	}
	@Override
	public Stack<String> getStackOfMarkDoneTaskTaskType() {
		return stackOfMarkDoneTaskTypes;
	}
	public Stack<String> getStackOfMarkUndoneTaskTaskType() {
		return stackOfMarkUndoneTaskTypes;
	}
	@Override
	public Stack<String> getStackOfListTypes() {
		return stackOfListTypes;
	}
	@Override 
	public Stack<String> getStackOfListTypesRedo() {
		return stackOfListTypesRedo;
	}
	//=========== Filtered Task List Accessors ===============================================================

	/** Raises an event to indicate the config has changed */
	private void indicateConfigChanged(Path destination, Config config) {
		raise(new ConfigChangedEvent(destination, config));
	}

	@Override
	public synchronized void changeLocation(Path destination, Config config) throws DataConversionException, IOException, TaskNotFoundException {
		indicateConfigChanged(destination, config);
		indicateWhatNowChanged();
	}
	@Override
	public void changeLocation(ReadOnlyTask target) throws DataConversionException, IOException, TaskNotFoundException {

	}
	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
		updateFilteredListToShowAllIncomplete();
		return new UnmodifiableObservableList<>(filteredTasks);
	}
	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getCurrentFilteredTaskList() {
		return new UnmodifiableObservableList<>(filteredTasks);
	}

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList(Set<String> keyword) {
		updateFilteredTaskList(keyword);
		return new UnmodifiableObservableList<>(filteredTasks);
	}

	@Override
	public void updateFilteredListToShowAll() {
		String[] taskType = {TASK_TYPE_FLOATING};
		Set<String> keyword = new HashSet<>(Arrays.asList(taskType));
		updateFilteredTaskList(new PredicateExpression(new TaskTypeQualifier(keyword)));
	}

	@Override
	public void updateFilteredListToShowAllIncomplete() {
		filteredTasks.setPredicate(p -> {
			if ((p.getTaskType().equals((TASK_TYPE_FLOATING)) && (p.getStatus().equals(TASK_STATUS_INCOMPLETE)))) {
				return true;
			} else {
				return false;
			}}
				);
	}

	@Override
	public void updateFilteredListToShowAllCompleted() {
		filteredTasks.setPredicate(p -> {
			if ((p.getTaskType().equals((TASK_TYPE_FLOATING)) && (p.getStatus().equals(TASK_STATUS_COMPLETED)))) {
				return true;
			} else {
				return false;
			}}
				);
	}

	@Override
	public void updateFilteredListToShowAllByStatus(Set<String> keyword) {
		updateFilteredTaskList(new PredicateExpression(new TaskStatusQualifier(keyword)));
	}

	@Override
	public void updateFilteredTaskList(Set<String> keywords){
		filteredTasks.setPredicate(p -> {
			if ((keywords.stream()
					.filter(key -> StringUtil.containsIgnoreCase(p.getName().fullName, key))
					.findAny()
					.isPresent()) && p.getTaskType().equals(TASK_TYPE_FLOATING))  {
				return true;
			} else {
				return false;
			}
		});
	}
	//=========== Filtered Schedule List Accessors ===============================================================

	@Override 
	public UnmodifiableObservableList<ReadOnlyTask> getFilteredScheduleList() {
		updateFilteredScheduleListToShowAllIncomplete();
		return new UnmodifiableObservableList<>(filteredSchedules);
	}

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getCurrentFilteredScheduleList() {
		return new UnmodifiableObservableList<>(filteredSchedules);
	}

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getFilteredScheduleList(Set<String> keyword) {
		updateFilteredScheduleList(keyword);
		return new UnmodifiableObservableList<>(filteredSchedules);
	}

	@Override
	public void updateFilteredScheduleListToShowAll() {
		String[] taskType = {TASK_TYPE_NOT_FLOATING};
		Set<String> keyword = new HashSet<>(Arrays.asList(taskType));
		updateFilteredScheduleList(new PredicateExpression(new TaskTypeQualifier(keyword)));
	}

	@Override
	public void updateFilteredScheduleListToShowAllIncomplete() {
		filteredSchedules.setPredicate(p -> {
			if ((p.getTaskType().equals((TASK_TYPE_NOT_FLOATING)) && (p.getStatus().equals(TASK_STATUS_INCOMPLETE)))) {
				return true;
			} else {
				return false;
			}}
				);
	}
	private void updateFilteredTaskList(Expression expression) {
		filteredTasks.setPredicate(expression::satisfies);
	}

	@Override
	public void updateFilteredScheduleListToShowAllCompleted() {
		filteredSchedules.setPredicate(p -> {
			if ((p.getTaskType().equals((TASK_TYPE_NOT_FLOATING)) && (p.getStatus().equals(TASK_STATUS_COMPLETED)))) {
				return true;
			} else {
				return false;
			}}
				);
	}

	@Override
	public void updateFilteredScheduleListToShowAllByStatus(Set<String> keyword) {
		updateFilteredScheduleList(new PredicateExpression(new TaskStatusQualifier(keyword)));
	}

	@Override
	public void updateFilteredScheduleList(Set<String> keywords){
		filteredSchedules.setPredicate(p -> {
			if ((keywords.stream()
					.filter(key -> StringUtil.containsIgnoreCase(p.getName().fullName, key))
					.findAny()
					.isPresent()) && p.getTaskType().equals(TASK_TYPE_NOT_FLOATING))  {
				return true;
			} else {
				return false;
			}
		});
	}

	private void updateFilteredScheduleList(Expression expression) {
		filteredSchedules.setPredicate(expression::satisfies);
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

	private class TaskStatusQualifier implements Qualifier {
		private Set<String> status;

		TaskStatusQualifier(Set<String> status) {
			this.status = status;
		}

		@Override
		public boolean run(ReadOnlyTask task) {
			return status.stream()
					.filter(keyword -> StringUtil.containsIgnoreCase(task.getStatus(), keyword))
					.findAny()
					.isPresent();
		}

		@Override
		public String toString() {
			return "Status=" + String.join(", ", status);
		}
	}

	private class TaskTypeQualifier implements Qualifier {
		private Set<String> taskType;

		TaskTypeQualifier(Set<String> taskType) {
			this.taskType = taskType;
		}

		@Override
		public boolean run(ReadOnlyTask task) {
			return taskType.stream()
					.filter(keyword -> StringUtil.containsIgnoreCase(task.getTaskType(), keyword))
					.findAny()
					.isPresent();
		}

		@Override
		public String toString() {
			return "TaskType=" + String.join(", ", taskType);
		}
	}
}
