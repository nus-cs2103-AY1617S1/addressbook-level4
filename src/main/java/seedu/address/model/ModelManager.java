package seedu.address.model;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.util.Pair;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.SetStorageCommand;
import seedu.address.commons.events.model.AliasManagerChangedEvent;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskFilter;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.ReadOnlyAlias;
import seedu.address.model.alias.UniqueAliasList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.ReadOnlyTaskFilter;
import seedu.address.model.task.Status;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;
import com.google.common.io.Files;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final AliasManager aliasManager;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Alias> filteredAliases;
    private final Config config;
    private Stack<TaskManager> stateHistory;
    private Stack<TaskManager> undoHistory;

    /**
     * Initializes a ModelManager with the given TaskManager
     * TaskManager and its variables should not be null
     */

    public ModelManager(TaskManager taskManager, Config config, UserPrefs userPrefs, AliasManager aliasManager) {
        super();
        assert taskManager != null;
        assert userPrefs != null;
        assert aliasManager != null;

        logger.fine("Initializing with task manager: " + taskManager + ", user prefs " + userPrefs 
        		+ "and alias manager: " + aliasManager);

        this.taskManager = new TaskManager(taskManager);
        this.aliasManager = new AliasManager(aliasManager);
        filteredTasks = new FilteredList<>(taskManager.getFilteredTasks());
        filteredAliases = new FilteredList<>(aliasManager.getInternalList());
        stateHistory = new Stack<>();
        undoHistory = new Stack<>();
        this.config = config;
    }

    public ModelManager() {
        this(new TaskManager(), new Config(), new UserPrefs(), new AliasManager());
    }

    public ModelManager(ReadOnlyTaskManager initialTaskManagerData, Config config, UserPrefs userPrefs, ReadOnlyAliasManager initialAliasManagerData) {
        taskManager = new TaskManager(initialTaskManagerData);
        aliasManager = new AliasManager(initialAliasManagerData);
        filteredTasks = new FilteredList<>(taskManager.getFilteredTasks());
        filteredAliases = new FilteredList<>(aliasManager.getInternalList());
        stateHistory = new Stack<>();
        undoHistory = new Stack<>();
        this.config = config;
        this.updateFilteredTaskList(ReadOnlyTaskFilter.isDone().negate());
    }
    
  //@@author A0141019U
    public void saveState() {
    	stateHistory.push(new TaskManager(taskManager));
    	// Allow redos only if the previous action is an undo
    	undoHistory.clear();
    }
    
    public void undoSaveState() {
    	stateHistory.pop();
    }
    
    public void loadPreviousState() throws EmptyStackException {
    	TaskManager oldTaskManager = stateHistory.pop();
    	
    	undoHistory.push(new TaskManager(taskManager));
    	
    	taskManager.setTasks(oldTaskManager.getFilteredTasks());
    	taskManager.setTags(oldTaskManager.getTagList());
    	
    	indicateTaskManagerChanged();
    }
    
    public void loadNextState() throws EmptyStackException {
    	TaskManager oldTaskManager = undoHistory.pop();

    	stateHistory.push(new TaskManager(taskManager));
    	
    	taskManager.setTasks(oldTaskManager.getFilteredTasks());
    	taskManager.setTags(oldTaskManager.getTagList());
    	
    	indicateTaskManagerChanged();
    }
    
    //@@author
    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }
    
    //@@author A0143756Y
    @Override
    public String getTaskManagerStorageFilePath() {
    	return config.getTaskManagerFilePath();
    }
    //@@author

    /** Raises an event to indicate that the taskManager in model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }
    
    /** Raise an event to indicate that the aliasManager in model has changed */
    private void indicateAliasManagerChanged() {
    	raise(new AliasManagerChangedEvent(aliasManager));
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
        checkForOverdueTasks();
        updateFilteredTaskList(ReadOnlyTaskFilter.isDone().negate());
        indicateTaskManagerChanged();
    }
    
    @Override
    public synchronized void editTask(int index, Task task) throws TaskNotFoundException {
        taskManager.editTask(index, task);
        checkForOverdueTasks();
        indicateTaskManagerChanged();
    }
    
    //@@author A0143756Y
    @Override
    public synchronized void addAlias(Alias aliasToAdd) throws UniqueAliasList.DuplicateAliasException {
    	assert aliasToAdd != null;
    	
    	aliasManager.addAlias(aliasToAdd);
    	indicateAliasManagerChanged();
    }
    
    @Override
    public synchronized boolean validateAliasforAddAliasCommand(String alias) {
    	assert alias != null;
    	assert !alias.isEmpty();
    	
    	ObservableList<Alias> aliasList = aliasManager.getInternalList();
    	for(Alias currentAlias: aliasList){
    		if(currentAlias.getAlias().contains(alias) || alias.contains(currentAlias.getAlias())){
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    //@@author A0141019U
    @Override
    public synchronized void checkForOverdueTasks() {
    	LocalDateTime now = LocalDateTime.now();
    	
    	for (Task task : taskManager.getUniqueTaskList().getInternalList()) {
    		
    		if (!task.getStatus().isDone() && task.getEndDate().orElse(LocalDateTime.MAX).isBefore(now)) {
    			System.out.println("now: " + now);
    			System.out.println("endDateee: " + task.getEndDate());
    			task.setStatus(new Status("overdue"));
    		} 
    		else if (task.getStatus().isOverdue() && task.getEndDate().orElse(LocalDateTime.MIN).isAfter(now)) {
    			task.setStatus(new Status("pending"));
    		}
    	}
    }

    //@@author A0143756Y
    @Override
    public synchronized Pair<Path, Path> validateSetStorage(String userSpecifiedStorageFolder, String userSpecifiedStorageFileName) 
    		throws InvalidPathException, SecurityException, IllegalArgumentException {	
    	Path newStorageFolderFilePath = Paths.get(userSpecifiedStorageFolder);  //Throws InvalidPathException
    	
    	if(java.nio.file.Files.notExists(newStorageFolderFilePath)){  //Throws SecurityException
    		throw new IllegalArgumentException(String.format(SetStorageCommand.MESSAGE_FOLDER_DOES_NOT_EXIST, userSpecifiedStorageFolder)); 
    	} 
    	
    	if(!java.nio.file.Files.isDirectory(newStorageFolderFilePath)){  //Throws SecurityException
    		throw new IllegalArgumentException(String.format(SetStorageCommand.MESSAGE_FOLDER_NOT_DIRECTORY, userSpecifiedStorageFolder)); 
    	}        	        	        	
    	
    	Path newStorageFileFilePath = newStorageFolderFilePath.resolve(userSpecifiedStorageFileName +".xml");  //Throws InvalidPathException
    	
    	Path oldStorageFileFilePath = Paths.get(getTaskManagerStorageFilePath());  //Throws InvalidPathException
    	
    	if(newStorageFileFilePath.equals(oldStorageFileFilePath)){
    		throw new IllegalArgumentException(String.format(SetStorageCommand.MESSAGE_STORAGE_PREVIOUSLY_SET, oldStorageFileFilePath.toString())); 
    	} 
    	
    	if(java.nio.file.Files.exists(newStorageFileFilePath)){  //Throws SecurityException
    		throw new IllegalArgumentException(String.format(SetStorageCommand.MESSAGE_FILE_WITH_IDENTICAL_NAME_EXISTS, userSpecifiedStorageFileName 
    				+ ".xml", userSpecifiedStorageFolder));
    	} 
    	
    	return new Pair<Path, Path>(newStorageFileFilePath, oldStorageFileFilePath);
    }
    
    @Override
    public synchronized void setStorage(File newStorageFile, File oldStorageFile) throws IOException{
    	assert newStorageFile!= null;
    	assert oldStorageFile!= null;
    	assert !newStorageFile.equals(oldStorageFile);
    	
    	Files.copy(oldStorageFile, newStorageFile);  //Throws IOException
    	
    	//Updates taskManagerFilePath attribute in Config instance, config
    	config.setTaskManagerFilePath(newStorageFile.getCanonicalPath());  //Throws IOException
    	
    	//Serializes Config instance, config to JSON file indicated by config.configFilePath, overwrites existing JSON file
    	ConfigUtil.saveConfig(config, config.getConfigFilePath());  //Throws IOException
    }
    //@@author 
    
    //=========== Filtered Task List Accessors ===============================================================
    
    public List<Alias> getAliasList() {
    	return aliasManager.getInternalList();
    }
    //@@author
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }
    
    //@@author A0142184L
    @Override
    public UnmodifiableObservableList<ReadOnlyAlias> getFilteredAliasList() {
        return new UnmodifiableObservableList<>(filteredAliases);
    }
    //@@author
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getUnfilteredTaskList() {
        return new UnmodifiableObservableList<>(taskManager.getFilteredTasks());
    }
    
    //@@author A0142184L
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getNonDoneTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks.filtered(TaskFilter.isDone().negate()));
    }

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getTodayTaskList() {
        return new UnmodifiableObservableList<>(taskManager.getFilteredTasks().filtered(TaskFilter.isDone().negate().and(TaskFilter.isTodayTask())));
	}

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getTomorrowTaskList() {
        return new UnmodifiableObservableList<>(taskManager.getFilteredTasks().filtered(TaskFilter.isDone().negate().and(TaskFilter.isTomorrowTask())));
	}

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getIn7DaysTaskList() {
        return new UnmodifiableObservableList<>(taskManager.getFilteredTasks().filtered(TaskFilter.isDone().negate().and(TaskFilter.isIn7DaysTask())));
	}

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getIn30DaysTaskList() {
        return new UnmodifiableObservableList<>(taskManager.getFilteredTasks().filtered(TaskFilter.isDone().negate().and(TaskFilter.isIn30DaysTask())));
	}

	@Override
	public UnmodifiableObservableList<ReadOnlyTask> getSomedayTaskList() {
        return new UnmodifiableObservableList<>(taskManager.getFilteredTasks().filtered(TaskFilter.isDone().negate().and(TaskFilter.isSomedayTask())));
	}
	
	//@@author
    @Override
    public void updateFilteredListToShowAll() {
       filteredTasks.setPredicate(null);
    }
    
    //@@author A0139339W
    @Override
    public void updateFilteredTaskList(Predicate<ReadOnlyTask> taskFilter) {
    	filteredTasks.setPredicate(taskFilter);
    }
    //@@author

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
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().value + task.tagsString(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
}
