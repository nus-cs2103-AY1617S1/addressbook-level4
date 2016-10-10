package seedu.taskman.model;

import seedu.taskman.commons.core.UnmodifiableObservableList;
import seedu.taskman.model.event.Activity;
import seedu.taskman.model.event.Task;
import seedu.taskman.model.event.UniqueActivityList;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskMan newData);

    /** Returns the TaskMan */
    ReadOnlyTaskMan getTaskMan();

    /** Deletes the given activity. */
    void deleteActivity(Activity target) throws UniqueActivityList.ActivityNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueActivityList.DuplicateActivityException;

    void addActivity(Activity activity) throws  UniqueActivityList.DuplicateActivityException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<Activity>} */
    UnmodifiableObservableList<Activity> getFilteredActivityList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredActivityList(Set<String> keywords);

}
