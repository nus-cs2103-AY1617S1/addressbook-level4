package seedu.oneline.model;

import javafx.collections.ObservableList;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.TagColor;
import seedu.oneline.model.tag.TagColorMap;
import seedu.oneline.model.tag.UniqueTagList;
import seedu.oneline.model.tag.UniqueTagList.DuplicateTagException;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.TaskName;
import seedu.oneline.model.task.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the task-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;
    private final TagColorMap tagColorMap;

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
        tagColorMap = new TagColorMap();
    }

    public TaskBook() {}

    /**
     * Tasks and Tags are copied into this task book
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList(), toBeCopied.getTagColorMap());
        updateTags();
    }

    /**
     * Tasks and Tags are copied into this task book
     */
    public TaskBook(UniqueTaskList persons, UniqueTagList tags, TagColorMap colorMap) {
        resetData(persons.getInternalList(), tags.getInternalList(), colorMap.getInternalMap());
        updateTags();
    }

    public static ReadOnlyTaskBook getEmptyTaskBook() {
        return new TaskBook();
    }

//// list overwrite operations

    public ObservableList<Tag> getTags() {
        return tags.getInternalList();
    }
    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
        updateTags();
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }
    
    public void setTagColors(Map<Tag, TagColor> tagColors) {
        this.tagColorMap.getInternalMap().clear();
        this.tagColorMap.getInternalMap().putAll(tagColors);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags, Map<Tag, TagColor> newTagColors) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
        setTagColors(newTagColors);
        updateTags();
    }

    public void resetData(ReadOnlyTaskBook newData) {
        resetData(newData.getTaskList(), newData.getTagList(), newData.getInternalTagColorMap());
        updateTags();
    }

//// person-level operations

    /**
     * Adds a task to the task book.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent person already exists.
     */
    public void addTask(Task t) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(t);
        tasks.add(t);
        updateTags();
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
//        if (!this.getUniqueTagList().contains(task.getTag())) {
//            try {
//                this.getUniqueTagList().add(task.getTag());
//            } catch (DuplicateTagException e) {
//                assert false;
//            }
//        }
    }
    
    public void updateTags() {
        Set<Tag> allTags = new HashSet<Tag>();
        for (Task t : tasks.getInternalList()) {
            allTags.add(t.getTag());
        }
        setTags(allTags);
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            updateTags();
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
//// tag-level operations

//    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
//        tags.add(t);
//    }
    
    public TagColor getTagColor(Tag t) {
       return tagColorMap.getTagColor(t);
    }

    public void setTagColor(Tag t, TagColor c) {
        tagColorMap.setTagColor(t, c);
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
    public Map<Tag, TagColor> getInternalTagColorMap() {
        return Collections.unmodifiableMap(this.tagColorMap.getInternalMap());
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
    public TagColorMap getTagColorMap() {
        return this.tagColorMap;
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
