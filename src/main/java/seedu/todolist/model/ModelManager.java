package seedu.todolist.model;

import javafx.collections.transformation.FilteredList;
import seedu.todolist.commons.core.ComponentManager;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.events.model.ToDoListChangedEvent;
import seedu.todolist.commons.util.StringUtil;
import seedu.todolist.logic.commands.FindCommand;
import seedu.todolist.logic.commands.ListCommand;
import seedu.todolist.model.parser.DateParser;
import seedu.todolist.model.task.ReadOnlyTask;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList;
import seedu.todolist.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.todolist.ui.MainWindow;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ToDoList ToDoList;
    
    private final FilteredList<Task> filteredAllTasks;
    private final FilteredList<Task> filteredCompleteTasks;
    private final FilteredList<Task> filteredIncompleteTasks;
    private final FilteredList<Task> filteredOverdueTasks;
    
    private final Stack<ReadOnlyToDoList> ToDoListHistory;
    private final Stack<ReadOnlyToDoList> ToDoListUndoHistory;
    
    private String currentTab;

    /**
     * Initializes a ModelManager with the given ToDoList
     * ToDoList and its variables should not be null
     */
    public ModelManager(ToDoList src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        ToDoList = new ToDoList(src);
        filteredAllTasks = new FilteredList<>(ToDoList.getAllTasks());
        filteredCompleteTasks = new FilteredList<>(ToDoList.getCompletedTasks());
        filteredIncompleteTasks = new FilteredList<>(ToDoList.getIncompleteTasks());
        filteredOverdueTasks = new FilteredList<>(ToDoList.getOverdueTasks());
        ToDoListHistory = new Stack<ReadOnlyToDoList>();
        ToDoListUndoHistory = new Stack<ReadOnlyToDoList>();
        currentTab = MainWindow.TAB_TASK_INCOMPLETE;
    }

    public ModelManager() {
        this(new ToDoList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyToDoList initialData, UserPrefs userPrefs) {
        ToDoList = new ToDoList(initialData);
        filteredAllTasks = new FilteredList<>(ToDoList.getAllTasks());
        filteredCompleteTasks = new FilteredList<>(ToDoList.getCompletedTasks());
        filteredIncompleteTasks = new FilteredList<>(ToDoList.getIncompleteTasks());
        filteredOverdueTasks = new FilteredList<>(ToDoList.getOverdueTasks());
        ToDoListHistory = new Stack<ReadOnlyToDoList>();
        ToDoListUndoHistory = new Stack<ReadOnlyToDoList>();
        currentTab = MainWindow.TAB_TASK_INCOMPLETE;
    }

    @Override
    public void resetData(ReadOnlyToDoList newData) {
    	ToDoList currentToDoList = new ToDoList(this.ToDoList);
    	updateToDoListHistory(currentToDoList);
    	ToDoList.resetData(newData);
        indicateToDoListChanged();
    }

    @Override
    public ReadOnlyToDoList getToDoList() {
        return ToDoList;
    }

    //@@author A0153736B
    @Override
    public synchronized void undoToDoList() throws EmptyStackException {
    	ToDoList currentToDoList = new ToDoList(this.ToDoList);
    	ToDoList.resetData(ToDoListHistory.pop());
    	ToDoListUndoHistory.push(currentToDoList);
    	updateFilteredListToShowAll();
    	indicateToDoListChanged();
    }
    
    @Override
    public synchronized void redoToDoList() throws EmptyStackException {
    	ToDoList previousToDoList = new ToDoList(this.ToDoList);
    	ToDoList.resetData(ToDoListUndoHistory.pop());
    	ToDoListHistory.push(previousToDoList);
    	updateFilteredListToShowAll();
    	indicateToDoListChanged();
    }
    //@@author
    
    /** Raises an event to indicate the model has changed */
    private void indicateToDoListChanged() {
        raise(new ToDoListChangedEvent(ToDoList));
    }
    
    @Override
    public int getIndexFromIncompleteList(Task task) {
        return filteredIncompleteTasks.indexOf(task);
    }
    
    @Override
    public int getIndexFromOverdueList(Task task) {
        return filteredOverdueTasks.indexOf(task);
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
    	ToDoList previousToDoList = new ToDoList(this.ToDoList);
    	ToDoList.markTask(tasks);
    	updateToDoListHistory(previousToDoList);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask... tasks) throws TaskNotFoundException {
        ToDoList previousToDoList = new ToDoList(this.ToDoList);
    	ToDoList.removeTask(tasks);
    	updateToDoListHistory(previousToDoList);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
    	ToDoList previousToDoList = new ToDoList(this.ToDoList);
    	ToDoList.addTask(task);
    	updateToDoListHistory(previousToDoList);
        updateFilteredListToShowAll();
        indicateToDoListChanged();
    }
    
    @Override
    //@@author A0146682X
    public synchronized void editTask(ReadOnlyTask target, Task replacement) throws TaskNotFoundException {
    	ToDoList previousToDoList = new ToDoList(this.ToDoList);
    	ToDoList.editTask(target, replacement);
    	updateToDoListHistory(previousToDoList);
        indicateToDoListChanged();
    }
    
    //@@author A0153736B
    /**
     * Update Stacks of ToDoListHistory and ToDoListUndoHistory when task list of ToDoList is going to be changed.
     * @param previousToDoList
     */
    private void updateToDoListHistory(ToDoList previousToDoList) {
    	ToDoListHistory.push(previousToDoList);
    	ToDoListUndoHistory.clear();
    }
    //@@author

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredAllTaskList() {
        return new UnmodifiableObservableList<>(filteredAllTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredCompleteTaskList() {
        return new UnmodifiableObservableList<>(filteredCompleteTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredIncompleteTaskList() {
        return new UnmodifiableObservableList<>(filteredIncompleteTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredOverdueTaskList() {
        return new UnmodifiableObservableList<>(filteredOverdueTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredAllTasks.setPredicate(null);
        filteredCompleteTasks.setPredicate(null);
        filteredIncompleteTasks.setPredicate(null);
        filteredOverdueTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(List<String> keywords, String findType){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords, findType)));
    }
    
    private void updateFilteredTaskList(Expression expression) {
        filteredAllTasks.setPredicate(expression::satisfies);
        filteredCompleteTasks.setPredicate(expression::satisfies);
        filteredIncompleteTasks.setPredicate(expression::satisfies);
        filteredOverdueTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredTaskList(String filter) throws DateTimeException {
    	updateFilteredTaskList(new PredicateExpression(new DateQualifier(filter)));
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

    //@@author A0153736B
    /**
     * Determine if task name contain any/all/phrase of provided keywords according to the provided findType.
     */    
    private class NameQualifier implements Qualifier {
        private List<String> nameKeyWords;
        private String findType;

        NameQualifier(List<String> nameKeyWords, String findType) {
            this.nameKeyWords = nameKeyWords;
            this.findType = findType;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (FindCommand.FINDTYPE_ALL.equals(findType)) {
            	for (String keyword : nameKeyWords) {
            		if (!StringUtil.containsIgnoreCase(task.getName().fullName, keyword)) {
            			return false;
            		}
            	}
            	return true;
            }
            else if (FindCommand.FINDTYPE_PHRASE.equals(findType)) {
            	String keyword = String.join(" ", nameKeyWords).trim().toLowerCase();
            	return task.getName().fullName.toLowerCase().contains(keyword);
            }
            else {
            	return nameKeyWords.stream()
            			.filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
            			.findAny()
            			.isPresent();
            }
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    /**
     * Determine if task date is within the period of the provided dateFilter.
     * 
     * @throws DateTimeException if the date of the provided dateFilter is invalid
     */  
    private class DateQualifier implements Qualifier {
        private String dateFilter;

        DateQualifier(String dateFilter) {
            this.dateFilter = dateFilter;
        }
        
        @Override
        public boolean run(ReadOnlyTask task) throws DateTimeException {
        	if (task.getInterval().isFloat()) {
        		return false;
        	}
        	
        	LocalDate currentDate = LocalDate.now();
        	
        	int currentDayOfWeek = currentDate.getDayOfWeek().getValue();
        	LocalDate currentWeekStart = currentDate.minusDays(currentDayOfWeek);
        	LocalDate currentWeekEnd = currentDate.plusDays(6-currentDayOfWeek);
        	
        	int currentDayOfMonth = currentDate.getDayOfMonth();
        	LocalDate currentMonthStart = currentDate.minusDays(currentDayOfMonth-1);
        	LocalDate currentMonthEnd = currentDate.plusDays(currentDate.lengthOfMonth()-currentDayOfMonth);
        	
        	LocalDate taskEndDate = task.getInterval().getEndDate().getDate();
        	LocalDate taskStartDate = (task.getInterval().getStartDate() == null? 
        			taskEndDate: task.getInterval().getStartDate().getDate());

            if (ListCommand.FILTER_TODAY.equals(dateFilter)) {
               	return (!taskEndDate.isBefore(currentDate) && !taskStartDate.isAfter(currentDate));
            }
            else if (ListCommand.FILTER_WEEK.equals(dateFilter)) {
               	return (!taskEndDate.isBefore(currentWeekStart) && !taskStartDate.isAfter(currentWeekEnd));
            }
            else if (ListCommand.FILTER_MONTH.equals(dateFilter)) {
               	return (!taskEndDate.isBefore(currentMonthStart) && !taskStartDate.isAfter(currentMonthEnd));
            } 
            else {
                LocalDate date = DateParser.parseDate(dateFilter);
                return (!taskEndDate.isBefore(date) && !taskStartDate.isAfter(date));
            }  	
        }

        @Override
        public String toString() {
            return "date=" + dateFilter;
        }
    }
    
}
