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
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDay1TaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDay2TaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDay3TaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDay4TaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDay5TaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDay6TaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDay7TaskList();
    
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
    
    /** Edits the floating task at the specified index */
    void editReadOnlyTask(int targetIdx, ReadOnlyTask task);

    /** Sets the task to be completed/incomplete */
    void completeTask(ReadOnlyTask taskToComplete, boolean isComplete);
}
