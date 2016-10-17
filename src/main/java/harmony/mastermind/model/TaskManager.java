package harmony.mastermind.model;

import javafx.collections.ObservableList;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import harmony.mastermind.commons.exceptions.FolderDoesNotExistException;
import harmony.mastermind.logic.commands.FindCommand;
import harmony.mastermind.logic.parser.ParserSearch;
import harmony.mastermind.memory.Memory;
import harmony.mastermind.model.tag.Tag;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.ArchiveTaskList;
import harmony.mastermind.model.task.ReadOnlyTask;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.UniqueTaskList;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Wraps all data at the task-manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;
    private final ArchiveTaskList archivedTasks;
    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        archivedTasks = new ArchiveTaskList();
        tags = new UniqueTagList();
    }

    public TaskManager() {}

    /**
     * Tasks and Tags are copied into this TaskManager
     */
    //@@author A0124797R
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList(), toBeCopied.getUniqueArchiveList());
    }

    /**
     * Tasks and Tags are copied into this TaskManager
     */
    //@@author A0124797R
    public TaskManager(UniqueTaskList tasks, UniqueTagList tags, ArchiveTaskList archiveTasks) {
        resetData(tasks.getInternalList(), tags.getInternalList(), archiveTasks.getInternalList());
    }

    public static ReadOnlyTaskManager getEmptyTaskManager() {
        return new TaskManager();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }
    
    //@@author A0124797R
    public ObservableList<Task> getArchives() {
        return archivedTasks.getInternalList();
    }
    
    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }
    //@@author A0124797R
    public void setArchiveTasks(Collection<Task> archiveTasks) {
        this.archivedTasks.getInternalList().setAll(archiveTasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    //@@author A0124797R
    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags,
            Collection<? extends ReadOnlyTask> newArchiveTasks) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
        setArchiveTasks(newArchiveTasks.stream().map(Task::new).map(Task::mark).collect(Collectors.toList()));
    }

    //@@author A0124797R
    public void resetData(ReadOnlyTaskManager newData) {
        resetData(newData.getTaskList(), newData.getTagList(), newData.getArchiveList());
    }

    //@@author A0139194X
    public void checkSaveLocation(String newFilePath) throws FolderDoesNotExistException {
        Path filePath = Paths.get(newFilePath);
        if (!Files.exists(filePath)) {
            throw new FolderDoesNotExistException(newFilePath + " does not exist");
        }
    }

//// task-level operations

    /**
     * Adds a task to the task manager.
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
    
    /**
     * marks task as completed by
     * removing the task from tasks and adds into archivedtasks
     * throws TaskNotFoundException
     */
    //@@author A0124797R
    public boolean markTask(Task key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            archivedTasks.add(key.mark());
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    /**
     * marks task as not completed by
     * removing the task from archivedTasks and adds into tasks
     * throws TaskNotFoundException, DuplicateTaskException 
     */
    //@@author A0124797R
    public boolean unmarkTask(Task key) throws ArchiveTaskList.TaskNotFoundException,
    UniqueTaskList.DuplicateTaskException {
        if (archivedTasks.remove(key)) {
            tasks.add(key.unmark());
            return true;
        } else {
            throw new ArchiveTaskList.TaskNotFoundException();
        }
    }


//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags,"
                + archivedTasks.getInternalList().size();
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }
    
    //@@author A0124797R
    @Override
    public List<ReadOnlyTask> getArchiveList() {
        return Collections.unmodifiableList(archivedTasks.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    //@@author A0124797R
    @Override
    public ArchiveTaskList getUniqueArchiveList() {
        return this.archivedTasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }

    //@@author A0124797R
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.tasks.equals(((TaskManager) other).tasks)
                && this.tags.equals(((TaskManager) other).tags)
                && this.archivedTasks.equals(((TaskManager) other).archivedTasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }

    public void searchTask(String keyword, Memory memory) {
        ParserSearch.run(keyword, memory);
        
    }

}
