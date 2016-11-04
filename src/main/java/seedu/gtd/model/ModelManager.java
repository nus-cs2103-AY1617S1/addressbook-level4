package seedu.gtd.model;

import javafx.collections.transformation.FilteredList;
import seedu.gtd.commons.core.ComponentManager;
import seedu.gtd.commons.core.Config;
import seedu.gtd.commons.core.LogsCenter;
import seedu.gtd.commons.core.UnmodifiableObservableList;
import seedu.gtd.commons.events.model.AddressBookChangedEvent;
import seedu.gtd.commons.exceptions.DataConversionException;
import seedu.gtd.commons.util.ConfigUtil;
import seedu.gtd.commons.util.StringUtil;
import seedu.gtd.model.task.Task;
import seedu.gtd.model.tag.Tag;
import seedu.gtd.model.tag.UniqueTagList;
import seedu.gtd.model.task.ReadOnlyTask;
import seedu.gtd.model.task.UniqueTaskList;
import seedu.gtd.model.task.UniqueTaskList.TaskNotFoundException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
	//@@author addressbook-level4
	
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private AddressBook addressBook;
    private final FilteredList<Task> filteredTasks;
    private Stack<AddressBook> previousAddressBook;

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
        filteredTasks = new FilteredList<>(addressBook.getTasks());
        previousAddressBook = new Stack<AddressBook>();
        previousAddressBook.push(new AddressBook(addressBook));
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyAddressBook initialData, UserPrefs userPrefs) {
        addressBook = new AddressBook(initialData);
        filteredTasks = new FilteredList<>(addressBook.getTasks());
        previousAddressBook = new Stack<AddressBook>();
        previousAddressBook.push(new AddressBook(addressBook));
    }

    private void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }
    
    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }
    
    private void savePreviousAddressBook() {
    	previousAddressBook.push(new AddressBook(addressBook));
    }
    
    @Override
    public boolean undoAddressBookChange() {
    	if(previousAddressBook.isEmpty()) {
    		return false;
    	}
    	else{
    		resetData(previousAddressBook.pop());
    		return true;
    	}
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
    	savePreviousAddressBook();
        addressBook.removeTask(target);
        indicateAddressBookChanged();
    }
    
    @Override
    public synchronized void doneTask(int targetIndex, Task task) throws TaskNotFoundException {
    	savePreviousAddressBook();
    	System.out.println("in model manager");
        addressBook.doneTask(targetIndex, task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
    	savePreviousAddressBook();
        addressBook.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    //@@author A0146130W
    @Override
    public synchronized void editTask(int targetIndex, Task task) throws TaskNotFoundException {
    	savePreviousAddressBook();
    	System.out.println("editing task..");
        addressBook.editTask(targetIndex, task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    @Override
    public void clearTaskList() {
    	savePreviousAddressBook();
    	resetData(AddressBook.getEmptyAddressBook());
    }
    
    //@@author addressbook-level4
    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
    	updateFilteredListToShowAll(new PredicateExpression(new AllQualifiers()));
    	//updateFilteredListToShowAll(null);
    	System.out.println("show all");
    }
    
    private void updateFilteredListToShowAll(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredListToShowRemoved() {
    	updateFilteredListToShowRemoved(new PredicateExpression(new DoneQualifier()));
    	System.out.println("show done list");
    }
    
    private void updateFilteredListToShowRemoved(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredTaskList(Set<String> keywordSet) {
    	updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywordSet)));
    }

    @Override
    public void updateFilteredTaskList(String keywords, Set<String> keywordSet){
        updateFilteredTaskList(new PredicateExpression(new orderedNameQualifier(keywords, keywordSet)));
    }
    
    @Override
    public void updateFilteredTaskList(String keywords, String cmd) {
    	updateFilteredTaskList(new PredicateExpression(new otherFieldsQualifier(keywords, cmd)));
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
        protected Set<String> keywordSet;

        NameQualifier(Set<String> keywordSet) {
            this.keywordSet = keywordSet;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
        	return keywordSet.stream()
            .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
            .findAny()
            .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", keywordSet);
        }
    }
    
    private class otherFieldsQualifier implements Qualifier {
        protected String nameKeyWords;
        protected String cmd;

        otherFieldsQualifier(String keywords, String cmd) {
            this.nameKeyWords = keywords;
            this.cmd = cmd;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
        	if (cmd == "address") {
        		System.out.println("finding address..");
        		String address = task.getAddress().toString().toLowerCase();
        		boolean addressMatch = address.contains(nameKeyWords.toLowerCase());
        		return addressMatch;
        	} else if (cmd == "priority") {
        		System.out.println("finding priority..");
        		String priority = task.getPriority().toString();
        		boolean priorityMatch = priority.contains(nameKeyWords);
        		return priorityMatch;
        	} else if (cmd == "dueDate") {
        		System.out.println("finding dueDate..");
        		String dueDate = task.getDueDate().toString();
        		boolean dueDateMatch = dueDate.contains(nameKeyWords);
        		return dueDateMatch;
        	} else if (cmd == "tagArguments") {
        		System.out.println("finding tags.. ");
        		UniqueTagList tagsList = task.getTags();
        		boolean tagsMatch = tagsList.containSearch(nameKeyWords.toLowerCase());
        		return tagsMatch;
        	}
        	
        	// cmd == "nil"
        	
        	String taskFullNameLowerCase = task.getName().fullName.toLowerCase();
        	String priority = task.getPriority().toString();
        	String address = task.getAddress().toString().toLowerCase();
        	String dueDate = task.getDueDate().toString();
        	UniqueTagList tagsList = task.getTags();
        	
        	boolean nameMatch = taskFullNameLowerCase.contains(nameKeyWords.toLowerCase());
        	boolean addressMatch = address.contains(nameKeyWords.toLowerCase());
        	boolean priorityMatch = priority.contains(nameKeyWords);
        	boolean dueDateMatch = dueDate.contains(nameKeyWords);
        	boolean tagsMatch = tagsList.containSearch(nameKeyWords.toLowerCase());
        	boolean eachWordMatch = false;
        	
        	String[] eachWord = nameKeyWords.split(" ");
        	for (String word : eachWord) {
        		System.out.println("each: " + word);
        		eachWordMatch = eachWordMatch || taskFullNameLowerCase.contains(word.toLowerCase());
        	}
        	
            return eachWordMatch || nameMatch || addressMatch || priorityMatch || dueDateMatch || tagsMatch;
        }
    }
    
    private class orderedNameQualifier extends NameQualifier implements Qualifier {
		private String nameKeyWords;

        orderedNameQualifier(String keywords, Set<String> keywordSet) {
        	super(keywordSet);
            this.nameKeyWords = keywords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
        	String taskFullNameLowerCase = task.getName().fullName.toLowerCase();
        	boolean nameMatch = taskFullNameLowerCase.contains(nameKeyWords.toLowerCase());
        	
        	boolean eachWordMatch = keywordSet.stream()
            .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
            .findAny()
            .isPresent();
        	return eachWordMatch && nameMatch;
        }
    }
<<<<<<< HEAD
    
    private class AllQualifiers implements Qualifier {

        AllQualifiers() {}
        
        @Override
        public boolean run(ReadOnlyTask task) {
        	return true; 	
        }
    }
    
    // to check and return a list of tasks that are already done
    private class DoneQualifier implements Qualifier {

        DoneQualifier() {}
        
        @Override
        public boolean run(ReadOnlyTask task) {
        	return task.getisDone(); 	
        }
    }
    
    // default display tasks that are not yet done
    private class RemoveDoneQualifier implements Qualifier {

        RemoveDoneQualifier() {}
        
        @Override
        public boolean run(ReadOnlyTask task) {
        	System.out.println(task.getName());
        	return !task.getisDone(); 	
        	//return task.getisDone(); 	

        }
    }
=======

		@Override
		//@@author A0139072H    
		//application-wide operations
	   public void setFilePathTask(String newFilePath) throws IOException{
	        addressBook.setFilePathTask(newFilePath);
	        indicateAddressBookChanged();
	        //NEEDS TO SAVE TO NEW FILEPATH
	   };
>>>>>>> C3/change-storage-path
}
