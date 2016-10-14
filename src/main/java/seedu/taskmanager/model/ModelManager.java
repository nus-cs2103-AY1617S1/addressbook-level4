package seedu.taskmanager.model;

import javafx.collections.transformation.FilteredList;

import seedu.taskmanager.commons.core.ComponentManager;
import seedu.taskmanager.commons.core.LogsCenter;
import seedu.taskmanager.commons.core.UnmodifiableObservableList;
import seedu.taskmanager.commons.events.model.TaskManagerChangedEvent;
import seedu.taskmanager.commons.events.ui.ChangeDoneEvent;
import seedu.taskmanager.commons.util.StringUtil;
import seedu.taskmanager.model.item.Item;
import seedu.taskmanager.model.item.ReadOnlyItem;
import seedu.taskmanager.model.item.UniqueItemList;
import seedu.taskmanager.model.item.UniqueItemList.ItemNotFoundException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManger;
    private final FilteredList<Item> filteredItems;

    /**
     * Initializes a ModelManager with the given AddressBook
     * AddressBook and its variables should not be null
     */
    public ModelManager(TaskManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        taskManger = new TaskManager(src);
        filteredItems = new FilteredList<>(taskManger.getItems());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManger = new TaskManager(initialData);
        filteredItems = new FilteredList<>(taskManger.getItems());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManger.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskManager getAddressBook() {
        return taskManger;
    }
    
    @Override
    public synchronized void setDone(ReadOnlyItem target) throws ItemNotFoundException {
        taskManger.setDone(target);
        raise(new ChangeDoneEvent());
        indicateAddressBookChanged();
    }
    
    @Override
    public synchronized void setUndone(ReadOnlyItem target) throws ItemNotFoundException {
        taskManger.setUndone(target);
        raise(new ChangeDoneEvent());
        indicateAddressBookChanged();
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new TaskManagerChangedEvent(taskManger));
    }

    @Override
    public synchronized void deleteItem(ReadOnlyItem target) throws ItemNotFoundException {
        taskManger.removeItem(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addItem(Item item) throws UniqueItemList.DuplicateItemException {
        taskManger.addItem(item);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    @Override
    public synchronized void replaceItem(ReadOnlyItem target, Item toReplace) 
            throws ItemNotFoundException, UniqueItemList.DuplicateItemException {
        taskManger.replaceItem(target, toReplace);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    //=========== Filtered Item List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyItem> getFilteredItemList() {
        return new UnmodifiableObservableList<>(filteredItems);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredItems.setPredicate(null);
    }
    
    
    @Override
    public void updateFilteredListToShowTask() {
    	final String[] itemType = {"task"}; 
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(itemType));
        updateFilteredPersonList(new PredicateExpression(new ItemTypeQualifier(keywordSet)));
    }
    
    @Override
    public void updateFilteredListToShowDeadline() {
    	final String[] itemType = {"deadline"}; 
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(itemType));
        updateFilteredPersonList(new PredicateExpression(new ItemTypeQualifier(keywordSet)));
    }
    
    @Override
    public void updateFilteredListToShowEvent() {
    	final String[] itemType = {"event"}; 
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(itemType));
        updateFilteredPersonList(new PredicateExpression(new ItemTypeQualifier(keywordSet)));
    }

    @Override
    public void updateFilteredPersonList(Set<String> keywords){
        updateFilteredPersonList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredPersonList(Expression expression) {
        filteredItems.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyItem person);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyItem person) {
            return qualifier.run(person);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyItem person);
        String toString();
    }
    
    private class ItemTypeQualifier implements Qualifier {
        private Set<String> itemType;

        ItemTypeQualifier(Set<String> itemType) {
            this.itemType = itemType;
        }

        @Override
        public boolean run(ReadOnlyItem person) {
            return itemType.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(person.getItemType().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", itemType);
        }
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyItem person) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(person.getName().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
