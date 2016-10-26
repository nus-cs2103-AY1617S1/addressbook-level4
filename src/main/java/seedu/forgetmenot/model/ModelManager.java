package seedu.forgetmenot.model;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.forgetmenot.commons.core.ComponentManager;
import seedu.forgetmenot.commons.core.LogsCenter;
import seedu.forgetmenot.commons.core.UnmodifiableObservableList;
import seedu.forgetmenot.commons.events.model.TaskManagerChangedEvent;
import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.commons.util.StringUtil;
import seedu.forgetmenot.logic.commands.ShowCommand;
import seedu.forgetmenot.logic.commands.ShowDoneCommand;
import seedu.forgetmenot.model.task.Done;
import seedu.forgetmenot.model.task.ReadOnlyTask;
import seedu.forgetmenot.model.task.Task;
import seedu.forgetmenot.model.task.Time;
import seedu.forgetmenot.model.task.UniqueTaskList;
import seedu.forgetmenot.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.forgetmenot.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
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
    
    //@@author A0139198N
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
        indicateTaskManagerChanged();
    }
    
    @Override
    public void loadFromUndoHistory() throws NoSuchElementException {
        TaskManager oldManager = undoHistory.pop();
        taskManagerHistory.push(new TaskManager(taskManager));
        taskManager.setTasks(oldManager.getTasks());
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
    public synchronized void sortTasks() {
        taskManager.sortTasksList();
    }
    
    //@@author A0139198N
    @Override
    public synchronized void doneTask(ReadOnlyTask target) throws TaskNotFoundException {
    	taskManager.doneTask(target);
    	updateFilteredTaskListToShowNotDone();
    	indicateTaskManagerChanged();
    	
    }
    
    //@@author A0139198N
    @Override
    public synchronized void undoneTask(ReadOnlyTask target) throws TaskNotFoundException {
    	taskManager.undoneTask(target);
    	updateFilteredTaskListToShowDone();
    	indicateTaskManagerChanged();
    	
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    
    @Override
    public synchronized void addRecurringTask(ReadOnlyTask task, String days) throws DuplicateTaskException, IllegalValueException {
        
        //Recurring task with only end time.
        if (task.getStartTime().appearOnUIFormat().equals("-") && !task.getEndTime().appearOnUIFormat().equals("")) {
            addTask(new Task(
                    task.getName(), 
                    new Done(false),
                    new Time(""),
                    new Time(days + " after " + task.getEndTime().appearOnUIFormat()),
                    task.getRecurrence()
                    ));
        }
        //Recurring task with only start time.
        else if (!task.getStartTime().appearOnUIFormat().equals("-") && task.getEndTime().appearOnUIFormat().equals("-")) {
            addTask(new Task(
                    task.getName(), 
                    new Done(false),
                    new Time(days + " after " + task.getStartTime().appearOnUIFormat()),
                    new Time(""),
                    task.getRecurrence()
                    ));
        }
        //Recurring task wth both start and end times  
        else if (!task.getStartTime().appearOnUIFormat().equals("") && !task.getEndTime().appearOnUIFormat().equals("")) {
            addTask(new Task(
                    task.getName(), 
                    new Done(false),
                    new Time(days + " after " + task.getStartTime().appearOnUIFormat()),
                    new Time(days + " after " + task.getEndTime().appearOnUIFormat()),
                    task.getRecurrence()
                    ));
        }
        
        updateFilteredTaskListToShowNotDone();
        indicateTaskManagerChanged();
    }
    
    @Override
    public synchronized void editTask(ReadOnlyTask task, String newName, String newStart, String newEnd, String newRecur) throws TaskNotFoundException, IllegalValueException {
        if (newName != null)
            taskManager.editTaskName(task, newName);
        
        if (newStart != null)
            taskManager.editTaskStartTime(task, newStart);
        
        if (newEnd != null)
            taskManager.editTaskEndTime(task, newEnd);
        
        if (newRecur != null)
            taskManager.editTaskRecurFreq(task, newRecur);
        
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
    	sortTasks();
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
    	sortTasks();
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
    	sortTasks();
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    //@@author A0139198N
    @Override
    public void updateFilteredTaskListToShowDone() {
    	sortTasks();
    	filteredTasks.setPredicate(isDone());
    }
    
    //@@author A0139198N
    @Override
    public void updateFilteredTaskListToShowNotDone() {
    	sortTasks();
    	filteredTasks.setPredicate(isNotDone());
    }
    
    //@@author A0139198N
    @Override
    public void updateFilteredTaskListToShowDate(String date) {
    	sortTasks();
    	filteredTasks.setPredicate(filterByDate(date));
    }
    
    //@@author A0139198N
    @Override
    public void updateFilteredTaskListToShowOverdue() {
        filteredTasks.setPredicate(isOverdue());
        taskManager.counter();
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
    
    //@@author A0139198N
    public static Predicate<Task> isDone() {
    	return t -> t.getDone().value == true;
    }
    
    //@@author A0139198N
    public static Predicate<Task> filterByDate(String date) {
    	return t -> (t.getStartTime().appearOnUIFormatForDate().equals(date)
    			|| t.getEndTime().appearOnUIFormatForDate().equals(date));
    }
    
    //@@author A0139198N
    public static Predicate<Task> isNotDone() {
    	return t -> t.getDone().value == false;
    }
    
    //@@author A0139198N
    public static Predicate<Task> isOverdue() {
        return t -> t.checkOverdue() == true;
    }
}
