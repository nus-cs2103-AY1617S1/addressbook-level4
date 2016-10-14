package seedu.tasklist.model;

import java.util.ArrayList;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.tasklist.model.task.ReadOnlyTask;

public class UndoInfo {

    private int undo_ID;
    private ArrayList<ReadOnlyTask> tasks;
    
    public UndoInfo(int undo_ID, ReadOnlyTask... tasks){
        this.undo_ID = undo_ID;
        this.tasks = (ArrayList<ReadOnlyTask>) Arrays.asList(tasks);
    }
    
    public int getUndoID(){
        return undo_ID;
    }
    
    public ArrayList<ReadOnlyTask> getTasks(){
        return tasks;
    }
    
}
