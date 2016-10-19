package seedu.tasklist.model;

import java.util.ArrayList;
import java.util.Collection;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.tasklist.model.task.Task;

public class UndoInfo {

    private int undo_ID;
    private ArrayList<Task> tasks;
    
    public UndoInfo(int undo_ID, Task... tasks){
        this.undo_ID = undo_ID;
        Collection<Task> collection = Arrays.asList(tasks);
        this.tasks = new ArrayList<Task>(collection);
    }
    
    public int getUndoID(){
        return undo_ID;
    }
    
    public ArrayList<Task> getTasks(){
        return tasks;
    }
    
}
