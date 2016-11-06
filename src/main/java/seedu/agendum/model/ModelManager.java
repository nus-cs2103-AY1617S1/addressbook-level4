package seedu.agendum.model;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.agendum.commons.core.LogsCenter;
import seedu.agendum.commons.core.UnmodifiableObservableList;
import seedu.agendum.commons.util.StringUtil;
import seedu.agendum.commons.util.XmlUtil;
import seedu.agendum.model.task.ReadOnlyTask;
import seedu.agendum.model.task.Task;
import seedu.agendum.model.task.UniqueTaskList;
import seedu.agendum.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.agendum.commons.events.model.LoadDataRequestEvent;
import seedu.agendum.commons.events.model.ChangeSaveLocationEvent;
import seedu.agendum.commons.events.model.ToDoListChangedEvent;
import seedu.agendum.commons.events.storage.LoadDataCompleteEvent;
import seedu.agendum.commons.core.ComponentManager;

import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import seedu.agendum.sync.Sync;
import seedu.agendum.sync.SyncManager;
import seedu.agendum.sync.SyncProviderGoogle;

/**
 * Represents the in-memory model of the to do list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ToDoList toDoList;
    private final Stack<ToDoList> previousLists;
    private final FilteredList<Task> filteredTasks;
    private final SortedList<Task> sortedTasks;

    private final SyncManager syncManager;

    //@@author A0133367E
    /**
     * Signals that an operation to remove a list from the stack of previous lists would fail
     * as the stack must contain at least one list.
     */
    public static class NoPreviousListFoundException extends Exception {}
    //@@author

    /**
     * Initializes a ModelManager with the given ToDoList
     * ToDoList and its variables should not be null
     */
    public ModelManager(ToDoList src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with to do list: " + src + " and user prefs " + userPrefs);

        toDoList = new ToDoList(src);
        filteredTasks = new FilteredList<>(toDoList.getTasks());
        sortedTasks = filteredTasks.sorted();
        previousLists = new Stack<ToDoList>();
        backupCurrentToDoList();

        syncManager = new SyncManager(new SyncProviderGoogle());
    }

    public ModelManager() {
        this(new ToDoList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyToDoList initialData) {
        toDoList = new ToDoList(initialData);
        filteredTasks = new FilteredList<Task>(toDoList.getTasks());
        sortedTasks = filteredTasks.sorted();
        previousLists = new Stack<ToDoList>();
        backupCurrentToDoList();

        syncManager = new SyncManager(new SyncProviderGoogle());
    }

    //@@author A0133367E
    @Override
    public void resetData(ReadOnlyToDoList newData) {
        toDoList.resetData(newData);
        logger.fine("[MODEL] --- successfully reset data of the to-do list");
        backupCurrentToDoList();
        indicateToDoListChanged();
    }
    //@@author

    @Override
    public ReadOnlyToDoList getToDoList() {
        return toDoList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateToDoListChanged() {
        // force a reset/refresh for list view in UI
        toDoList.resetData(toDoList);
        raise(new ToDoListChangedEvent(toDoList));
    }
    
    //@@author A0148095X
    /** Raises an event to indicate that save location has changed */
    private void indicateChangeSaveLocation(String location) {
        raise(new ChangeSaveLocationEvent(location));
    }
    
    /** Raises an event to indicate that save location has changed */
    private void indicateLoadDataRequest(String location) {
        raise(new LoadDataRequestEvent(location));
    }

    //@@author A0133367E
    @Override
    public synchronized void deleteTasks(List<ReadOnlyTask> targets) throws TaskNotFoundException {
        for (ReadOnlyTask target: targets) {
            toDoList.removeTask(target);

            // Delete tasks in sync manager
            syncManager.deleteEvent((Task) target);
        }

        logger.fine("[MODEL] --- successfully deleted all specified targets from the to-do list");
        backupCurrentToDoList();
        indicateToDoListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        toDoList.addTask(task);    

        logger.fine("[MODEL] --- successfully added the new task to the to-do list");
        backupCurrentToDoList();
        updateFilteredListToShowAll();
        indicateToDoListChanged();

        syncManager.addNewEvent(task);
    }
    
    @Override
    public synchronized void updateTask(ReadOnlyTask target, Task updatedTask)
            throws UniqueTaskList.TaskNotFoundException, UniqueTaskList.DuplicateTaskException {
        toDoList.updateTask(target, updatedTask);

        logger.fine("[MODEL] --- successfully updated the target task in the to-do list");
        backupCurrentToDoList();
        updateFilteredListToShowAll();
        indicateToDoListChanged();

        // Delete old task and add new task
        syncManager.deleteEvent((Task) target);
        syncManager.addNewEvent(updatedTask);
    }

    @Override
    public synchronized void markTasks(List<ReadOnlyTask> targets) 
            throws UniqueTaskList.TaskNotFoundException, UniqueTaskList.DuplicateTaskException {
        for (ReadOnlyTask target: targets) {
            toDoList.markTask(target);
        } 

        logger.fine("[MODEL] --- successfully marked all specified targets from the to-do list");
        backupCurrentToDoList();
        indicateToDoListChanged();
    }
    
    @Override
    public synchronized void unmarkTasks(List<ReadOnlyTask> targets) 
            throws UniqueTaskList.TaskNotFoundException, UniqueTaskList.DuplicateTaskException {
        for (ReadOnlyTask target: targets) {
            toDoList.unmarkTask(target);
        }

        logger.fine("[MODEL] --- successfully unmarked all specified targets from the to-do list");
        backupCurrentToDoList();
        indicateToDoListChanged();
    }

    /**
     * Restores the previous (second latest) list saved in the stack of previous lists.
     */
    @Override
    public synchronized void restorePreviousToDoList() throws NoPreviousListFoundException {            
        removeLastSavedToDoList();
        resetDataToLastSavedList();
        logger.fine("[MODEL] --- successfully restored the previous the to-do list from this session");
        indicateToDoListChanged();
    }

    /**
     * Resets the to-do list data to match the top list in the stack of previous lists
     */
    @Override
    public void resetDataToLastSavedList() {
        assert !previousLists.empty();
        ToDoList lastSavedListFromHistory = previousLists.peek();
        toDoList.resetData(lastSavedListFromHistory);
    }
 
    private void backupCurrentToDoList() {
        ToDoList latestList = new ToDoList(this.getToDoList());
        previousLists.push(latestList);
    }

    private void clearAllPreviousToDoLists() {
        previousLists.clear();
    }

    /**
     * Pops the top list from the stack of previous lists if there are more than 1 list in the stack
     * @throws NoPreviousListFoundException if there is only 1 list in the stack
     */
    private void removeLastSavedToDoList() throws NoPreviousListFoundException {
        assert !previousLists.empty();

        if (previousLists.size() == 1) {
            throw new NoPreviousListFoundException();
        }

        previousLists.pop();
    }


    //@@author A0148095X
    //=========== Storage Methods ==========================================================================

    @Override
    public synchronized void changeSaveLocation(String location){
        assert StringUtil.isValidPathToFile(location);
        indicateChangeSaveLocation(location);
        indicateToDoListChanged();
    }

    @Override
    public synchronized void loadFromLocation(String location) {
        assert StringUtil.isValidPathToFile(location);
        assert XmlUtil.isFileCorrectFormat(location);

        indicateChangeSaveLocation(location);
        indicateLoadDataRequest(location);
    }
    //@@author A0003878Y

    //=========== Sync Methods ===============================================================================

    @Override
    public void activateModelSyncing() {
        if (syncManager.getSyncStatus() != Sync.SyncStatus.RUNNING) {
            syncManager.startSyncing();

            // Add all current events into sync provider
            toDoList.getTasks().forEach(syncManager::addNewEvent);
        }
    }

    @Override
    public void deactivateModelSyncing() {
        if (syncManager.getSyncStatus() != Sync.SyncStatus.NOTRUNNING) {
            syncManager.stopSyncing();
        }
    }

    //@@author
    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(sortedTasks);
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

    //========== event handling ==================================================
    //@@author A0148095X
    @Override
    @Subscribe
    public void handleLoadDataCompleteEvent(LoadDataCompleteEvent event) {
        this.toDoList.resetData(event.data);
        indicateToDoListChanged();
        clearAllPreviousToDoLists();
        backupCurrentToDoList();
        logger.info("Loading completed - Todolist updated.");
    }
}
