package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.item.FloatingTask;
import seedu.address.model.item.ReadOnlyFloatingTask;
import seedu.address.model.item.UniqueFloatingTaskList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given floating task. */
    void deleteFloatingTask(ReadOnlyFloatingTask target) throws UniqueFloatingTaskList.FloatingTaskNotFoundException;

    /** Adds the given floating task */
    void addFloatingTask(FloatingTask floatingTask) throws UniqueFloatingTaskList.DuplicateFloatingTaskException;

    /** Returns the filtered floating task list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyFloatingTask> getFilteredFloatingTaskList();

    /** Updates the filter of the filtered floating task list to show all floating tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered floating task list to filter by the given keywords*/
    void updateFilteredFloatingTaskList(Set<String> keywords);

}
