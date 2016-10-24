package seedu.address.model.task;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

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
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    
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
    
    public boolean markOverdue(ReadOnlyTask key) {
    	assert key != null;
    	int overdueIndex = internalList.indexOf(key);
    	Task overduedTask = new Task(internalList.get(overdueIndex));
    	if (overduedTask.isOverdue(overduedTask.getDate(), overduedTask.getEnd())) {
    		overduedTask.setOverdue(1);
    		internalList.set(overdueIndex, overduedTask);
    		return true;
    	}
    	else
    		return false;
    }

    public boolean edit(ReadOnlyTask key, String args) throws IllegalValueException {
        // TODO Auto-generated method stub
        String keyword = args.substring(0, args.indexOf(' '));
        args = args.substring(args.indexOf(' ') + 1);
        
        int editIndex = internalList.indexOf(key);
        //System.out.println(key + " " + args);
        Task toEdit = new Task(internalList.get(editIndex));
        //System.out.println(keyword);
        //System.out.println(args);
        //System.out.println(keyword.equals(EditCommand.DESCRIPTION_WORD));
        if (keyword.equals(EditCommand.DESCRIPTION_WORD)) {
            //internalList.get(editIndex).setName(new Name(args));
            //Task toEdit = new Task(internalList.get(editIndex));
            toEdit.setName(new Name(args));
            internalList.set(editIndex, toEdit);
            //System.out.println("dummy2");
            return true;
        } else if (keyword.equals(EditCommand.DATE_WORD)) {
            //internalList.get(editIndex).setDate(new Date(args));
            //Task toEdit = new Task(internalList.get(editIndex));
            toEdit.setDate(new Date(args));
            internalList.set(editIndex, toEdit);
            return true;
        } else if (keyword.equals(EditCommand.START_WORD)) {
            //internalList.get(editIndex).setStart(new Start(args));
            //Task toEdit = new Task(internalList.get(editIndex));
            toEdit.setStart(new Start(args));
            internalList.set(editIndex, toEdit);
            return true;
        } else if (keyword.equals(EditCommand.END_WORD)) {
            //internalList.get(editIndex).setEnd(new End(args));
            //Task toEdit = new Task(internalList.get(editIndex));
            toEdit.setEnd(new End(args));
            internalList.set(editIndex, toEdit);
            return true;
        } else if (keyword.equals(EditCommand.TAG_WORD)) {
            //internalList.get(editIndex).setTags(new UniqueTagList(new Tag(args)));
            //Task toEdit = new Task(internalList.get(editIndex));
            
            if (args.contains(">")){
                String[] beforeAndAfter = args.replaceAll(" ","").split(">");              
                toEdit.setTags(beforeAndAfter[0], beforeAndAfter[beforeAndAfter.length-1]);
            }
            else{
                toEdit.setTags(new UniqueTagList(new Tag(args)));;
            }

            internalList.set(editIndex, toEdit);
            return true;
            
        } else if (keyword.equals(EditCommand.ADD_WORD)) {            
            String[] newTag = args.replaceAll(" ", "").replaceFirst("#", "").split("#");          
            final Set<Tag> tagSet = new HashSet<>();
            for (int i = 0; i < newTag.length; i++) {
                tagSet.add(new Tag(newTag[i]));
            }
            UniqueTagList addTagList = new UniqueTagList(tagSet);            
            toEdit.addTags(addTagList);          
            internalList.set(editIndex, toEdit);
            return true;
        }

        else {
            return false;
        }
    }
}
