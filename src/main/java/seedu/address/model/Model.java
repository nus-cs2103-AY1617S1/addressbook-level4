package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyToDo newData);

    /** Returns the ToDo */
    ReadOnlyToDo getToDo();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Marks the given task. */
    void markTask(ReadOnlyTask taskToMark) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    //@@author A0126649W
    /** Saves to the new file path */
    void saveToDo(String filePath) throws IOException;
    
    /** Loads file from file path */
    void loadToDo(String filePath) throws IOException;
    //@@author
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    //@@author A0135767U
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by completion*/
    void updateFilteredListToShowCompleted(boolean done);

    /** Updates the filter of the filtered task list to filter by upcoming*/
    void updateFilteredListToShowUpcoming();

    /** Updates the filter of the filtered task list to filter by overdue*/
    void updateFilteredListToShowOverdue();

    /** Updates the specific task's fields with changes 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws TaskNotFoundException */
    ReadOnlyTask editTask(ReadOnlyTask task, HashMap<Field, Object> changesToBeMade) throws TaskNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException;

}
