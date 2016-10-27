package seedu.taskscheduler.model;

import seedu.taskscheduler.commons.core.UnmodifiableObservableList;
import seedu.taskscheduler.commons.exceptions.IllegalValueException;
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

    //@@author A0148145E
    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask... targets) throws UniqueTaskList.TaskNotFoundException;

    //@@author A0148145E
    /** Marks the given task. 
     * @throws IllegalValueException */
    void markTask(Task target) throws UniqueTaskList.TaskNotFoundException, IllegalValueException;

    //@@author A0148145E
    /** Unmarks the given task. */
    void unMarkTask(Task target) throws UniqueTaskList.TaskNotFoundException, IllegalValueException;

    //@@author A0148145E
    /** Adds the given task */
    void addTask(Task... tasks) throws UniqueTaskList.DuplicateTaskException;
    
    //@@author A0140007B
	/** Insert the newTask into the given position */
	void insertTask(int index, Task newTask) throws TaskNotFoundException;

    //@@author A0148145E
    /** Replaces the given task. 
     * @throws DuplicateTaskException */
    void replaceTask(Task oldTask, Task newTask) throws TaskNotFoundException, DuplicateTaskException;
    //@@author
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

}
