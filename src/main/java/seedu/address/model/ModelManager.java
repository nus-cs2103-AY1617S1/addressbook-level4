package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.state.StateManager;
import seedu.address.model.state.TaskManagerState;
import seedu.address.model.task.EventDate;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.storage.StoragePathChangedEvent;
import seedu.address.commons.exceptions.StateLimitException;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private FilteredList<Task> filteredTasks;
    private final StateManager stateManager;

    /**
     * Initializes a ModelManager with the given TaskManager
     * TaskManager and its variables should not be null
     */
    public ModelManager(TaskManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task manager: " + src + " and user prefs " + userPrefs);

        taskManager = new TaskManager(src);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
        stateManager = new StateManager(new TaskManagerState(taskManager, ""));
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
        stateManager = new StateManager(new TaskManagerState(taskManager, ""));
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

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    @Override
    public synchronized void refreshTask(){
        taskManager.refreshTask();
        //System.out.println("inside refreshTask");
        //filteredTasks = new FilteredList<>(taskManager.getTasks());
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    
    @Override
    public synchronized void markTask(ReadOnlyTask task) {
        taskManager.markTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    
    @Override
    public void updateTaskManager(String filePath, boolean isToClearOld) {
        EventsCenter.getInstance().post(new StoragePathChangedEvent(filePath, isToClearOld));
        indicateTaskManagerChanged();
    }
    
    @Override
    public void saveState(String message) {
        stateManager.saveState(new TaskManagerState(taskManager, message));
    }
    
    @Override
    public String getPreviousState() throws StateLimitException {
        TaskManagerState previousState = stateManager.getPreviousState();
        return getState(previousState);
    }
    
    @Override
    public String getNextState() throws StateLimitException {
        TaskManagerState nextState = stateManager.getNextState();
        return getState(nextState);
    }
    
    private String getState(TaskManagerState state) {
        resetData(state.getTaskManager());
        return state.getMessage();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
      /*   for(Task e:filteredTasks){
           if(e.isRecurring())
             System.out.println(e.getDate().toString());
        }*/
        
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }
    
    @Override
    public void updateFilteredTaskList(String event){
    	if(event.equals("events")) {
    	updateFilteredTaskList(new PredicateExpression(new EventQualifier()));
    	} else if(event.equals("tasks")) {
    		updateFilteredTaskList(new PredicateExpression(new TaskQualifier()));
    	} else {
    		updateFilteredTaskList(new PredicateExpression(new DoneQualifier(event)));
    	}

    }
    
    @Override
    public void updateFilteredTaskList(String dateValue, boolean isEventDate){
        updateFilteredTaskList(new PredicateExpression(new DateQualifier(dateValue, isEventDate)));
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
        private String keyword;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
            this.keyword=null;
        }
        NameQualifier(String keyword){
        	this.keyword=keyword;
        	this.nameKeyWords=null;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
        	if(nameKeyWords!=null){
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().taskName, keyword))
                    .findAny()
                    .isPresent();
        	}else{
        		return task.getName().taskName.equals(keyword.trim());
        	}
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
    private class EventQualifier implements Qualifier{
        EventQualifier(){}
		@Override
		public boolean run(ReadOnlyTask task) {
			
			return task.isEvent();
		}
		@Override
		public String toString(){
			return "name";
		}
    	
    }
    
    private class TaskQualifier implements Qualifier{
    	TaskQualifier(){}
    	@Override
    	public boolean run(ReadOnlyTask task){
    		return !task.isEvent();
    	}
    	@Override
    	public String toString(){
    		return "name";
    	}
    }

    private class DoneQualifier implements Qualifier{
        private boolean isDone;
        
        DoneQualifier(String isDone){
            this.isDone = isDone.equals("done");
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.isDone() == isDone;
        }
        
        @Override
        public String toString(){
            return "done=" + isDone;
        }
    }
    	
    private class DateQualifier implements Qualifier {
        private String dateValue;
        private boolean isEventDate;

        DateQualifier(String dateValue, boolean isEventDate) {
            assert dateValue != null;
            this.dateValue = dateValue.trim();
            this.isEventDate = isEventDate;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (isEventDate) {
                return task.isEvent() && ((EventDate) task.getDate()).getStartDate().equals(dateValue);
            } else {
                return task.getDate().getValue().equals(dateValue);
            }
        }

        @Override
        public String toString() {
            return "date=" + dateValue;
        }
    }
    
}
