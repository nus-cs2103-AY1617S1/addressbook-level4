package seedu.emeraldo.model.task;

import seedu.emeraldo.model.tag.Tag;
import seedu.emeraldo.model.tag.UniqueTagList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.emeraldo.commons.exceptions.DuplicateDataException;
import seedu.emeraldo.commons.exceptions.IllegalValueException;
import seedu.emeraldo.commons.exceptions.TagExistException;
import seedu.emeraldo.commons.exceptions.TagListEmptyException;
import seedu.emeraldo.commons.exceptions.TagNotFoundException;
import seedu.emeraldo.commons.exceptions.TaskAlreadyCompletedException;
import seedu.emeraldo.commons.util.CollectionUtil;

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
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

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
    
    //@@author A0139196U
    public void edit(Task toEditObj, Description description, DateTime dateTime) throws IllegalValueException {
        assert description != null;
        assert dateTime != null;
        if(!description.toString().isEmpty()){
            toEditObj.setDescription(description);
        }
        if(!dateTime.value.isEmpty()){
            toEditObj.setDateTime(dateTime);
        }
        
        int mainListIndex = internalList.indexOf(toEditObj);
        internalList.set(mainListIndex, toEditObj);
    }
    
    //@@author A0142290N
    public void complete(Task toCompleteObj) throws IllegalValueException, TaskAlreadyCompletedException {
    	if (toCompleteObj.getDateTime().completedValueDate != null){
    		throw new TaskAlreadyCompletedException();
    	}
    	
    	else {
    		toCompleteObj.getDateTime().setCompletedDateTime();
    		int mainListIndex = internalList.indexOf(toCompleteObj);
    		internalList.set(mainListIndex, toCompleteObj);
    	}
    }
    
    //@@author A0139196U
    /**
     * Adds the new tag to the equivalent task from the list.
     *
     * @throws IllegalValueException if no such task could be found in the list.
     * @throws TagExistException if tag already exists in the list.
     */
    public void addTag(Task toEditTagTask, Tag tag) throws IllegalValueException, TagExistException {
        
        UniqueTagList editedTagList = toEditTagTask.getTags();

        if (editedTagList.contains(tag)) {
            throw new TagExistException();
        }
        editedTagList.mergeFrom(new UniqueTagList(tag));
        toEditTagTask.setTags(editedTagList);
        
        int mainListIndex = internalList.indexOf(toEditTagTask);
        internalList.set(mainListIndex, toEditTagTask);
    }
    
    /**
     * Deletes the given tag from the equivalent task from the list.
     * 
     * @throws TagNotFoundException if no such tag could be found in the task.
    */
    public void deleteTag(Task toEditTagTask, Tag tag) throws IllegalValueException, TagNotFoundException {
        
        UniqueTagList editedTagList = toEditTagTask.getTags();
        
        if (editedTagList.contains(tag)) {
            editedTagList.delete(tag);
        }
        else {
            throw new TagNotFoundException();
        }
        toEditTagTask.setTags(editedTagList);
        
        int mainListIndex = internalList.indexOf(toEditTagTask);
        internalList.set(mainListIndex, toEditTagTask);
    }
    
    /**
     * Clears all tags from the indicated task from the list.
    */
    public void clearTag(Task toEditTagTask) throws IllegalValueException, TagListEmptyException {
        if (toEditTagTask.getTags().getInternalList().isEmpty()){
            throw new TagListEmptyException();
        }
        else {
            toEditTagTask.getTags().getInternalList().clear();
        }
    }
    //@@author

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
