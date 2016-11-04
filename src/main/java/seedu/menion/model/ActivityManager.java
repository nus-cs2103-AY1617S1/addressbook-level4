package seedu.menion.model;

import javafx.collections.ObservableList;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.background.BackgroundDateCheck;
import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.logic.commands.EditCommand;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.UniqueActivityList;
import seedu.menion.model.activity.UniqueActivityList.ActivityNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task manager level
 * Duplicates are not allowed (by .equals comparison)
 */
//@@author A0146752B
public class ActivityManager implements ReadOnlyActivityManager {

    private final UniqueActivityList tasks;
    private final UniqueActivityList floatingTasks;
    private final UniqueActivityList events;

    {
        tasks = new UniqueActivityList();
        floatingTasks = new UniqueActivityList();
        events = new UniqueActivityList();
    }

    public ActivityManager() {}

    /**
     * Tasks and Tags are copied into this activity manager
     */
    public ActivityManager(ReadOnlyActivityManager toBeCopied) {
        this(toBeCopied.getUniqueTaskList(),
                 toBeCopied.getUniqueFloatingTaskList(),
                 toBeCopied.getUniqueEventList());
    }

    /**
     * Tasks and Tags are copied into this task manager
     */
    public ActivityManager(UniqueActivityList tasks, 
                            UniqueActivityList floatingTasks,
                            UniqueActivityList events) {
        resetData(tasks.getInternalList(),
                     floatingTasks.getInternalList(),
                     events.getInternalList());
    }

    public static ReadOnlyActivityManager getEmptyActivityManager() {
        return new ActivityManager();
    }

//// list overwrite operations

    public ObservableList<Activity> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Activity> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }
    
    
    public ObservableList<Activity> getFloatingTasks() {
        return floatingTasks.getInternalList();
    }

    public void setFloatingTasks(List<Activity> floatingTasks) {
        this.floatingTasks.getInternalList().setAll(floatingTasks);
    }
    
    public ObservableList<Activity> getEvents() {
        return events.getInternalList();
    }

    public void setEvents(List<Activity> events) {
        this.events.getInternalList().setAll(events);
    }
    
    


    public void resetData(Collection<? extends ReadOnlyActivity> newTasks, 
                            Collection<? extends ReadOnlyActivity> newFloatingTasks,
                            Collection<? extends ReadOnlyActivity> newEvents) {
        setTasks(newTasks.stream().map(Activity::new).collect(Collectors.toList()));
        setFloatingTasks(newFloatingTasks.stream().map(Activity::new).collect(Collectors.toList()));
        setEvents(newEvents.stream().map(Activity::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyActivityManager newData) {
        resetData(newData.getTaskList(),
                    newData.getFloatingTaskList(),
                    newData.getEventList());
    }

//// task-level operations

    /**
     * Adds a task to the activity manager.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueActivityList.DuplicateTaskException if an equivalent tasks already exists.
     */
    public void addTask(Activity t) throws UniqueActivityList.DuplicateTaskException {
        //syncTagsWithMasterList(t);
        tasks.add(t);
        Collections.sort(tasks.getInternalList(), new TaskComparator());
    }
    
    /**
     * Adds a floating task to the activity manager.
     * Also checks the new floating task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the floating task to point to those in {@link #tags}.
     *
     * @throws UniqueActivityList.DuplicateTaskException if an equivalent tasks already exists.
     */
    
    public void addFloatingTask(Activity t) throws UniqueActivityList.DuplicateTaskException {
        //syncTagsWithMasterList(t);
        floatingTasks.add(t);
        Collections.sort(floatingTasks.getInternalList(), new FloatingTaskComparator());
    }
    
    
    /**
     * Adds an event to the activity manager.
     * Also checks the new event's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the event to point to those in {@link #tags}.
     *
     * @throws UniqueActivityList.DuplicateTaskException if an equivalent tasks already exists.
     */
    
    public void addEvent(Activity t) throws UniqueActivityList.DuplicateTaskException {
        //syncTagsWithMasterList(t);
        events.add(t);
        Collections.sort(events.getInternalList(), new EventComparator());
    }
    
    
    //@@author A0139164A
    /**
     * Methods, Completes an activity in the activity manager.
     * Passes in the index of the list to complete
     * 
     * @param activityToComplete
     */
    public void completeTask(ReadOnlyActivity activityToComplete) throws ActivityNotFoundException {
        Activity dub = (Activity)activityToComplete;
        dub.setCompleted();
        tasks.getInternalList().set(tasks.getIndexOf(activityToComplete), dub);  
        Collections.sort(tasks.getInternalList(), new TaskComparator());
    }

    public void completeFloatingTask(ReadOnlyActivity activityToComplete) throws ActivityNotFoundException {
        Activity dub = (Activity)activityToComplete;
        dub.setCompleted();
        floatingTasks.getInternalList().set(floatingTasks.getIndexOf(activityToComplete), dub);
        Collections.sort(floatingTasks.getInternalList(), new FloatingTaskComparator());
    }
    
    /**
     * Methods, UnCompletes an activity in the activity manager.
     * Passes in the index of the list to complete
     * @param activityToUncomplete
     */
    public void unCompleteFloatingTask(ReadOnlyActivity activityToUncomplete) throws ActivityNotFoundException {
        Activity dub = (Activity)activityToUncomplete;
        dub.setUncompleted();
        floatingTasks.getInternalList().set(floatingTasks.getIndexOf(activityToUncomplete), dub);
        Collections.sort(floatingTasks.getInternalList(), new FloatingTaskComparator());
    }
    
    public void unCompleteTask(ReadOnlyActivity activityToUncomplete) throws ActivityNotFoundException {
        Activity dub = (Activity)activityToUncomplete;
        dub.setUncompleted();
        tasks.getInternalList().set(tasks.getIndexOf(activityToUncomplete), dub);
        Collections.sort(tasks.getInternalList(), new TaskComparator());
    }
    
    /**
     * Methods, edits an activity's NAME in the activity manager.
     * Passes in the index of the list to complete, and changes to make
     * @param floatingTaskToEdit
     * @throws IllegalValueException 
     */
    public void editFloatingTaskName(ReadOnlyActivity floatingTaskToEdit, String changes) throws IllegalValueException, ActivityNotFoundException {
        Activity dub = (Activity)floatingTaskToEdit;
        dub.setActivityName(changes);
        floatingTasks.getInternalList().set(floatingTasks.getIndexOf(floatingTaskToEdit), dub);
        Collections.sort(floatingTasks.getInternalList(), new FloatingTaskComparator());
    }
    
    public void editTaskName(ReadOnlyActivity taskToEdit, String changes) throws IllegalValueException, ActivityNotFoundException {
        Activity dub = (Activity)taskToEdit;
        dub.setActivityName(changes);
        tasks.getInternalList().set(tasks.getIndexOf(taskToEdit), dub); 
        Collections.sort(tasks.getInternalList(), new TaskComparator());
    }
    
    public void editEventName(ReadOnlyActivity eventToEdit, String changes) throws IllegalValueException, ActivityNotFoundException {
        Activity dub = (Activity)eventToEdit;
        dub.setActivityName(changes);
        events.getInternalList().set(events.getIndexOf(eventToEdit), dub);   
        Collections.sort(events.getInternalList(), new EventComparator());
    }
    
    /**
     * Methods, edits an activity's NOTE in the activity manager.
     * Passes in the index of the list to complete, and changes to make
     * @param floatingTaskToEdit
     * @throws IllegalValueException 
     */
    public void editFloatingTaskNote(ReadOnlyActivity floatingTaskToEdit, String changes) throws IllegalValueException, ActivityNotFoundException {
        Activity dub = (Activity)floatingTaskToEdit;
        dub.setActivityNote(changes);
        floatingTasks.getInternalList().set(floatingTasks.getIndexOf(floatingTaskToEdit), dub);
    }
    
    public void editTaskNote(ReadOnlyActivity taskToEdit, String changes) throws IllegalValueException, ActivityNotFoundException {
        Activity dub = (Activity)taskToEdit;
        dub.setActivityNote(changes);
        tasks.getInternalList().set(tasks.getIndexOf(taskToEdit), dub); 
    }
    
    public void editEventNote(ReadOnlyActivity eventToEdit, String changes) throws IllegalValueException, ActivityNotFoundException {
        Activity dub = (Activity)eventToEdit;
        dub.setActivityNote(changes);
        events.getInternalList().set(events.getIndexOf(eventToEdit), dub);
    }
    
    /**
     * Methods, edits a Activity's starting Date & Time in the activity manager.
     * Passes in the index of the list to complete, and changes to make
     * @param taskToEdit
     * @throws IllegalValueException 
     */

    public void editTaskDateTime(ReadOnlyActivity taskToEdit, String newDate, String newTime) throws IllegalValueException, ActivityNotFoundException {
        Activity dub = (Activity)taskToEdit;
        dub.setActivityStartDateTime(newDate, newTime);
        // Check if the task is overdue/not overdue after an edit.
        Calendar currentTime = Calendar.getInstance();
        
        // Only goes into this conditional statement if it is overdue.
        if (BackgroundDateCheck.isActivityOver(currentTime, taskToEdit)) {
            dub.setTimePassed(true);
        } else {
            // Not overdue.
            dub.setTimePassed(false);
            dub.setEmailSent(false);
        }
        // It is a floatingTask changing to a Task
        // Removes the floatingTask from it's panel and adds it to task
        if (taskToEdit.getActivityType().equals(Activity.FLOATING_TASK_TYPE)) {
            removeFloatingTask(taskToEdit);
            dub.setActivityType(Activity.TASK_TYPE);
            tasks.getInternalList().add(dub);
            Collections.sort(tasks.getInternalList(), new TaskComparator());
            return;
        }
        tasks.getInternalList().set(tasks.getIndexOf(taskToEdit), dub); 
        Collections.sort(tasks.getInternalList(), new TaskComparator());
    }

    public void editEventStartDateTime(ReadOnlyActivity eventToEdit, String newDate, String newTime) throws IllegalValueException, ActivityNotFoundException {
        Activity dub = (Activity)eventToEdit;
        dub.setActivityStartDateTime(newDate, newTime);
        events.getInternalList().set(events.getIndexOf(eventToEdit), dub);
        Collections.sort(events.getInternalList(), new EventComparator());
    }
    
    public void editEventEndDateTime(ReadOnlyActivity eventToEdit, String newDate, String newTime) throws IllegalValueException, ActivityNotFoundException {    
        Activity dub = (Activity)eventToEdit;
        dub.setActivityEndDateTime(newDate, newTime);
        events.getInternalList().set(events.getIndexOf(eventToEdit), dub);
    }
    
    //@@author A0146752B
    public boolean removeTask(ReadOnlyActivity key) throws UniqueActivityList.ActivityNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueActivityList.ActivityNotFoundException();
        }
    }
    
    
    public boolean removeFloatingTask(ReadOnlyActivity key) throws UniqueActivityList.ActivityNotFoundException {
        if (floatingTasks.remove(key)) {
            return true;
        } else {
            throw new UniqueActivityList.ActivityNotFoundException();
        }
    }
    
    
    public boolean removeEvent(ReadOnlyActivity key) throws UniqueActivityList.ActivityNotFoundException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new UniqueActivityList.ActivityNotFoundException();
        }
    }
    

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, "
                + floatingTasks.getInternalList().size() + " floating tasks, "
                + events.getInternalList().size() + " events, ";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyActivity> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }
    
    
    @Override
    public List<ReadOnlyActivity> getFloatingTaskList() {
        return Collections.unmodifiableList(floatingTasks.getInternalList());
    }
    
    @Override
    public List<ReadOnlyActivity> getEventList() {
        return Collections.unmodifiableList(events.getInternalList());
    }


    @Override
    public UniqueActivityList getUniqueTaskList() {
        return this.tasks;
    }

    
    @Override
    public UniqueActivityList getUniqueFloatingTaskList() {
        return this.floatingTasks;
    }
    
    @Override
    public UniqueActivityList getUniqueEventList() {
        return this.events;
    }
    

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityManager // instanceof handles nulls
                && this.tasks.equals(((ActivityManager) other).tasks)
                && this.floatingTasks.equals(((ActivityManager) other).floatingTasks)
                && this.events.equals(((ActivityManager) other).events));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        //return Objects.hash(tasks);
        return Objects.hash(tasks, floatingTasks, events);
    }
}
