package seedu.todolist.model;

import javafx.collections.transformation.FilteredList;
import seedu.todolist.commons.core.ComponentManager;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.events.model.AddressBookChangedEvent;
import seedu.todolist.commons.util.StringUtil;
import seedu.todolist.model.parser.DateParser;
import seedu.todolist.model.task.ReadOnlyTask;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList;
import seedu.todolist.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.todolist.ui.MainWindow;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    
    private final FilteredList<Task> filteredAllTasks;
    private final FilteredList<Task> filteredCompleteTasks;
    private final FilteredList<Task> filteredIncompleteTasks;
    
    private final Stack<ReadOnlyAddressBook> addressBookHistory;
    
    private String currentTab;

    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(AddressBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        addressBook = new AddressBook(src);
        filteredAllTasks = new FilteredList<>(addressBook.getAllTasks());
        filteredCompleteTasks = new FilteredList<>(addressBook.getCompletedTasks());
        filteredIncompleteTasks = new FilteredList<>(addressBook.getIncompleteTasks());
        addressBookHistory = new Stack<ReadOnlyAddressBook>();
        currentTab = MainWindow.TAB_TASK_INCOMPLETE;
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyAddressBook initialData, UserPrefs userPrefs) {
        addressBook = new AddressBook(initialData);
        filteredAllTasks = new FilteredList<>(addressBook.getAllTasks());
        filteredCompleteTasks = new FilteredList<>(addressBook.getCompletedTasks());
        filteredIncompleteTasks = new FilteredList<>(addressBook.getIncompleteTasks());
        addressBookHistory = new Stack<ReadOnlyAddressBook>();
        currentTab = MainWindow.TAB_TASK_INCOMPLETE;
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
    	addressBookHistory.push(new AddressBook(this.addressBook));
    	addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    //@@author A0153736B
    @Override
    public synchronized void undoAddressBook() throws EmptyStackException {
    	addressBook.resetData(addressBookHistory.pop());
    	updateFilteredListToShowAll();
    	indicateAddressBookChanged();
    }
    //@@author
    
    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
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
    	AddressBook previousAddressBook = new AddressBook(this.addressBook);
    	addressBook.markTask(tasks);
    	addressBookHistory.push(previousAddressBook);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask... tasks) throws TaskNotFoundException {
        AddressBook previousAddressBook = new AddressBook(this.addressBook);
    	addressBook.removeTask(tasks);
    	addressBookHistory.push(previousAddressBook);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
    	AddressBook previousAddressBook = new AddressBook(this.addressBook);
    	addressBook.addTask(task);
    	addressBookHistory.push(previousAddressBook);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    @Override
    //@author A0146682X
    public synchronized void editTask(ReadOnlyTask target, Task replacement) throws TaskNotFoundException {
    	AddressBook previousAddressBook = new AddressBook(this.addressBook);
    	addressBook.editTask(target, replacement);
    	addressBookHistory.push(previousAddressBook);
        indicateAddressBookChanged();
    }
    //@author

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredAllTaskList() {
        return new UnmodifiableObservableList<>(filteredAllTasks);
    }
    
    //@@author A0138601M
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredCompleteTaskList() {
        return new UnmodifiableObservableList<>(filteredCompleteTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredIncompleteTaskList() {
        return new UnmodifiableObservableList<>(filteredIncompleteTasks);
    }
    //@@author

    @Override
    public void updateFilteredListToShowAll() {
        filteredAllTasks.setPredicate(null);
        filteredCompleteTasks.setPredicate(null);
        filteredIncompleteTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords, String findType){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords, findType)));
    }
    
    private void updateFilteredTaskList(Expression expression) {
        filteredAllTasks.setPredicate(expression::satisfies);
        filteredCompleteTasks.setPredicate(expression::satisfies);
        filteredIncompleteTasks.setPredicate(expression::satisfies);
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

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;
        private String findType;

        NameQualifier(Set<String> nameKeyWords, String findType) {
            this.nameKeyWords = nameKeyWords;
            this.findType = findType;
        }

        //@@author A0153736B
        @Override
        public boolean run(ReadOnlyTask task) {
            if (findType.equals("all")) {
            	for (String keyword : nameKeyWords) {
            		if (!StringUtil.containsIgnoreCase(task.getName().fullName, keyword)) {
            			return false;
            		}
            	}
            	return true;
            }
            else if (findType.equals("exactly")) {
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
     	//@@author

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
    //@@author A0153736B
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
        	LocalDate currentMonthStart = currentDate.minusDays(currentDayOfMonth+1);
        	LocalDate currentMonthEnd = currentDate.plusDays(currentDate.lengthOfMonth()-currentDayOfMonth);
        	
        	if (task.getInterval().isDeadlineWithTime() || task.getInterval().isDeadlineWithoutTime()) {
            	LocalDate taskEndDate = task.getInterval().getEndDate().getDate();
            	if ("today".equals(dateFilter)) {
                	return taskEndDate.equals(currentDate);
                }
                else if ("week".equals(dateFilter)) {
                	return (!taskEndDate.isBefore(currentWeekStart) && !taskEndDate.isAfter(currentWeekEnd));
                }
                else if ("month".equals(dateFilter)) {
                	return (!taskEndDate.isBefore(currentMonthStart) && !taskEndDate.isAfter(currentMonthEnd));
                } 
                else {
                    LocalDate date = DateParser.parseDate(dateFilter);
                    return taskEndDate.equals(date);
                }
        	}
        	else {
        		LocalDate taskStartDate = task.getInterval().getStartDate().getDate();
            	LocalDate taskEndDate = task.getInterval().getEndDate().getDate();
            	if ("today".equals(dateFilter)) {
                	return (!taskEndDate.isBefore(currentDate) && !taskStartDate.isAfter(currentDate));
                }
                else if ("week".equals(dateFilter)) {
                	return (!taskEndDate.isBefore(currentWeekStart) && !taskStartDate.isAfter(currentWeekEnd));
                }
                else if ("month".equals(dateFilter)) {
                	return (!taskEndDate.isBefore(currentMonthStart) && !taskStartDate.isAfter(currentMonthEnd));
                } 
                else {
                    LocalDate date = DateParser.parseDate(dateFilter);
                	return (!taskEndDate.isBefore(date) && !taskStartDate.isAfter(date));
                }  	
        	}
        }

        @Override
        public String toString() {
            return "date=" + dateFilter;
        }
    }
    
}
