package seedu.menion.model;

import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.commons.exceptions.IllegalValueException;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.UniqueActivityList;

import java.util.Set;
import java.util.Stack;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyActivityManager newData);

    /** Returns the Activity Manager */
    ReadOnlyActivityManager getActivityManager();

    /** Deletes the given task */
    void deleteTask(ReadOnlyActivity target) throws UniqueActivityList.ActivityNotFoundException;
    
    //@@author A0139164A
    /** Completes the given Activity, given it's index. */
    void completeFloatingTask(int index);
    void completeTask(int index);
    void completeEvent(int index);
    
    /** Uncompletes the given Activity, given it's index. */
    void UncompleteFloatingTask(int index);
    void UncompleteTask(int index);
    void UncompleteEvent(int index);
 
    /** 
     * Edits the name of the given Activity, given it's index. 
     * @throws IllegalValueException 
     */
    void editFloatingTaskName(int index, String changes) throws IllegalValueException;
    void editTaskName(int index, String changes) throws IllegalValueException;
    void editEventName(int index, String changes) throws IllegalValueException;
    
    /**
     * Edits the note of the given Activity, given it's index. 
     * @throws IllegalValueException 
     */
    void editFloatingTaskNote(int index, String changes) throws IllegalValueException;
    void editTaskNote(int index, String changes) throws IllegalValueException;
    void editEventNote(int index, String changes) throws IllegalValueException;
    
    /**
     * Edits the Start Date & Time of the given Task/Event, given it's index. 
     * @throws IllegalValueException 
     */
    void editTaskDateTime(int index, String newDate, String newTime) throws IllegalValueException;
    void editEventEndDateTime(int index, String newDate, String newTime) throws IllegalValueException;
    void editEventStartDateTime(int index, String newDate, String newTime) throws IllegalValueException;
    
    //@@author A0139515A
    /**
     * Methods for undo 
     * 
     */
    
    /** add an activity manager state into undo stack */
    void addStateToUndoStack(ReadOnlyActivityManager activityManager);
    
    /** retrieve previous activity manager from undo stack */
    ReadOnlyActivityManager retrievePreviousStateFromUndoStack();
    
    /** check if there is any previous activity manager in undo stack */
    boolean checkStatesInUndoStack();
    
    /**
     * Methods for redo
     * 
     */
    

    /** add an activity manager state into redo stack */
    void addStateToRedoStack(ReadOnlyActivityManager activityManager);
    
    /** retrieve previous activity manager from redo stack */
    ReadOnlyActivityManager retrievePreviousStateFromRedoStack();
  
    /** check if there is any previous activity manager in redo stack */
    boolean checkStatesInRedoStack();

    //@@author A0146752B
    /** Adds the given task */
    void addTask(Activity task) throws UniqueActivityList.DuplicateTaskException;
    
    /** Deletes the given floating task */
    void deleteFloatingTask(ReadOnlyActivity target) throws UniqueActivityList.ActivityNotFoundException;

    /** Adds the given floating task */
    void addFloatingTask(Activity task) throws UniqueActivityList.DuplicateTaskException;
    
    /** Deletes the given event */
    void deleteEvent(ReadOnlyActivity target) throws UniqueActivityList.ActivityNotFoundException;

    /** Adds the given event */
    void addEvent(Activity task) throws UniqueActivityList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyActivity> getFilteredTaskList();
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyFloatingTask>} */
    UnmodifiableObservableList<ReadOnlyActivity> getFilteredFloatingTaskList();
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyEvent>} */
    UnmodifiableObservableList<ReadOnlyActivity> getFilteredEventList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredFloatingTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredEventList(Set<String> keywords);


}
