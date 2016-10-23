package seedu.agendum.model;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.agendum.commons.core.LogsCenter;
import seedu.agendum.commons.core.UnmodifiableObservableList;
import seedu.agendum.commons.util.ConfigUtil;
import seedu.agendum.commons.util.StringUtil;
import seedu.agendum.model.task.ReadOnlyTask;
import seedu.agendum.model.task.Task;
import seedu.agendum.model.task.UniqueTaskList;
import seedu.agendum.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.agendum.commons.events.model.SaveLocationChangedEvent;
import seedu.agendum.commons.events.model.ToDoListChangedEvent;
import seedu.agendum.commons.core.ComponentManager;
import seedu.agendum.commons.core.Config;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

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
    private final Config config;

    /**
     * Initializes a ModelManager with the given ToDoList
     * ToDoList and its variables should not be null
     */
    public ModelManager(ToDoList src, UserPrefs userPrefs, Config config) {
        super();
        assert src != null;
        assert userPrefs != null;
        assert config != null;

        logger.fine("Initializing with to do list: " + src + " and user prefs " + userPrefs);

        toDoList = new ToDoList(src);
        filteredTasks = new FilteredList<>(toDoList.getTasks());
        sortedTasks = filteredTasks.sorted();
        previousLists = new Stack<ToDoList>();
        backupNewToDoList();

        this.config = config;
    }

    public ModelManager() {
        this(new ToDoList(), new UserPrefs(), new Config());
    }

    public ModelManager(ReadOnlyToDoList initialData, UserPrefs userPrefs, Config config) {
        toDoList = new ToDoList(initialData);
        filteredTasks = new FilteredList<>(toDoList.getTasks());
        sortedTasks = filteredTasks.sorted();
        previousLists = new Stack<ToDoList>();
        backupNewToDoList();

        this.config = config;
    }

    @Override
    public void resetData(ReadOnlyToDoList newData) {
        toDoList.resetData(newData);
        logger.fine("[MODEL] --- succesfully reset data of the to-do list");
        backupNewToDoList();
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
    
    /** Raises an event to indicate that save location has changed */
    private void indicateSaveLocationChanged(String location) {
        raise(new SaveLocationChangedEvent(location));
    }

    @Override
    public synchronized void deleteTasks(List<ReadOnlyTask> targets) throws TaskNotFoundException {
        for (ReadOnlyTask target: targets) {
            toDoList.removeTask(target);
        }
        logger.fine("[MODEL] --- succesfully deleted all specified targets from the to-do list");
        backupNewToDoList();
        indicateToDoListChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        toDoList.addTask(task);      
        logger.fine("[MODEL] --- succesfully added the new task to the to-do list");
        backupNewToDoList();
        updateFilteredListToShowAll();
        indicateToDoListChanged();
    }

    @Override
    public synchronized void updateTask(ReadOnlyTask target, Task updatedTask)
            throws UniqueTaskList.TaskNotFoundException, UniqueTaskList.DuplicateTaskException {
        toDoList.updateTask(target, updatedTask);
        logger.fine("[MODEL] --- succesfully updated the target task in the to-do list");
        backupNewToDoList();
        updateFilteredListToShowAll();
        indicateToDoListChanged();
    }

    @Override
    public synchronized void markTasks(List<ReadOnlyTask> targets) throws TaskNotFoundException {
        for (ReadOnlyTask target: targets) {
            toDoList.markTask(target);
        } 
        logger.fine("[MODEL] --- succesfully marked all specified targets from the to-do list");
        backupNewToDoList();
        indicateToDoListChanged();
    }
    
    @Override
    public synchronized void unmarkTasks(List<ReadOnlyTask> targets) throws TaskNotFoundException {
        for (ReadOnlyTask target: targets) {
            toDoList.unmarkTask(target);
        }
        logger.fine("[MODEL] --- succesfully unmarked all specified targets from the to-do list");
        backupNewToDoList();
        indicateToDoListChanged();
    }

    @Override
    public synchronized boolean restorePreviousToDoList() {
        assert !previousLists.empty();
        if (previousLists.size() == 1) {
            return false;
        } else {
            previousLists.pop();
            toDoList.resetData(previousLists.peek());
            logger.fine("[MODEL] --- succesfully restored the previous the to-do list from this session");
            indicateToDoListChanged();
            return true;
        }
    }
 
    private void backupNewToDoList() {
        ToDoList latestList = new ToDoList(this.getToDoList());
        previousLists.push(latestList);
    }
    
    // Storage
    @Override
    public synchronized void changeSaveLocation(String location){
        assert StringUtil.isValidPathToFile(location);

        config.setToDoListFilePath(location);
        indicateSaveLocationChanged(location);
        saveConfigFile();
    }

    private void saveConfigFile() {
        try {
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }        
    }

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

}
