package seedu.address.model;

import java.util.Set;
import java.util.Stack;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskBook newData);

    /** Returns the AddressBook */
    ReadOnlyTaskBook getAddressBook();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList();
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineList();
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTodoList();
    
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredEventList(Set<String> keywords);
    void updateFilteredDeadlineList(Set<String> keywords);
    void updateFilteredTodoList(Set<String> keywords);

    /** Edits the given task 
     * @throws IllegalValueException 
     * @throws TaskNotFoundException */
    void editTask(ReadOnlyTask target, String args, char category) throws TaskNotFoundException, IllegalValueException;
    
    /**
     * Returns the undo and redo stacks
     * @return
     */
    Stack<SaveState> getUndoStack();
    
    Stack<SaveState> getRedoStack();
    
    /**
     * Add to undo stack
     */
    void addToUndoStack();
    
    /**
     * Returns the config object 
     */
    Config getConfig();
    
    /**
     * Sets the config object
     */
    void setConfig(Config config);
}
