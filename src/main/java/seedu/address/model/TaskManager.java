package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.item.Task;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.ReadOnlyTask;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.model.item.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList floatingTasks;

    {
        floatingTasks = new UniqueTaskList();
    }

    public TaskManager() {}

    /**
     * FloatingTasks and Tags are copied into this addressbook
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this(toBeCopied.getUniqueTaskList());
    }

    /**
     * Persons and Tags are copied into this addressbook
     */
    public TaskManager(UniqueTaskList floatingTasks) {
        resetData(floatingTasks.getInternalList());
    }

    public static ReadOnlyTaskManager getEmptyTaskManager() {
        return new TaskManager();
    }

//// list overwrite operations

    public ObservableList<Task> getFloatingTasks() {
        return floatingTasks.getInternalList();
    }

    public void setFloatingTasks(List<Task> floatingTasks) {
        this.floatingTasks.getInternalList().setAll(floatingTasks);
    }



    public void resetData(Collection<? extends ReadOnlyTask> newFloatingTasks) {
        setFloatingTasks(newFloatingTasks.stream().map(Task::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyTaskManager newData) {
        resetData(newData.getTaskList());
    }

//// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     */
    public void addFloatingTask(Task f) {
        floatingTasks.add(f);
    }

    public boolean removeFloatingTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (floatingTasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }


//// util methods

    @Override
    public String toString() {
        return floatingTasks.getInternalList().size() + " floating tasks";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(floatingTasks.getInternalList());
    }


    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.floatingTasks;
    }




    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.floatingTasks.equals(((TaskManager) other).floatingTasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(floatingTasks);
    }

}
