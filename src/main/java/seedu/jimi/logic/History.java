/**
 * 
 */
package seedu.jimi.logic;

import java.util.Stack;

import seedu.jimi.logic.commands.Command;

/**
 * History of the operations
 * This is a singleton class
 */
public final class History {

    private static History instance = null;
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();
    
    
    public void undo() {
        if(!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
        } else {
            throw new NoUndoableOperationException("Already earliest operation!");
        }
    }
    
    public void redo() {
        if(!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        } else {
            throw new NoRedoableOperationException("Already most recent operation!");
        }
    }
    
    public void execute(final Command cmd) {
        undoStack.push(cmd);
        redoStack.clear();
    }
    
    public static History getInstance() {
        if(History.instance == null) {
            History.instance = new History();
        }
        return History.instance;
    }

    public History() { };

}
