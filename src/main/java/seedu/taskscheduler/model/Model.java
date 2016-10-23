package seedu.taskscheduler.model;

import seedu.taskscheduler.commons.core.UnmodifiableObservableList;
import seedu.taskscheduler.model.tag.UniqueTagList.DuplicateTagException;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.Task;
import seedu.taskscheduler.model.task.UniqueTaskList;
import seedu.taskscheduler.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.taskscheduler.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskScheduler newData);

    /** Returns the TaskScheduler */
    ReadOnlyTaskScheduler getTaskScheduler();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask... targets) throws UniqueTaskList.TaskNotFoundException;

    /** Marks the given task. */
    void markTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException, DuplicateTagException;
    
    /** Unmarks the given task. */
    void unMarkTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Adds the given task */
    void addTask(Task... tasks) throws UniqueTaskList.DuplicateTaskException;

    /** Replace the given oldTask with newTask */
	void replaceTask(Task oldTask, Task newTask) throws TaskNotFoundException;
	
	/** Insert the newTask into oldTask's position */
	void insertTask(int index, Task newTask) throws TaskNotFoundException;

    /** Edits the given task. 
     * @throws DuplicateTaskException */
    void editTask(ReadOnlyTask target, Task task) throws TaskNotFoundException, DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

}
