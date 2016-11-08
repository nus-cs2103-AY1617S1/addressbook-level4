package seedu.dailyplanner.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.FilteredList;
import seedu.dailyplanner.commons.core.ComponentManager;
import seedu.dailyplanner.commons.core.LogsCenter;
import seedu.dailyplanner.commons.core.UnmodifiableObservableList;
import seedu.dailyplanner.commons.events.model.DailyPlannerChangedEvent;
import seedu.dailyplanner.commons.util.DateUtil;
import seedu.dailyplanner.commons.util.StringUtil;
import seedu.dailyplanner.history.HistoryManager;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.Task;
import seedu.dailyplanner.model.task.UniqueTaskList;
import seedu.dailyplanner.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the daily planner data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final DailyPlanner dailyPlanner;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Task> pinnedTasks;
    private final HistoryManager history;
    private IntegerProperty lastTaskAddedIndex;
    private StringProperty lastShowDate;

    /**
     * Initializes a ModelManager with the given DailyPlanner DailyPlanner and
     * its variables should not be null
     */
    //@@author A0140124B
    public ModelManager(DailyPlanner src, UserPrefs userPrefs) {
	super();
	assert src != null;
	assert userPrefs != null;

	logger.fine("Initializing with daily planner: " + src + " and user prefs " + userPrefs);

	dailyPlanner = new DailyPlanner(src);
	filteredTasks = new FilteredList<>(dailyPlanner.getTasks());
	pinnedTasks = new FilteredList<>(dailyPlanner.getPinnedTasks());
	history = new HistoryManager();
	lastTaskAddedIndex = new SimpleIntegerProperty(0);
	lastShowDate = new SimpleStringProperty();
    }

    public ModelManager() {
	this(new DailyPlanner(), new UserPrefs());
    }

    public ModelManager(ReadOnlyDailyPlanner initialData, UserPrefs userPrefs) {
	dailyPlanner = new DailyPlanner(initialData);
	filteredTasks = new FilteredList<>(dailyPlanner.getTasks());
	pinnedTasks = new FilteredList<>(dailyPlanner.getPinnedTasks());
	history = new HistoryManager();
	lastTaskAddedIndex = new SimpleIntegerProperty(0);
	lastShowDate = new SimpleStringProperty();
    }

    //@@author
    @Override
    public void resetData(ReadOnlyDailyPlanner newData) {
	dailyPlanner.resetData(newData);
	indicateDailyPlannerChanged();
    }

    @Override
    public void resetPinBoard() {
	dailyPlanner.resetPinBoard();
    }

    @Override
    public ReadOnlyDailyPlanner getDailyPlanner() {
	return dailyPlanner;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateDailyPlannerChanged() {
	raise(new DailyPlannerChangedEvent(dailyPlanner));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
	dailyPlanner.removeTask(target);
	indicateDailyPlannerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
	dailyPlanner.addTask(task);
	setLastTaskAddedIndex(dailyPlanner.indexOf(task));
	updateFilteredListToShowAll();
	indicateDailyPlannerChanged();
	setLastShowDate(StringUtil.EMPTY_STRING);
    }
    //@@author A0139102U
    public synchronized void markTaskAsComplete(ReadOnlyTask taskToComplete) throws TaskNotFoundException {
	dailyPlanner.markTaskAsComplete(taskToComplete);
	setLastTaskAddedIndex(dailyPlanner.indexOf((Task) taskToComplete));
	indicateDailyPlannerChanged();
    }

    public synchronized void markTaskAsIncomplete(ReadOnlyTask taskToIncomplete) throws TaskNotFoundException {
	int targetIndex = dailyPlanner.indexOf((Task) taskToIncomplete);
	uncompleteTask(targetIndex);
	setLastTaskAddedIndex(targetIndex);
	indicateDailyPlannerChanged();
    }

    // @@author A0146749N
    @Override
    public void pinTask(ReadOnlyTask taskToPin) throws TaskNotFoundException {
	dailyPlanner.pinTask(taskToPin);
	indicateDailyPlannerChanged();
    }

    @Override
    public void unpinTask(int targetIndex) throws TaskNotFoundException {
	dailyPlanner.unpinTask(targetIndex);
	indicateDailyPlannerChanged();
    }

    @Override
    public void uncompleteTask(int targetIndex) {
	dailyPlanner.uncompleteTask(targetIndex);
	indicateDailyPlannerChanged();
    }

    @Override
    public void updatePinBoard() {
	dailyPlanner.updatePinBoard();
    }

    //@@author
    // =========== Filtered Task List Accessors
    // ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
	return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getPinnedTaskList() {
	return new UnmodifiableObservableList<>(pinnedTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
	filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
	updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
	filteredTasks.setPredicate(expression::satisfies);
    }
 //@@author A0146749N
    @Override
    public void updateFilteredTaskListByDate(Set<String> keywords) {
	updateFilteredTaskList(new PredicateExpression(new DateQualifier(keywords)));
    }

    @Override
    public void updateFilteredTaskListByCompletion(Set<String> keywords) {
	updateFilteredTaskList(new PredicateExpression(new CompletionQualifier(keywords)));
    }

    @Override
    public int getLastTaskAddedIndex() {
	return lastTaskAddedIndex.get();
    }
    
    @Override
    public void setLastTaskAddedIndex(int index) {
	if (index == lastTaskAddedIndex.get()) {
	    lastTaskAddedIndex.set(-1);
	}
	lastTaskAddedIndex.set(index);
    }

    @Override
    public IntegerProperty getLastTaskAddedIndexProperty() {
	return lastTaskAddedIndex;
    }

    @Override
    public String getLastShowDate() {
	return lastShowDate.get();
    }

    @Override
    public void setLastShowDate(String showInput) {
	lastShowDate.set(showInput);
    }

    @Override
    public StringProperty getLastShowDateProperty() {
	return lastShowDate;
    }
  //@@author 
    // ========== Inner classes/interfaces used for filtering
    // ==================================================

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
    //@@author A0146749N
    private class CompletionQualifier implements Qualifier {
	private Set<String> completionKeywords;

	CompletionQualifier(Set<String> completionKeyword) {
	    this.completionKeywords = completionKeyword;
	}

	@Override
	public boolean run(ReadOnlyTask task) {
	    return completionKeywords.contains(task.getCompletion().toLowerCase());
	}

	@Override
	public String toString() {
	    return "completion=" + String.join(", ", completionKeywords);
	}
    }

    private class DateQualifier implements Qualifier {
	private Set<String> dateKeyWords;

	DateQualifier(Set<String> dateKeyWords) {
	    this.dateKeyWords = dateKeyWords;
	}

	@Override
	public boolean run(ReadOnlyTask task) {
	    return dateKeyWords.stream().filter(keyword -> DateUtil.withinDateRange(task, keyword)).findAny()
		    .isPresent();
	}

	@Override
	public String toString() {
	    return "date=" + String.join(", ", dateKeyWords);
	}
    }

    private class NameQualifier implements Qualifier {
	private Set<String> nameKeyWords;

	NameQualifier(Set<String> nameKeyWords) {
	    this.nameKeyWords = nameKeyWords;
	}

	@Override
	public boolean run(ReadOnlyTask task) {
	    return nameKeyWords.stream().filter(keyword -> StringUtil.containsIgnoreCase(task.getName(), keyword))
		    .findAny().isPresent();
	}

	@Override
	public String toString() {
	    return "name=" + String.join(", ", nameKeyWords);
	}
    }

    @Override
    public HistoryManager getHistory() {

	return history;
    }

}
