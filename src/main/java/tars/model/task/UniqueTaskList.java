package tars.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tars.commons.exceptions.DuplicateTaskException;
import tars.commons.exceptions.IllegalValueException;
import tars.commons.util.CollectionUtil;
import tars.model.tag.ReadOnlyTag;
import tars.model.tag.Tag;
import tars.model.tag.UniqueTagList;
import tars.model.tag.UniqueTagList.DuplicateTagException;
import tars.model.tag.UniqueTagList.TagNotFoundException;

import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }
    
    /**
     * Rename all Task who had the old tag with the new tag
     * 
     * @param oldTag tag to be replaced with new tag name
     * @param tagToUpdate new tag name
     * @throws IllegalValueException if the given tag name string is invalid.
     * @throws TagNotFoundException if there is no matching tags.
     */
    public void renameTag(ReadOnlyTag oldTag, Tag newTag)
            throws IllegalValueException, TagNotFoundException, DuplicateTagException {

        for (int i = 0; i < internalList.size(); i++) {
            Task toEdit = new Task(internalList.get(i));
            UniqueTagList tags = toEdit.getTags();
            if (tags.contains(new Tag(oldTag))) {
                tags.remove(new Tag(oldTag));
                tags.add(newTag);
                toEdit.setTags(tags);
                internalList.set(i, toEdit);
            }
        }
    }

    public ObservableList<Task> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
