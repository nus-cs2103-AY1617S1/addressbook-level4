package teamfour.tasc.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import teamfour.tasc.model.history.HistoryItem;
import teamfour.tasc.model.tag.Tag;
import teamfour.tasc.model.tag.UniqueTagList;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Task;
import teamfour.tasc.model.task.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task-list level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskList implements ReadOnlyTaskList, HistoryItem<TaskList> {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskList() {}

    /**
     * Tasks and Tags are copied into this tasklist
     */
    public TaskList(ReadOnlyTaskList toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this tasklist
     */
    public TaskList(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskList getEmptyTaskList() {
        return new TaskList();
    }
    
    @Override
    public TaskList createStateAsDeepCopy() {
        return new TaskList(this);
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskList newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }

//// task-level operations

    /**
     * Adds a task to the task list.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the tasks to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(task);
        tasks.add(task);
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList personTags = task.getTags();
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of person tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : personTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        task.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

    /**
     * Update the old task with new task details.
     * 
     * @param oldTask that will be updated
     * @param newTask details
     * @throws UniqueTaskList.TaskNotFoundException if the old task cannot be found in the list
     */
    public void updateTask(ReadOnlyTask oldTask, Task newTask) throws UniqueTaskList.TaskNotFoundException {
        tasks.updateTask(oldTask, newTask);
    }
    
    //@@author A0148096W
    /**
     * Precondition: Argument is not null.
     * Sort this task list by comparator.
     * Does affect the positions of tasks in the list internally.
     * 
     * @param comparator Comparator used to compare ReadOnlyTask
     */
    public void sortUsingComparator(Comparator<ReadOnlyTask> comparator) {
        assert comparator != null;
        FXCollections.sort(tasks.getInternalList(), comparator);
    }
    //@@author

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
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
                || (other instanceof TaskList // instanceof handles nulls
                && this.tasks.equals(((TaskList) other).tasks)
                && this.tags.equals(((TaskList) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
