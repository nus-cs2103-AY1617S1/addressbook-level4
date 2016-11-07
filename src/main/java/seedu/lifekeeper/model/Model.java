package seedu.lifekeeper.model;

<<<<<<< V0.5_additional_tests:src/main/java/seedu/lifekeeper/model/Model.java
import seedu.lifekeeper.commons.core.UnmodifiableObservableList;
import seedu.lifekeeper.model.activity.Activity;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.model.activity.UniqueActivityList;
import seedu.lifekeeper.model.activity.UniqueActivityList.DuplicateTaskException;
import seedu.lifekeeper.model.activity.UniqueActivityList.TaskNotFoundException;
import seedu.lifekeeper.model.activity.task.Task;
=======
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.UniqueActivityList;
import seedu.address.model.activity.UniqueActivityList.DuplicateTaskException;
import seedu.address.model.activity.UniqueActivityList.TaskNotFoundException;
import seedu.address.model.activity.task.ReadOnlyTask;
import seedu.address.model.activity.task.Task;
>>>>>>> origin/Branch_for_Merging:src/main/java/seedu/address/model/Model.java

import java.util.Set;

import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyLifeKeeper newData);

    /** Returns the Lifekeeper */
    ReadOnlyLifeKeeper getLifekeeper();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyActivity target) throws UniqueActivityList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Activity activity) throws UniqueActivityList.DuplicateTaskException;
    
    /** Undo delete of the given task at the specific index
     * @throws DuplicateTaskException */
	void undoDelete(int index, Activity taskToAdd) throws DuplicateTaskException;
    
	/** Edits the given task
     * @return The edited task
     */
    Activity editTask(Activity oldTask, Activity newParams) throws TaskNotFoundException, DuplicateTaskException;

    /** Undo edit 
     * @return The original task before edit
     */
    Activity undoEditTask(Activity oldTask, Activity newParams) throws TaskNotFoundException, DuplicateTaskException;
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyActivity> getFilteredTaskList();

    /** Returns the filtered overdue task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyActivity> getFilteredOverdueTaskList();
    
    /** Returns the filtered overdue task list as an {@code UnmodifiableObservableList<ReadOnlyActivity>} */
    UnmodifiableObservableList<ReadOnlyActivity> getFilteredUpcomingList();
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<Task>} */
    UnmodifiableObservableList<Activity> getFilteredTaskListForEditing();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Marks task as completed **/
	void markTask(Activity unmarkedTask, boolean isComplete) throws TaskNotFoundException;
	//@@author A0131813R
    void updateFilteredEventListToShowAll();

    void updateFilteredActivityListToShowAll();

    void updateFilteredTaskListToShowAll();

    void updateFilteredByTagListToShowAll(String tag);

    void updateFilteredDoneListToShowAll();

<<<<<<< V0.5_additional_tests:src/main/java/seedu/lifekeeper/model/Model.java
    void updateAllListToShowAll();
=======
	

>>>>>>> origin/Branch_for_Merging:src/main/java/seedu/address/model/Model.java

	UnmodifiableObservableList<ReadOnlyActivity> getFilteredOverdueTaskList();

    
}
