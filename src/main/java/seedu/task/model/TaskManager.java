package seedu.task.model;

import javafx.collections.ObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task-manager level Duplicates are not allowed (by
 * .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskManager() {
    }

    /**
     * Tasks and Tags are copied into this taskmanager
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this taskmanager
     */
    public TaskManager(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskManager getEmptyTaskManager() {
        return new TaskManager();
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
        setTasks(newTasks.stream().map(t -> {
            try {
                return new Task(t);
            } catch (IllegalValueException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            
        }).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskManager newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }

    //// task-level operations

    /**
     * Adds a task to the task list. Also checks the new task's tags and updates
     * {@link #tags} with any new tags found, and updates the Tag objects in the
     * task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException
     *             if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(p);
        tasks.add(p);
    }

    /**
     * Updates a specific task to the task list. Also checks the new task's tags
     * and updates {@link #tags} with any new tags found, and updates the Tag
     * objects in the task to point to those in {@link #tags}.
     * 
     * @throw UniqueTaskList.DuplicateTaskException if the same task already
     *        exists in the list.
     */
    public void updateTask(ReadOnlyTask originalTask, Task updateTask) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(updateTask);
        tasks.update(originalTask, updateTask);
    }
    
    //@@author A0153467Y
    /**
     * Pins a specfic task to the task list as important. 
     * 
     * @param originalTask the orginial task on the list
     * @param toPin Task which is pinned
     */
    public void pinTask(ReadOnlyTask originalTask, Task toPin) {
        tasks.pin(originalTask, toPin);
    }
    
    //@@author A0153467Y
    /**
     * Unpins a specfic pinned task to the task list. 
     * 
     * @param originalTask the orginial task on the list
     * @param toPin Task which is unpinnned
     */
    public void unpinTask(ReadOnlyTask originalTask, Task toUnpin) {
        tasks.unpin(originalTask, toUnpin);
    }
    
    /**
     * Marks a specific task as completed to the task list.
     * 
     * @param originalTask refers to the task that selected from the list
     * @param completeTask refers to a task same as original task except being marked as complete
     */
    
    public void completeTask(ReadOnlyTask originalTask, Task completeTask) {
    	tasks.complete(originalTask,completeTask); 
    }
    
    //@@author A0153467Y
    /**
     * Unmark a specific completed task as not completed to the task list.
     * 
     * @param originalTask refers to task which is marked as complete
     * @param uncompleteTask refers to task which is now marked as not complete
     */
    public void uncompleteTask(ReadOnlyTask originalTask, Task uncompleteTask) {
        tasks.uncomplete(originalTask,uncompleteTask); 
    }
    //@@author
    /**
     * Ensures that every tag in this task: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
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
    
    /**
     * Rollback the previous change made to TaskList
     * @return true if the operation is successful, else false
     */
    public boolean rollback() {
        tasks.rollback();
        return true;
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() + " tags";
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
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(tasks, tags);
    }
}
