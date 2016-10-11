package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.ModifiableObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.commons.events.model.ToDoListChangedEvent;
import seedu.address.commons.core.ComponentManager;
import seedu.address.model.qualifiers.*;

import java.time.LocalDateTime;
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
    public boolean undo() {
        if (toDoList.undo()) {
            indicateAddressBookChanged();
            return true;
        }
        return false;
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
            toDoList.getTasks().get(index).setOnDate(newTask.getOnDate());
            toDoList.getTasks().get(index).setByDate(newTask.getByDate());
            toDoList.syncTagsWithMasterList(toDoList.getTasks().get(index));
            indicateAddressBookChanged();
        }
    }

    @Override
    public synchronized void updateTaskTags(ReadOnlyTask oldTask, ReadOnlyTask newTask) throws TaskNotFoundException {
        int index = toDoList.getTasks().indexOf(oldTask);
        
        if(index < 0) {
            throw new TaskNotFoundException();
        } else {
            toDoList.getTasks().get(index).setTags(newTask.getTags());
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
        updateFilteredTaskList(new PredicateExpression(new CompletedQualifier(true))); //force change
        filteredTasks.setPredicate(null);
    }
    
    @Override
    public void updateFilteredListToShowAllCompleted(){
        updateFilteredTaskList(new PredicateExpression(new CompletedQualifier(true)));
    }
    
    @Override
    public void updateFilteredListToShowAllNotCompleted(){
        updateFilteredTaskList(new PredicateExpression(new CompletedQualifier(false)));
    }

    @Override
    public void updateFilteredTaskListByKeywords(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    @Override
    public void updateFilteredTaskListByTag(String tagName){
        updateFilteredTaskList(new PredicateExpression(new TagQualifier(tagName)));
    }
    
    @Override
    public void updateFilteredTaskListOnDate(LocalDateTime datetime){
        
        updateFilteredTaskList(new PredicateExpression(new OnDateQualifier(datetime)));
    }
    
    @Override
    public void updateFilteredTaskListBeforeDate(LocalDateTime datetime){
        updateFilteredTaskList(new PredicateExpression(new BeforeDateQualifier(datetime)));
    }
    
    @Override
    public void updateFilteredTaskListAfterDate(LocalDateTime datetime){
        updateFilteredTaskList(new PredicateExpression(new AfterDateQualifier(datetime)));
    }
    
    @Override
    public void updateFilteredTaskListFromTillDate(LocalDateTime fromDateTime, LocalDateTime tillDateTime){
        updateFilteredTaskList(new PredicateExpression(new FromTillDateQualifier(fromDateTime, tillDateTime)));
    }
    
    
    private void updateFilteredTaskList(Expression expression) {
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
    
}
