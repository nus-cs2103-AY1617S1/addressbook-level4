package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.events.model.ToDoListChangedEvent;
import seedu.address.commons.core.ComponentManager;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.ReadOnlyToDo;
import seedu.address.model.todo.UniqueToDoList;
import seedu.address.model.todo.UniqueToDoList.ToDoNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ToDoList toDoList;
    private final FilteredList<ToDo> filteredToDos;

    /**
     * Initializes a ModelManager with the given ToDoList
     * ToDoList and its variables should not be null
     */
    public ModelManager(ToDoList src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with address book: " + src + " and user prefs " + userPrefs);

        toDoList = new ToDoList(src);
        filteredToDos = new FilteredList<>(toDoList.getToDos());
    }

    public ModelManager() {
        this(new ToDoList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyToDoList initialData, UserPrefs userPrefs) {
        toDoList = new ToDoList(initialData);
        filteredToDos = new FilteredList<>(toDoList.getToDos());
    }

    @Override
    public void resetData(ReadOnlyToDoList newData) {
        toDoList.resetData(newData);
        indicateToDoListChanged();
    }

    @Override
    public ReadOnlyToDoList getToDoList() {
        return toDoList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateToDoListChanged() {
        raise(new ToDoListChangedEvent(toDoList));
    }

    @Override
    public synchronized void deleteToDo(ReadOnlyToDo target) throws ToDoNotFoundException {
        toDoList.removeToDo(target);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void addToDo(ToDo toDo) throws UniqueToDoList.DuplicateToDoException {
        toDoList.addToDo(toDo);
        updateFilteredListToShowAll();
        indicateToDoListChanged();
    }

    //=========== Filtered ToDo List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyToDo> getFilteredToDoList() {
        return new UnmodifiableObservableList<>(filteredToDos);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredToDos.setPredicate(null);
    }

    @Override
    public void updateFilteredToDoList(Set<String> keywords){
        updateFilteredToDoList(new PredicateExpression(new TitleQualifier(keywords)));
    }

    private void updateFilteredToDoList(Expression expression) {
        filteredToDos.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyToDo toDo);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyToDo toDo) {
            return qualifier.run(toDo);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyToDo toDo);
        String toString();
    }

    private class TitleQualifier implements Qualifier {
        private Set<String> titleKeyWords;

        TitleQualifier(Set<String> titleKeyWords) {
            this.titleKeyWords = titleKeyWords;
        }

        @Override
        public boolean run(ReadOnlyToDo toDo) {
            return titleKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(toDo.getTitle().fullTitle, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "Title=" + String.join(", ", titleKeyWords);
        }
    }

}
