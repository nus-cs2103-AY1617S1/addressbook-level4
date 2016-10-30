package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.Stemmer;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.state.StateManager;
import seedu.address.model.state.TaskManagerState;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.EventDate;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.storage.RedoStoragePathChangedEvent;
import seedu.address.commons.events.storage.StoragePathChangedBackEvent;
import seedu.address.commons.events.storage.StoragePathChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.StateLimitException;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    //@@author A0146123R
    private static final String EVENTS = "events";
    private static final String TASKS = "tasks";
    private static final String DONE = "done";
    private static final String UNDONE = "undone";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String DEADLINE = "deadline";
    private static final String RECURRING = "recurring";
    //@@author
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
	public void editTask(ReadOnlyTask task, String type, String details) throws IllegalValueException {
		taskManager.editTask(task, type, details);
		updateFilteredListToShowAll();
        indicateTaskManagerChanged();
	}

    //@@author A0142325R
    @Override
    public synchronized void refreshTask(){
        taskManager.refreshTask();
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void markTask(ReadOnlyTask task) {
        taskManager.markTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    
    //@@author A0146123R
    @Override
    public void updateTaskManager(String filePath, boolean isToClearOld) {
        EventsCenter.getInstance().post(new StoragePathChangedEvent(filePath, isToClearOld));
        indicateTaskManagerChanged();
    }

    @Override
    public void changeBackTaskManager(boolean isToClearNew) {
        EventsCenter.getInstance().post(new StoragePathChangedBackEvent(isToClearNew));
        indicateTaskManagerChanged();
    }
    
    @Override
    public void redoUpdateTaskManager(boolean isToClearOld) {
        EventsCenter.getInstance().post(new RedoStoragePathChangedEvent(isToClearOld));
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

    //@@author A0142325R
    @Override
    public void updateFilteredTaskList(String type) {
        switch (type) {
        case EVENTS:
            updateFilteredTaskList(new PredicateExpression(new EventQualifier()));
            break;
        case TASKS:
            updateFilteredTaskList(new PredicateExpression(new TaskQualifier()));
            break;
        case DONE:
        case UNDONE:
            updateFilteredTaskList(new PredicateExpression(new DoneQualifier(type)));
            break;
        default:
            updateFilteredListToShowAll();
            break;
        }
    }
    
    //@@author A0146123R
    @Override
    public void updateFilteredTaskList(String keyword, String type) {
        switch (type) {
        case START_DATE:
        case DEADLINE:
        case END_DATE:
            updateFilteredTaskList(new PredicateExpression(new DateQualifier(keyword, type)));
            break;
        case RECURRING:
            updateFilteredTaskList(new PredicateExpression(new RecurringQualifier(keyword)));
            break;
        default:
            updateFilteredListToShowAll();
            break;
        }
    }

    @Override
    public void updateFilteredTaskListWithKeywords(Set<Set<String>> keywordsGroups){
        PredicateExpression[] predicate = new PredicateExpression[keywordsGroups.size()];
        int i = 0;
        for (Set<String> keywords : keywordsGroups) {
            predicate[i] = new PredicateExpression(new NameQualifier(keywords));
            i++;
        }
        updateFilteredTaskList(predicate);
    }
    
    @Override
    public void updateFilteredTaskListWithStemmedKeywords(Set<Set<String>> keywordsGroups){
        PredicateExpression[] predicate = new PredicateExpression[keywordsGroups.size()];
        int i = 0;
        for (Set<String> keywords : keywordsGroups) {
            predicate[i] = new PredicateExpression(new StemmedNameQualifier(keywords));
            i++;
        }
        updateFilteredTaskList(predicate);
    }

    @Override
    public void updateFilteredTaskListByTags(Set<String> keyword) {
        updateFilteredTaskList(new PredicateExpression(new TagQualifier(keyword)));
    }
    
    private void updateFilteredTaskList(Expression... expression) {
        Predicate<? super Task> predicate;
        Predicate<Task> predicates = task -> expression[0].satisfies(task);;
        for (Expression e: expression) {
            predicate = task -> e.satisfies(task);
            predicates = predicates.and(predicate);
        }
        filteredTasks.setPredicate(predicates);
    }
    //@@author

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
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().taskName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }
    
    //@@author A0146123R
    private class StemmedNameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        StemmedNameQualifier(Set<String> nameKeyWords) {
            Stemmer stemmer = new Stemmer();
            this.nameKeyWords = nameKeyWords.stream().map(keyword -> stemmer.stem(keyword))
                    .collect(Collectors.toSet());
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            Set<String> taskName = getStemmedTaskName(task);
            Stemmer stemmer = new Stemmer();
            return nameKeyWords.stream()
                    .filter(keyword -> taskName.stream()
                            .map(name -> stemmer.stem(name))
                            .filter(name -> name.equals(keyword)).count() > 0)
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "stemmed name=" + String.join(", ", nameKeyWords);
        }
        
        private Set<String> getStemmedTaskName(ReadOnlyTask task) {
            String[] taskName = task.getName().taskName.split("\\s+");
            return new HashSet<>(Arrays.asList(taskName));
        }
    }
    
    //@@author A0142325R
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
            this.isDone = isDone.equals(DONE);
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
    
    //@@author A0146123R
    private class DateQualifier implements Qualifier {
        private String dateValue;
        private String dateType;

        DateQualifier(String dateValue, String dateType) {
            assert dateValue != null;
            this.dateValue = dateValue.trim();
            this.dateType = dateType;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            switch (dateType) {
            case START_DATE:
                return task.isEvent() && ((EventDate) task.getDate()).getStartDate().equals(dateValue);
            case END_DATE:
                return task.isEvent() && ((EventDate) task.getDate()).getEndDate().equals(dateValue);
            case DEADLINE:
                return task.getDate().getValue().equals(dateValue);
            default:
                return false;
            }
        }

        @Override
        public String toString() {
            return "date type=" + dateType +  " date=" + dateValue;
        }
    }
    
    //@@author A0142325R
    private class RecurringQualifier implements Qualifier{
        private String recurring;
        private static final String EMPTY = "";
        
        RecurringQualifier(String recurring){
            this.recurring = recurring;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (recurring.equals(EMPTY)) {
                return task.getRecurring() == null;
            } else {
                return task.getRecurring() != null && this.recurring.equals(task.getRecurring().recurringFrequency);
            }
        }
        
        @Override
        public String toString(){
            return "recurring=" + recurring;
        }
    }
    
    //@@author A0146123R
    private class TagQualifier implements Qualifier {
        private Set<String> tagKeyWords;

        TagQualifier(Set<String> tagKeyWords) {
            this.tagKeyWords = tagKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return tagKeyWords.stream().filter(keyword -> {
                try {
                    return task.getTags().contains(new Tag(keyword));
                } catch (IllegalValueException e) {
                    return false;
                }
            }).findAny().isPresent();
        }

        @Override
        public String toString() {
            return "tags=" + String.join(", ", tagKeyWords);
        }
    }

}
