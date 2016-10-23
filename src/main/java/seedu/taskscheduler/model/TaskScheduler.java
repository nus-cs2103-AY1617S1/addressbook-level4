package seedu.taskscheduler.model;

import javafx.collections.ObservableList;
import seedu.taskscheduler.model.tag.Tag;
import seedu.taskscheduler.model.tag.UniqueTagList;
import seedu.taskscheduler.model.tag.UniqueTagList.DuplicateTagException;
import seedu.taskscheduler.model.task.ReadOnlyTask;
import seedu.taskscheduler.model.task.Task;
import seedu.taskscheduler.model.task.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the Task Scheduler level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskScheduler implements ReadOnlyTaskScheduler {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskScheduler() {}

    /**
     * Tasks and Tags are copied into this task scheduler
     */
    public TaskScheduler(ReadOnlyTaskScheduler toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this task scheduler
     */
    public TaskScheduler(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskScheduler getEmptyTaskScheduler() {
        return new TaskScheduler();
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

    public void resetData(ReadOnlyTaskScheduler newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }

//// task-level operations

    /**
     * Adds a task to the task scheduler
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(p);
        tasks.add(p);
    }
    
    /**
     * Edits a task in the task scheduler
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void editTask(ReadOnlyTask oldTask, Task newTask) 
            throws UniqueTaskList.DuplicateTaskException, UniqueTaskList.TaskNotFoundException {
        tasks.edit(oldTask, newTask);        
        syncTagsWithMasterList(newTask);
    }
    
    /**
     * Marks a task in the task scheduler as completed
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     * @throws DuplicateTagException 
     */
    public void markTask(ReadOnlyTask task) 
            throws UniqueTaskList.TaskNotFoundException, DuplicateTagException {
        syncTagsWithMasterList((Task)task);
        tasks.mark(task);
    }
    
    /**
     * Unmarks a task in the task scheduler as completed
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     * @throws DuplicateTagException 
     */
    public void unMarkTask(ReadOnlyTask task) 
            throws UniqueTaskList.TaskNotFoundException, NullPointerException {
        syncTagsWithMasterList((Task)task);
        tasks.unMark(task);
    }
    
    /**
     * Replace a task in the task scheduler with another task
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void replaceTask(Task oldTask, Task newTask) 
            throws UniqueTaskList.TaskNotFoundException {
        syncTagsWithMasterList(newTask);
        tasks.replace(oldTask, newTask);
    }
    
    /**
     * Insert a task into another task's position in the task scheduler
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void insertTask(int index, Task newTask) 
            throws UniqueTaskList.TaskNotFoundException {
        syncTagsWithMasterList(newTask);
        tasks.insert(index, newTask);
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
                || (other instanceof TaskScheduler // instanceof handles nulls
                && this.tasks.equals(((TaskScheduler) other).tasks)
                && this.tags.equals(((TaskScheduler) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
