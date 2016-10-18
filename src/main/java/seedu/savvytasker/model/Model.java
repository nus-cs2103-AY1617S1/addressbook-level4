package seedu.savvytasker.model;

import java.util.Set;

import seedu.savvytasker.commons.core.UnmodifiableObservableList;
import seedu.savvytasker.model.person.ReadOnlyTask;
import seedu.savvytasker.model.person.Task;
import seedu.savvytasker.model.person.TaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlySavvyTasker newData);

    /** Returns Savvy Tasker */
    ReadOnlySavvyTasker getSavvyTasker();

    /** Deletes the given Task. */
    void deleteTask(ReadOnlyTask target) throws TaskNotFoundException;

    /** Modifies the given Task. */
    void modifyTask(ReadOnlyTask target, Task replacement) throws TaskNotFoundException;

    /** Adds the given Task. */
    void addTask(Task task);

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all active tasks sorted by due date */
    void updateFilteredListToShowActiveSortedByDueDate();

    /** Updates the filter of the filtered task list to show all active tasks sorted by priority level */
    void updateFilteredListToShowActiveSortedByPriorityLevel();

    /** Updates the filter of the filtered task list to show all active tasks */
    void updateFilteredListToShowActive();

    /** Updates the filter of the filtered task list to show all archived tasks */
    void updateFilteredListToShowArchived();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

}
