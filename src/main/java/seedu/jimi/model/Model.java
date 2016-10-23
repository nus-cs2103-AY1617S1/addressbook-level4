package seedu.jimi.model;

import java.util.Set;

import seedu.jimi.commons.core.UnmodifiableObservableList;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList;
import seedu.jimi.model.task.UniqueTaskList.TaskNotFoundException;

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
    void addTask(ReadOnlyTask task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
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


    /** Updates the filter of the filtered task list to show the default listings */
    void updateAllFilteredListsShowDefault();

    /** Updates the filter of the filtered task list to filter by the given keywords */
    void updateFilteredAgendaTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered event list to filter by the given keywords */
    void updateFilteredAgendaEventList(Set<String> keywords);
    
    /** Sets the task to be completed/incomplete 
     * @throws TaskNotFoundException */
    void completeTask(ReadOnlyTask taskToComplete, boolean isComplete) throws TaskNotFoundException;
    
    /** Replaces {@code oldTask} with {@code newTask} */
    void replaceTask(ReadOnlyTask oldTask, ReadOnlyTask newTask);
}
