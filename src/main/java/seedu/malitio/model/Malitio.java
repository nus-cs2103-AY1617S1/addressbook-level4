package seedu.malitio.model;

import javafx.collections.ObservableList;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.model.task.ReadOnlySchedule;
import seedu.malitio.model.task.ReadOnlyTask;
import seedu.malitio.model.task.Schedule;
import seedu.malitio.model.task.Task;
import seedu.malitio.model.task.UniqueScheduleList;
import seedu.malitio.model.task.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the application level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Malitio implements ReadOnlyMalitio {

    private final UniqueTaskList tasks;
    private final UniqueScheduleList schedules;
    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        schedules = new UniqueScheduleList();
        tags = new UniqueTagList();
    }

    public Malitio() {}

    /**
     * Tasks, Schedules and Tags are copied into this Malitio
     */
    public Malitio(ReadOnlyMalitio toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueScheduleList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this Malitio
     */
    public Malitio(UniqueTaskList tasks, UniqueScheduleList schedules, UniqueTagList tags) {
        resetData(tasks.getInternalList(), schedules.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyMalitio getEmptymalitio() {
        return new Malitio();
    }

//// list overwrite operations

    public ObservableList<Task> getFloatingTasks() {
        return tasks.getInternalList();
    }
    
    public ObservableList<Schedule> getSchedules() {
        return schedules.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }
    
    public void setSchedules(List<Schedule> tasks) {
        this.schedules.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<? extends ReadOnlySchedule> newSchedules, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(FloatingTask::new).collect(Collectors.toList()));
        setSchedules(newSchedules.stream().map(Schedule::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyMalitio newData) {
        resetData(newData.getTaskList(), newData.getScheduleList(), newData.getTagList());
    }

//// task-level operations

    /**
     * Adds a task to Malitio.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addFloatingTask(FloatingTask p) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(p);
        tasks.add(p);
    }
    
    /**
     * Adds a task to Malitio.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addSchedule(Schedule p) throws UniqueScheduleList.DuplicateScheduleException {
        syncTagsWithMasterList(p);
        schedules.add(p);
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(FloatingTask task) {
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
    
    private void syncTagsWithMasterList(Schedule schedule) {
        final UniqueTagList taskTags = schedule.getTags();
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
        schedule.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public boolean removeTask2(ReadOnlySchedule key) throws UniqueScheduleList.ScheduleNotFoundException {
        if (schedules.remove(key)) {
            return true;
        } else {
            throw new UniqueScheduleList.ScheduleNotFoundException();
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
    
    public List<ReadOnlySchedule> getScheduleList() {
        return Collections.unmodifiableList(schedules.getInternalList());
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
    public UniqueScheduleList getUniqueScheduleList() {
        return this.schedules;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Malitio // instanceof handles nulls
                && this.tasks.equals(((Malitio) other).tasks)
                && this.tags.equals(((Malitio) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }

}
