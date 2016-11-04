package seedu.stask.model.undo;

import java.util.Stack;

import seedu.stask.model.task.ReadOnlyTask;

//@@author A0139145E
/*
 * Implements a circular linked list to store the UndoTasks (up to 3 actions)
 * using Last-In-First-Out (LIFO)
 */
public class UndoList {

    private int size;

    private Stack<UndoTask> undoStack;
    
    public UndoList(){
        this.size = 0;
        this.undoStack = new Stack<>();
    }

    /*
     * Adds a Undo action to the front of the stack.
     */
    public void add(String cmd, ReadOnlyTask postData, ReadOnlyTask preData){
        undoStack.push(new UndoTask(cmd, postData, preData));
        size++;
    }

    /**
     * Removes a Undo action to the front of the list.
     * @return UndoTask, or null if no actions to undo
     * 
     **/
    public UndoTask retrieve(){
        if (size == 0) {
            return null;
        }
        UndoTask toReturn = undoStack.pop();
        size--;
        return toReturn;
    }

    @Override
    public String toString(){
        return "UndoList has " + size + " undo tasks.";
    }

}
//@@author
