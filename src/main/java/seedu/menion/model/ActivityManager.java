package seedu.menion.model;

import javafx.collections.ObservableList;
import seedu.menion.model.activity.ReadOnlyActivity;
import seedu.menion.model.activity.Activity;
import seedu.menion.model.activity.UniqueActivityList;
import seedu.menion.model.tag.Tag;
import seedu.menion.model.tag.UniqueTagList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class ActivityManager implements ReadOnlyActivityManager {

    private final UniqueActivityList tasks;
    private final UniqueTagList tags;
    private final UniqueActivityList floatingTasks;
    private final UniqueActivityList events;

    {
        tasks = new UniqueActivityList();
        tags = new UniqueTagList();
        floatingTasks = new UniqueActivityList();
        events = new UniqueActivityList();
    }

    public ActivityManager() {}

    /**
     * Tasks and Tags are copied into this activity manager
     */
    public ActivityManager(ReadOnlyActivityManager toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList(),
                 toBeCopied.getUniqueFloatingTaskList(),
                 toBeCopied.getUniqueEventList());
    }

    /**
     * Tasks and Tags are copied into this task manager
     */
    public ActivityManager(UniqueActivityList tasks, UniqueTagList tags, 
                            UniqueActivityList floatingTasks,
                            UniqueActivityList events) {
        resetData(tasks.getInternalList(), tags.getInternalList(),
                     floatingTasks.getInternalList(),
                     events.getInternalList());
    }

    public static ReadOnlyActivityManager getEmptyActivityManager() {
        return new ActivityManager();
    }

//// list overwrite operations

    public ObservableList<Activity> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Activity> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }
    
    
    public ObservableList<Activity> getFloatingTasks() {
        return floatingTasks.getInternalList();
    }

    public void setFloatingTasks(List<Activity> floatingTasks) {
        this.floatingTasks.getInternalList().setAll(floatingTasks);
    }
    
    public ObservableList<Activity> getEvents() {
        return events.getInternalList();
    }

    public void setEvents(List<Activity> events) {
        this.events.getInternalList().setAll(events);
    }
    
    
    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyActivity> newTasks, Collection<Tag> newTags,
                            Collection<? extends ReadOnlyActivity> newFloatingTasks,
                            Collection<? extends ReadOnlyActivity> newEvents) {
        setTasks(newTasks.stream().map(Activity::new).collect(Collectors.toList()));
        setTags(newTags);
        setFloatingTasks(newFloatingTasks.stream().map(Activity::new).collect(Collectors.toList()));
        setEvents(newEvents.stream().map(Activity::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyActivityManager newData) {
        resetData(newData.getTaskList(), newData.getTagList(),
                    newData.getFloatingTaskList(),
                    newData.getEventList());
    }

//// task-level operations

    /**
     * Adds a task to the activity manager.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueActivityList.DuplicateTaskException if an equivalent tasks already exists.
     */
    public void addTask(Activity t) throws UniqueActivityList.DuplicateTaskException {
        //syncTagsWithMasterList(t);
        tasks.add(t);
    }
    
    /**
     * Adds a floating task to the activity manager.
     * Also checks the new floating task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the floating task to point to those in {@link #tags}.
     *
     * @throws UniqueActivityList.DuplicateTaskException if an equivalent tasks already exists.
     */
    
    public void addFloatingTask(Activity t) throws UniqueActivityList.DuplicateTaskException {
        //syncTagsWithMasterList(t);
        floatingTasks.add(t);
    }
    
    
    /**
     * Adds an event to the activity manager.
     * Also checks the new event's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the event to point to those in {@link #tags}.
     *
     * @throws UniqueActivityList.DuplicateTaskException if an equivalent tasks already exists.
     */
    
    public void addEvent(Activity t) throws UniqueActivityList.DuplicateTaskException {
        //syncTagsWithMasterList(t);
        events.add(t);
    }
    

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Activity task) {
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

    public boolean removeTask(ReadOnlyActivity key) throws UniqueActivityList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueActivityList.TaskNotFoundException();
        }
    }
    
    
    public boolean removeFloatingTask(ReadOnlyActivity key) throws UniqueActivityList.TaskNotFoundException {
        if (floatingTasks.remove(key)) {
            return true;
        } else {
            throw new UniqueActivityList.TaskNotFoundException();
        }
    }
    
    
    public boolean removeEvent(ReadOnlyActivity key) throws UniqueActivityList.TaskNotFoundException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new UniqueActivityList.TaskNotFoundException();
        }
    }
    

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, "
                + "" + tags.getInternalList().size() +  " tags"
                + floatingTasks.getInternalList().size() + " floating tasks, "
                + events.getInternalList().size() + " events, ";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyActivity> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }
    
    
    @Override
    public List<ReadOnlyActivity> getFloatingTaskList() {
        return Collections.unmodifiableList(floatingTasks.getInternalList());
    }
    
    @Override
    public List<ReadOnlyActivity> getEventList() {
        return Collections.unmodifiableList(events.getInternalList());
    }
    
    
    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueActivityList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }
    
    
    @Override
    public UniqueActivityList getUniqueFloatingTaskList() {
        return this.floatingTasks;
    }
    
    @Override
    public UniqueActivityList getUniqueEventList() {
        return this.events;
    }
    

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityManager // instanceof handles nulls
                && this.tasks.equals(((ActivityManager) other).tasks)
                && this.tags.equals(((ActivityManager) other).tags));
                //&& this.floatingTasks.equals(((ActivityManager) other).floatingTasks)
                //&& this.events.equals(((ActivityManager) other).events);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
        //return Objects.hash(tasks, tags, floatingTasks, events);
    }
}
