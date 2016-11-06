package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.Stemmer;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.Types;
import seedu.address.model.state.StateManager;
import seedu.address.model.state.TaskManagerState;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.EventDate;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.ui.FilterPanelChangedEvent;
import seedu.address.commons.events.ui.UpdateFilterPanelEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.StateLimitException;
import seedu.address.commons.core.ComponentManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

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
    public synchronized void addTask(Task task) {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }
    //@@author A0138717X
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
        stateManager.saveFilePath(filePath, isToClearOld);
        indicateTaskManagerChanged();
    }

    @Override
    public void changeBackTaskManager(boolean isToClearNew) throws StateLimitException {
        stateManager.getPreviousFilePath(isToClearNew);
        indicateTaskManagerChanged();
    }

    @Override
    public void redoUpdateTaskManager() throws StateLimitException {
        stateManager.getNextFilePath();
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

    //@@author A016123R
    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        raise(new UpdateFilterPanelEvent(new HashSet<Types>(), new HashMap<Types, String>(), new HashSet<String>()));
    }
    //@@author

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    //@@author A0142325R
    @Override
    public void updateFilteredTaskList(Types type) {
        updateFilteredTaskList(getPredicateForType(type));
        Set<Types> types = new HashSet<>();
        types.add(type);
        raise(new UpdateFilterPanelEvent(types, new HashMap<Types, String>(), new HashSet<String>()));
    }

    private Expression getPredicateForType(Types type) {
        switch (type) {
        case EVENTS:
            return new PredicateExpression(new EventQualifier());
        case TASKS:
            return new PredicateExpression(new TaskQualifier());
        case DONE:
        case UNDONE:
            return new PredicateExpression(new DoneQualifier(type));
        default:
            return null;
        }
    }

    //@@author A0146123R
    @Override
    public void updateFilteredTaskList(Map<Types, String> qualifications, Set<String> tags) {
        updateFilteredTaskListAndOperation(getPredicateForMultipleQualifications(qualifications, tags));
        raise(new UpdateFilterPanelEvent(new HashSet<Types>(), qualifications, tags));
    }

    private ArrayList<Expression> getPredicateForMultipleQualifications(Map<Types, String> qualifications,
            Set<String> tags) {
        ArrayList<Expression> predicate = new ArrayList<>();
        qualifications.forEach((attribute, keyword) -> predicate.add(getPredicateForKeywordType(attribute, keyword)));
        if (!tags.isEmpty()) {
            predicate.add(getPredicateForTags(tags));
        }
        return predicate;
    }
    
    private Expression getPredicateForKeywordType(Types attribute, String keyword) {
        switch (attribute) {
        case START_DATE:
        case DEADLINE:
        case END_DATE:
            return new PredicateExpression(new DateQualifier(keyword, attribute));
        case RECURRING:
            return new PredicateExpression(new RecurringQualifier(keyword));
        case PRIORITY_LEVEL:
            return new PredicateExpression(new PriorityQualifier(Integer.parseInt(keyword)));
        default:
            return null;
        }
    }

    @Override
    public void updateFilteredTaskList(Set<Types> types, Map<Types, String> qualifications, Set<String> tags) {
        ArrayList<Expression> predicate = getPredicateForMultipleQualifications(qualifications, tags);
        types.forEach(type -> predicate.add(getPredicateForType(type)));
        updateFilteredTaskListAndOperation(predicate);
    }
    
    @Override
    public void updateFilteredTaskListWithKeywords(Set<Set<String>> keywordsGroups) {
        ArrayList<Expression> predicate = new ArrayList<>();
        for (Set<String> keywords : keywordsGroups) {
            predicate.add(new PredicateExpression(new NameQualifier(keywords)));
        }
        updateFilteredTaskListAndOperation(predicate);
    }

    @Override
    public void updateFilteredTaskListWithStemmedKeywords(Set<Set<String>> keywordsGroups) {
        ArrayList<Expression> predicate = new ArrayList<>();
        for (Set<String> keywords : keywordsGroups) {
            predicate.add(new PredicateExpression(new StemmedNameQualifier(keywords)));
        }
        updateFilteredTaskListAndOperation(predicate);
    }

    private Expression getPredicateForTags(Set<String> keyword) {
        return new PredicateExpression(new TagQualifier(keyword));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    private void updateFilteredTaskListAndOperation(ArrayList<Expression> expression) {
        Predicate<? super Task> predicate;
        Predicate<Task> predicates = task -> true;
        for (Expression e : expression) {
            predicate = task -> e.satisfies(task);
            predicates = predicates.and(predicate);
        }
        filteredTasks.setPredicate(predicates);
    }

    @Subscribe
    @Override
    public void handleFilterPanelChangedEvent(FilterPanelChangedEvent event) {
        updateFilteredTaskList(event.getTypes(), event.getQualifications(), event.getTags());
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

        DoneQualifier(Types isDone){
            this.isDone = isDone.equals(Types.DONE);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return task.isDone() == isDone;
        }

        @Override
        public String toString() {
            return "done=" + isDone;
        }
    }

    // @@author A0146123R
    private class DateQualifier implements Qualifier {
        private final String TIME_SEPERATOR = "-";
        private final int NUM_OF_PARTS_DAY = 1;
        private final int DAY = 0;

        private String dateValue;
        private Types dateType;
        private boolean isEvent;
        private boolean isDay;

        DateQualifier(String dateValue, Types dateType) {
            assert dateValue != null;
            this.dateValue = dateValue.trim();
            this.dateType = dateType;
            this.isEvent = dateType.equals(Types.DEADLINE) ? false : true;
            this.isDay = isDay(this.dateValue);
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (task.isEvent() == isEvent) {
                String date;
                switch (dateType) {
                case START_DATE:
                    date = ((EventDate) task.getDate()).getStartDate();
                    break;
                case END_DATE:
                    date = ((EventDate) task.getDate()).getEndDate();
                    break;
                case DEADLINE:
                    date = task.getDate().getValue();
                    break;
                default:
                    return false;
                }
                return isDay ? dateValue.equals(getDay(date)) : dateValue.equals(date);
            }
            return false;
        }

        @Override
        public String toString() {
            return "date type=" + dateType + " date=" + dateValue;
        }

        private boolean isDay(String date) {
            return date.split(TIME_SEPERATOR).length == NUM_OF_PARTS_DAY;
        }

        private String getDay(String date) {
            return date.split(TIME_SEPERATOR)[DAY];
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

    //@@author A0138717X
    private class PriorityQualifier implements Qualifier {
    	private int priorityLevel;

    	PriorityQualifier(int priorityLevel) {
    		this.priorityLevel = priorityLevel;
    	}

		@Override
		public boolean run(ReadOnlyTask task) {
			return task.getPriorityLevel().priorityLevel == priorityLevel;
		}

		@Override
        public String toString(){
            return "priority=" + priorityLevel;
        }
    }

}
