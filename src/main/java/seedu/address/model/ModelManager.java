package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.PersonNotFoundException;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.core.ComponentManager;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBook addressBook;
    private final FilteredList<Task> filteredPersons;
    private FilteredList<Task> filteredDeadlines;
    private FilteredList<Task> filteredTodos;

    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(TaskBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        addressBook = new TaskBook(src);
        filteredPersons = new FilteredList<>(addressBook.getPersons());
        filteredDeadlines = new FilteredList<>(addressBook.getDeadlines());
        filteredTodos = new FilteredList<>(addressBook.getTodo());
    }

    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskBook initialData, UserPrefs userPrefs) {
        addressBook = new TaskBook(initialData);
        filteredPersons = new FilteredList<>(addressBook.getPersons());
        filteredDeadlines = new FilteredList<>(addressBook.getDeadlines());
        filteredTodos = new FilteredList<>(addressBook.getTodo());
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyTask target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }
    
    @Override 
    public synchronized void editPerson(ReadOnlyTask target, String args) throws PersonNotFoundException, IllegalValueException {
        addressBook.changePerson(target, args);
        //updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task person) throws UniqueTaskList.DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredPersonList() {
        return new UnmodifiableObservableList<>(filteredPersons);
    }

    public UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineList() {
        return new UnmodifiableObservableList<>(filteredDeadlines);
    }

    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTodoList() {
        return new UnmodifiableObservableList<>(filteredTodos);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredPersons.setPredicate(null);
        filteredDeadlines.setPredicate(null);
        filteredTodos.setPredicate(null);;
    }

    @Override
    public void updateFilteredPersonList(Set<String> keywords){
        updateFilteredPersonList(new PredicateExpression(new NameQualifier(keywords)));
    }
    
    @Override
    public void updateFilteredDeadlineList(Set<String> keywords){
        updateFilteredDeadlineList(new PredicateExpression(new NameQualifier(keywords)));
    }

    @Override
    public void updateFilteredTodoList(Set<String> keywords){
        updateFilteredTodoList(new PredicateExpression(new NameQualifier(keywords)));
    }
    private void updateFilteredPersonList(Expression expression) {
        filteredPersons.setPredicate(expression::satisfies);
    }

    private void updateFilteredDeadlineList(Expression expression) {
        filteredDeadlines.setPredicate(expression::satisfies);
    }
    private void updateFilteredTodoList(Expression expression) {
        filteredTodos.setPredicate(expression::satisfies);
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
        public boolean run(ReadOnlyTask Task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(Task.getName().taskDetails.toLowerCase(), keyword)
                    || StringUtil.containsIgnoreCase(Task.getDate().value, keyword)
                    || StringUtil.containsIgnoreCase(Task.getStart().value, keyword)
                    || StringUtil.containsIgnoreCase(Task.getEnd().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
