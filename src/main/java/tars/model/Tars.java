package tars.model;

import javafx.collections.ObservableList;
import tars.model.task.Task;
import tars.model.task.DateTime;
import tars.model.task.Name;
import tars.model.task.Priority;
import tars.model.task.ReadOnlyTask;
import tars.model.task.UniqueTaskList;
import tars.model.task.UniqueTaskList.TaskNotFoundException;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.prefixes.Prefixes;
import tars.commons.util.DateTimeUtil;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;
import tars.model.tag.UniqueTagList.DuplicateTagException;
import tars.model.tag.UniqueTagList.TagNotFoundException;

import java.time.DateTimeException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the tars level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Tars implements ReadOnlyTars {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;
    
    private static final int DATETIME_INDEX_OF_ENDDATE = 1;
    private static final int DATETIME_INDEX_OF_STARTDATE = 0;

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public Tars() {}

    /**
     * Tasks and Tags are copied into this tars
     */
    public Tars(ReadOnlyTars toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this tars
     */
    public Tars(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTars getEmptyTars() {
        return new Tars();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }
    /**
     * Replaces task in tars internal list
     * @param toReplace
     * @param replacement
     */
            
    public void replaceTask(ReadOnlyTask toReplace, Task replacement) {
        ObservableList<Task> list = this.tasks.getInternalList();
        int toReplaceIndex = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSameStateAs(toReplace)){
                toReplaceIndex = i;
                break;
            }
        }
        list.set(toReplaceIndex, replacement);
}

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTars newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }

//// task-level operations

    /**
     * Adds a task to tars.
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
     * Edits a task in tars
     * @throws UniqueTaskList.TaskNotFoundException if task to edit could not be found.
     * @throws DateTimeException DateTimeExcpetion if problem encountered while calculating dateTime.
     * @throws DuplicateTagException if the Tag to add is a duplicate of an existing Tag in the list.
     * @throws TagNotFoundException if no such tag could be found.
     * @throws IllegalValueException if argument(s) in argsToEdit is/are invalid.
     */
    public Task editTask(ReadOnlyTask toEdit, String[] argsToEdit) throws TaskNotFoundException, DateTimeException, 
    DuplicateTagException, TagNotFoundException, IllegalValueException {
        if (!tasks.getInternalList().contains(toEdit)) {
            throw new TaskNotFoundException();
        }
        
        Task taskToEdit = new Task(toEdit);
        for (int i = 1; i < argsToEdit.length; i++) {
            String inputData = argsToEdit[i];
            int separatorIndex = inputData.indexOf(" ");
            String dataPrefix = inputData.substring(0, separatorIndex);
            String data = inputData.substring(separatorIndex+1);
                                   
            switch (dataPrefix) {
            case Prefixes.NAME:
                Name editedName = new Name(data);
                taskToEdit.setName(editedName);
                break;
            case Prefixes.PRIORITY:
                Priority editedPriority = new Priority(data);
                taskToEdit.setPriority(editedPriority);
                break;
            case Prefixes.DATETIME:
                String[] dateTimeArray = DateTimeUtil.getDateTimeFromArgs(data);
                DateTime editedDateTime = new DateTime(
                        dateTimeArray[DATETIME_INDEX_OF_STARTDATE],
                        dateTimeArray[DATETIME_INDEX_OF_ENDDATE]);
                taskToEdit.setDateTime(editedDateTime);
                break;
            case Prefixes.ADDTAG:
                Tag toAdd = new Tag(data);
                UniqueTagList replacement = taskToEdit.getTags();
                replacement.add(toAdd);
                taskToEdit.setTags(replacement);
                break;
            case Prefixes.REMOVETAG:
                Tag toRemove = new Tag(data);
                UniqueTagList modified = taskToEdit.getTags();
                modified.remove(toRemove);
                taskToEdit.setTags(modified);
                break;
            }
        }
        replaceTask(toEdit, taskToEdit);
        syncTagsWithMasterList(taskToEdit);
        return taskToEdit;
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
    
    public void removeTag(Tag t) throws UniqueTagList.TagNotFoundException {
        tags.remove(t);
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
                || (other instanceof Tars // instanceof handles nulls
                && this.tasks.equals(((Tars) other).tasks)
                && this.tags.equals(((Tars) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
    
}
