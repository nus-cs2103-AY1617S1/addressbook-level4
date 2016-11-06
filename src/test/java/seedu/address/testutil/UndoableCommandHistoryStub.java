package seedu.address.testutil;

import seedu.address.history.UndoableCommandHistory;
import seedu.address.logic.commands.UndoableCommand;

//@@author A0093960X
public class UndoableCommandHistoryStub implements UndoableCommandHistory  {
    
    private boolean isEarliestCommand;
    private boolean isLatestCommand;
    
    public UndoableCommandHistoryStub() {
        isEarliestCommand = true;
        isLatestCommand = true;
    }

    @Override
    public void updateCommandHistory(UndoableCommand undoableCommand) {
        return;
    }

    @Override
    public boolean isEarliestCommand() {
        return isEarliestCommand;
    }

    @Override
    public boolean isLatestCommand() {
        return isLatestCommand;
    }
    

    @Override
    public UndoableCommand undoStep() {
        return null;
    }

    @Override
    public UndoableCommand redoStep() {
        return null;
    }
    
    public void setIsEarliestCommand(boolean isEarliestCommand) {
        this.isEarliestCommand = isEarliestCommand;
    }

    public void setIsLatestCommand(boolean isLatestCommand) {
        this.isLatestCommand = isLatestCommand;
    }

}
