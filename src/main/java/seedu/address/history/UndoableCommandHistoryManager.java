package seedu.address.history;

import java.util.Stack;

import seedu.address.logic.commands.UndoableCommand;

/**
 * Stores the history of undoable and redoable commands for UndoCommand to use.
 * Also stores the history of user inputs for navigating previous and next user inputs using up and down arrow keys.
 */
public class UndoableCommandHistoryManager implements UndoableCommandHistory{
    
    private static UndoableCommandHistoryManager theUndoableCommandHistory;

    // command effects
    private Stack<UndoableCommand> undoableCommands;
    private Stack<UndoableCommand> redoableCommands;
    
    // Private constructor for Singleton Pattern
    private UndoableCommandHistoryManager(){
        undoableCommands = new Stack<UndoableCommand>();
        redoableCommands = new Stack<UndoableCommand>();
    }
    
    // Use Singleton Pattern here
    public static UndoableCommandHistoryManager getInstance(){
        if (theUndoableCommandHistory == null){
            theUndoableCommandHistory = new UndoableCommandHistoryManager();
        }
        return theUndoableCommandHistory;
    }
    
    // Methods dealing with undo and redo

    public void updateCommandHistory(UndoableCommand undoableCommand){
        assert undoableCommands != null;
        undoableCommands.push(undoableCommand);
    }
    
    public boolean isEarliestCommand(){
        assert undoableCommands != null;
        return undoableCommands.isEmpty();
    }
    
    public boolean isLatestCommand(){
        assert redoableCommands != null;
        return redoableCommands.isEmpty();
    }
    
    public UndoableCommand undoStep(){
        assert redoableCommands != null && undoableCommands != null;
        return redoableCommands.push(undoableCommands.pop());
    }
    
    public UndoableCommand redoStep(){
        assert redoableCommands != null && undoableCommands != null;
        return undoableCommands.push(redoableCommands.pop());
    }
    
    public void resetRedo(){
        // not sure if using clear() gives worse performance
        redoableCommands = new Stack<UndoableCommand>();
    }
    
}
