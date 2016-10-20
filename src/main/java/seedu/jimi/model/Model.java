package seedu.jimi.model;

import java.util.Set;

import seedu.jimi.commons.core.UnmodifiableObservableList;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskBook newData);

    /** Returns the TaskBook */
    ReadOnlyTaskBook getTaskBook();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(ReadOnlyTask floatingTask) throws UniqueTaskList.DuplicateTaskException;
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredFloatingTaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredCompletedTaskList();

    UnmodifiableObservableList<ReadOnlyTask> getFilteredIncompleteTaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredMondayTaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTuesdayTaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredWednesdayTaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredThursdayTaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredFridayTaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredSaturdayTaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredSundayTaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredAgendaTaskList();

    UnmodifiableObservableList<ReadOnlyTask> getFilteredAgendaEventList();


    /** Updates the filter of the filtered task list to show all task */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    void updateFilteredFloatingTaskList(Set<String> keywords);
    
    void updateFilteredCompletedTaskList(Set<String> keywords);

    void updateFilteredIncompleteTaskList(Set<String> keywords);

    void updateFilteredAgendaTaskList(Set<String> keywords);

    void updateFilteredAgendaEventList(Set<String> keywords);
    
    /* if auto update, remove this part
    void updateFilteredMondayTaskList(Set<String> keywords);

    void updateFilteredTuesdayTaskList(Set<String> keywords);

    void updateFilteredWednesdayTaskList(Set<String> keywords);

    void updateFilteredThursdayTaskList(Set<String> keywords);

    void updateFilteredFridayTaskList(Set<String> keywords);

    void updateFilteredSaturdayTaskList(Set<String> keywords);

    void updateFilteredSundayTaskList(Set<String> keywords);
    */
    
    /** Edits the floating task at the specified index */
    void editReadOnlyTask(int targetIdx, ReadOnlyTask floatingTask);

    /** Sets the task to be completed/incomplete */
    void completeTask(ReadOnlyTask taskToComplete, boolean isComplete);
}
