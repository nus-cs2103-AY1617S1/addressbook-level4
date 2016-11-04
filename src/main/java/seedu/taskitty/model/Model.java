package seedu.taskitty.model;

import seedu.taskitty.commons.core.UnmodifiableObservableList;
import seedu.taskitty.commons.exceptions.NoPreviousValidCommandException;
import seedu.taskitty.commons.exceptions.NoRecentUndoCommandException;
import seedu.taskitty.model.task.ReadOnlyTask;
import seedu.taskitty.model.task.Task;
import seedu.taskitty.model.task.UniqueTaskList;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTasks(List<ReadOnlyTask> target);
    
    /** Edits the given task. */
    void editTask(ReadOnlyTask target, Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Marks the given task as done. 
     * @throws DuplicateMarkAsDoneException */
    void markTasksAsDone(List<ReadOnlyTask> target);
    
    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    //@@author A0139052L
    /** Undoes the previous command if there is any 
     * @throws NoPreviousValidCommandException */
    String undo() throws NoPreviousValidCommandException;
    
    /** Redoes the previous undo command if there is any 
     * @throws NoRecentUndoCommandException */
    String redo() throws NoRecentUndoCommandException;
    
    /**
     * stores the info from an add/edit command that is needed for undoing/redoing functions
     */
    public void storeCommandInfo(String commandWord, String commandText, ReadOnlyTask... tasks);
    
    /**
     * stores the info from a delete/done command that is needed for undoing/redoing functions
     */
    public void storeCommandInfo(String commandWord, String commandText, List<ReadOnlyTask> deletedTasks);
    
    /**
     * stores the info from a clear command that is needed for undoing/redoing functions
     */
    public void storeCommandInfo(String commandWord);

    
    //@@author
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getTaskList();
    
    //@@author A0139930B
    /** Returns the filtered todo task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTodoList();
    
    /** Returns the filtered deadline task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineList();
    
    /** Returns the filtered event task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList();

    //@@author

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    //@@author A0130853L
    /** Updates the filter of the filtered task list to filter out tasks that are done*/
	void updateFilteredDoneList();

	/** Updates the filter of the filtered task list according to date specified*/
	void updateFilteredDateTaskList(LocalDate date);
	
	/** Updates the filter such that events from today onwards and all deadlines and tasks are shown*/
	void updateToDefaultList();
	
	/** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

}
