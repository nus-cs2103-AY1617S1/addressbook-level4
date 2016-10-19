package seedu.todolist.model;

import javafx.collections.transformation.FilteredList;
import seedu.todolist.commons.core.ComponentManager;
import seedu.todolist.commons.core.LogsCenter;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.events.model.AddressBookChangedEvent;
import seedu.todolist.commons.util.StringUtil;
import seedu.todolist.model.task.ReadOnlyTask;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList;
import seedu.todolist.model.task.UniqueTaskList.TaskNotFoundException;

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
    private final FilteredList<Task> filteredTasks;
    private final Stack<ReadOnlyAddressBook> addressBookHistory;

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
        addressBookHistory = new Stack<ReadOnlyAddressBook>();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyAddressBook initialData, UserPrefs userPrefs) {
        addressBook = new AddressBook(initialData);
        filteredTasks = new FilteredList<>(addressBook.getTasks());
        addressBookHistory = new Stack<ReadOnlyAddressBook>();
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

    @Override
    public void undoAddressBook() throws EmptyStackException {
    	addressBook.resetData(addressBookHistory.pop());
    	indicateAddressBookChanged();
    }
    
    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }
    
    @Override
    public synchronized void markTask(ReadOnlyTask target) throws TaskNotFoundException {
        addressBookHistory.push(new AddressBook(this.addressBook));
        addressBook.markTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
    	addressBookHistory.push(new AddressBook(this.addressBook));
    	addressBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
    	addressBookHistory.push(new AddressBook(this.addressBook));
    	addressBook.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    @Override
    public synchronized void editTask(ReadOnlyTask target, Task replacement) throws TaskNotFoundException {
    	addressBookHistory.push(new AddressBook(this.addressBook));
    	addressBook.editTask(target, replacement);
        indicateAddressBookChanged();
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
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
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

}
