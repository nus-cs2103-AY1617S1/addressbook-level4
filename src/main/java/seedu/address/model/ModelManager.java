package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.core.ComponentManager;

import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<Task> filteredTasks;

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
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getAddressBook() {
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
    @Override

    public void updateFilteredTaskList(String event){
    	if(event.equals("events")){
    	updateFilteredTaskList(new PredicateExpression(new EventQualifier()));
    	}else if(event.equals("tasks")){
    		updateFilteredTaskList(new PredicateExpression(new TaskQualifier()));
    	}else{
    		updateFilteredTaskList(new PredicateExpression(new DoneQualifier()));
    	}

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
    	DoneQualifier(){}

		@Override
		public boolean run(ReadOnlyTask task) {
			return task.isDone();
		}
		@Override
		public String toString(){
			return "name";
		}
    	
    }

}
