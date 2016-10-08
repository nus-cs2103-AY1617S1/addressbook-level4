package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.ModifiableObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.commons.events.model.ToDoListChangedEvent;
import seedu.address.commons.core.ComponentManager;

import java.time.LocalDate;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ToDoList toDoList;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given ToDoList
     * ToDoList and its variables should not be null
     */
    public ModelManager(ToDoList src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with to-do app: " + src + " and user prefs " + userPrefs);

        toDoList = new ToDoList(src);
        filteredTasks = new FilteredList<>(toDoList.getTasks());
    }

    public ModelManager() {
        this(new ToDoList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyToDoList initialData, UserPrefs userPrefs) {
        toDoList = new ToDoList(initialData);
        filteredTasks = new FilteredList<>(toDoList.getTasks());
    }

    @Override
    public void resetData(ReadOnlyToDoList newData) {
        toDoList.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyToDoList getToDoList() {
        return toDoList;
    }

    /** Raises an event to indicate the model has changed */
    public void indicateAddressBookChanged() {
        raise(new ToDoListChangedEvent(toDoList));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        toDoList.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        toDoList.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    @Override
    public synchronized Task getTask(ReadOnlyTask target) throws TaskNotFoundException {
        int index = toDoList.getTasks().indexOf(target);
        
        if(index < 0) {
            throw new TaskNotFoundException();
        } else {
            return toDoList.getTasks().get(index);
        }
    }
    
    @Override
    public synchronized void updateTask(ReadOnlyTask oldTask, ReadOnlyTask newTask) throws TaskNotFoundException {
        int index = toDoList.getTasks().indexOf(oldTask);
        
        if(index < 0) {
            throw new TaskNotFoundException();
        } else {
            toDoList.getTasks().get(index).setName(newTask.getName());
            toDoList.getTasks().get(index).setDetail(newTask.getDetail());
            toDoList.getTasks().get(index).setFromDate(newTask.getFromDate());
            toDoList.getTasks().get(index).setTillDate(newTask.getTillDate());
            toDoList.getTasks().get(index).setTags(newTask.getTags());
            toDoList.syncTagsWithMasterList(toDoList.getTasks().get(index));
            indicateAddressBookChanged();
        }
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getUnmodifiableFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }
    
    @Override
    public ModifiableObservableList<Task> getFilteredTaskList() {
        return new ModifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredPersonList(new PredicateExpression(new NameQualifier(keywords)));
    }

    @Override
    public void updateFilteredTaskList(LocalDate date){
        updateFilteredPersonList(new PredicateExpression(new DateQualifier(date)));
    }
    
    private void updateFilteredPersonList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
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
    
    private class DateQualifier implements Qualifier {
        private LocalDate date;

        DateQualifier(LocalDate date) {
            this.date = date;
        }

        @Override
        public boolean run(ReadOnlyTask person) {
            return (person.getFromDate().getDate() != null && person.getFromDate().getDate().equals(date))
                || (person.getTillDate().getDate() != null && person.getTillDate().getDate().equals(date));
        }

        @Override
        public String toString() {
            return "date=" + date.toString();
        }
    }

}
