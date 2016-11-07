package seedu.lifekeeper.model;

import seedu.lifekeeper.commons.core.UnmodifiableObservableList;
import seedu.lifekeeper.model.activity.Activity;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;
import seedu.lifekeeper.model.activity.UniqueActivityList;
import seedu.lifekeeper.model.activity.UniqueActivityList.DuplicateTaskException;
import seedu.lifekeeper.model.activity.UniqueActivityList.TaskNotFoundException;
import java.util.Set;

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
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<Task>} */
    UnmodifiableObservableList<Activity> getFilteredTaskListForEditing();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Marks task as completed **/
	void markTask(Activity unmarkedTask, boolean isComplete) throws TaskNotFoundException;
	//@@author A0131813R
    /** Updates the filter of the filtered event list*/
    void updateFilteredEventListToShowAll();
    
    /** Updates the filter of the filtered activity list*/
    void updateFilteredActivityListToShowAll();

    /** Updates the filter of the filtered task list*/
    void updateFilteredTaskListToShowAll();

    /** Updates the filter of the filtered list filtered by the tag name requested*/
    void updateFilteredByTagListToShowAll(String tag);

    /** Updates the filter of the filtered list including events thats have passed, tasks and activities that are done*/
    void updateFilteredDoneListToShowAll();

    /** Updates the filter of the filtered list showing all existing entries*/
    void updateAllListToShowAll();

	UnmodifiableObservableList<ReadOnlyActivity> getFilteredOverdueTaskList();

    
}
