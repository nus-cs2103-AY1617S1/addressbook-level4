package seedu.tasklist.model;

import java.util.List;
import java.util.Set;

import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskList newData);

    /** Returns the AddressBook */
    ReadOnlyTaskList getTaskList();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.PersonNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicatePersonException;
    
    /** Marks the given task as complete */
    void markTaskAsComplete(ReadOnlyTask target) throws UniqueTaskList.PersonNotFoundException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredPersonList();

    /** Updates the filter of the filtered task list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords */
    void updateFilteredTaskList(Set<String> keywords);

    Set<String> getKeywordsFromList(List<ReadOnlyTask> matchingTasks);

    /** Updates the filter of the filtered task list to only show incomplete */
	void updateFilteredListToShowIncomplete();

    UnmodifiableObservableList<Task> getModifiableTaskList();

    void updateFilteredList();
    
    /** Updates the filter of the filtered task list to only show complete */
    void updateFilteredListToShowComplete();
    
    /** Updates the filter of the filtered task list to only certain priority */
    public void updateFilteredListToShowPriority(String priority);

}
