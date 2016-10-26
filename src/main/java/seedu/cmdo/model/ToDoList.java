package seedu.cmdo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.cmdo.model.tag.Tag;
import seedu.cmdo.model.tag.UniqueTagList;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.model.task.Task;
import seedu.cmdo.model.task.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the todo list level
 * Duplicates are not allowed (by .equals comparison)
 */
public class ToDoList implements ReadOnlyToDoList {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public ToDoList() {}

    /**
     * Tasks and Tags are copied into this ToDoList
     */
    public ToDoList(ReadOnlyToDoList toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this ToDoList
     */
    public ToDoList(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }
    
    //@@author A0139661Y
    public ToDoList(ArrayList<ReadOnlyTask> tasks, UniqueTagList tags) {
    	resetData(FXCollections.observableList(tasks), tags.getInternalList());
    }

    public static ReadOnlyToDoList getEmptyToDoList() {
        return new ToDoList();
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

    public void resetData(ReadOnlyToDoList newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }

//// Task-level operations

    /**
     * Adds a Task to the address book.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task t) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(t);
        tasks.add(t);
    }
    
    /**
     * Edits a Task in the address book.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the tast to oint to those in {@link @tags}.
     * 
     * @author A0139661Y
     */
    public void editTask(ReadOnlyTask toEdit, Task toEditWith) throws UniqueTaskList.TaskNotFoundException {
    	// Add tags from replacing task
    	syncTagsWithMasterList(toEditWith);
    	tasks.edit(toEdit, toEditWith);
    	
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
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
                || (other instanceof ToDoList // instanceof handles nulls
                && this.tasks.equals(((ToDoList) other).tasks)
                && this.tags.equals(((ToDoList) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
