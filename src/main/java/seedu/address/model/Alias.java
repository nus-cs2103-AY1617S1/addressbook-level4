package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Alias {

    private final HashMap<String, String> alias;

    {
        alias = new HashMap<String, String>();
    }

    public Alias() {}

//    /**
//     * Tasks and Tags are copied into this task manager
//     */
//    public Alias (ReadOnlyTaskManager toBeCopied) {
//        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
//    }
//
//    /**
//     * Tasks and Tags are copied into this task manager
//     */
//    public TaskManager(UniqueTaskList tasks, UniqueTagList tags) {
//        resetData(tasks.getInternalList(), tags.getInternalList());
//    }
//
//    public static Alias getEmptyAlias() {
//        return new Alias();
//    }

//// list overwrite operations

//    public ObservableList<Task> getFilteredTasks() {
//        return alias.getInternalList();
//    }

//    public void setAlias(List<Task> tasks) {
//        this.tasks.getInternalList().setAll(tasks);
//    }


//    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
//        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
//        setTags(newTags);
//    }
//
//    public void resetData(ReadOnlyTaskManager newData) {
//        resetData(newData.getTaskList(), newData.getTagList());
//    }

//// task-level operations

    /**
     * Adds a task to the address book.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addAlias(String key, String value) throws UniqueTaskList.DuplicateTaskException {
        alias.put(key, value);
    }


    public String removeAlias(String key) throws UniqueTaskList.TaskNotFoundException {
        return alias.remove(key);
    }
    
    public String editAlias(String key, String value) throws UniqueTaskList.TaskNotFoundException {
        //syncTagsWithMasterList(p);
    	return alias.replace(key, value);
    }

//// util methods

    @Override
    public String toString() {
        return alias.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.tasks.equals(((TaskManager) other).tasks)
                && this.tags.equals(((TaskManager) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
