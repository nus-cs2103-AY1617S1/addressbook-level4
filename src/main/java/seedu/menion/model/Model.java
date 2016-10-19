package seedu.menion.model;

import seedu.menion.commons.core.UnmodifiableObservableList;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.UniqueActivityList;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyActivityManager newData);

    /** Returns the Activity Manager */
    ReadOnlyActivityManager getActivityManager();

    /** Deletes the given task */
    void deleteTask(ReadOnlyActivity target) throws UniqueActivityList.TaskNotFoundException;
    
    /** Completes the given Activity, given it's index. */
    void completeFloatingTask(int index);
    void completeTask(int index);
    void completeEvent(int index);
    
    /** Uncompletes the given Activity, given it's index. */
    void UncompleteFloatingTask(int index);
    void UncompleteTask(int index);
    void UncompleteEvent(int index);
    
    /** 
     * @author Marx  Low A0139164A
     * Edits the name of the given Activity, given it's index. 
     */
    void editFloatingTaskName(int index, String changes);
    void editTaskName(int index, String changes);
    void editEventName(int index, String changes);
    

    /**
     * @author Marx Low A0139164A
     * Edits the note of the given Activity, given it's index. 
     */
    void editFloatingTaskNote(int index, String changes);
    void editTaskNote(int index, String changes);
    void editEditNote(int index, String changes);
    
    /** Adds the given task */
    void addTask(Activity task) throws UniqueActivityList.DuplicateTaskException;
    
    /** Deletes the given floating task */
    void deleteFloatingTask(ReadOnlyActivity target) throws UniqueActivityList.TaskNotFoundException;

    /** Adds the given floating task */
    void addFloatingTask(Activity task) throws UniqueActivityList.DuplicateTaskException;
    
    /** Deletes the given event */
    void deleteEvent(ReadOnlyActivity target) throws UniqueActivityList.TaskNotFoundException;

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
