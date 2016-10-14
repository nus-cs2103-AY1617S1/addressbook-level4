package seedu.malitio.model;

import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.model.task.ReadOnlyTask;
import seedu.malitio.model.task.Task;
import seedu.malitio.model.task.UniqueTaskList;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyMalitio newData);

    /** Returns Malitio */
    ReadOnlyMalitio getMalitio();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    /** Returns the filtered task list2 as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList2();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list2 to show all tasks */
    void updateFilteredList2ToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList2(Set<String> keywords);

}
