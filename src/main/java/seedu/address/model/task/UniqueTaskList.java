package seedu.address.model.task;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
  //@@author A0139430L JingRui
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
        FXCollections.sort(internalList);
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
    //@@author A0138993L
    public boolean markOverdue(ReadOnlyTask key) {
    	assert key != null;
    	int overdueIndex = internalList.indexOf(key);
    	Task overduedTask = internalList.get(overdueIndex);
    	if (overduedTask.isOverdue(overduedTask.getDate(), overduedTask.getEnd())) {
    		overduedTask.setOverdue(1);
    		return true;
    	}
    	else {
    		overduedTask.setOverdue(0);;
    		return false;
    	}
    }

  //@@author A0139430L JingRui
	public boolean edit(ReadOnlyTask key, String args) throws IllegalValueException {
        // TODO Auto-generated method stub
        String keyword = args.substring(0, args.indexOf(' '));
        args = args.substring(args.indexOf(' ') + 1);
        
        int editIndex = internalList.indexOf(key);
        //System.out.println(key + " " + args);
        Task toEdit = new Task(internalList.get(editIndex));
        if (keyword.equals(EditCommand.DESCRIPTION_WORD)) {
            toEdit.setName(new Name(args));
            internalList.set(editIndex, toEdit);
            return true;
        } else if (keyword.equals(EditCommand.DATE_WORD)) {
            if(args.compareTo("no date") == 0 & toEdit.getTaskCategory()!=3){ // change to Todo
                toEdit.setDate(new Date("no date"));
                toEdit.setStart(new Start("no start"));
                toEdit.setEnd(new End("no end"));
                toEdit.setTaskCategory(3);
            }           
            else if(toEdit.getTaskCategory()==3){//todo to deadline
                toEdit.setDate(new Date(args));
                toEdit.setEnd(new End("2359"));
                toEdit.setTaskCategory(2);  
            }
            else
                toEdit.setDate(new Date(args));          
            internalList.set(editIndex, toEdit);
            return true;
        } else if (keyword.equals(EditCommand.START_WORD)) {
            if(args.compareTo("no start") == 0 & toEdit.getTaskCategory()==1){ //event to deadline
                toEdit.setStart(new Start(args));
                toEdit.setTaskCategory(2);
            }
            else if(toEdit.getTaskCategory()==2){   //deadline to event
                toEdit.setStart(new Start(args));
                toEdit.setTaskCategory(1);
            }
            else if(toEdit.getTaskCategory()==3){  //todo to Event              
                toEdit.setDate(new Date(this.getCurrentDate()));
                toEdit.setStart(new Start(args));
                toEdit.setEnd(new End("2359"));
                toEdit.setTaskCategory(1);
            }
            else
                toEdit.setStart(new Start(args));
            internalList.set(editIndex, toEdit);
            return true;
        } else if (keyword.equals(EditCommand.END_WORD)) {
            if(args.compareTo("no end") == 0 & toEdit.getTaskCategory()!=3){ //not todo default end time 2359
                toEdit.setEnd(new End("2359"));
            }
            else if(toEdit.getTaskCategory()==3 & args.compareTo("no end") != 0){  //todo to Deadline
                toEdit.setDate(new Date(this.getCurrentDate()));
                toEdit.setStart(new Start("no start"));
                toEdit.setEnd(new End(args));
                toEdit.setTaskCategory(2);
            }
            else
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
    
	//@@author A0135722L Zhiyuan
    public boolean completed(ReadOnlyTask target) {
        int completeIndex = internalList.indexOf(target);
        Task toComplete = new Task(internalList.get(completeIndex));
        toComplete.setCompleted(true);
        internalList.set(completeIndex, toComplete);
        return true;
    }

    public String getCurrentDate(){
        LocalDate now = LocalDate.now();
        String date = now.toString();
        String[] date_cat = date.split("-");
        String localDate = date_cat[2];
        localDate = localDate.concat(date_cat[1]);
        localDate = localDate.concat(date_cat[0].substring(2));
        return localDate;
    }
}
