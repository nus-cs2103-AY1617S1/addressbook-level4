package seedu.unburden.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.unburden.commons.exceptions.*;
import seedu.unburden.commons.util.CollectionUtil;
import seedu.unburden.logic.History;

import java.util.*;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
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
     * Signals that an operation targeting a specified person in the list would fail because
     * there is no such matching person in the list.
     */
    public static class TaskNotFoundException extends Exception {}

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PersonList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }
    

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }
    
    public boolean edit(ReadOnlyTask key, String args) throws TaskNotFoundException, IllegalValueException {
        String[] tokens = args.split(" ");
        
        String[] newArgs = getNewArgs(tokens);      
       
        int taskIndex = internalList.indexOf(key);
        
        Task newTask = internalList.get(taskIndex);
        for (int i = 0; i < newArgs.length; i++) {
        	if (newArgs[i] == "")
        		continue;
        	else {
	            switch (newArgs[i].substring(0, 2)) {
	            	case ("i/") : newTask.setTaskDescription(new TaskDescription(newArgs[i].substring(2, newArgs[i].length())));
	            				  internalList.set(taskIndex, newTask);
	            				  break;
	            	
	                case ("d/") : newTask.setDate(new Date(newArgs[i].substring(2, newArgs[i].length())));
	                              internalList.set(taskIndex, newTask);
	                              break;
	                              
	                case ("s/") : newTask.setStartTime(new Time(newArgs[i].substring(2, newArgs[i].length())));
	                              internalList.set(taskIndex, newTask);
	                              break;
	                              
	                case ("e/") : newTask.setEndTime(new Time(newArgs[i].substring(2, newArgs[i].length())));
	                              internalList.set(taskIndex, newTask);
	                              break;
	                              
	                default     : newTask.setName(new Name(newArgs[i]));
	                			  internalList.set(taskIndex, newTask);
	                			  break;
	            }
        	}
        }
        return true;
    }
    
    private String[] getNewArgs(String[] tokens) {
    	 String[] newArgs = new String[5];
         for (int i=0;i<5;i++)
         	newArgs[i] = "";
         
         int loopIndex = 0;
         int targetIndex = 0;
         while (loopIndex < tokens.length) {
        	 System.out.println(targetIndex);
        	 System.out.println(loopIndex);
        	 if (tokens[loopIndex].charAt(1) == '/') {
        		 switch (tokens[loopIndex].charAt(0)) {
        		 	case ('i') : targetIndex = 1;
        		 			     break;
        		 	case ('d') : targetIndex = 2;
		 			   		     break;
        		 	case ('s') : targetIndex = 3;
			   		   			 break;
        		 	case ('e') : targetIndex = 4;
			   		   			 break;
			   		default    : break; 
        		 }
        	 }
        	 
        	 if (newArgs[targetIndex] == "") {
        		 newArgs[targetIndex] = tokens[loopIndex] + " ";
        	 	 System.out.println(newArgs[targetIndex]);
        	 }
        	 else {
        		 newArgs[targetIndex] = newArgs[targetIndex] + (tokens[loopIndex]) + " ";
        		 System.out.println(newArgs[targetIndex]);
        	 }
    		 loopIndex = loopIndex + 1;
         }
         
         for (int i=0;i<newArgs.length;i++) {
        	 newArgs[i] = newArgs[i].trim();
         }
         
    	return newArgs;
    }
    
    public void undo() {
    	String toDo = History.toUndo.pop();
    	
    	
    	
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

	public void done(ReadOnlyTask key, boolean isDone) {
		assert key != null;
		int taskIndex = internalList.indexOf(key);
        Task newTask = internalList.get(taskIndex);
        newTask.setDone(isDone);
        internalList.set(taskIndex, newTask);
	}
}
