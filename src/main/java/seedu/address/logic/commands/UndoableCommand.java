package seedu.address.logic.commands;

public abstract class UndoableCommand extends Command {
    
    /**
     * Undoes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult undo();   
    
    public void updateHistory(){
        history.updateCommandHistory(this);
        history.resetRedo();
    }

}
