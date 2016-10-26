package seedu.todo.model;

import javafx.collections.transformation.FilteredList;
import seedu.todo.commons.core.ComponentManager;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.events.model.ToDoListChangedEvent;
import seedu.todo.model.qualifiers.*;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;
import seedu.todo.model.task.Priority;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.UniqueTaskList;
import seedu.todo.model.task.UniqueTaskList.TaskNotFoundException;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final DoDoBird dodobird;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Task> todayTasks;
    private final FilteredList<Tag> tagList;
    
    //@@author A0138967J
    /**
     * Initializes a ModelManager with the given ToDoList
     * ToDoList and its variables should not be null
     */
    public ModelManager(ReadOnlyToDoList src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with to-do app: " + src + " and user prefs " + userPrefs);

        dodobird = new DoDoBird(src);
        filteredTasks = new FilteredList<>(dodobird.getTasks());
        todayTasks = new FilteredList<>(dodobird.getTasks());
        tagList = new FilteredList<>(dodobird.getTags());
        updateTodayListToShowAll();
 
    }
    //@@author
    
    public ModelManager() {
        this(new DoDoBird(), new UserPrefs());
    }    

    @Override
    public ReadOnlyToDoList getToDoList() {
        return dodobird;
    }
    
    @Override
    public void resetData(ReadOnlyToDoList newData) {
        dodobird.resetData(newData);
        indicateToDoListChanged();
    }
    
    /** Raises an event to indicate the model has changed */
    public void indicateToDoListChanged() {
        raise(new ToDoListChangedEvent(dodobird));
    }
    
    @Override
    public boolean undo() {
        if (dodobird.undo()) {
            indicateToDoListChanged();
            return true;
        }
        return false;
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        dodobird.addTask(task);
        indicateToDoListChanged();
    }
    
    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        dodobird.deleteTask(target);
        indicateToDoListChanged();
    }
    
    //@@author A0093896H
    @Override
    public synchronized Task getTask(ReadOnlyTask target) {
        return dodobird.getTask(dodobird.getTaskIndex(target));
    }
    
    @Override
    public synchronized void updateTask(ReadOnlyTask oldTask, ReadOnlyTask newTask) throws TaskNotFoundException {
        dodobird.updateTask(oldTask, newTask);
        indicateToDoListChanged();
    }
    
    @Override
    public synchronized void addTaskTags(ReadOnlyTask oldTask, UniqueTagList newTagList) throws TaskNotFoundException {
        dodobird.addTaskTags(oldTask, newTagList);
        indicateToDoListChanged();
    }
    
    @Override
    public synchronized void deleteTaskTags(ReadOnlyTask oldTask, UniqueTagList newTagList) 
            throws TaskNotFoundException {
        dodobird.deleteTaskTags(oldTask, newTagList);
        indicateToDoListChanged();
    }
    
    //=========== Filtered Task List Accessors ===============================================================
    
    //@@author A0093896H
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }
    
    //@@author A0138967J
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTodayTaskList() {
        return new UnmodifiableObservableList<>(todayTasks);
    } 
    
    //@@author A0142421X
    public UnmodifiableObservableList<Tag> getUnmodifiableTagList() {
        return new UnmodifiableObservableList<>(tagList);
    } 
    
    //@@author A0138967J
    public void updateTodayListToShowAll() {
        todayTasks.setPredicate((new PredicateExpression(new TodayDateQualifier(LocalDateTime.now())))::satisfies);
    }

    //@@author A0093896H
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

    //@@author A0121643R
    @Override
    public void updateFilteredTaskListByPriority(Priority priority) {
        updateFilteredTaskList(new PredicateExpression(new PriorityQualifier(priority)));   
    }

    //@@author A0138967J-unused
    @Override
    public void updateFilteredTaskListTodayDate(LocalDateTime datetime){
        updateFilteredTaskList(new PredicateExpression(new TodayDateQualifier(datetime)));
    }
    //@@author
    
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
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    
}
