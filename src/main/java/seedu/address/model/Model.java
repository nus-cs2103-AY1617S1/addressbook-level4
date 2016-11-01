package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

import javafx.util.Pair;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();
    
    //@@author A0143756Y
    /** Returns TaskManagerStorageFilePath */
    String getTaskManagerStorageFilePath();
    //@@author 
    
    /** Saves the state of the model in case the user wishes to undo an action. */
    void saveState();
    
    /** Reverts back to previous task and tag list before the last command was executed. */
    void loadPreviousState();
    
    /** Redoes an action after an undo. */
    void loadNextState();

    /** Deletes the given tasks. */
    void deleteTasks(ArrayList<ReadOnlyTask> targets) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Updates the given task */
    void editTask(int index, Task task) throws UniqueTaskList.TaskNotFoundException;
    
    //@@author A0143756Y
    /** Validates arguments passed to SetStorageCommand() method.
     * 	Returns newStorageFileFilePath (Path) and oldStorageFileFilePath (Path) if arguments are valid.
     */
    Pair<Path, Path> validateSetStorage(String userSpecifiedStorageFolder, String userSpecifiedStorageFileName);
    
    /** Sets task manager data storage location */    
    void setStorage(File newStorageFileFilePath, File oldStorageFileFilePath) throws IOException;
    //@@author
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    //@@author A0142184L
    /** Returns the list showing only non-done tasks (not-done and overdue tasks) as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getNonDoneTaskList();

    /** Returns the today task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getTodayTaskList();

    /** Returns the tomorrow task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getTomorrowTaskList();

    /** Returns the in-7-days task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getIn7DaysTaskList();

    /** Returns the in-30-days task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getIn30DaysTaskList();

    /** Returns the someday task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getSomedayTaskList();
	
    //@@author A0139339W
    /** Returns the unfiltered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getUnfilteredTaskList();
    //@@author

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list according to a specified predicate*/
    void updateFilteredTaskList(Predicate<ReadOnlyTask> taskFilter);

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);


}
