package seedu.task.model;

import javafx.collections.ObservableList;
import seedu.task.model.task.Task;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final UniqueTaskList tasks;
//    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
//        tags = new UniqueTagList();
    }

    public TaskBook() {}

    /**
     * Tasks are copied into this taskbook
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this(toBeCopied.getUniqueTaskList());
    }

    /**
     * Tasks are copied into this taskbook
     */
    public TaskBook(UniqueTaskList tasks) {
        resetData(tasks.getInternalList());
    }

    public static ReadOnlyTaskBook getEmptyTaskBook() {
        return new TaskBook();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyTaskBook newData) {
        resetData(newData.getTaskList());
    }

//// task-level operations

    /**
     * Adds a task to the task book.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        tasks.add(p);
    }

//    /**
//     * Ensures that every tag in this person:
//     *  - exists in the master list {@link #tags}
//     *  - points to a Tag object in the master list
//     */
//    private void syncTagsWithMasterList(Task person) {
//        final UniqueTagList personTags = person.getTags();
//        tags.mergeFrom(personTags);
//
//        // Create map with values = tag object references in the master list
//        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
//        for (Tag tag : tags) {
//            masterTagObjects.put(tag, tag);
//        }
//
//        // Rebuild the list of person tags using references from the master list
//        final Set<Tag> commonTagReferences = new HashSet<>();
//        for (Tag tag : personTags) {
//            commonTagReferences.add(masterTagObjects.get(tag));
//        }
//        person.setTags(new UniqueTagList(commonTagReferences));
//    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                && this.tasks.equals(((TaskBook) other).tasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks);
    }
}
