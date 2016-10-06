package harmony.model;

import javafx.collections.transformation.FilteredList;

import java.util.Collections;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import harmony.commons.core.ComponentManager;
import harmony.commons.core.LogsCenter;
import harmony.commons.core.UnmodifiableObservableList;
import harmony.commons.events.model.TaskManagerChangedEvent;
import harmony.commons.util.StringUtil;
import harmony.logic.commands.Command;
import harmony.model.tag.Tag;
import harmony.model.task.ReadOnlyTask;
import harmony.model.task.Task;
import harmony.model.task.UniqueTaskList;
import harmony.model.task.UniqueTaskList.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager addressBook;
    private final FilteredList<Task> filteredPersons;
    private final Stack<Command> commandHistory;

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
        filteredPersons = new FilteredList<>(addressBook.getPersons());
        commandHistory = new Stack<>();
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        addressBook = new TaskManager(initialData);
        filteredPersons = new FilteredList<>(addressBook.getPersons());
        commandHistory = new Stack<>();
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
        raise(new TaskManagerChangedEvent(addressBook));
    }
    
    @Override
    public Stack<Command> getCommandHistory() {
        return commandHistory;
    }

    @Override
    public synchronized void deletePerson(ReadOnlyTask target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Task person) throws UniqueTaskList.DuplicatePersonException {
        addressBook.addPerson(person);
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
    
    @Override
    public void updateFilteredTagPersonList(Set<Tag> keywords){
        updateFilteredPersonList(new PredicateExpression(new TagQualifier(keywords)));
    }

    private void updateFilteredPersonList(Expression expression) {
        filteredPersons.setPredicate(expression::satisfies);
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
    
    private class TagQualifier implements Qualifier {
        private Set<Tag> tagKeyWords;

        TagQualifier(Set<Tag> tagKeyWords) {
            this.tagKeyWords = tagKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask person) {
            final Set<Tag> tagList = person.getTags().toSet();
            
            return !Collections.disjoint(tagList, tagKeyWords);
        }

        @Override
        public String toString() {
            return "tags=" + String.join(", ", tagKeyWords.toString());
        }
    }

}
