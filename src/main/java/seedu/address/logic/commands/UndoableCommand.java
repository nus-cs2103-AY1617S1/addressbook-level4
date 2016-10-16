package seedu.address.logic.commands;

public abstract class UndoableCommand extends Command {
    
    // Temporary fix, don't really like this.
    private boolean isRedo;
    
    /**
     * Undoes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult undo();   
    
    public void updateHistory(){
        
        // temporary fix, don't really like this.
        if (isRedo) {
            return;
        }
        
        history.updateCommandHistory(this);
        history.resetRedo();
        isRedo = true;
    }

}
