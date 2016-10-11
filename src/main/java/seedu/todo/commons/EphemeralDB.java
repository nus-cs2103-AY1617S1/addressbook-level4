package seedu.todo.commons;

import java.util.ArrayList;

import seedu.todo.models.Task;

/**
 * A bit like Redis, essentially a data store for things that should not be
 * persisted to disk, but should be shared between all modules.
 * 
 * All variables should be public. In-place modifications of variables are
 * encouraged.
 * 
 * @author louietyj
 */
public class EphemeralDB {
	
    private static EphemeralDB instance = null;
    
    protected EphemeralDB() {
        // Prevent instantiation.
    }

    public static EphemeralDB getInstance() {
        if (instance == null) {
            instance = new EphemeralDB();
        }
        return instance;
    }
    
    /** ======== DISPLAYED TASKS ======== **/
	
    public ArrayList<Task> displayedTasks;
    
    /**
     * Returns a Task from displayedTasks according to their displayed ID.
     * Their displayed ID is simply their index in the ArrayList + 1 (due to 0-indexing of ArrayLists).
     * 
     * @param id   Display ID of task. Bounded between 1 and the size of the ArrayList.
     * @return     Returns the Task at the specified display index.
     */
    public Task getTaskByDisplayedId(int id) {
    	if (id <= 0 || id > displayedTasks.size()) {
    		return null;
    	} else {
    		return displayedTasks.get(id - 1);
    	}
    }
    
    public int addToDisplayedTask(Task task) {
    	displayedTasks.add(task);
    	return displayedTasks.size();
    }

}
