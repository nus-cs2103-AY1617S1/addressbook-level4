package seedu.unburden.model;

import javafx.collections.transformation.FilteredList;
import seedu.unburden.commons.core.ComponentManager;
import seedu.unburden.commons.core.LogsCenter;
import seedu.unburden.commons.core.UnmodifiableObservableList;
import seedu.unburden.commons.events.model.ListOfTaskChangedEvent;
import seedu.unburden.commons.util.StringUtil;
import seedu.unburden.commons.exceptions.*;
import seedu.unburden.model.task.ReadOnlyTask;
import seedu.unburden.model.task.Task;
import seedu.unburden.model.task.UniqueTaskList;
import seedu.unburden.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.base.Predicate;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ListOfTask listOfTask;
    private final FilteredList<Task> filteredTasks;
    private ArrayDeque<ListOfTask> prevLists = new ArrayDeque<ListOfTask>();
    private ArrayDeque<ListOfTask> undoHistory = new ArrayDeque<ListOfTask>();

    /**
     * Initializes a ModelManager with the given ListOfTask
     * ListOfTask and its variables should not be null
     */
    public ModelManager(ListOfTask src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        listOfTask = new ListOfTask(src);
        filteredTasks = new FilteredList<>(listOfTask.getTasks());
    }

    public ModelManager() {
        this(new ListOfTask(), new UserPrefs());
    }

    public ModelManager(ReadOnlyListOfTask initialData, UserPrefs userPrefs) {
        listOfTask = new ListOfTask(initialData);
        filteredTasks = new FilteredList<>(listOfTask.getTasks());
    }

    @Override
    public void resetData(ReadOnlyListOfTask newData) {
    	prevLists.push(listOfTask);
        listOfTask.resetData(newData);
        indicateTaskListChanged();
    }

    @Override
    public ReadOnlyListOfTask getListOfTask() {
        return listOfTask;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskListChanged() {
        raise(new ListOfTaskChangedEvent(listOfTask));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        listOfTask.removeTask(target);
        indicateTaskListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        listOfTask.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskListChanged();
    }
    
    @Override
    public synchronized void editTask(ReadOnlyTask target, String args) throws UniqueTaskList.TaskNotFoundException, IllegalValueException {
        listOfTask.editTask(target, args);
        updateFilteredListToShowAll();
        indicateTaskListChanged();
    }
    
    @Override 
    public synchronized void doneTask(ReadOnlyTask taskToDone, boolean isDone){
    	listOfTask.doneTask(taskToDone,isDone);
    	updateFilteredListToShowAll();
    	indicateTaskListChanged();
    }
    
    @Override 
    public synchronized void undoneTask(ReadOnlyTask taskToDone, boolean isunDone){
    	listOfTask.doneTask(taskToDone,isunDone);
    	updateFilteredListToShowAll();
    	indicateTaskListChanged();
    }
    
    @Override
    public synchronized void saveToPrevLists() {
    	prevLists.push(new ListOfTask(listOfTask));
    	
    } 
    
    @Override
    public synchronized void loadFromPrevLists() throws NoSuchElementException {
    	ListOfTask oldCopy = prevLists.pop();
    	undoHistory.push(new ListOfTask(oldCopy));
    	listOfTask.setTasks(oldCopy.getTasks());
    	indicateTaskListChanged();
    }
    
    @Override
    public synchronized void loadFromUndoHistory() throws NoSuchElementException {
    	ListOfTask oldCopy = undoHistory.pop();
    	prevLists.push(new ListOfTask(oldCopy));
    	listOfTask.setTasks(oldCopy.getTasks());
    	indicateTaskListChanged();
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
    public void updateFilteredTaskList(java.util.function.Predicate<? super Task> predicate){
        filteredTasks.setPredicate(predicate);
    }
    
    @Override
    public void updateFilteredTaskListForDate(Set<String> keywords){
    	updateFilteredPersonList(new PredicateExpression(new DateQualifier(keywords)));
    }
	
	@Override
	public void updateFilteredListToShow(java.util.function.Predicate<? super Task> predicate){
    	filteredTasks.setPredicate(predicate);
    }

    private void updateFilteredPersonList(Expression expression) {
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
            return checkName(task);
        }

		/**
		 * @param task
		 * @return
		 */
		/*private boolean checkTags(ReadOnlyTask task) {
			return nameKeyWords.stream()
			.filter(keyword -> StringUtil.tagsContainsIgnoreCase(task.getTags(), keyword))
			.findAny()
			.isPresent();
		}*/

		/**
		 * @param task
		 * @return
		 */
		private boolean checkName(ReadOnlyTask task) {
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
    
    private class DateQualifier implements Qualifier {
    	private Set<String> dateKeyWords;
    	
    	DateQualifier(Set<String> dateKeyWords){
    		this.dateKeyWords = dateKeyWords;
    	}
    	
    	@Override
    	public boolean run(ReadOnlyTask task){
    		return dateKeyWords.stream()
    				.filter(keyword -> StringUtil.containsDate(task.getDate().fullDate, keyword))
    				.findAny()
    				.isPresent();
    	}
    	
    	@Override
        public String toString() {
    		return "date=" + String.join(", ", dateKeyWords);
        }
    }

}
