//@@author A0144919W
package seedu.tasklist.model;

import java.util.ArrayList;
import java.util.Collection;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.tasklist.model.task.Task;

/**
 * Stores the undo information for reverting to the previous state
 * Previous state of the task is stored
 * Also, the command that was performed on this task is stored using a command ID
 * For undoing setstorage, the original filepath is stored as a string in filePath
 */
public class UndoInfo {

    private int undo_ID;
    private String filePath;
    private ArrayList<Task> tasks;
    
    public UndoInfo(int undo_ID, String filePath, Task... tasks){
        this.undo_ID = undo_ID;
        Collection<Task> collection = Arrays.asList(tasks);
        this.filePath = filePath;
        this.tasks = new ArrayList<Task>(collection);
    }
    
    public int getUndoID(){
        return undo_ID;
    }
    
    public ArrayList<Task> getTasks(){
        return tasks;
    }
    
    public String getFilePath(){
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
