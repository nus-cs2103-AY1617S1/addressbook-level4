package seedu.task.model;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.ui.DisplayDirectoryChooserRequestEvent.SelectedFilePathEmptyException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskAlreadyCompletedException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;

import java.io.IOException;
import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskBook newData);

    /** Returns the TaskBook */
    ReadOnlyTaskBook getTaskBook();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task taskToAdd) throws DuplicateTaskException;
    
    /** Edits the given task */
    void editTask(ReadOnlyTask taskToEdit, Task taskEditedTo) throws TaskNotFoundException, DuplicateTaskException;
    
    /** Completes the given task */
    void completeTask(ReadOnlyTask taskToComplete) throws TaskNotFoundException, TaskAlreadyCompletedException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Indicates storage file path changed */
    void indicateStorageFilePathChanged(String newFilePath);
    
    String changeStorageFilePath(String newFilePath) throws SelectedFilePathEmptyException, IOException;

}
