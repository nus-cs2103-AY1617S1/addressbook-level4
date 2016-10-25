package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;
import com.google.common.io.Files;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<Task> filteredTasks;
    private final Config config;
    private Stack<TaskManager> stateHistory;
    private Stack<TaskManager> undoHistory;

    /**
     * Initializes a ModelManager with the given TaskManager
     * TaskManager and its variables should not be null
     */
    public ModelManager(TaskManager src, Config config, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task manager: " + src + " and user prefs " + userPrefs);

        taskManager = new TaskManager(src);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
        stateHistory = new Stack<>();
        undoHistory = new Stack<>();
        this.config = config;
    }

    public ModelManager(ReadOnlyTaskManager initialData, Config config, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
        stateHistory = new Stack<>();
        undoHistory = new Stack<>();
        this.config = config;
    }
    
    public void saveState() {
    	stateHistory.push(new TaskManager(taskManager));
    	// Allow redos only if the previous action is an undo
    	undoHistory.clear();
    }
    
    public void loadPreviousState() throws EmptyStackException {
    	TaskManager oldTaskManager = stateHistory.pop();
    	
    	undoHistory.push(new TaskManager(taskManager));
    	
    	taskManager.setTasks(oldTaskManager.getTasks());
    	taskManager.setTags(oldTaskManager.getTagList());
    	
    	indicateTaskManagerChanged();
    }
    
    public void loadNextState() throws EmptyStackException {
    	TaskManager oldTaskManager = undoHistory.pop();

    	stateHistory.push(new TaskManager(taskManager));
    	
    	taskManager.setTasks(oldTaskManager.getTasks());
    	taskManager.setTags(oldTaskManager.getTagList());
    	
    	indicateTaskManagerChanged();
    }
    

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }
    
    @Override
    public String getTaskManagerStorageFilePath() {
    	return config.getTaskManagerFilePath();
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTasks(ArrayList<ReadOnlyTask> targets) throws TaskNotFoundException {
        for(ReadOnlyTask target : targets) {
        	taskManager.removeTask(target);
        	indicateTaskManagerChanged();
        }
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    
    @Override
    public synchronized void editTask(int index, Task task) throws TaskNotFoundException {
        taskManager.editTask(index, task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
	}
    
    //@@author A0143756Y
    @Override
    public synchronized void setStorage(File newStorageFile, File oldStorageFile) throws IOException{
    	assert newStorageFile!= null;
    	assert oldStorageFile!= null;
    	assert !newStorageFile.equals(oldStorageFile);
    	
    	Files.copy(oldStorageFile, newStorageFile);  //Throws IOException
    	
    	//Updates taskManagerFilePath (attribute) in config (Config object)
    	config.setTaskManagerFilePath(newStorageFile.getCanonicalPath());  //Throws IOException
    	
    	//Saves config (Config object) to config.json (JSON file), overwrites existing config.json
    	ConfigUtil.saveConfig(config, "config.json");  //Throws IOException
    }
    //@@author 
    
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
