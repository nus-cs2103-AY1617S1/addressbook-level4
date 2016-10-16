package seedu.address.history;

import seedu.address.logic.commands.UndoableCommand;

public interface UndoableCommandHistory {

    public void updateCommandHistory(UndoableCommand undoableCommand);
    
    public boolean isEarliestCommand();
    
    public boolean isLatestCommand();
    
    public UndoableCommand undoStep();
    
    public UndoableCommand redoStep();
    
    public void resetRedo();
}
