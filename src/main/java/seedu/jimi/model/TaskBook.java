package seedu.jimi.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.jimi.model.event.Event;
import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.DeadlineTask;
import seedu.jimi.model.task.FloatingTask;
import seedu.jimi.model.task.ReadOnlyTask;
import seedu.jimi.model.task.UniqueTaskList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final UniqueTaskList<ReadOnlyTask> tasks;
    private final UniqueTaskList<ReadOnlyTask> deadlineTasks;
    private final UniqueTaskList<ReadOnlyTask> events;
    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        deadlineTasks = new UniqueTaskList();
        events = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskBook() {}

    /**
     * Tasks and Tags are copied into this addressbook
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueDeadlineTaskList(), toBeCopied.getUniqueEventList(),
                toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this addressbook
     */
    public TaskBook(UniqueTaskList tasks, UniqueTaskList deadlineTasks, UniqueTaskList events, UniqueTagList tags) {
        resetData(tasks.getInternalList(), deadlineTasks.getInternalList(), events.getInternalList(),
                tags.getInternalList());
    }

    public static ReadOnlyTaskBook getEmptyTaskBook() {
        return new TaskBook();
    }

//// list overwrite operations

    public ObservableList<ReadOnlyTask> getTasks() {
        return tasks.getInternalList();
    }
    
    public ObservableList<ReadOnlyTask> getDeadlineTasks() {
        return deadlineTasks.getInternalList();
    }
    
    public ObservableList<ReadOnlyTask> getEvents() {
        return events.getInternalList();
    }

    public void setReadOnlyTasks(List<ReadOnlyTask> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }
    
    public void setDeadlineTasks(List<ReadOnlyTask> deadlineTasks) {
        this.deadlineTasks.getInternalList().setAll(deadlineTasks);
    }
    
    public void setEvents(List<ReadOnlyTask> events) {
        this.events.getInternalList().setAll(events);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<? extends ReadOnlyTask> newDeadlineTasks,
            Collection<? extends ReadOnlyTask> newEvents, Collection<Tag> newTags) {
        ArrayList<ReadOnlyTask> newTaskList = new ArrayList<ReadOnlyTask>();
        ArrayList<ReadOnlyTask> newDeadlineTaskList = new ArrayList<ReadOnlyTask>();
        ArrayList<ReadOnlyTask> newEventList = new ArrayList<ReadOnlyTask>();

        for (ReadOnlyTask t : newTasks) {
            if (t instanceof DeadlineTask) {
                newDeadlineTaskList.add(new DeadlineTask((DeadlineTask) t));
            } else if (t instanceof Event) {
                newEventList.add(new Event((Event) t));
            } else {
                newTaskList.add(new FloatingTask((FloatingTask) t));
            }
        }
        
        setReadOnlyTasks(newTaskList);
        setDeadlineTasks(newDeadlineTaskList);
        setEvents(newEventList);
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskBook newData) {
        resetData(newData.getTaskList(), newData.getDeadlineTaskList(), newData.getEventList(), newData.getTagList());
    }

//// task-level operations

    /**
     * Adds a task to the address book.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(ReadOnlyTask p) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList((ReadOnlyTask) p);
        tasks.add(p);
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(ReadOnlyTask task) {
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
    
    public void editTask(int targetIndex, ReadOnlyTask newTask) {
        tasks.edit(targetIndex, newTask);
    }

    public void completeTask(ReadOnlyTask taskToComplete, boolean isComplete) {
        tasks.complete(taskToComplete, isComplete);
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
    public List<ReadOnlyTask> getDeadlineTaskList() {
        return Collections.unmodifiableList(deadlineTasks.getInternalList());
    }

    @Override
    public List<ReadOnlyTask> getEventList() {
        return Collections.unmodifiableList(events.getInternalList());
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
    public UniqueTaskList getUniqueDeadlineTaskList() {
        return this.deadlineTasks;
    }

    @Override
    public UniqueTaskList getUniqueEventList() {
        return this.events;
    }
    
    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                && this.tasks.equals(((TaskBook) other).tasks)
                && this.tags.equals(((TaskBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }


}
