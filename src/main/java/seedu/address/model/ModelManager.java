package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager addressBook;
    private final FilteredList<Task> filteredPersons;

    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(TaskManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        addressBook = new TaskManager(src);
        filteredPersons = new FilteredList<>(addressBook.getTasks());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        addressBook = new TaskManager(initialData);
        filteredPersons = new FilteredList<>(addressBook.getTasks());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskManager getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        addressBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Task person) throws UniqueTaskList.DuplicateTaskException {
        addressBook.addTask(person);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    

    //=========== Filtered Person List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredPersonList() {
        return new UnmodifiableObservableList<>(filteredPersons);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredPersons.setPredicate(null);
    }

    @Override
    public void updateFilteredPersonList(Set<String> keywords){
        updateFilteredPersonList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredPersonList(Expression expression) {
        filteredPersons.setPredicate(expression::satisfies);
    }
    
    public void updateFilteredListToShowClashing(){
    	FilteredList<Task> clashingTasks = new FilteredList<Task>(null);
    	for(int i=0; i<filteredPersons.size(); i++){
    		boolean isClashing = false;
    		Task task = filteredPersons.get(i);
    		Deadline deadline = task.getDeadline();
    		for(int j=i; j<filteredPersons.size()-i; j++){
    			Task task2 = filteredPersons.get(j);
    			Deadline deadline2 = task2.getDeadline();
    			if(deadline.equals(deadline2)){
    				clashingTasks.add(task2);
    				isClashing = true;
    			}
    		}
    		if(isClashing){
    			clashingTasks.add(task);
    		}
    	}
    	//filteredPersons = clashingTasks;
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
        boolean run(ReadOnlyTask task);
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
    
}
