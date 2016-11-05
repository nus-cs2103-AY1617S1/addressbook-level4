package seedu.menion.model;

import javafx.collections.transformation.FilteredList;
import seedu.menion.commons.core.ComponentManager;
import seedu.menion.commons.core.LogsCenter;
import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.commons.events.model.ActivityManagerChangedEvent;
import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.commons.util.StringUtil;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.UniqueActivityList;
import seedu.menion.model.activity.UniqueActivityList.ActivityNotFoundException;
import seedu.menion.model.TaskComparator;

import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the activity manager data. All changes to
 * any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ActivityManager activityManager;
    private final FilteredList<Activity> filteredTasks;
    private final FilteredList<Activity> filteredFloatingTasks;
    private final FilteredList<Activity> filteredEvents;
    private Stack<ReadOnlyActivityManager> activityManagerUndoStack;
    private Stack<ReadOnlyActivityManager> activityManagerRedoStack;
    private ReadOnlyActivity mostRecentUpdatedActivity;
    
    /**
     * Initializes a ModelManager with the given Activity Manager
     * ActivityManager and its variables should not be null
     */
    public ModelManager(ActivityManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with activity manager: " + src + " and user prefs " + userPrefs);

        activityManager = new ActivityManager(src);
        filteredTasks = new FilteredList<>(activityManager.getTasks());
        filteredFloatingTasks = new FilteredList<>(activityManager.getFloatingTasks());
        filteredEvents = new FilteredList<>(activityManager.getEvents());
        activityManagerUndoStack = new Stack<ReadOnlyActivityManager>();
        activityManagerRedoStack = new Stack<ReadOnlyActivityManager>();
    }

    public ModelManager() {
        this(new ActivityManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyActivityManager initialData, UserPrefs userPrefs) {
        activityManager = new ActivityManager(initialData);
        filteredTasks = new FilteredList<>(activityManager.getTasks());
        filteredFloatingTasks = new FilteredList<>(activityManager.getFloatingTasks());
        filteredEvents = new FilteredList<>(activityManager.getEvents());
        activityManagerUndoStack = new Stack<ReadOnlyActivityManager>();
        activityManagerRedoStack = new Stack<ReadOnlyActivityManager>();
    }

    @Override
    public void resetData(ReadOnlyActivityManager newData) {
        activityManager.resetData(newData);
        indicateActivityManagerChanged();
    }

    @Override
    public ReadOnlyActivityManager getActivityManager() {
        return activityManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateActivityManagerChanged() {
        raise(new ActivityManagerChangedEvent(activityManager));
    }

    //@@author A0139515A
    /**
     * Methods for undo 
     * 
     */

    @Override
    public void addStateToUndoStack(ReadOnlyActivityManager activityManager) {
    	activityManagerUndoStack.push(activityManager);
    }

    @Override
    public ReadOnlyActivityManager retrievePreviousStateFromUndoStack() {
    	return activityManagerUndoStack.pop();
    }

    @Override
    public boolean checkStatesInUndoStack() {
    	return this.activityManagerUndoStack.isEmpty();
    }
    
    /**
     * Methods for redo
     * 
     */
    @Override
    public void addStateToRedoStack(ReadOnlyActivityManager activityManager) {
    	activityManagerRedoStack.push(activityManager);
    }

    @Override
    public ReadOnlyActivityManager retrievePreviousStateFromRedoStack() {
    	return activityManagerRedoStack.pop();
    }

    @Override
    public boolean checkStatesInRedoStack() {
    	return this.activityManagerRedoStack.isEmpty();
    }
    
    /**
     * Methods for most recently changed activity
     */
    
    @Override
    public void updateRecentChangedActivity(ReadOnlyActivity activity) {
    	this.mostRecentUpdatedActivity = activity;
    }
    
    @Override
    public ReadOnlyActivity getMostRecentUpdatedActivity() {
    	return this.mostRecentUpdatedActivity;
    }

    //@@author A0139164A
    /**
     * Methods for Completing an activity
     */
    @Override
    public void completeFloatingTask(ReadOnlyActivity activityToComplete) throws ActivityNotFoundException {
        activityManager.completeFloatingTask(activityToComplete);
        indicateActivityManagerChanged();
    }
    @Override
    public void completeTask(ReadOnlyActivity activityToComplete) throws ActivityNotFoundException {
        activityManager.completeTask(activityToComplete);
        indicateActivityManagerChanged();
    }

    /**
     * Methods for Un-completing an activity
     */
    @Override
    public void UncompleteFloatingTask(ReadOnlyActivity activityToUncomplete) throws ActivityNotFoundException {
        activityManager.unCompleteFloatingTask(activityToUncomplete);
        indicateActivityManagerChanged();
    }

    @Override
    public void UncompleteTask(ReadOnlyActivity activityToUncomplete) throws ActivityNotFoundException {
        activityManager.unCompleteTask(activityToUncomplete);
        indicateActivityManagerChanged();
    }

    
    //@@author A0139164A
    /**
     * Methods for editing Activity's name
     * @throws IllegalValueException 
     */
    @Override
    public void editFloatingTaskName(ReadOnlyActivity floatingTaskToEdit, String changes) throws IllegalValueException, ActivityNotFoundException{
        activityManager.editFloatingTaskName(floatingTaskToEdit, changes);
        indicateActivityManagerChanged();
    }
    
    @Override 
    public void editTaskName(ReadOnlyActivity taskToEdit, String changes) throws IllegalValueException, ActivityNotFoundException {
        activityManager.editTaskName(taskToEdit, changes);
        indicateActivityManagerChanged();
    }
    
    @Override
    public void editEventName(ReadOnlyActivity eventToEdit, String changes) throws IllegalValueException, ActivityNotFoundException {
        activityManager.editEventName(eventToEdit, changes);
        indicateActivityManagerChanged();
    }

    /**
     * Methods for editting Activity's note
     * @throws IllegalValueException 
     */
    @Override
    public void editFloatingTaskNote(ReadOnlyActivity floatingTaskToEdit, String changes) throws IllegalValueException, ActivityNotFoundException {
        activityManager.editFloatingTaskNote(floatingTaskToEdit, changes);
        indicateActivityManagerChanged();
    }

    @Override
    public void editTaskNote(ReadOnlyActivity taskToEdit, String changes) throws IllegalValueException, ActivityNotFoundException {
        activityManager.editTaskNote(taskToEdit, changes);
        indicateActivityManagerChanged();
    }
    
    @Override
    public void editEventNote(ReadOnlyActivity eventToEdit, String changes) throws IllegalValueException, ActivityNotFoundException {
        activityManager.editEventNote(eventToEdit, changes);
        indicateActivityManagerChanged();
    }
    
    /**
     * Methods for editting Activities Starting Date & Time
     * @throws IllegalValueException 
     */
    
    @Override
    public void editTaskToFloating(ReadOnlyActivity taskToEdit)
            throws IllegalValueException, ActivityNotFoundException {
        activityManager.editTaskToFloating(taskToEdit);
        indicateActivityManagerChanged();
        
    }
    @Override
    public void editTaskDateTime(ReadOnlyActivity taskToEdit, String newDate, String newTime) throws IllegalValueException, ActivityNotFoundException {
        activityManager.editTaskDateTime(taskToEdit, newDate, newTime);
        indicateActivityManagerChanged();
    }
    
    @Override 
    public void editEventStartDateTime(ReadOnlyActivity eventToEdit, String newDate, String newTime) throws IllegalValueException, ActivityNotFoundException {
        activityManager.editEventStartDateTime(eventToEdit, newDate, newTime);
        indicateActivityManagerChanged();
        
    }
    
    @Override
    public void editEventEndDateTime(ReadOnlyActivity eventToEdit, String newDate, String newTime) throws IllegalValueException, ActivityNotFoundException {
        activityManager.editEventEndDateTime(eventToEdit, newDate, newTime);
        indicateActivityManagerChanged();
    }
    
    //@@author A0146752B
    @Override
    public synchronized void deleteTask(ReadOnlyActivity target) throws ActivityNotFoundException {
        activityManager.removeTask(target);
        indicateActivityManagerChanged();
    }

    @Override
    public synchronized void addTask(Activity activity) throws UniqueActivityList.DuplicateTaskException {
        activityManager.addTask(activity);
        indicateActivityManagerChanged();
    }
    
    @Override
    public synchronized void deleteFloatingTask(ReadOnlyActivity target) throws ActivityNotFoundException {
        activityManager.removeFloatingTask(target);
        indicateActivityManagerChanged();
    }

    @Override
    public synchronized void addFloatingTask(Activity activity) throws UniqueActivityList.DuplicateTaskException {
        activityManager.addFloatingTask(activity);
        indicateActivityManagerChanged();
    }
    
    @Override
    public synchronized void deleteEvent(ReadOnlyActivity target) throws ActivityNotFoundException {
        activityManager.removeEvent(target);
        indicateActivityManagerChanged();
    }

    @Override
    public synchronized void addEvent(Activity activity) throws UniqueActivityList.DuplicateTaskException {
        activityManager.addEvent(activity);
        indicateActivityManagerChanged();
    }

    // =========== Filtered activity List Accessors
    // ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyActivity> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyActivity> getFilteredFloatingTaskList() {
        return new UnmodifiableObservableList<>(filteredFloatingTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyActivity> getFilteredEventList() {
        return new UnmodifiableObservableList<>(filteredEvents);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        filteredFloatingTasks.setPredicate(null);
        filteredEvents.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredFloatingTaskList(Set<String> keywords) {
        updateFilteredFloatingTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredFloatingTaskList(Expression expression) {
        filteredFloatingTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredEventList(Set<String> keywords) {
        updateFilteredEventList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredEventList(Expression expression) {
        filteredEvents.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering
    // ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyActivity activity);

        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyActivity activity) {
            return qualifier.run(activity);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyActivity activity);

        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        //@@author A0139277U
        @Override
        public boolean run(ReadOnlyActivity activity) {
        	String activityKeyWords;
        	
        	if (activity.getActivityType().equals(Activity.TASK_TYPE)){
        		activityKeyWords = activity.getActivityName().fullName + " " + activity.getActivityStartDate().toString() +
        				" " + activity.getActivityStartDate().getMonth() + " " + activity.getActivityStatus().toString();
        	}
        	else if (activity.getActivityType().equals(Activity.EVENT_TYPE)){
        		activityKeyWords = activity.getActivityName().fullName + " " + activity.getActivityStartDate().toString() + 
        				" " + activity.getActivityEndDate() + " " + activity.getActivityStartDate().getMonth() + " " + 
        				activity.getActivityEndDate().getMonth() + " " + activity.getActivityStatus().toString(); 
        	}
        	else {
        		activityKeyWords = activity.getActivityName().fullName + " " + activity.getActivityStatus().toString();
        	}
        	
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(activityKeyWords, keyword)).findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
