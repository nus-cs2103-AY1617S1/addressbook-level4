package seedu.flexitrack.model.task;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.flexitrack.commons.exceptions.DuplicateDataException;
import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.commons.util.CollectionUtil;

/**
 * A list of tasks that enforces uniqueness between its elements and does not
 * allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    /**
     * Signals that an operation would have violated the 'no duplicates'
     * property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified person in the list would
     * fail because there is no such matching person in the list.
     */
    public static class TaskNotFoundException extends Exception {
    }

    public static class IllegalEditException extends Exception {
    }

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PersonList.
     */
    public UniqueTaskList() {
    }

    /**
     * Returns true if the list contains an equivalent person as the given
     * argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException
     *             if the person to add is a duplicate of an existing person in
     *             the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException
     *             if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                        && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

  //@@author A0138455Y
    public void mark(ReadOnlyTask targetTask, boolean isDone) throws IllegalValueException {
        assert targetTask != null;      
        int targetIndex = internalList.indexOf(targetTask);
        Task markTask = internalList.get(targetIndex);
        markTask.markTask(isDone);
        internalList.set(targetIndex, markTask);

    }
  //@@author
    
  //@@author A0127855W
    /**
     * Sorts the observable list according to the ReadOnlyTask comparator
     */
    public void sort(){
    	Collections.sort(internalList);;
    }
  
    /**
     * edit
     * -----------------------------------------------------
     * finds the target task to be edited by the specified index and edits the task using the given argument array
     * @param targetIndex
     * @param args: Array of edit parameters
     * @return The new duration if the item being edited is an event, or "" if it is a floating task or task
     * @throws IllegalEditException
     * @throws TaskNotFoundException
     * @throws IllegalValueException
     */
    public Task edit(int targetIndex, String[] args)
            throws IllegalEditException, TaskNotFoundException, IllegalValueException {
        assert targetIndex >= 0;
        Task editTask;

        try {
            editTask = internalList.get(targetIndex);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new TaskNotFoundException();
        }

        checkForIllegalFloatingTaskEdit(args, editTask);
        editTaskParameters(editTask, args);
        internalList.set(targetIndex, editTask);
        return editTask; 

    }

    /**
     * checkForIllegalFloatingTaskEdit
     * -------------------------------------------------
     * checks that the appropriate edit parameters to a floating task; user should not add both task and event parameters to a floating task,
     * he must also make the floating task a complete event (with start and end time) if he were to edit it into an event. 
     * @param args
     * @param editTask
     * @throws IllegalEditException
     */
    private void checkForIllegalFloatingTaskEdit(String[] args, Task editTask) throws IllegalEditException {
        assert args != null;
        assert editTask != null;
        if (!editTask.getIsTask() && !editTask.getIsEvent()) {
            if ((args[1] != null) && (args[2] != null || args[3] != null)) {
                throw new IllegalEditException();
            }
            if ((args[2] != null && args[3] == null) || (args[3] != null && args[2] == null)) {
                throw new IllegalEditException();
            }
        }
    }

    /**
     * editTaskParameters
     * ---------------------------------------------------
     * edits the actual task with given parameters, checking that the wrong parameters are not given to the wrong type of task;
     * i.e. user should not add start date to a task, nor should he add a due date to an event
     * @param editTask
     * @param args
     * @throws IllegalValueException
     * @throws IllegalEditException
     */
    private void editTaskParameters(Task editTask, String[] args) throws IllegalValueException, IllegalEditException {
        assert args != null;
        assert editTask != null;
        for (int i = 0; i < args.length; i++) {
            if (!(args[i] == null)) {
                switch (i) {
                case 0:
                    editTask.setName(args[i]);
                    break;
                case 1:
                    if (!editTask.getIsEvent()) {
                        editTask.setDueDate(args[i]);
                        editTask.setIsTask(true);
                    } else {
                        throw new IllegalEditException();
                    }
                    break;
                case 2:
                    if (!editTask.getIsTask()) {
                        editTask.setStartTime(args[i]);
                        editTask.setIsEvent(true);
                    } else {
                        throw new IllegalEditException();
                    }
                    break;
                case 3:
                    if (!editTask.getIsTask()) {
                        editTask.setEndTime(args[i]);
                        editTask.setIsEvent(true);
                    } else {
                        throw new IllegalEditException();
                    }
                    break;
                }
            }
        }
    }

}
