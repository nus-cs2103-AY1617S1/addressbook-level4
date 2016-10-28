package seedu.flexitrack.model;

import java.util.Set;

import seedu.flexitrack.commons.core.UnmodifiableObservableList;
import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.model.task.UniqueTaskList;
import seedu.flexitrack.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.flexitrack.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyFlexiTrack newData);

    /** Returns the FLexiTrack */
    ReadOnlyFlexiTrack getFlexiTrack();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws DuplicateTaskException;

    /**
     * Returns the filtered task list as an
     * {@code UnmodifiableObservableList<ReadOnlyTask>}
     */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    //@@author A0127686R
    /** Updates the filter of the filtered task list to show future tasks */
    void updateFilteredListToFitUserInput( String args );
        
    //@@author 
    /**
     * Updates the filter of the filtered task list to filter by the given
     * keywords
     */
    void updateFilteredTaskList(Set<String> keywords);

    /**
     * Marks the given task as done
     * 
     * @throws TaskNotFoundException
     */
    Task markTask(ReadOnlyTask taskToMark) throws IllegalValueException;

    /**
     * Unmarks the given task as done
     * 
     * @throws TaskNotFoundException
     */
    Task unmarkTask(ReadOnlyTask taskToMark) throws IllegalValueException;

    //@@author A0127855W
    /**
     * Edits the given task
     * 
     * @throws TaskNotFoundException
     */
    Task editTask(ReadOnlyTask taskToEdit, String[] args)
            throws UniqueTaskList.IllegalEditException, IllegalValueException;

    //@@author
    /**
     * 
     * @param Task toAdd
     * @return true if new event want to place at a period that reserve for other event
     */
    boolean checkBlock(Task toAdd) throws DuplicateTaskException;

    void indicateFlexiTrackerChanged();

}
