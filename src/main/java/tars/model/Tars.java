package tars.model;

import javafx.collections.ObservableList;
import tars.model.task.Task;
import tars.model.task.DateTime;
import tars.model.task.DateTime.IllegalDateException;
import tars.model.task.Name;
import tars.model.task.Priority;
import tars.model.task.ReadOnlyTask;
import tars.model.task.Status;
import tars.model.task.UniqueTaskList;
import tars.model.task.UniqueTaskList.TaskNotFoundException;
import tars.model.task.rsv.RsvTask;
import tars.model.task.rsv.UniqueRsvTaskList;
import tars.model.task.rsv.UniqueRsvTaskList.RsvTaskNotFoundException;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.util.DateTimeUtil;
import tars.logic.parser.ArgumentTokenizer;
import tars.logic.parser.Prefix;
import tars.model.tag.ReadOnlyTag;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;
import tars.model.tag.UniqueTagList.DuplicateTagException;
import tars.model.tag.UniqueTagList.TagNotFoundException;

import java.time.DateTimeException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the tars level Duplicates are not allowed (by .equals
 * comparison)
 */
public class Tars implements ReadOnlyTars {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;
    private final UniqueRsvTaskList rsvTasks;

    private static final int DATETIME_INDEX_OF_ENDDATE = 1;
    private static final int DATETIME_INDEX_OF_STARTDATE = 0;

    private static final String EMPTY_STRING = "";
    private static final String NAME_PREFIX = "/n";
    private static final String DATETIME_PREFIX = "/dt";
    private static final String PRIORITY_PREFIX = "/p";
    private static final String ADDTAG_PREFIX = "/ta";
    private static final String REMOVETAG_PREFIX = "/tr";

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
        rsvTasks = new UniqueRsvTaskList();
    }

    public Tars() { }

    /**
     * Tasks and Tags are copied into this tars
     */
    public Tars(ReadOnlyTars toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList(), toBeCopied.getUniqueRsvTaskList());
    }

    /**
     * Tasks and Tags are copied into this tars
     */
    public Tars(UniqueTaskList tasks, UniqueTagList tags, UniqueRsvTaskList rsvTasks) {
        resetData(tasks.getInternalList(), rsvTasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTars getEmptyTars() {
        return new Tars();
    }

    //// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public ObservableList<RsvTask> getRsvTasks() {
        return rsvTasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setRsvTasks(List<RsvTask> rsvTasks) {
        this.rsvTasks.getInternalList().setAll(rsvTasks);
    }

    /**
     * Replaces task in tars internal list
     *
     * @@author A0121533W
     * @throws DuplicateTaskException if replacement task is the same as the task to replace
     */
    public void replaceTask(ReadOnlyTask toReplace, Task replacement) throws DuplicateTaskException {
        if (toReplace.isSameStateAs(replacement)) {
            throw new DuplicateTaskException();
        }
        ObservableList<Task> list = this.tasks.getInternalList();
        int toReplaceIndex = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSameStateAs(toReplace)) {
                toReplaceIndex = i;
                break;
            }
        }
        list.set(toReplaceIndex, replacement);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<RsvTask> newRsvTasks, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setRsvTasks(newRsvTasks.stream().collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTars newData) {
        resetData(newData.getTaskList(), newData.getRsvTaskList(), newData.getTagList());
    }

    //// task-level operations

    /**
     * Adds a task to tars. Also checks the new task's tags and updates {@link #tags} with any new
     * tags found, and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws DuplicateTaskException {
        syncTagsWithMasterList(p);
        tasks.add(p);
    }

    /**
     * Adds a reserved task to tars.
     *
     * @@author A0124333U
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent reserved task already exists.
     */
    public void addRsvTask(RsvTask rt) throws DuplicateTaskException {
        rsvTasks.add(rt);
    }

    /**
     * Edits a task in tars
     * 
     * @@author A0121533W
     * @throws UniqueTaskList.TaskNotFoundException if task to edit could not be found.
     * @throws DateTimeException if problem encountered while parsing dateTime.
     * @throws DuplicateTagException if the Tag to add is a duplicate of an existing Tag in the
     *         list.
     * @throws TagNotFoundException if no such tag could be found.
     * @throws IllegalValueException if argument(s) in argsToEdit is/are invalid.
     */
    public Task editTask(ReadOnlyTask toEdit, ArgumentTokenizer argsTokenizer) throws TaskNotFoundException,
    DateTimeException, DuplicateTagException, TagNotFoundException, IllegalValueException {
        if (!tasks.getInternalList().contains(toEdit)) {
            throw new TaskNotFoundException();
        }
        
        Task taskToEdit = new Task(toEdit);

        editName(argsTokenizer, taskToEdit);
        editPriority(argsTokenizer, taskToEdit);
        editDateTime(argsTokenizer, taskToEdit);
        addTags(argsTokenizer, taskToEdit);
        removeTags(argsTokenizer, taskToEdit);

        replaceTask(toEdit, taskToEdit);
        syncTagsWithMasterList(taskToEdit);

        return taskToEdit;
    }

    private void editName(ArgumentTokenizer argsTokenizer, Task taskToEdit) 
            throws IllegalValueException {
        Prefix namePrefix = new Prefix(NAME_PREFIX);
        if (!argsTokenizer.getValue(namePrefix).orElse(EMPTY_STRING).equals(EMPTY_STRING)) {
            Name editedName = new Name(argsTokenizer.getValue(namePrefix).get());
            taskToEdit.setName(editedName);
        }
    }
    
    private void editPriority(ArgumentTokenizer argsTokenizer, Task taskToEdit) 
            throws IllegalValueException {
        Prefix priorityPrefix = new Prefix(PRIORITY_PREFIX);
        if (!argsTokenizer.getValue(priorityPrefix).orElse(EMPTY_STRING).equals(EMPTY_STRING)) {
            Priority editedPriority = new Priority(argsTokenizer.getValue(priorityPrefix).get());
            taskToEdit.setPriority(editedPriority);
        }
    }

    private void editDateTime(ArgumentTokenizer argsTokenizer, Task taskToEdit) 
            throws IllegalDateException {
        Prefix dateTimePrefix = new Prefix(DATETIME_PREFIX);
        if (!argsTokenizer.getValue(dateTimePrefix).orElse(EMPTY_STRING).equals(EMPTY_STRING)) {
            String[] dateTimeArray =
                    DateTimeUtil.parseStringToDateTime(argsTokenizer.getValue(dateTimePrefix).get());
            DateTime editedDateTime = new DateTime(dateTimeArray[DATETIME_INDEX_OF_STARTDATE],
                    dateTimeArray[DATETIME_INDEX_OF_ENDDATE]);
            taskToEdit.setDateTime(editedDateTime);
        }
    }
       
    private void addTags(ArgumentTokenizer argsTokenizer, Task taskToEdit)
            throws IllegalValueException, DuplicateTagException, TagNotFoundException {
        Prefix addTagPrefix = new Prefix(ADDTAG_PREFIX);
        handleTagsEdit(argsTokenizer, taskToEdit, addTagPrefix);
    }

    private void removeTags(ArgumentTokenizer argsTokenizer, Task taskToEdit)
                    throws IllegalValueException, TagNotFoundException {
        Prefix removeTagPrefix = new Prefix(REMOVETAG_PREFIX);
        handleTagsEdit(argsTokenizer, taskToEdit, removeTagPrefix);
    }
    
    /**
     * Edits a task by adding or removing its tags based on given prefix
     * @throws TagNotFoundException 
     * 
     * @@author A0121533W
     */
    private void handleTagsEdit(ArgumentTokenizer argsTokenizer, Task taskToEdit, Prefix prefix) 
            throws IllegalValueException, DuplicateTagException, TagNotFoundException {
        Set<String> tagsToHandle =
                argsTokenizer.getMultipleValues(prefix).orElse(new HashSet<>());
        for (String t : tagsToHandle) {
            Tag toHandle = new Tag(t);
            UniqueTagList replacement = taskToEdit.getTags();
            if (prefix.value.equals(ADDTAG_PREFIX)){
                replacement.add(toHandle);
            } else if (prefix.value.equals(REMOVETAG_PREFIX)) {
                replacement.remove(toHandle);
            }
            taskToEdit.setTags(replacement);
        }
        
    }

    /**
     * Marks every task in respective lists as done or undone
     * 
     * @@author A0121533W
     * @throws DuplicateTaskException
     */
    public void mark(ArrayList<ReadOnlyTask> toMarkList, Status status) throws DuplicateTaskException {
        for (ReadOnlyTask t : toMarkList) {
            if (!t.getStatus().equals(status)) {
                // prevent marking tasks which are already marked
                Task toMark = new Task(t);
                toMark.setStatus(status);
                replaceTask(t, toMark);
            }
        }
    }

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

    public boolean removeRsvTask(RsvTask key) throws RsvTaskNotFoundException {
        if (rsvTasks.remove(key)) {
            return true;
        } else {
            throw new RsvTaskNotFoundException();
        }
    }

    /** 
     * Sorts internal list by priority from low to high
     * 
     * @@author A0140022H 
     */
    public void sortByPriority() {
        this.tasks.getInternalList().sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getPriority().compareTo(o2.getPriority());
            }
        });
    }

    /** 
     * Sorts internal list by priority from high to low
     * 
     * @@author A0140022H 
     */
    public void sortByPriorityDescending() {
        this.tasks.getInternalList().sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o2.getPriority().compareTo(o1.getPriority());
            }
        });
    }

    /** 
     * Sorts internal list by earliest end dateTime first
     * 
     * @@author A0140022H 
     */
    public void sortByDatetime() {
        this.tasks.getInternalList().sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });
    }

    /** 
     * Sorts internal list by latest end dateTime first
     * 
     * @@author A0140022H 
     */
    public void sortByDatetimeDescending() {
        this.tasks.getInternalList().sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o2.getDateTime().compareTo(o1.getDateTime());
            }
        });
    }

    //// tag-level operations

    //@@author A0139924W
    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //@@author A0139924W
    public void removeTag(Tag t) throws UniqueTagList.TagNotFoundException {
        tags.remove(t);
    }

    /**
     * Rename all task with the new tag
     * 
     * @@author A0139924W
     * @param toBeRenamed tag to be replaced with new the new tag
     * @param newTag new tag
     * @throws IllegalValueException if the given tag name string is invalid.
     * @throws TagNotFoundException if there is no matching tags.
     */
    public void renameTasksWithNewTag(ReadOnlyTag toBeRenamed, Tag newTag)
            throws IllegalValueException, TagNotFoundException {

        for (int i = 0; i < tasks.getInternalList().size(); i++) {
            Task toEdit = new Task(tasks.getInternalList().get(i));
            UniqueTagList tags = toEdit.getTags();
            if (tags.contains(new Tag(toBeRenamed))) {
                tags.update(toBeRenamed, newTag);
                toEdit.setTags(tags);
                tasks.getInternalList().set(i, toEdit);
            }
        }

    }

    /**
     * Remove the tag from all tasks
     * 
     * @@author A0139924W
     * @param toBeDeleted
     * @throws IllegalValueException if the given tag name string is invalid.
     * @throws TagNotFoundException if there is no matching tags.
     */
    public ArrayList<ReadOnlyTask> removeTagFromAllTasks(ReadOnlyTag toBeDeleted)
            throws IllegalValueException, TagNotFoundException, DuplicateTagException {
        ArrayList<ReadOnlyTask> editedTasks = new ArrayList<ReadOnlyTask>();

        for (int i = 0; i < tasks.getInternalList().size(); i++) {
            Task toEdit = new Task(tasks.getInternalList().get(i));
            UniqueTagList tags = toEdit.getTags();
            if (tags.contains(new Tag(toBeDeleted))) {
                tags.remove(new Tag(toBeDeleted));
                toEdit.setTags(tags);
                tasks.getInternalList().set(i, toEdit);
                editedTasks.add(toEdit);
            }
        }

        return editedTasks;
    }

    /**
     * Remove the tag from all tasks
     * 
     * @@author A0139924W
     * @param toBeDeleted
     * @throws IllegalValueException if the given tag name string is invalid.
     * @throws TagNotFoundException if there is no matching tags.
     */
    public void addTagToAllTasks(ReadOnlyTag toBeAdded, ArrayList<ReadOnlyTask> allTasks)
            throws IllegalValueException, TagNotFoundException, DuplicateTagException {

        for (int i = 0; i < allTasks.size(); i++) {
            for (int j = 0; j < tasks.getInternalList().size(); j++) {
                Task toEdit = new Task(tasks.getInternalList().get(j));
                if (toEdit.equals(allTasks.get(i))) {
                    UniqueTagList tags = toEdit.getTags();
                    tags.add(new Tag(toBeAdded));
                    toEdit.setTags(tags);
                    tasks.getInternalList().set(i, toEdit);
                }
            }
        }
    }

    //@@author

    //// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + rsvTasks.getInternalList().size() + " reserved tasks," + tags.getInternalList().size() + " tags";
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<RsvTask> getRsvTaskList() {
        return Collections.unmodifiableList(rsvTasks.getInternalList());
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
    public UniqueRsvTaskList getUniqueRsvTaskList() {
        return this.rsvTasks;
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
                        && this.rsvTasks.equals(((Tars) other).rsvTasks)
                        && this.tags.equals(((Tars) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(tasks, tags, rsvTasks);
    }

}
