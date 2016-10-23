package seedu.taskitty.model;

import javafx.collections.transformation.FilteredList;
import seedu.taskitty.commons.core.ComponentManager;
import seedu.taskitty.commons.core.LogsCenter;
import seedu.taskitty.commons.core.UnmodifiableObservableList;
import seedu.taskitty.commons.events.model.TaskManagerChangedEvent;
import seedu.taskitty.commons.exceptions.NoPreviousCommandException;
import seedu.taskitty.commons.util.StringUtil;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.model.task.UniqueTaskList;
import seedu.taskitty.model.task.UniqueTaskList.DuplicateMarkAsDoneException;
import seedu.taskitty.model.task.UniqueTaskList.TaskNotFoundException;

import java.time.LocalDate;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<Task> allTasks;

    private FilteredList<Task> filteredTodos;
    private FilteredList<Task> filteredDeadlines;
    private FilteredList<Task> filteredEvents;
    
    private final Stack<ReadOnlyTaskManager> historyTaskManagers;
    private final Stack<String> historyCommands;

    private final Stack<Predicate> historyPredicates;

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
        allTasks = new FilteredList<>(taskManager.getAllTasks());
        filteredTodos = new FilteredList<>(taskManager.getFilteredTodos());
        filteredDeadlines = new FilteredList<>(taskManager.getFilteredDeadlines());
        filteredEvents = new FilteredList<>(taskManager.getFilteredEvents());
        historyTaskManagers = new Stack<ReadOnlyTaskManager>();
        historyCommands = new Stack<String>();
        historyPredicates = new Stack<Predicate>();
        taskManager.sortList();
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        allTasks = new FilteredList<>(taskManager.getAllTasks());
        filteredTodos = new FilteredList<>(taskManager.getFilteredTodos());
        filteredDeadlines = new FilteredList<>(taskManager.getFilteredDeadlines());
        filteredEvents = new FilteredList<>(taskManager.getFilteredEvents());
        historyTaskManagers = new Stack<ReadOnlyTaskManager>();
        historyCommands = new Stack<String>();
        historyPredicates = new Stack<Predicate>();
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
        updateFilters();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilters();
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    
    //@@ author A0139052L
    public synchronized String undo() throws NoPreviousCommandException {
        if (!hasPreviousCommand()) {            
            throw new NoPreviousCommandException(null);
        }
        assert !historyPredicates.isEmpty() && !historyTaskManagers.isEmpty();
        resetData(getPreviousTaskManager());   
        updateFilteredTaskList(getPreviousPredicate());
        return getPreviousCommand();
    }
    
    public synchronized void saveState(String command) {
        historyTaskManagers.push(new TaskManager(taskManager));
        historyCommands.push(command);
        historyPredicates.push(filteredTodos.getPredicate());
    }
    
    public synchronized void removeUnchangedState() {
        historyTaskManagers.pop();
        historyCommands.pop();
        historyPredicates.pop();
    }
    
    private ReadOnlyTaskManager getPreviousTaskManager() {
        return historyTaskManagers.pop();
    }
    
    private Predicate getPreviousPredicate() {
        return historyPredicates.pop();
    }
    
    private String getPreviousCommand() {
        return historyCommands.pop();
    }
    
    private boolean hasPreviousCommand() {
        return !historyCommands.isEmpty();
    }
    
    //@@author A0135793W
    private void updateFilters() {
        filteredTodos = new FilteredList<>(taskManager.getFilteredTodos());
        filteredDeadlines = new FilteredList<>(taskManager.getFilteredDeadlines());
        filteredEvents = new FilteredList<>(taskManager.getFilteredEvents());     
    }
    //@@author
        
    @Override
    public synchronized void doneTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException, DuplicateMarkAsDoneException{
    	taskManager.doneTask(target);
    	updateFilters();
    	updateFilteredListToShowAll();
    	indicateTaskManagerChanged();
    }
    
    //@@author A0135793W
   	@Override
    public synchronized void editTask(ReadOnlyTask target, Task task) throws UniqueTaskList.TaskNotFoundException, UniqueTaskList.DuplicateTaskException {
   	    taskManager.addTask(task);
        indicateTaskManagerChanged();
        taskManager.removeTask(target);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
   	//@@author

    //=========== Filtered Person List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getTaskList() {
        return new UnmodifiableObservableList<>(allTasks);
    }
    
    //@@author A0139930B
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTodoList() {
        return new UnmodifiableObservableList<>(filteredTodos);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineList() {
        return new UnmodifiableObservableList<>(filteredDeadlines);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList() {
        return new UnmodifiableObservableList<>(filteredEvents);
    }
    
    //@@author
    @Override
    public void updateFilteredListToShowAll() {
        allTasks.setPredicate(null);
        filteredTodos.setPredicate(null);
        filteredDeadlines.setPredicate(null);
        filteredEvents.setPredicate(null);
    }
    
    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }
    
    @Override
    public void updateFilteredDoneList() {
    	updateFilteredTaskList(new PredicateExpression(p -> p.getIsDone() == true));
    }
    	

	@Override
	public void updateFilteredDateTaskList(LocalDate date, boolean hasDate) {
		allTasks.setPredicate(p -> p.isTodo() || isDeadlineAndIsNotAfterDate(p, date) || isEventAndDateIsWithinEventPeriod(p, date));
		filteredTodos.setPredicate(null);
		if (hasDate) {
			filteredDeadlines.setPredicate(p -> isDeadlineAndIsNotAfterDate(p, date));
		}
		filteredEvents.setPredicate(p -> isEventAndDateIsWithinEventPeriod(p, date));
	}
	
	
    private void updateFilteredTaskList(Expression expression) {
        allTasks.setPredicate(expression::satisfies);
        filteredTodos.setPredicate(expression::satisfies);
        filteredDeadlines.setPredicate(expression::satisfies);
        filteredEvents.setPredicate(expression::satisfies);
    }
    
    private void updateFilteredTaskList(Predicate previousPredicate) {
        allTasks.setPredicate(previousPredicate);
        filteredTodos.setPredicate(previousPredicate);
        filteredDeadlines.setPredicate(previousPredicate);
        filteredEvents.setPredicate(previousPredicate);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask person);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask person) {
            return qualifier.run(person);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask person);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask person) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(person.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    /**
     * Evaluates if the task is a deadline and is not after the specified date.
     * @param task
     * @param date
     * @return the evaluated boolean expression
     */
    private boolean isDeadlineAndIsNotAfterDate(Task task, LocalDate date) {
		return task.isDeadline() && !task.getPeriod().getEndDate().getDate().isAfter(date);
	}
	/**
	 * Evaluates if the task is an event and the specified date is within the event period.
	 * @param task
	 * @param date
	 * @return the evaluated boolean expression
	 */
	private boolean isEventAndDateIsWithinEventPeriod(Task task, LocalDate date) {
		return task.isEvent() && !(task.getPeriod().getEndDate().getDate().isBefore(date) || task.getPeriod().getStartDate().getDate().isAfter(date));
	}


}
